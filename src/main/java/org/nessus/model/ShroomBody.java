package org.nessus.model;

import org.nessus.Skeleton;

/**
 * A gombatestet reprezentáló osztály.
 * A gombatest a gombák egyik fontos része, amely a spórák termeléséért felelős.
 * A {@link Shroom} osztályból származik le.
 * @see org.nessus.model.Shroom
 * @see org.nessus.model.Tecton
 * @see org.nessus.model.Spore
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
        Skeleton.LogFunctionCall(this, "Upgrade");
        level++;
        Skeleton.LogReturnCall(this, "Upgrade");
    }

    /**
     * A céltekton távolságának ellenőrzése
     * @param tecton - Az a tekton, amelyre egy új spórt akarunk dobni
     * @return boolean
     */
    public boolean InRange(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "InRange", tecton);
        boolean inRange = Skeleton.YesNoQuestion("Elég közel van a tekton a spóraköpéshez?");
        Skeleton.LogReturnCall(this, "InRange", inRange);
        return inRange;
    }

    /**
     * Spóra kialakítása
     * @param tecton - Az a tekton, amelyre egy új spórt akarunk dobni
     * @return Spore - A kialakított spóra
     */
    public Spore FormSpore(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "FormSpore", tecton);
        
        if (!InRange(tecton)) {
            Skeleton.LogReturnCall(this, "FormSpore", "null");
            return null;
        }

        sporeMaterials -= 2;
        remainingThrows--;
        
        Spore spore = new Spore(shroom, tecton);
        Skeleton.AddObject(spore, "spore");
        tecton.ThrowSpore(spore);
        
        if (remainingThrows <= 0) {
            tecton.ClearShroomBody();
            shroom.RemoveShroomBody(this);
        }

        Skeleton.LogReturnCall(this, "FormSpore", spore);
        return spore;
    }

    /**
     * Spóra termelése
     * @apiNote Még nincs implementálva
     * @throws UnsupportedOperationException
     * @return void
     */
    public void ProduceSpore() {
        throw new UnsupportedOperationException();
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
        Skeleton.LogFunctionCall(this, "SetRemainingThrows", remainingThrows);
        this.remainingThrows = remainingThrows;
        Skeleton.LogReturnCall(this, "SetRemainingThrows");
    }

    /**
     * A spórákhoz szükséges anyagok mennyiségének beállítása
     * @param sporeMaterials
     * @return void
     */
    public void SetSporeMaterials(int sporeMaterials) {
        Skeleton.LogFunctionCall(this, "SetSporeMaterials", sporeMaterials);
        this.sporeMaterials = sporeMaterials;
        Skeleton.LogReturnCall(this, "SetSporeMaterials");
    }
}
