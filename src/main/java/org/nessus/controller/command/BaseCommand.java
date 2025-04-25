package org.nessus.controller.command;

import org.nessus.model.tecton.Tecton;
import org.nessus.view.View;

import java.util.Optional;

public abstract class BaseCommand {
    public abstract void Run(String[] args);

    protected Optional<Integer> ConvertToInteger(String arg){
        try {
            return Optional.of(Integer.parseInt(arg));
        } catch (NumberFormatException e) {
            System.out.println("Rossz paraméter");
            return Optional.empty();
        }
    }

    protected Object InferType(String s) {
        // Try Integer
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ignored) {}

        // Try Double
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ignored) {}

        // Try Character (length == 1)
        if (s.length() == 1) {
            return s.charAt(0);
        }

        var view = View.GetObjectStore();
        Object obj = view.GetObject(s);
        if(obj != null)
            return obj;

        // Default: String
        return s;
    }

    protected static Class<?> GetTypeClass(Object o) {
        // if (o instanceof Integer) return int.class;
        // if (o instanceof Double) return double.class;
        // if (o instanceof Character) return char.class;
        // // TODO: Az összes osztályhoz felvenni egy ilyen sort (vagy más megoldást keresni)
        // if (o instanceof Tecton) return Tecton.class;
        // return String.class;
        return o.getClass();
    }
    
    protected boolean NotEnoughArgs(Object[] args, int minlength){
        if(args.length < minlength) {
            System.out.println("Nincs elegendő számú paraméter");
            return true;
        }
        return false;
    }
}
