package commands.music;

import managers.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashSet;
import java.util.Set;

public class repeatingTimesCommand implements ServerCommand {
    @Override
    public void performCommand(String[] arguments, Guild guild, Member member, TextChannel textChannel, Message message)  {
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

        Set<Character> set = new HashSet<>();
        set.clear();

        GuildMusicManager manager = PlayerManager.getInstance().getMusicManager(guild);
        String s = arguments[1];
        Integer numOfTimes = Integer.parseInt(s);
        manager.t1.setTimes(numOfTimes);
        manager.t1.setIsRepeating(true);
        textChannel.sendMessage("now repeating current song" + " " + numOfTimes + " time/s" ).queue();
    }
}
