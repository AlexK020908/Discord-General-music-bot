package generalCommands;

import managers.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.TimeoutException;

import static net.dv8tion.jda.api.Permission.KICK_MEMBERS;

public class KickCommand implements ServerCommand {
    @Override
    public void performCommand(String[] arguments, Guild guild, Member member, TextChannel textChannel, Message message) {
        //kick @user
        if(arguments.length == 2) {

            Member target = message.getMentionedMembers().get(0);
            if (!member.canInteract(target) || !member.hasPermission(KICK_MEMBERS)) {
                textChannel.sendMessage("can not kick member becuase you have no permission to do so").queue();
            } else if((!guild.getSelfMember().canInteract(target) || !guild.getSelfMember().hasPermission(KICK_MEMBERS))) {
                textChannel.sendMessage("I can not kick member").queue();
            } else {
                guild.kick(target).queue(
                        (__) -> textChannel.sendMessage("kick was succesful").queue());
            }


        } else {
            textChannel.sendMessage("please recheck your format").queue();
        }
    }

}
