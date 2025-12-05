package com.company.crm.utils;

import java.lang.reflect.Field;
import java.util.List;

public class TableViewer {
    public static void showTable(List<?> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("Таблица пуста.");
            return;
        }

        Class<?> clazz = data.get(0).getClass();
        System.out.println("Таблица: " + clazz.getSimpleName());
        System.out.println("-".repeat(200));


        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals("counter") || field.getName().equals("someOtherField")) {
                continue;
            }
            System.out.printf("%-30s", field.getName());
        }
        System.out.println();
        System.out.println("-".repeat(200));


        for (Object obj : data) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().equals("counter") || field.getName().equals("someOtherField")) {
                    continue;
                }
                try {
                    System.out.printf("%-30s", field.get(obj));
                } catch (IllegalAccessException e) {
                    System.out.print("Ошибка");
                }
            }
            System.out.println();
        }


    }


}
