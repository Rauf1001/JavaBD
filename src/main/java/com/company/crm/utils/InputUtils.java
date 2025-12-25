package com.company.crm.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputUtils {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");


    public static String readRequired(Scanner sc, String text){
        while(true){
            System.out.print(text + ": ");
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Поле не может быть пустым");
        }
    }
    public static String promptWithDefault(Scanner scanner, String fieldName, String oldValue) {
        System.out.print("Введите новое " + fieldName);
        String input = scanner.nextLine();
        return input.isBlank() ? oldValue : input;
    }


    public static LocalDate promptDateWithDefault(Scanner scanner, String fieldName, LocalDate oldValue) {
        System.out.print("Введите новую " + fieldName + " в формате 'DD.MM.YYYY' ");
        String input = scanner.nextLine();


        if (input.isBlank()) {
            return oldValue;
        }

        try {
            return LocalDate.parse(input, DATE_FORMATTER);
        } catch (Exception e) {
            System.out.println("Неверный формат даты! Оставлено старое значение.");
            return oldValue;
        }
    }

    public static boolean readBoolean(Scanner sc) {
        while (true) {
            System.out.print("Введите (true/false, да/нет, 1/0): ");
            String input = sc.nextLine().trim().toLowerCase();

            if (input.equals("true") || input.equals("t") || input.equals("1") ||
                    input.equals("да") || input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("false") || input.equals("f") || input.equals("0") ||
                    input.equals("нет") || input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }


    public static boolean askStatusWithDefault(Scanner sc, boolean def) {
        System.out.print("Статус (по умолчанию " + (def ? "true" : "false") + ") → ");

        String input = sc.nextLine().trim();

        if (input.isEmpty()) return def;

        return readBoolean(new Scanner(input));
    }

    public static int readIntRequired(Scanner sc, String text) {
        while (true) {
            System.out.print(text + ": ");
            String input = sc.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= 0) {
                    return value;
                }
                System.out.println("Число должно быть неотрицательным.");
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число.");
            }
        }
    }

    public static int promptIntWithDefault(Scanner sc, String fieldName, int oldValue) {
        System.out.print("Введите новый " + fieldName + " (по умолчанию " + oldValue + "): ");
        String input = sc.nextLine().trim();

        if (input.isEmpty()) return oldValue;

        try {
            int value = Integer.parseInt(input);
            if (value >= 0) return value;
        } catch (NumberFormatException ignored) {}

        System.out.println("Некорректный ввод. Оставлено старое значение.");
        return oldValue;
    }


}
