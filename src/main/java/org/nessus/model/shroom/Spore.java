package org.nessus.model.shroom;

import org.nessus.model.bug.Bug;
import org.nessus.model.effect.*;
import org.nessus.model.tecton.Tecton;
import org.nessus.view.View;

/**
 * A spórát reprezentáló osztály.
 * A spóra egy gomba és egy tecton párosításából áll.
 * A spórát megeheti egy rovar, amelynek tápanyagot ad és egy véletlenszerű hatást is ad.
 * @see org.nessus.model.shroom.Shroom
 * @see org.nessus.model.tecton.Tecton
 * @see org.nessus.model.bug.Bug
 */
public class Spore {
    private Shroom shroom; // A spóra gomba része
    private Tecton tecton; // A spóra tecton része

    int nutrient = 3; // A spóra tápanyagértéke

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
     * @see org.nessus.model.bug.Bug
     * @see org.nessus.model.effect.CoffeeEffect
     * @see org.nessus.model.effect.CripplingEffect
     * @see org.nessus.model.effect.JawLockEffect
     * @see org.nessus.model.effect.SlowEffect
     * @return void
     */
    public void EatenBy(Bug bug) {
        // effectId = RANDOM OF [1, 2, 3, 4, 5]

        // CASE effectId OF
        //     1: effect := CoffeEffect
        //     2: effect := SlowEffect
        //     3: effect := JawLockEffect
        //     4: effect := CripplingEffect
        //     5: effect := DivisionEffect
        // END CASE

        // bug.AddNutrients(nutrients)
        // bug.AddEffect(effect)

        // tecton.RemoveSpore(this)
        // shroom.RemoveSpore(this)

        // TODO use IRandomProvider
        int effectId = (int) (Math.random() * 5) + 1;
        BugEffect effect = null;

        switch (effectId) {
            case 1:
                effect = new CoffeeEffect();
                break;
            case 2:
                effect = new SlowEffect();
                break;
            case 3:
                effect = new JawLockEffect();
                break;
            case 4:
                effect = new CripplingEffect();
                break;
            case 5:
                effect = new DivisionEffect();
                break;
            default:
                throw new IllegalArgumentException("Invalid effect ID when creating a random effect at Spore.EatenBy(Bug bug): " + effectId);
        }

        bug.AddNutrients(nutrient);
        bug.AddEffect(effect);

        tecton.RemoveSpore(this);
        shroom.RemoveSpore(this);
    }

    public int GetNutrient() {
        return nutrient;
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
        this.tecton = tecton;
    }
}