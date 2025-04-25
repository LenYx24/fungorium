package org.nessus.controller.command.assertcmd;

import org.nessus.controller.command.BaseCommand;
import org.nessus.view.View;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public class ShowCmd extends BaseCommand {
    private String GetValueOfObject(Object value){
        if(value == null) return null;
        String valuename = View.GetObjectStore().GetName(value);
        return valuename == null ? value.toString() : valuename;
    }
    
    @Override
    public void Run(String[] args) {
        if(NotEnoughArgs(args,1))
            return;

        String name = args[0];

        var view = View.GetObjectStore();
        Object gameObject = view.GetObject(name);

        if(gameObject == null){
            System.out.println("Nincs ilyen objektum");
            return;
        }

        Class<?> clazz = gameObject.getClass();
        System.out.println(name + ":");

        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
            try {
                field.setAccessible(true); // allow access to private fields
                var fieldObject = field.get(gameObject);

                if (fieldObject instanceof Collection<?> valueList) {
                    System.out.print("\t" + field.getName() + ": {");
                    
                    if (valueList.isEmpty()) {
                        System.out.println(" }");
                    } else {
                        System.out.println();

                        var values = valueList.stream().map(x -> "\t\t" + GetValueOfObject(x)).toList();
                        var formatted = String.join(",\n", values);

                        System.out.println(formatted + "\n\t}");
                    }
                } else {
                    System.out.println("\t" + field.getName() + ": " + GetValueOfObject(fieldObject));
                }
            } catch (IllegalAccessException e) {
                System.out.println("\t" + field.getName() + ": <access denied>");
            } finally {
                field.setAccessible(false);
            }
        }
    }
}
