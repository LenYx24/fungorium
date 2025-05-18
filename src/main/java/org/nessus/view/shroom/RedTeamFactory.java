package org.nessus.view.shroom;

import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.view.entities.ShroomBodyView;
import org.nessus.view.entities.ShroomThreadView;
import org.nessus.view.entities.SporeView;

import java.awt.*;

import static org.nessus.utility.ImageReader.GetImage;

/**
 * A piros csapat gomba nézeteinek factory osztálya.
 * A ShroomViewFactory leszármazottja, amely piros színű gomba nézeteket hoz létre.
 */
public class RedTeamFactory extends ShroomViewFactory {

    /**
     * Létrehoz egy új piros gombatest nézetet.
     * @param body A gombatest modell, amelyhez a nézetet létre kell hozni
     * @return A létrehozott piros gombatest nézet
     */
    @Override
    public ShroomBodyView CreateShroomBodyView(ShroomBody body) {
        return new ShroomBodyView(body, GetImage("red_shroombody"));
    }

    /**
     * Létrehoz egy új piros gombafonal nézetet.
     * @param shroomThread A gombafonal modell, amelyhez a nézetet létre kell hozni
     * @return A létrehozott piros gombafonal nézet
     */
    @Override
    public ShroomThreadView CreateShroomThreadView(ShroomThread shroomThread) {
        return new ShroomThreadView(shroomThread, Color.RED);
    }

    /**
     * Létrehoz egy új piros spóra nézetet.
     * @param spore A spóra modell, amelyhez a nézetet létre kell hozni
     * @return A létrehozott piros spóra nézet
     */
    @Override
    public SporeView CreateSporeView(Spore spore) {
        return new SporeView(spore, GetImage("red_spore"));
    }
}
