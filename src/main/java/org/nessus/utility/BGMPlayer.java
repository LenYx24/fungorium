package org.nessus.utility;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class BGMPlayer {
    private Clip clip; // A zene

    /**
     * Konstruktor, amely URL-ből olvassa a zenét
     * @param audioUrl - A zene elérési URL-je
     */
    public BGMPlayer(URL audioUrl) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioUrl);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Zene lejátszási ciklus, folyamatosan játssza a zenét
     */
    public void playLoop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }
}