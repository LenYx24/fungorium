package org.nessus.view.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Absztrakt osztály a sprite-alapú entitás nézetek számára.
 * Alapvető funkcionalitást biztosít a képekkel megjelenített entitások számára.
 */
public abstract class EntitySpriteView implements IEntityView{
    /**
     * Az entitás x koordinátája a képernyőn.
     */
    protected double x;
    
    /**
     * Az entitás y koordinátája a képernyőn.
     */
    protected double y;
    
    /**
     * Az entitás mérete pixelben.
     */
    protected int size = 100;
    
    /**
     * A kijelölési keret vastagsága.
     */
    protected int padding = 2;

    /**
     * Az entitás képe.
     */
    protected BufferedImage image;
    
    /**
     * Az entitás kijelölési állapota.
     */
    protected boolean selection = false;

    /**
     * Az entitás x koordinátájának lekérdezése.
     * @return Az x koordináta
     */
    public double GetX(){
        return x;
    }

    /**
     * Az entitás y koordinátájának lekérdezése.
     * @return Az y koordináta
     */
    public double GetY(){
        return y;
    }

    /**
     * Az entitás x koordinátájának beállítása.
     * @param x Az új x koordináta
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Az entitás y koordinátájának beállítása.
     * @param y Az új y koordináta
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Az entitás sprite-jának kirajzolása.
     * Ha az entitás ki van jelölve, piros keretet rajzol köré.
     * @param g2d A grafikus kontextus
     */
    protected void DrawSprite(Graphics2D g2d){
        int _x = (int)x - size/2;
        int _y = (int)y - size/2;
        
        if(selection) {
            g2d.setStroke(new BasicStroke(padding));
            g2d.setColor(new Color(255,0,0));
            g2d.drawRect(_x - padding,_y - padding,size + 2*padding,size + 2*padding);
        }

        g2d.drawImage(image, _x, _y,size,size,null);
    }
    
    /**
     * Ellenőrzi, hogy az adott pont az entitáson belül van-e.
     * @param x A pont x koordinátája
     * @param y A pont y koordinátája
     * @return Igaz, ha a pont az entitáson belül van
     */
    public boolean ContainsPoint(int x, int y) {
        int tX = (int)GetX();
        int tY = (int)GetY();

        int topLeftX = tX - size / 2;
        int topLeftY = tY - size / 2;

        boolean boundCheckX = x >= topLeftX && x <= topLeftX + size;
        boolean boundCheckY = y >= topLeftY && y <= topLeftY + size;
        
        if (boundCheckX && boundCheckY) {
            System.out.println("HIT: " + this);
        }

        return boundCheckX && boundCheckY;
    }

    /**
     * Az entitás kijelölési állapotának beállítása.
     * @param selected Igaz, ha az entitás ki van jelölve
     */
    public void SetSelected(boolean selected){
        selection = selected;
    }
}
