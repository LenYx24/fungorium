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
 * A BlueTeamFactory osztály a kék csapat gombáinak megjelenítéséért felelős.
 * A ShroomViewFactory absztrakt osztály leszármazottja, amely implementálja a gomba nézetek létrehozásának módszereit.
 */
public class BlueTeamFactory extends ShroomViewFactory {

    /**
     * Létrehoz egy új ShroomBodyView objektumot a megadott ShroomBody modell alapján.
     * A kék csapat gombatesteihez kék textúrát használ.
     * 
     * @param body A ShroomBody modell, amelyhez a nézetet létre kell hozni
     * @return ShroomBodyView - Az újonnan létrehozott gombatest nézet
     */
    @Override
    public ShroomBodyView CreateShroomBodyView(ShroomBody body) {
        return new ShroomBodyView(body, GetImage("blue_shroombody"));
    }

    /**
     * Létrehoz egy új ShroomThreadView objektumot a megadott ShroomThread modell alapján.
     * A kék csapat gombafonalaihoz kék színt használ.
     * 
     * @param shroomThread A ShroomThread modell, amelyhez a nézetet létre kell hozni
     * @return ShroomThreadView - Az újonnan létrehozott gombafonal nézet
     */
    @Override
    public ShroomThreadView CreateShroomThreadView(ShroomThread shroomThread) {
        return new ShroomThreadView(shroomThread, Color.BLUE);
    }

    /**
     * Létrehoz egy új SporeView objektumot a megadott Spore modell alapján.
     * A kék csapat spóráihoz kék textúrát használ.
     * 
     * @param spore A Spore modell, amelyhez a nézetet létre kell hozni
     * @return SporeView - Az újonnan létrehozott spóra nézet
     */
    @Override
    public SporeView CreateSporeView(Spore spore) {
        return new SporeView(spore, GetImage("blue_spore"));
    }
}
