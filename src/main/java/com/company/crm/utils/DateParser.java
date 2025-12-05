package com.company.crm.utils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateParser {
    public static LocalDate parserDate(String input) {
        List<String> patterns = List.of(
                "dd.MM.yyyy",
                "yyyy-MM-dd",
                "dd/MM/yyyy",
                "MM-dd-yyyy",
                "d.M.yyyy"


        );


        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                input = input.trim().replace(",",".").replace("-",".").replace("/",".");
                return LocalDate.parse(input, formatter);

            } catch (DateTimeException ignored) {

            }
        }

        return null;
    }


}
