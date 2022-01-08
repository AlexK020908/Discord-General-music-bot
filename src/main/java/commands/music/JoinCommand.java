package commands.music;


import managers.ServerCommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ServerCommand {
    @Override
    public void performCommand(String[] arguments, Guild guild, Member member, TextChannel textChannel, Message message) {
        Member bot = guild.getSelfMember();
        GuildVoiceState selfVoiceState = bot.getVoiceState();

        if(selfVoiceState.inVoiceChannel()) {
            textChannel.sendMessage("I am already in a channel").queue();
            return;
        } else if (member.getVoiceState().inVoiceChannel()) {
            AudioManager audioManagerForGuild = guild.getAudioManager();
            VoiceChannel voiceChannel = member.getVoiceState().getChannel();
            if (bot.hasPermission()) {
                audioManagerForGuild.openAudioConnection(voiceChannel);
                textChannel.sendMessage("joining...").queue();
                return;
            }
        } else {
            textChannel.sendMessage("you need to be in a channel bozo").queue();
            return;
        }
    }
    // this is my discord project
}
