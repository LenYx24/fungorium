package org.nessus.view.entities;

import java.awt.*;

import org.nessus.utility.EntitySelector;

public interface IEntityView {
    void Draw(Graphics2D g2d);
    int GetLayer();
    boolean ContainsPoint(int x, int y);
    boolean IsViewing(Object obj);
    String GetEntityInfo();
    void Accept(EntitySelector selector);
    Object GetModel();
    void SetSelected(boolean selected);
}
