package org.nessus.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageReader {
    public static BufferedImage GetImage(String textureName){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("src/main/resources/textures/" + textureName + ".png"));
        }catch(IOException e){
            System.err.println("File was not found");
        }
        return img;
    }
}
