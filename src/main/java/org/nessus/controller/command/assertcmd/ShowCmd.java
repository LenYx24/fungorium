package org.nessus.controller.command.assertcmd;

import org.nessus.controller.command.BaseCommand;
import org.nessus.view.View;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Collection;

public class ShowCmd extends BaseCommand {
    private OutputStream os;

    public ShowCmd(OutputStream os) {
        this.os = os;
    }

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
        var writer = new PrintWriter(os);

        if(gameObject == null){
            writer.println("Nincs ilyen objektum");
            return;
        }

        Class<?> clazz = gameObject.getClass();
        Class<?> zuper = clazz.getSuperclass();
        if (!zuper.equals(Object.class))
            clazz = zuper;

        writer.println(name + ":");

        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
            try {
                field.setAccessible(true); // allow access to private fields
                var fieldObject = field.get(gameObject);

                if (fieldObject instanceof Collection<?> valueList) {
                    writer.print("\t" + field.getName() + ": {");
                    
                    if (valueList.isEmpty()) {
                        writer.println(" }");
                    } else {
                        writer.println();

                        var values = valueList.stream().map(x -> "\t\t" + GetValueOfObject(x)).toList();
                        var formatted = String.join(",\n", values);

                        writer.println(formatted + "\n\t}");
                    }
                } else {
                    writer.println("\t" + field.getName() + ": " + GetValueOfObject(fieldObject));
                }
            } catch (IllegalAccessException e) {
                writer.println("\t" + field.getName() + ": <access denied>");
            } finally {
                field.setAccessible(false);
            }
        }

        writer.flush();
    }
}
