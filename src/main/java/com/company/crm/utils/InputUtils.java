package com.company.crm.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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

            switch (input) {
                case "true", "t", "1", "да", "yes", "y" -> {
                    return true;
                }
                case "false", "f", "0", "нет", "no", "n" -> {
                    return false;
                }
                default -> System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }

    public static boolean askStatusWithDefault(Scanner sc, boolean def) {
        System.out.print("Статус (по умолчанию " + (def ? "true" : "false") + ") → ");

        String input = sc.nextLine().trim();

        if (input.isEmpty()) return def;

        return readBoolean(new Scanner(input));
    }

    public static boolean readStatus(Scanner scanner) {
        while (true) {
            System.out.print("Введите статус (да/нет): ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "да":
                case "true":
                case "1":
                    return true;
                case "нет":
                case "false":
                case "0":
                    return false;
                default:
                    System.out.println("Некорректный ввод. Введите: да/нет");
            }
        }
    }
    public static boolean parseBooleanInput(String input) {
        if (input == null) return false;
        String s = input.trim().toLowerCase();
        if (s.isEmpty()) return false; // если пусто — можно вернуть дефолт вне этой ф-ии

        switch (s) {
            case "да":
            case "y":
            case "yes":
            case "true":
            case "1":
                return true;
            case "нет":
            case "n":
            case "no":
            case "false":
            case "0":
                return false;
            default:
                // по умолчанию — false, либо можно бросать исключение / запрашивать снова
                return false;
        }
    }



}
