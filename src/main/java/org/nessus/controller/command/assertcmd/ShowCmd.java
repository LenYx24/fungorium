package org.nessus.controller.command.assertcmd;

import org.nessus.controller.command.BaseCommand;
import org.nessus.view.View;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

public class ShowCmd extends BaseCommand {
    private String GetValueName(Object value){
        if(value == null) return null;
        String valuename = View.GetObjectStore().GetName(value);
        return valuename == null ? value.toString() : valuename;
    }
    private void PrintArrayRecursive(Object array) {
        if (!array.getClass().isArray()) {
            String name = GetValueName(array);
            System.out.print(name != null ? name : array);
            return;
        }

        int length = Array.getLength(array);
        System.out.println("\t" + GetValueName(array) + ": {");
        for (int i = 0; i < length; i++) {
            Object element = Array.get(array, i);
            PrintArrayRecursive(element);
            if (i < length - 1) System.out.print(", ");
        }
        System.out.print("}");
    }
    @Override
    public void Run(String[] args) {
        if(NotEnoughArgs(args,1))
            return;

        String name = args[0];

        var view = View.GetObjectStore();
        Object obj = view.GetObject(name);

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
                //if(field instanceof Collection<?>){
                //    PrintArrayRecursive(value);
                //}else{
                    System.out.println("\t" + field.getName() + ": " + GetValueName(value));
                //}
            } catch (IllegalAccessException e) {
                System.out.println("\t" + field.getName() + ": <access denied>");
            }
        }
    }
}
