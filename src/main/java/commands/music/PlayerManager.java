package commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.music.GuildMusicManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;

    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;


    public PlayerManager() {
        musicManagers = new HashMap<>();
        audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager g = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(g.getAudioPlayerSendHandler());
            return g;

        });


    }



    public void loadAndPlay(TextChannel channel, String url) {
        //GET THE MUSIC manager
        GuildMusicManager manager =  this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(manager, url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                channel.sendMessage("adding to queue..... ")
                        .append(audioTrack.getInfo().title)
                        .append("by " + audioTrack.getInfo().author).queue();

                manager.t1.addTrackToQueue(audioTrack);

            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                List<AudioTrack> tracks = audioPlaylist.getTracks();
                AudioTrack firstResult = tracks.get(0);
                manager.t1.addTrackToQueue(firstResult);


                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(0x8c14fc);
                embedBuilder.setTitle("Notification");
                embedBuilder.setThumbnail(channel.getGuild().getIconUrl());
                String message = ("adding to queue: ") + (firstResult.getInfo().title)
                        + ("by" + " " + firstResult.getInfo().author);
                String message2 = " queue length is now: " + manager.t1.getQueue().size();
                embedBuilder.setDescription(message +
                        "\n" + message2);
                channel.sendMessage(embedBuilder.build()).queue();


                channel.sendMessage(firstResult.getInfo().uri).queue();


            }
            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException e) {

            }
        });

    }
    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }
}
