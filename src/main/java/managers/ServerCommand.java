package managers;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.IOException;
import java.util.ArrayList;

public interface ServerCommand {

    // this is where we put our command method

    public void performCommand(String[] arguments, Guild guild, Member member, TextChannel textChannel, Message message) throws IOException;

}
