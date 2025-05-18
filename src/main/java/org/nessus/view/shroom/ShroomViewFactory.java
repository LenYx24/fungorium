package org.nessus.view.shroom;

import org.nessus.model.shroom.Shroom;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.view.entities.ShroomBodyView;
import org.nessus.view.entities.ShroomThreadView;
import org.nessus.view.entities.SporeView;

/**
 * Absztrakt factory osztály a gomba nézetek létrehozásához.
 * A Factory tervezési minta implementációja, amely lehetővé teszi
 * különböző megjelenésű gomba nézetek létrehozását.
 */
public abstract class ShroomViewFactory {
    /**
     * A gomba, amelyhez a factory tartozik.
     */
    protected Shroom shroom;
    
    /**
     * Létrehoz egy új gombatest nézetet a megadott gombatest modellhez.
     * @param body A gombatest modell, amelyhez a nézetet létre kell hozni
     * @return A létrehozott gombatest nézet
     */
    public abstract ShroomBodyView CreateShroomBodyView(ShroomBody body);
    
    /**
     * Létrehoz egy új gombafonal nézetet a megadott gombafonal modellhez.
     * @param shroomThread A gombafonal modell, amelyhez a nézetet létre kell hozni
     * @return A létrehozott gombafonal nézet
     */
    public abstract ShroomThreadView CreateShroomThreadView(ShroomThread shroomThread);
    
    /**
     * Létrehoz egy új spóra nézetet a megadott spóra modellhez.
     * @param spore A spóra modell, amelyhez a nézetet létre kell hozni
     * @return A létrehozott spóra nézet
     */
    public abstract SporeView CreateSporeView(Spore spore);
}
