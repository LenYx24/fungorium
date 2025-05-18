package org.nessus.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageReader {
    public static BufferedImage GetImage(String textureName) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(ImageReader.class.getResource("/textures/" + textureName + ".png"));
        } catch (IOException e) {
            System.err.println("Failed to load texture: " + textureName);
            e.printStackTrace();
        }

        return img;
    }
}
