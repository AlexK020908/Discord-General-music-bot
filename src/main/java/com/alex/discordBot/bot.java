package com.alex.discordBot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.Timer;

public class bot extends ListenerAdapter{

        ///static becuase of static method
        private static JDABuilder jdaBuilder;
        private static JDA jda;

        public static void main(String[] args) {
            jdaBuilder = JDABuilder.createDefault("input key here").enableCache(CacheFlag.VOICE_STATE);
            jdaBuilder.setStatus(OnlineStatus.DO_NOT_DISTURB);
            jdaBuilder.setActivity(Activity.playing("music, !help to view help pannel"));
            jdaBuilder.addEventListeners(new bot());
            //need to enable intent to new user joining

            try {
                jda = jdaBuilder.build();
            } catch (LoginException e) {
                e.printStackTrace();
            }
            updateDescription();
            initializeCommands();

        }

        @Override
        public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
            String message = event.getMessage().getContentRaw(); //getting the content of the message as a string
            String[] arguments = event.getMessage().getContentRaw().split(" ");
            if(arguments[0].equals("!VALORANT")) {
                Member member = event.getMessage().getMentionedMembers().get(0);
                if (!member.getUser().isBot()) {
                    event.getChannel().sendMessage("Notifying " + member.getUser().getAsMention() + " to get on and hit diamond").queue();
                } else if (member.getUser().isBot()) {
                    event.getChannel().sendMessage("can not @ a bot bozo");
                }
            }

            if (message.equals("!private")) { //!private
                event.getChannel().sendMessage("message been sent").queue();
                event.getMember().getUser().openPrivateChannel().queue(privateChannel -> {
                    privateChannel.sendMessage("hi what can I help you with").queue();
                });

            }
            if (message.equals("!bot")) {
                event.getChannel().sendMessage("Hi my name is ben dover, I am your general bot for the server, my music brother is coming soon").queue();
                //all messages are sent in queue, or you can use complete to make it run before all others
            }

        }


        @Override
        public void onGuildMemberJoin( GuildMemberJoinEvent event) {
            if (!event.getMember().getUser().isBot()) {
                TextChannel textChannel = event.getGuild().getSystemChannel();
                if (textChannel != null) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setColor(0x9a12b3);
                    embedBuilder.setTitle("new member has joined");
                    embedBuilder.setDescription(event.getMember().getUser().getName() + " " + "has joined the server");
                    embedBuilder.setThumbnail(event.getMember().getUser().getAvatarUrl());

                    textChannel.sendMessage(embedBuilder.build()).queue();


                }
            }
        }

        public static JDA getJda() {
            return jda;
        }

        private static void updateDescription() {
            descriptionChanger d = new descriptionChanger();
            Timer timer = new Timer();
            timer.schedule(d, 0, 5000);
        }

        private static void initializeCommands() {
            CommandManager commandManager = new CommandManager();
            jda.addEventListener(commandManager);
        }



}
