package org.nessus.model;

import org.nessus.Skeleton;

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
    int isolationCounter = 0; // Az izoláció számlálója
    boolean connectedToShroomBody = false; // A fonal kapcsolódik-e a gomba testéhez

    /**
     * A fonal fejlődési szintjét növeli a paraméterben kapott értékkel.
     * @param n - A fejlődési szint növelésére szolgáló érték
     * @see ShroomThread#evolution
     */
    private void Evolve(int n) {
        evolution = Math.min(evolution+n, 3);
    }

    /**
     * Az osztály konstruktora.
     * @param tecton1
     * @param tecton2
     */
    public ShroomThread(Tecton tecton1, Tecton tecton2) {
        this.tecton1 = tecton1;
        this.tecton2 = tecton2;
    }
    
    /**
     * Az osztály konstruktora.
     * @param shroom
     * @param tecton1
     * @param tecton2
     */
    public ShroomThread(Shroom shroom, Tecton tecton1, Tecton tecton2) {
        this(tecton1, tecton2);
        this.shroom = shroom;
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
        Skeleton.LogFunctionCall(this, "ValidateLife");
        boolean growthBoost1 = tecton1.HasSporeOfShroom(this.shroom);
        boolean growthBoost2 = tecton2.HasSporeOfShroom(this.shroom);

        Evolve((growthBoost1 || growthBoost2) ? 2 : 1);

        boolean decayed = Skeleton.YesNoQuestion("Felszívódott-e a fonal?");

        if (decayed) {
            this.Remove();
        }
        Skeleton.LogReturnCall(this, "ValidateLife");
    }

    /**
     * Ellenőrzi, hogy a kiválasztott tekton elérhető-e a fonalból.
     * @param tecton - A tekton, amelyet ellenőrizni kell
     * @return boolean - Az eredmény
     */
    public boolean IsTectonReachable(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "IsTectonReachable", tecton);
        String threadName = Skeleton.GetName(this);
        String tectonName = Skeleton.GetName(tecton);
        boolean isTectonReachable = Skeleton.YesNoQuestion("A "+ threadName + " nevű fonal egyik végpontja a " + tectonName + " tecton?");
        Skeleton.LogReturnCall(this, "IsTectonReachable", isTectonReachable);
        return isTectonReachable;
    }

    /**
     * Eltávolítja a fonalat a kapcsolódó gombákból és tektonokból.
     * @return void
    */
    public void Remove() {
        Skeleton.LogFunctionCall(this, "Remove");
        tecton1.RemoveShroomThread(this);
        tecton2.RemoveShroomThread(this);
        shroom.RemoveShroomThread(this);
        Skeleton.LogReturnCall(this, "Remove");
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
        Skeleton.LogFunctionCall(this, "SetTecton1", tecton);
        tecton1 = tecton;
        Skeleton.LogReturnCall(this, "SetTecton1");
    }

    /**
     * Beállítja az egyik tektont.
     * @param tecton - Az egyik tekton
     * @return void
     */
    public void SetTecton2(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "SetTecton2", tecton);
        tecton2 = tecton;
        Skeleton.LogReturnCall(this, "SetTecton2");
    }
}