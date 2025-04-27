package org.nessus.model.shroom;

import org.nessus.controller.IRandomProvider;
import org.nessus.model.bug.Bug;
import org.nessus.model.effect.BugEffect;
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

    int nutrients = 3; // A spóra tápanyagértéke

    /**
     * A spóra konstruktora.
     */
    public Spore() {
    }
    /**
     * A spóra konstruktora.
     * @param shroom
     */
    public Spore(Shroom shroom) {
        this.shroom = shroom;
        shroom.SetSpore(this);
    }
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
        var objStore = View.GetObjectStore();
        IRandomProvider rand = objStore.GetRandomProvider();
        BugEffect randEffect = rand.RandomBugEffect();

        objStore.AddObjectWithNameGen("bugEffect", randEffect);
        
        bug.AddNutrients(nutrients);
        bug.AddEffect(randEffect);

        tecton.RemoveSpore(this);
        shroom.RemoveSpore(this);
    }

    /**
     * A spóra tápanyagának lekérdezése.
     * @return int - A spóra tápanyaga
     */
    public int GetNutrient() {
        return nutrients;
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