package com.company.crm.utils;

import javax.xml.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(\\+7|8)\\d{10}$");

    private static final Pattern WORKBOOK_PATTERN =
            Pattern.compile("^\\d{7,8}$");

    private static final Pattern PRICE_PATTERN =
            Pattern.compile("^\\d+(\\.\\d{1,2})?$");

    private static final Pattern PASSPORT_PATTERN =
            Pattern.compile("^\\d{10}$");
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-zА-Яа-яЁё\\s-]{2,50}$");


    public static boolean isValidEmail(String email){
        return EMAIL_PATTERN.matcher(email).matches();
    }
    public static boolean isValidPhone(String phone) {
        String digits = extractDigitsPhone(phone);
        if (digits == null) return false;

        return digits.length() == 11 &&
                (digits.startsWith("7") || digits.startsWith("8"));
    }
    public static boolean isValidWorkBook(String workbook){
        return WORKBOOK_PATTERN.matcher(workbook).matches();
    }
    public static boolean isValidPrice(String price){
        return PRICE_PATTERN.matcher(price).matches();
    }
    public static boolean isValidPrice(BigDecimal price){
        return price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }
    public static boolean isValidPassport(String passport){
        String digits = extractDigitsPassport(passport);
        if (digits == null) return false;

        return digits.length() == 10;
    }
    public static boolean isValidName(String name){
        return name != null && NAME_PATTERN.matcher(name).matches();
    }
    public static boolean isValidComment(String comment){
        return comment == null || comment.length() <= 200;
    }
    public static boolean isValidGuestCount(int count){
        return count > 0 && count <= 100; // пример
    }



    public static String extractDigitsPhone(String phone) {
        return phone == null ? null : phone.replaceAll("\\D", "");
    }
    public static String extractDigitsPassport(String passport) {
        return passport == null ? null : passport.replaceAll("\\D", "");
    }
    public static boolean isValidDateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            return false;
        }
        return !start.isAfter(end);
    }

    public static String normalizePhone(String phone) {
        String digits = extractDigitsPhone(phone);

        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException("Некорректный номер телефона");
        }

        // заменяем 8 → 7
        digits = "7" + digits.substring(1);

        return String.format(
                "+7(%s)%s-%s-%s",
                digits.substring(1, 4),
                digits.substring(4, 7),
                digits.substring(7, 9),
                digits.substring(9, 11)
        );
    }
    public static String normalizePassport(String passport) {
        String digits = extractDigitsPassport(passport);

        if (!isValidPassport(passport)) {
            throw new IllegalArgumentException("Некорректные паспортные данные. Должно быть 10 цифр.");
        }

        return String.format("%s %s",
                digits.substring(0, 4),
                digits.substring(4, 10));
    }
}
