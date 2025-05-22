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
 * A zöld csapat gomba nézeteik factory osztálya.
 * A ShroomViewFactory leszármazottja, amely zöld színű gomba nézeteket hoz létre.
 */
public class GreenTeamFactory extends ShroomViewFactory {

    /**
     * Létrehoz egy új zöld gombatest nézetet.
     * @param body A gombatest modell, amelyhez a nézetet létre kell hozni
     * @return A létrehozott zöld gombatest nézet
     */
    @Override
    public ShroomBodyView CreateShroomBodyView(ShroomBody body) {
        return new ShroomBodyView(body, GetImage("green_shroombody"));
    }

    /**
     * Létrehoz egy új zöld gombafonal nézetet.
     * @param shroomThread A gombafonal modell, amelyhez a nézetet létre kell hozni
     * @return A létrehozott zöld gombafonal nézet
     */
    @Override
    public ShroomThreadView CreateShroomThreadView(ShroomThread shroomThread) {
        return new ShroomThreadView(shroomThread, Color.GREEN);
    }

    /**
     * Létrehoz egy új zöld spóra nézetet.
     * @param spore A spóra modell, amelyhez a nézetet létre kell hozni
     * @return A létrehozott zöld spóra nézet
     */
    @Override
    public SporeView CreateSporeView(Spore spore) {
        return new SporeView(spore, GetImage("green_spore"));
    }
}
