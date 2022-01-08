package com.alex.discordBot;

import commands.music.*;
import generalCommands.BanCommand;
import generalCommands.HelpCommand;
import generalCommands.KickCommand;
import generalCommands.UnbanCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


import java.io.IOException;

//this class is where we put all the availiable commands
public class CommandManager extends ListenerAdapter {

    private final HelpCommand helpCommand;
    private final KickCommand kickCommand;
    private final BanCommand banCommand;
    private final UnbanCommand unbanCommand;
    private final PlayCommand playCommand;
    private final MuteCommand muteCommand;
    private final UnmuteCommand unmuteCommand;
    private final StopCommand stopCommand;
    private final ResumeCommand resumeCommand;
    private final SkipCommand skipCommand;
    private final ClearCommand clearCommand;
    private final LeaveCommand leaveCommand1;
    private final JoinCommand joinCommand;
    private final RepeatCommand repeatCommand;
    private final repeatingTimesCommand repeatingTimesCommand;
    private final showQueueCommand showQueueCommand;


    public CommandManager() {
        helpCommand = new HelpCommand();
        kickCommand = new KickCommand();
        banCommand = new BanCommand();
        unbanCommand = new UnbanCommand();
        playCommand = new PlayCommand();
        muteCommand = new MuteCommand();
        unmuteCommand = new UnmuteCommand();
        stopCommand = new StopCommand();
        resumeCommand = new ResumeCommand();
        skipCommand = new SkipCommand();
        clearCommand = new ClearCommand();
        leaveCommand1 = new LeaveCommand();
        joinCommand = new JoinCommand();
        repeatCommand = new RepeatCommand();
        repeatingTimesCommand = new repeatingTimesCommand();
        showQueueCommand = new showQueueCommand();
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(!event.getMember().getUser().isBot()) {
            String[] arguments = event.getMessage().getContentRaw().split(" ");
            Guild guild = event.getGuild();
            Member member = event.getMember();
            TextChannel textChannel = event.getChannel();
            Message message = event.getMessage();

            switch(arguments[0]) {
                case "!queue":
                    showQueueCommand.performCommand(arguments, guild, member, textChannel, message);
                    break;

                case "!repeating":
                    repeatingTimesCommand.performCommand(arguments, guild, member, textChannel, message);
                    break;

                case "!repeat":
                    repeatCommand.performCommand(arguments, guild, member, textChannel, message);
                    break;
                case "!join":
                    joinCommand.performCommand(arguments, guild,member, textChannel, message);
                    break;
                case "!leave":
                    leaveCommand1.performCommand(arguments, guild,member, textChannel, message);
                    break;
                case "!clear":
                    clearCommand.performCommand(arguments, guild,member, textChannel, message);
                    break;
                case "!skip":
                    skipCommand.performCommand(arguments, guild,member, textChannel, message);
                    break;
                case "!resume":
                    resumeCommand.performCommand(arguments, guild,member, textChannel, message);
                    break;

                case "!pause" :
                    stopCommand.performCommand(arguments, guild,member, textChannel, message);
                    break;

                case "!unmute":
                    unmuteCommand.performCommand(arguments, guild,member, textChannel, message);
                    break;
                case "!mute":
                    muteCommand.performCommand(arguments, guild,member, textChannel, message);
                    break;
                case "!play":
                    try {
                        playCommand.performCommand(arguments, guild,member, textChannel, message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;


                case "!unban":
                    unbanCommand.performCommand(arguments, guild,member, textChannel, message);
                    break;

                case "!ban":
                    banCommand.performCommand(arguments, guild, member, textChannel, message);
                    break;

                case "!kick":
                    kickCommand.performCommand(arguments, guild, member, textChannel, message);
                    break;

                case "!help":
                    helpCommand.performCommand(arguments, guild, member, textChannel, message);
                    break;

            }

        }
    }

}
