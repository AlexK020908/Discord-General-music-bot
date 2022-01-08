package generalCommands;


import managers.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class HelpCommand implements ServerCommand {
    @Override
    public void performCommand(String[] arguments, Guild guild, Member member, TextChannel textChannel, Message message) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(0xc8f7c5);
        embedBuilder.setThumbnail(member.getUser().getAvatarUrl());
        embedBuilder.setDescription("**!bot** - to view info about bot\n" +
                "\n**!VALORANT @user** to notify users to hop on and play valorant." +
                "\n**!kick @user** to kick user" +
                "\n**!ban @user** to ban user" +
                "\n **!play song name**" +
                "\n **!stop** to stop playing the current song" +
                "\n **!resume** to resume the paused song" +
                "\n **!mute** to mute me" +
                "\n **!unmute** to unmute me" +
                "\n **!clear** to clear all the queues" +
                "\n **!leave** to make me leave the server" +
                "\n **!join** to call me back into the server" +
                "\n **!repeating <number of times>** to repeat current song this number of times" +
                "\n **!queue** to print out all the songs ");
        embedBuilder.setTitle("Helper menu");

        textChannel.sendMessage(embedBuilder.build()).queue();

    }
}
