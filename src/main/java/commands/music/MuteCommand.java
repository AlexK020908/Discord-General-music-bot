package commands.music;


import managers.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.IOException;

public class MuteCommand implements ServerCommand {
    @Override
    public void performCommand(String[] arguments, Guild guild, Member member, TextChannel textChannel, Message message){
        if (!guild.getSelfMember().getVoiceState().isMuted()) {
            guild.getSelfMember().mute(true).queue();
            textChannel.sendMessage("I have been muted, please do **!unmute** to unmute").queue();

        } else {
            textChannel.sendMessage("I have already been muted").queue();
        }
    }
}
