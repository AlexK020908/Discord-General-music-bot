package commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import commands.music.AudioPlayerSendHandler;

public class GuildMusicManager {
    public final AudioPlayerSendHandler audioPlayerSendHandler;
    public final AudioPlayer audioPlayer;
    public final TaskScheduler t1;



    public GuildMusicManager(AudioPlayerManager manager) {
        audioPlayer = manager.createPlayer();
        t1 = new TaskScheduler(audioPlayer);
        audioPlayer.addListener(t1);
        audioPlayerSendHandler =  new AudioPlayerSendHandler(audioPlayer);

    }

    public AudioPlayerSendHandler getAudioPlayerSendHandler() {
        return audioPlayerSendHandler;
    }

//this class creates a new audio player and it assigns them to both the audio player and assigning it to task scheduler and audio player send handler
    // we can use this in player manager

}
