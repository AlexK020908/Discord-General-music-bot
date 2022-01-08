package generalCommands;

import managers.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class UnbanCommand implements ServerCommand {
    @Override
    public void performCommand(String[] arguments, Guild guild, Member member, TextChannel textChannel, Message message) {
        if (arguments.length == 2) {
            String id = arguments[1];
            if (id != null) {
                guild.unban(id).queue();
            }
            //unban
        } else {
            textChannel.sendMessage("please use unban command correctly");
        }
    }
}
