package org.nessus.model;

import org.nessus.Skeleton;
import org.nessus.model.effect.CoffeeEffect;
import org.nessus.model.effect.CripplingEffect;
import org.nessus.model.effect.JawLockEffect;
import org.nessus.model.effect.SlowEffect;

import java.util.Random;

/**
 * A spórát reprezentáló osztály.
 * A spóra egy gomba és egy tecton párosításából áll.
 * A spórát megeheti egy rovar, amelynek tápanyagot ad és egy véletlenszerű hatást is ad.
 * @see org.nessus.model.Shroom
 * @see org.nessus.model.Tecton
 * @see org.nessus.model.Bug
 */
public class Spore {
    private Shroom shroom; // A spóra gomba része
    private Tecton tecton; // A spóra tecton része

    int nutrient = 3; // A spóra tápanyagértéke
    Random rand = new Random(); // Véletlenszám generátor

    /**
     * A spóra konstruktora.
     * @param shroom
     * @param tecton
     */
    public Spore(Shroom shroom, Tecton tecton) {
        this.shroom = shroom;
        this.tecton = tecton;
    }

    /**
     * A spóra megehetése egy rovar által.
     * @param bug A rovar, amely megeszi a spórát
     * @see org.nessus.model.Bug
     * @see org.nessus.model.effect.CoffeeEffect
     * @see org.nessus.model.effect.CripplingEffect
     * @see org.nessus.model.effect.JawLockEffect
     * @see org.nessus.model.effect.SlowEffect
     * @return void
     */
    public void EatenBy(Bug bug) {
        bug.AddNutrients(nutrient);
        int randInt = rand.nextInt(4);
        switch (randInt) {
            case 0:
                bug.AddEffect(new CoffeeEffect());
                break;
            case 1:
                bug.AddEffect(new CripplingEffect());
                break;
            case 2:
                bug.AddEffect(new JawLockEffect());
                break;
            case 3:
                bug.AddEffect(new SlowEffect());
                break;
            default:
                break;
        }
    }

    /**
     * A gomba lekérdezése.
     * @return Shroom - A spóra gombája
     */
    public Shroom GetShroom() {
        return shroom;
    }

    /**
     * A tekton lekérdezése.
     * @return Tecton - A spóra tektonja
     */
    public Tecton GetTecton() {
        return tecton;
    }

    /**
     * A tekton beállítása.
     * @param tecton - A beállítandó tekton
     * @return void
     */
    public void SetTecton(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "SetTecton", tecton);
        this.tecton = tecton;
        Skeleton.LogReturnCall(this, "SetTecton");
    }
}