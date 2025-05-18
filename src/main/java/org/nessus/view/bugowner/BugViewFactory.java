package org.nessus.view.bugowner;

import org.nessus.model.bug.Bug;
import org.nessus.model.bug.BugOwner;
import org.nessus.view.entities.BugView;

/**
 * Absztrakt factory osztály a rovar nézetek létrehozásához.
 * A Factory tervezési minta implementációja, amely lehetővé teszi
 * különböző megjelenésű rovar nézetek létrehozását.
 */
public abstract class BugViewFactory {
    /**
     * A rovar tulajdonosa, akihez a factory tartozik.
     */
    protected BugOwner bugOwner;
    
    /**
     * Létrehoz egy új rovar nézetet a megadott rovar modellhez.
     * @param bug A rovar modell, amelyhez a nézetet létre kell hozni
     * @return A létrehozott rovar nézet
     */
    public abstract BugView CreateBugView(Bug bug);
}
