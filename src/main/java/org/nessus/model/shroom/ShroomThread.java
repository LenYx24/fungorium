package org.nessus.model.shroom;

import org.nessus.model.tecton.Tecton;
import org.nessus.model.bug.Bug;

/**
 * A fonalat reprezentáló osztály.
 * A fonalak a gombákhoz kapcsolódnak.
 * @see Shroom
 * @see Tecton
 */
public class ShroomThread {
    private Shroom shroom; // A gomba, amelyhez a fonal tartozik
    private Tecton tecton1; // Az egyik tecton, amelyhez a fonal kapcsolódik
    private Tecton tecton2; // A másik tecton, amelyhez a fonal kapcsolódik

    int evolution = 0; // A fonal fejlődési szintje
    boolean connectedToShroomBody = true; // A fonal kapcsolódik-e a gomba testéhez
    int isolationCounter = 0; // Az izoláció számlálója
    boolean cut = false; // A fonal el van-e vágva

    int cutDamageTimer = 3; // Megadja, a fonál hány kör után szívódik fel miután elvágták
    boolean sustained = false; // Megadja, hogy a fonált épp életben tartja-e egy ThreadSustainerTecton. Ilyenkor nem szívódik fel.

    /**
     * Az osztály konstruktora.
     * @param tecton1
     * @param tecton2
     */
    public ShroomThread(Shroom shroom) {
        this.shroom = shroom;
        shroom.SetShroomThread(this);
    }
    
    /**
     * Az osztály konstruktora.
     * @param shroom
     * @param tecton1
     * @param tecton2
     */
    public ShroomThread(Shroom shroom, Tecton tecton1, Tecton tecton2) {
        this.shroom = shroom;
        this.tecton1 = tecton1;
        this.tecton2 = tecton2;
    }

    /**
     * A fonal életének ellenőrzése.
     * A fonal fejlődési szintjét növeli, ha a fonalhoz kapcsolódó tektonokban van a gomba spórája.
     * Ha a fonal felszívódott, akkor eltávolítja a fonalat.
     * @see ShroomThread#Evolve
     * @see ShroomThread#Remove
     * @return void
     */
    public void ValidateLife() {
        boolean growthBoost1 = tecton1.HasSporeOfShroom(this.shroom);
        boolean growthBoost2 = tecton2.HasSporeOfShroom(this.shroom);

        int growthAmount = 0;

        if (growthBoost1 || growthBoost2)
            growthAmount += 2;
        else
            growthAmount += 1;

        evolution = Math.min(evolution + growthAmount, 3);

        if (cut)
            cutDamageTimer -= 1;

        if (!connectedToShroomBody && !sustained)
            isolationCounter -= 1;

        if (isolationCounter >= 3 || cutDamageTimer <= 0)
            Remove();
    }

    /**
     * Ellenőrzi, hogy a kiválasztott tekton elérhető-e a fonalból.
     * @param tecton - A tekton, amelyet ellenőrizni kell
     * @return boolean - Az eredmény
     */
    public boolean IsTectonReachable(Tecton tecton) {
        return evolution == 3 && (tecton.equals(tecton1) || tecton.equals(tecton2));
    }

    public boolean DevourCrippledBug(Bug bug) {
        boolean canMove = bug.GetCanMove();
        Tecton currentTecton = bug.GetTecton();
    
        if(!canMove && (currentTecton == tecton1 || currentTecton == tecton2)) {
            shroom.AddShroomBodyCost(-3);
            bug.Remove();

            return true;
        }

        return false;
    }

    /**
     * Eltávolítja a fonalat a kapcsolódó gombákból és tektonokból.
     * @return void
    */
    public void Remove() {
        tecton1.RemoveShroomThread(this);
        tecton2.RemoveShroomThread(this);
        shroom.RemoveShroomThread(this);
    }

    /**
     * Visszaadja a fonalhoz kapcsolódó gombát.
     * @return Shroom - A fonalhoz kapcsolódó gomba
     */
    public Shroom GetShroom() {
        return shroom;
    }

    /**
     * Visszaadja az egyik tektont.
     * @return Tecton - Az egyik tekton
     */
    public Tecton GetTecton1() {
        return tecton1;
    }

    /**
     * Visszaadja az egyik tektont.
     * @return Tecton - Az egyik tekton
     */
    public Tecton GetTecton2() {
        return tecton2;
    }

    /**
     * Beállítja az egyik tektont.
     * @param tecton - Az egyik tekton
     * @return void
     */
    public void SetTecton1(Tecton tecton) {
        tecton1 = tecton;
    }

    /**
     * Beállítja az egyik tektont.
     * @param tecton - Az egyik tekton
     * @return void
     */
    public void SetTecton2(Tecton tecton) {
        tecton2 = tecton;
    }

    /**
     * Visszaadja, hogy a fonal kapcsolódik-e a gomba testéhez.
     * @return boolean - A válasz
     */
    public boolean GetConnectedToShroomBody() {
        return connectedToShroomBody;
    }

    /**
     * Beállítja, hogy a fonal kapcsolódik-e a gomba testéhez.
     * @param connectedToShroomBody - Igaz vagy hamis
     * @return void
     */
    public void SetConnectedToShroomBody(boolean connectedToShroomBody) {
        this.connectedToShroomBody = connectedToShroomBody;
    }

    public void SetSustained() {
        sustained = true;
    }
}