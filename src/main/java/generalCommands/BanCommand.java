package generalCommands;

import managers.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;

public class BanCommand implements ServerCommand {
    @Override
    public void performCommand(String[] arguments, Guild guild, Member member, TextChannel textChannel, Message message) {
        if (arguments.length == 2) {
            Member target = message.getMentionedMembers().get(0);
            if (target != null) {
                if (member.hasPermission(Permission.BAN_MEMBERS) && member.canInteract(target)) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("ban members");
                    embedBuilder.setThumbnail(target.getUser().getAvatarUrl());
                    embedBuilder.setDescription(target.getUser().getName() + " " + "has been banned: " + member.getUser().getAsMention());
                    textChannel.sendMessage(embedBuilder.build()).queue();
                    target.ban(0).queue();
                }

            } else {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(0xf64747);
                embedBuilder.setTitle("ban members");
                embedBuilder.setThumbnail(target.getUser().getAvatarUrl());
                embedBuilder.setDescription("you do not have permission to ban" + target.getUser().getAsMention());
                textChannel.sendMessage(embedBuilder.build()).queue();
            }
        }
    }
}
