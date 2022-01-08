package commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.Nullable;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class AudioPlayerSendHandler implements AudioSendHandler {
    private AudioPlayer audioPlayer; //lava player audio player
    private MutableAudioFrame frame; //this is what our audio is writing the audio to
    private ByteBuffer buffer; //byte buffer is used to store the bytes that is sent to discord


    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        buffer = ByteBuffer.allocate(2028); //how much lava player will provide every milisecond --> meant for good computer
        frame = new MutableAudioFrame();
        frame.setBuffer(buffer); //everything we write on frame will be written on buffer
    }



    @Override
    public boolean canProvide() {
        return audioPlayer.provide(frame); //write to the mutabable audio frame which writes to byte buffer
    }


    @Override
    public ByteBuffer provide20MsAudio() {
        final Buffer buf = ( (Buffer) buffer).flip();
        return (ByteBuffer) buf;
    }

    //it returns true as we do not need to re encode the audio, the lava players decodes it for us
    @Override
    public boolean isOpus() {
        return true;
    }
}
