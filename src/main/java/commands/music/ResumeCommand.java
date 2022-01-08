package commands.music;

import managers.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ResumeCommand implements ServerCommand {
    @Override
    public void performCommand(String[] arguments, Guild guild, Member member, TextChannel textChannel, Message message){

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
        manager.t1.onPlayerResume(manager.audioPlayer);
        textChannel.sendMessage("resuming music...").queue();


    }
}
