package commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import managers.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class SkipCommand implements ServerCommand {
    @Override
    public void performCommand(String[] arguments, Guild guild, Member member, TextChannel textChannel, Message message) {

        Member bot = guild.getSelfMember();
        if (!bot.getVoiceState().inVoiceChannel()) {
            if(!member.getVoiceState().inVoiceChannel()) {
                textChannel.sendMessage("you are not in a voice channel").queue();
                return;
            }
        } else if(!member.getVoiceState().inVoiceChannel()) {
            textChannel.sendMessage("you are not in a voice channel").queue();
            return;
        } else if (!member.getVoiceState().getChannel().equals( bot.getVoiceState().getChannel())) {
            textChannel.sendMessage("we are not in the same channel").queue();
            return;
        }


        GuildMusicManager manager = PlayerManager.getInstance().getMusicManager(guild);
        AudioTrack track = manager.t1.getQueue().poll();
        manager.audioPlayer.startTrack(track, false);
        textChannel.sendMessage("skipping current song....").queue();
        textChannel.sendMessage("your queue length now is " + manager.t1.getQueue().size()).queue();
    }
}
