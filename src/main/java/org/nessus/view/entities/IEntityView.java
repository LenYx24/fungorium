package org.nessus.view.entities;

import java.awt.*;

import org.nessus.utility.EntitySelector;

/**
 * Entitás nézetek interfésze.
 * Minden játékban megjelenő entitás nézetének implementálnia kell ezt az interfészt.
 */
public interface IEntityView {
    /**
     * Az entitás kirajzolása a képernyőre.
     * @param g2d A grafikus kontextus, amire rajzolni kell
     */
    void Draw(Graphics2D g2d);
    
    /**
     * Az entitás rétegének lekérdezése a kirajzolási sorrendhez.
     * @return A réteg száma (magasabb érték = előrébb jelenik meg)
     */
    int GetLayer();
    
    /**
     * Ellenőrzi, hogy az adott pont az entitáson belül van-e.
     * @param x A pont x koordinátája
     * @param y A pont y koordinátája
     * @return Igaz, ha a pont az entitáson belül van
     */
    boolean ContainsPoint(int x, int y);
    
    /**
     * Ellenőrzi, hogy ez a nézet az adott objektumhoz tartozik-e.
     * @param obj Az ellenőrizendő objektum
     * @return Igaz, ha a nézet az adott objektumhoz tartozik
     */
    boolean IsViewing(Object obj);
    
    /**
     * Az entitás információinak lekérdezése szöveges formában.
     * @return Az entitás információi
     */
    String GetEntityInfo();
    
    /**
     * A Visitor tervezési minta Accept metódusa.
     * @param selector A látogató objektum
     */
    void Accept(EntitySelector selector);
    
    /**
     * Az entitás modelljének lekérdezése.
     * @return Az entitás modellje
     */
    Object GetModel();
    
    /**
     * Az entitás kijelölési állapotának beállítása.
     * @param selected Igaz, ha az entitás ki van jelölve
     */
    void SetSelected(boolean selected);
}
