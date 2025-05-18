package org.nessus.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Az ImageReader osztály felelős a textúrák betöltéséért.
 * Statikus metódusokat biztosít a képek betöltéséhez az erőforrásokból.
 */
public class ImageReader {
    /**
     * Betölt egy képet a megadott textúra névvel.
     * A képet a /textures/ mappából tölti be, és .png kiterjesztést feltételez.
     * 
     * @param textureName A textúra neve kiterjesztés nélkül
     * @return BufferedImage - A betöltött kép, vagy null, ha a betöltés sikertelen
     */
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
