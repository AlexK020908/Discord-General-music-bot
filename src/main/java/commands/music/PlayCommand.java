package commands.music;

import com.google.gson.Gson;
import commands.music.JoinCommand;

import managers.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.lang.management.MemoryManagerMXBean;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayCommand implements ServerCommand {

    @Override
    public void performCommand(String [] arguments, Guild guild, Member member, TextChannel textChannel, Message message) throws IOException {
        Member bot = guild.getSelfMember();
        StringBuilder s = new StringBuilder();
        for(String r : arguments) {
            s.append(r + " ");
        }
        String search = s.substring(5);


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
            textChannel.sendMessage("we are not even in the same channel").queue();
            return;
        }


        if (!isURL(search)){
            search = "ytsearch:" + search;
        }

        PlayerManager.getInstance().loadAndPlay(textChannel,
                search);

    }

    private boolean isURL(String search) {
        try {
            new URI(search);
            return true;
        } catch (URISyntaxException e) {
            return false;

        }
    }
}
