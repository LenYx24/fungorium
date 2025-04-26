package org.nessus.model.shroom;

import java.util.*;

import org.nessus.model.tecton.Tecton;
import org.nessus.view.View;

/**
 * A gombatestet reprezentáló osztály.
 * A gombatest a gombák egyik fontos része, amely a spórák termeléséért felelős.
 * A {@link Shroom} osztályból származik le.
 * @see org.nessus.model.shroom.Shroom
 * @see org.nessus.model.tecton.Tecton
 * @see org.nessus.model.shroom.Spore
 */
public class ShroomBody {
    private Shroom shroom; // a gombatesthez tartozó gomba
    private Tecton tecton = null; // a gombatesthez tartozó tekton

    private int level = 1; // a gombatest szintje
    private int remainingThrows = 5; // a hátralévő dobások száma
    private int sporeMaterials = 0; // a spórákhoz szükséges anyagok mennyisége

    /**
     * Az osztály konstruktora.
     * @param shroom
     * @param tecton
     */
    public ShroomBody(Shroom shroom, Tecton tecton) {
        this.shroom = shroom;
        this.tecton = tecton;
    }

    /**
     * A gombatest tektonjának lekérdezése
     * @return A gombatest tektonja
     */
    public Tecton GetTecton() {
        return tecton;
    }

    /**
     * A gombatest gombájának lekérdezése
     * @return A gombatest gombája
     */
    public Shroom GetShroom() {
        return shroom;
    }

    /**
     * A gombatest fejlesztése
     * @return void
     */
    public void Upgrade() {
        level++;
    }

    /**
     * A céltekton távolságának ellenőrzése
     * @param tecton - Az ellenőrizendő tekton
     * @return boolean
     */
    public boolean InRange(Tecton targetTecton) {
        Tecton startTecton = this.tecton;
        int maxDistance = this.level;

        Set<Tecton> visited = new HashSet<>();
        Queue<Map.Entry<Tecton, Integer>> queue = new LinkedList<>();

        queue.add(new AbstractMap.SimpleEntry<>(startTecton, 0));
        visited.add(startTecton);

        while (!queue.isEmpty()) {
            Map.Entry<Tecton, Integer> current = queue.poll();
            Tecton currentTecton = current.getKey();
            int distance = current.getValue();

            if (currentTecton == targetTecton) {
                return true;
            }

            if (distance < maxDistance) {
                for (Tecton neighbour : currentTecton.GetNeighbours()) {
                    if (!visited.contains(neighbour)) {
                        visited.add(neighbour);
                        queue.add(new AbstractMap.SimpleEntry<>(neighbour, distance + 1));
                    }
                }
            }
        }

        return false;
    }

    /**
     * Spóra kialakítása
     * @param tecton - Az a tekton, amelyre egy új spórt akarunk dobni
     * @return Spore - A kialakított spóra
     */
    public Spore FormSpore(Tecton tecton) {
        if (sporeMaterials <= 2 || !InRange(tecton))
            return null;

        sporeMaterials -= 2;
        remainingThrows--;

        Spore spore = new Spore(shroom, tecton);
        View.GetObjectStore().AddObject( "spore",spore);
        tecton.ThrowSpore(spore);

        if (remainingThrows <= 0) {
            tecton.ClearShroomBody();
            shroom.RemoveShroomBody(this);
        }

        return spore;
    }

    /**
     * A gombatest kapcsolódó tektonjainak ellenőrzése
     * @return void
     */
    public void ValidateThreadConnections() {
        Set<Tecton> visited = new HashSet<>();
        Queue<Tecton> queue = new LinkedList<>();

        queue.add(this.tecton);
        visited.add(this.tecton);

        while (!queue.isEmpty()) {
            Tecton currentTecton = queue.poll();

            for (ShroomThread thread : currentTecton.GetShroomThreads()) {
                if (thread.GetShroom() != this.GetShroom()) {
                    continue;
                }

                Tecton neighbour;
                if (thread.GetTecton1() == currentTecton) {
                    neighbour = thread.GetTecton2();
                } else {
                    neighbour = thread.GetTecton1();
                }

                if (!visited.contains(neighbour)) {
                    thread.connectedToShroomBody = true;
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
    }

    /**
     * Spóra anyagok termelése
     * @return void
     */
    public void ProduceSporeMaterial() {
        sporeMaterials++;
    }

    /**
     * A gombatest tektonjának beállítása
     * @param tecton - A beállítandó tekton
     * @return void
     */
    public void SetTecton(Tecton tecton) {
        this.tecton = tecton;
    }

    /**
     * A hátralévő spóradobások számának beállítása
     * @param remainingThrows
     * @return void
     */
    public void SetRemainingThrows(int remainingThrows) {
        this.remainingThrows = remainingThrows;
    }

    /**
     * A spórákhoz szükséges anyagok mennyiségének beállítása
     * @param sporeMaterials
     * @return void
     */
    public void SetSporeMaterials(int sporeMaterials) {
        this.sporeMaterials = sporeMaterials;
    }
}
