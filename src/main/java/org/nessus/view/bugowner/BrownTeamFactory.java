package org.nessus.view.bugowner;

import org.nessus.model.bug.Bug;
import org.nessus.view.entities.BugView;

import static org.nessus.utility.ImageReader.GetImage;

/**
 * A BrownTeamFactory osztály a barna csapat rovarainak megjelenítéséért felelős.
 * A BugViewFactory absztrakt osztály leszármazottja, amely implementálja a rovar nézetek létrehozásának módszerét.
 */
public class BrownTeamFactory extends BugViewFactory {
    /**
     * Létrehoz egy új BugView objektumot a megadott Bug modell alapján.
     * A barna csapat rovaraihoz barna textúrát használ.
     * 
     * @param bug A Bug modell, amelyhez a nézetet létre kell hozni
     * @return BugView - Az újonnan létrehozott rovar nézet
     */
    @Override
    public BugView CreateBugView(Bug bug) {
        return new BugView(bug, GetImage("brown_bug"));
    }
}
