package org.nessus.utility;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * BGM Player osztály, amely a háttérzene lejátszásáért felelős
 */
public class BGMPlayer {
    private Clip clip; // A zene

    /**
     * Konstruktor, paraméterként megadhatjuk neki a zenét
     * @param path - A zene elérési útvonala
     */
    public BGMPlayer(String path) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path));
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

