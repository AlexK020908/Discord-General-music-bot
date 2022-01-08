package commands.music;

import commands.music.GuildMusicManager;

import managers.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class LeaveCommand implements ServerCommand {
    @Override
    public void performCommand(String[] arguments, Guild guild, Member member, TextChannel textChannel, Message message)  {
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

        guild.getAudioManager().closeAudioConnection();
        textChannel.sendMessage("I am leaving for now good bye, you can call me back with **!join** anytime").queue();
        GuildMusicManager manager = PlayerManager.getInstance().getMusicManager(guild);
        manager.t1.getQueue().clear();
    }
}
