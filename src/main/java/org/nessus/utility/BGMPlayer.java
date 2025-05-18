package org.nessus.utility;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BGMPlayer
{
    private Clip clip;

    public BGMPlayer(String path)
    {
        try
        {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(audioStream);

        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }

    public void playLoop()
    {
        if (clip != null)
        {
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loops the music
            clip.start();
        }
    }
}

