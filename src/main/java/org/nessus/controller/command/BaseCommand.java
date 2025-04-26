package org.nessus.controller.command;

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

        var view = View.GetObjectStore();
        Object obj = view.GetObject(s);
        if(obj != null)
            return obj;

        // Default: String
        return s;
    }

    protected static Class<?> GetTypeClass(Object o) {
        // TODO
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
