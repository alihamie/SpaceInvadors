import javax.sound.sampled.*;
import java.net.URL;

public enum Sounds {
    SHOOT("/audio/shoot.wav"),
    EXPLOSION("/audio/explosion.wav"),
    INVADER_KILLED("/audio/invader_killed.wav"),
    INVADERS_0("/audio/invaders_0.wav"),
    INVADERS_1("/audio/invaders_1.wav"),
    INVADERS_2("/audio/invaders_2.wav"),
    INVADERS_3("/audio/invaders_3.wav"),
    UFO_LOOP("/audio/ufo_highpitch.wav"),
    UFO_DESTROYED("/audio/ufo_lowpitch.wav");;

    private Clip clip;

    Sounds(String fileName) {
        try {
            URL url = Sounds.class.getResource(fileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            System.out.println("Unable to load audio file");
            e.printStackTrace();
        }
    }

    public void setVolume(float volume) {
        FloatControl gain_control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gain_control.setValue(volume);
    }

    public void play() {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }

    public void play(float volume) {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        setVolume(volume);
        clip.start();
    }

    public void loop() {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void loop(float volume) {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        setVolume(volume);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public static void toggleMuteAll() {
        Sounds[] sounds = values();
        for (int i = 0; i < sounds.length; i++) {
            BooleanControl mute_control = (BooleanControl) sounds[i].clip.getControl(BooleanControl.Type.MUTE);
            mute_control.setValue(!mute_control.getValue());
        }

    }

    public static void init() {
        values();
    }
}
