package org.nessus.controller;

import java.util.*;

import org.nessus.model.bug.BugOwner;
import org.nessus.model.shroom.Shroom;
import org.nessus.model.tecton.Tecton;

import org.nessus.model.effect.*;
import org.nessus.view.View;

/**
 * Ez az osztály implementálja a Controllert, amely a játék logikájáért felelős.
 * A Controller osztály kezeli a parancsokat, és irányítja a játék állapotát.
 * A Controller osztályban található parancsok a játék különböző fázisait kezelik, mint például az elrendezést, a cselekvést és az állításokat.
 */
public class Controller implements IRandomProvider {
    private static Random rand = new Random();

    private IShroomController currentShroom = null; // a shroom
    private IBugOwnerController currentBugOwner = null; // a bug owner

    private boolean bugOwnerRound = false; // Bug owner köre
    private List<IBugOwnerController> bugOwners = new ArrayList<>(); // Bug owner lista
    private List<IShroomController> shrooms = new ArrayList<>(); // Shroom lista
    private List<ITectonController> tectons = new ArrayList<>();

    private static View view; // static lett

    /**
     * A Controller osztály konstruktora, amely inicializálja a parancsokat és beállítja a nézetet.
     * @param view A nézet, amelyet a Controller használ.
     */
    public Controller(View view) {
        this.view = view;
    }

    public void ClearMap() {
        bugOwners.clear();
        shrooms.clear();
        tectons.clear();
    }

    public void GenerateMap(int tectonCount) {
        // TODO
    }

    public void StartAction(IActionController action){
        // TODO
    }

    public void NextPlayer(){
        // TODO
    }

    public Object GetCurrentPlayer(){
        return bugOwnerRound ? currentBugOwner : currentShroom;
    }

    public int GetPlayerActionPoints(){
        // TODO
        return 0;
    }

    /**
     * Ez a metódus felvesz egy új BugOwner-t a bugOwners listába.
     * @param bugOwner A BugOwner, amelyet hozzá szeretnénk adni a listához
     * @return void
     */
    public void AddBugOwner(IBugOwnerController bugOwner) {
        // TODO: Kellene a nevet is megadni amikor létrejön a bug, kell egy új string paraméter
        bugOwners.add(bugOwner);
    }

    /**
     * Ez a metódus felvesz egy új Shroom-ot a shrooms listába.
     * @param shroom A Shroom, amelyet hozzá szeretnénk adni a listához
     * @return void
     */
    public void AddShroom(IShroomController shroom) {
        // TODO: Kellene a nevet is megadni amikor létrejön a shroom, kell egy új string paraméter
        shrooms.add(shroom);
    }

    public void AddTecton(ITectonController tecton) {
        tectons.add(tecton);
    }

    public void ViewSelectionChanged(){
        // TODO
    }

    /**
     * Ez a metódus egy random számot generál a megadott minimum és maximum érték között.
     * @param min A minimum érték
     * @param max A maximum érték
     * @return int - A generált random szám
     */
    public int RandomNumber(int min, int max) {
        return rand.nextInt(min, max + 1);
    }

    /**
     * Ez a metódus egy random BugEffect-et generál.
     * @return BugEffect - A generált random BugEffect
     */
    public BugEffect RandomBugEffect() {
        return switch (RandomNumber(1, 5)) {
            case 1 -> new CoffeeEffect();
            case 2 -> new SlowEffect();
            case 3 -> new JawLockEffect();
            case 4 -> new CripplingEffect();
            default -> new DivisionEffect();
        };
    }

    /**
     * Ez a metódus egy random boolean értéket generál.
     * @return boolean - A generált random boolean érték
     */
    public boolean RandomBoolean() {
        return rand.nextBoolean();
    }

    /**
     * Ez a metódus egy random Seed értéket állít be.
     * @param seed A Seed érték, amelyet be szeretnénk állítani
     * @return void
     */
    public void SetSeed(long seed) {
        rand.setSeed(seed);
    }
}