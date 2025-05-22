package org.nessus.utility;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class BGMPlayer {
    private Clip clip; // A zene
    private boolean audioAvailable = true; // Jelzi, hogy elérhető-e a hangeszköz

    /**
     * Konstruktor, amely URL-ből olvassa a zenét
     * @param audioUrl - A zene elérési URL-je
     */
    public BGMPlayer(URL audioUrl) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioUrl);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException e) {
            System.err.println("Hiba a hangfájl betöltésekor: " + e.getMessage());
            audioAvailable = false;
        } catch (LineUnavailableException e) {
            System.err.println("Nem található hangeszköz: " + e.getMessage());
            audioAvailable = false;
        } catch (Exception e) {
            System.err.println("Ismeretlen hiba a hang lejátszásakor: " + e.getMessage());
            audioAvailable = false;
        }
    }

    /**
     * Zene lejátszási ciklus, folyamatosan játssza a zenét
     */
    public void playLoop() {
        if (clip != null && audioAvailable) {
            try {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (Exception e) {
                System.err.println("Hiba a zene lejátszásakor: " + e.getMessage());
                audioAvailable = false;
            }
        }
    }
}