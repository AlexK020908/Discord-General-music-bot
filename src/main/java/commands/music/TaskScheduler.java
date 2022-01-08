package commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import sun.awt.image.ImageWatched;


import javax.print.DocFlavor;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

//this class gives us all the info about lava player/
public class TaskScheduler extends AudioEventAdapter {
    private AudioPlayer audioPlayer;
    private Queue<com.sedmelluq.discord.lavaplayer.track.AudioTrack> queue;
    private Boolean isRepeating;
    private int times = 0;


    //this is called when currently playing track ends
    public TaskScheduler(AudioPlayer player) {
        audioPlayer = player;
        queue = new LinkedBlockingDeque<>();
        isRepeating = false;

    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        player.setPaused(true);
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        player.setPaused(false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, com.sedmelluq.discord.lavaplayer.track.AudioTrack track, AudioTrackEndReason endReason) {

        if (endReason.mayStartNext) {
            if(isRepeating) {
                if(times == 0) {
                    audioPlayer.startTrack(track.makeClone(), false);
                    return;
                } else if (times > 0) {
                    while (times > 0) {
                        audioPlayer.startTrack(track.makeClone(), false);
                        times--;
                    }
                    isRepeating = false;
                    return;
                }
            } else {
                audioPlayer.startTrack(queue.poll(), false);
            }
        }
    }

    @Override
    //start the player and play the track
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        if (audioPlayer.isPaused()) {
            audioPlayer.startTrack(track, false);
        }

    }
    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        // Audio track has been unable to provide us any audio, might want to just start a new track
        if (audioPlayer.isPaused()) {
            audioPlayer.playTrack(queue.poll());
        }
    }

    //EFFECT: adding tracks to queue, this could be where the problem is at
    public void addTrackToQueue(AudioTrack track) {
        if(!this.audioPlayer.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    public Queue<com.sedmelluq.discord.lavaplayer.track.AudioTrack> getQueue() {
        return queue;
    }

    public void setIsRepeating(Boolean repeat) {
        this.isRepeating = repeat;
    }

    public void setTimes(Integer i) {
        this.times = i;
    }

}
