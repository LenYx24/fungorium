package org.nessus.controller.command.assertcmd;

import org.nessus.controller.command.BaseCommand;
import org.nessus.view.View;

import java.lang.reflect.Field;

public class ShowCmd extends BaseCommand {
    @Override
    public void Run(String[] args) {
        if(NotEnoughArgs(args,1)) {return;}
        String name = args[0];
        Object obj = View.GetObject(name);
        if(obj == null){
            System.out.println("Nincs ilyen objektum");
            return;
        }
        Class<?> clazz = obj.getClass();
        System.out.println(name + ":");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // allow access to private fields
            try {
                Object value = field.get(obj);
                System.out.println("\t" + field.getName() + ": " + value);
            } catch (IllegalAccessException e) {
                System.out.println("\t" + field.getName() + ": <access denied>");
            }
        }
    }
}
