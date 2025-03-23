package org.nessus.model;

import org.nessus.Skeleton;
import org.nessus.model.effect.CoffeeEffect;
import org.nessus.model.effect.CripplingEffect;
import org.nessus.model.effect.JawLockEffect;
import org.nessus.model.effect.SlowEffect;

import java.util.Random;

public class Spore {
    private Shroom shroom;
    private Tecton tecton;

    int nutrient = 3;
    Random rand = new Random();

    public Spore(Shroom shroom, Tecton tecton) {
        this.shroom = shroom;
        this.tecton = tecton;
    }

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

    public Shroom GetShroom() {
        return shroom;
    }

    public Tecton GetTecton() {
        return tecton;
    }

    public void SetTecton(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "SetTecton", tecton);
        this.tecton = tecton;
        Skeleton.LogReturnCall(this, "SetTecton");
    }
}
