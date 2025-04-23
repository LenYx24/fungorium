package org.nessus.model.tecton;

import org.nessus.View;

import org.nessus.model.*;
import org.nessus.model.shroom.ShroomThread;

import java.util.*;

/**
 * A sivatagi tectonokat reprezentáló osztály.
 * A sivatagi tectonok a shroomThreadeket 2 körönként elpusztítják.
 * A shroomThreadek elpusztításakor a shroomThreadeket tartalmazó shroomokat is el kell távolítani.
 * A shroomThreadeket tartalmazó tektonokból is el kell távolítani a shroomThreadeket.
 * 
 * @see org.nessus.model.tecton.Tecton
 */
public class DesertTecton extends Tecton {
    private HashMap<ShroomThread, Integer> decayTimers; // A shroomThreadek elpusztítására szolgáló időzítő.

    /**
     * Az osztály konstruktora.
     * Az osztály inicializálja a decayTimers-t.
     */
    public DesertTecton() {
        decayTimers = new HashMap<>();
    }

    /**
     * Az osztály másoló konstruktora.
     * @return Az osztály másolata.
     */
    @Override
    public Tecton Copy() {
        Tecton copyTecton = new DesertTecton();
        View.AddObject( "copyTecton", copyTecton);
        SpreadEntities(copyTecton);
        return copyTecton;
    }

    /**
     * ShroomThread növesztése a tektonon.
     * A shroomThreadet hozzáadja a shroomThreadek listájához és beállítja a decayTimer-t 2-re.
     * @param thread
     * @return boolean - sikerült-e a shroomThread növesztése.
     */
    @Override
    public boolean GrowShroomThread(ShroomThread thread) {
        this.shroomThreads.add(thread);
        this.decayTimers.put(thread, 2);
        return true;
    }

    /**
     * A tekton frissítése.
     * A shroomThreadeket ellenőrzi, hogy elérte-e a 0-át a decayTimer.
     * Ha igen, akkor a shroomThreadet és a shroomThreadeket tartalmazó shroomokat és tektonokat is el kell távolítani.
     * @see org.nessus.model.shroom.ShroomThread
     * @see org.nessus.model.shroom.Shroom
     * @see org.nessus.model.tecton.Tecton
     */
    @Override
    public void UpdateTecton() {
        for (ShroomThread thread : List.copyOf(shroomThreads)) {
            var decayTimer = decayTimers.get(thread);

            if (decayTimer <= 0) {
                thread.Remove();
                thread.GetTecton1().RemoveShroomThread(thread);
                thread.GetTecton2().RemoveShroomThread(thread);
                thread.GetShroom().RemoveShroomThread(thread);

                decayTimers.remove(thread);
            } else {
                decayTimers.replace(thread, decayTimer - 1);
            }
        }
    }
}
