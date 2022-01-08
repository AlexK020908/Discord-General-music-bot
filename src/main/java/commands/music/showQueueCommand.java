package commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import managers.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.Queue;

public class showQueueCommand implements ServerCommand {
    @Override
    public void performCommand(String[] arguments, Guild guild, Member member, TextChannel textChannel, Message message) {
        Member bot = guild.getSelfMember();
        if (!bot.getVoiceState().inVoiceChannel()) {
            if(!member.getVoiceState().inVoiceChannel()) {
                textChannel.sendMessage("you are not in a voice channel").queue();
                return;
            } else {

                textChannel.sendMessage("not in a voice channel, connecting to channel").queue();
                JoinCommand joinCommand = new JoinCommand();
                joinCommand.performCommand(arguments, guild, member, textChannel, message);
            }
        } else if(!member.getVoiceState().inVoiceChannel()) {
            textChannel.sendMessage("you are not in a voice channel").queue();
            return;
        } else if (!member.getVoiceState().getChannel().equals( bot.getVoiceState().getChannel())) {
            textChannel.sendMessage("we are not in the same channel").queue();
            return;
        }


        GuildMusicManager manager = PlayerManager.getInstance().getMusicManager(guild);
        StringBuilder stringBuilder = new StringBuilder();
        Queue<AudioTrack> queue = manager.t1.getQueue();
        int count = 0;
        for (AudioTrack a : queue) {
            count++;
            stringBuilder.append("\n " + count + ". " + a.getInfo().title + " by: " + a.getInfo().author);
        }
        if (queue.isEmpty()) {
            textChannel.sendMessage("there is not song in the queue right now").queue();




        } else {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.PINK);
            embedBuilder.setTitle("QUEUE LIST");
            embedBuilder.setDescription(stringBuilder.toString());
            textChannel.sendMessage(embedBuilder.build()).queue();
        }
    }

}
