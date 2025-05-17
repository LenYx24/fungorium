package org.nessus.model.effect;

import org.nessus.model.bug.Bug;
import org.nessus.utility.EffectInfoReader;

/**
 * Az osztály a "felosztás" hatását reprezentálja.
 * A "felosztás" hatására a rovarok felosztják magukat.
 * A hatás három alkalommal alkalmazható.
 * A {@link BugEffect} osztályból származik le.
 * @see org.nessus.model.bug.Bug
 * @see org.nessus.model.effect.BugEffect
 */
public class DivisionEffect extends BugEffect {
    /**
     * Az effekt inicializálása, a hátralévő használatok számának beállítása 0-ra.
     */
    public DivisionEffect() {
        remainingUses = 0;
    }

    /**
     * A hatás alkalmazása a rovarokra.
     * @param bug - A rovar, akire a hatást alkalmazzuk.
     * @return void
     */
    public void ApplyOn(Bug bug) {
        bug.ClearEffect(this);
        bug.Split();
    }

    @Override
    public void Accept(EffectInfoReader reader) {
        reader.Visit(this);
    }
}
