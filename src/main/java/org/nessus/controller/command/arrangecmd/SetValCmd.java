package org.nessus.controller.command.arrangecmd;

import org.nessus.controller.command.BaseCommand;
import org.nessus.view.View;

import java.lang.reflect.Field;

public class SetValCmd extends BaseCommand {
    private Object convertStringToType(String value, Class<?> type) {
        try {
            if (type == int.class || type == Integer.class) return Integer.parseInt(value);
            if (type == double.class || type == Double.class) return Double.parseDouble(value);
            if (type == char.class || type == Character.class) return value.charAt(0);
            if (type == String.class) return value;
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    @Override
    public void Run(String[] args) {
        if (NotEnoughArgs(args,3)) {
            return;
        }

        String objectName = args[0];
        String fieldName = args[1];
        String valueString = args[2];

        Object targetObject = View.GetObject(objectName);
        if (targetObject == null) {
            System.out.println("Nincs ilyen objektum: " + objectName);
            return;
        }

        Class<?> clazz = targetObject.getClass();

        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            // Típusdetektálás és konvertálás
            Object value = convertStringToType(valueString, field.getType());
            if (value == null) {
                System.out.println("Konvertálás sikertelen");
                return;
            }

            field.set(targetObject, value);
            System.out.println("Paraméter beállítva");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Hiba: " + e.getMessage());
        }
    }
}
