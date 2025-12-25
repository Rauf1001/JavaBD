package com.company.crm.utils;


import com.company.crm.models.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GenerateDatabase {


    private static final Random r = new Random();

    public static List<Client> generateClients(int count) {
        List<String> names = Arrays.asList("Петр","Яков","Владимир","Алиса","Боб","Диана","Эмма","Стивен","Елизавета");
        List<String> emails = Arrays.asList(
                "alex@mail.ru","vika@gmail.com","petr@yandex.ru","lena@outlook.com","max@bk.ru",
                "sveta@gmail.com","igor@mail.ru","anna@yandex.ru","roman@proton.me","dasha@icloud.com"
        );
        List<String> phones = Arrays.asList(
                "+7-900-111-22-33","+7-901-222-33-44","+7-902-333-44-55","+7-903-444-55-66","+7-904-555-66-77",
                "+7-908-111-22-54","+7-905-222-33-11","+7-906-333-44-09","+7-920-444-55-26","+7-910-555-66-38"
        );
        List<String> passports = Arrays.asList(
                "5829310476","1047392586","9274156038","3501869724","7682094351",
                "4917053628","8364290715","2758196043","6193470852","8032564917"
        );
        List<LocalDate> dates = Arrays.asList(
                LocalDate.of(1990,1,10), LocalDate.of(1985,3,5), LocalDate.of(1998,6,20),
                LocalDate.of(1992,8,11), LocalDate.of(2000,2,9), LocalDate.of(1996,12,1)
        );

        List<Client> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new Client(
                    names.get(r.nextInt(names.size())),
                    emails.get(r.nextInt(emails.size())),
                    phones.get(r.nextInt(phones.size())),
                    passports.get(r.nextInt(passports.size())),
                    dates.get(r.nextInt(dates.size()))
            ));
        }
        return list;
    }




    public static List<Staff> generateStaff(int count) {
        List<String> names = Arrays.asList(
                "Смит", "Иван", "Владимир", "Алиса", "Софья",
                "Чарли", "Диана", "Эмма", "Стивен", "Елизавета"
        );

        List<String> passports = Arrays.asList(
                "5829310476", "1047392586", "9274156038", "3501869724", "7682094351",
                "4917053628", "8364290715", "2758196043", "6193470852", "8032564917"
        );

        List<String> phones = Arrays.asList(
                "+7-900-111-22-33", "+7-901-222-33-44", "+7-902-333-44-55",
                "+7-903-444-55-66", "+7-904-555-66-77", "+7-908-111-22-54",
                "+7-905-222-33-11", "+7-906-333-44-09", "+7-920-444-55-26",
                "+7-910-555-66-38"
        );

        List<String> staffBookNumbers = Arrays.asList(
                "5829310476", "1047392586", "9274156038", "3501869724", "7682094351",
                "4917053628", "8364290715", "2758196043", "6193470852", "8032564917"
        );

        List<Integer> workExperiences = Arrays.asList(
                10,1,2,3,4,5,6,7,8,9
        );

        List<Staff> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new Staff(
                    names.get(r.nextInt(names.size())),
                    passports.get(r.nextInt(passports.size())),
                    phones.get(r.nextInt(phones.size())),
                    staffBookNumbers.get(r.nextInt(staffBookNumbers.size())),
                    workExperiences.get(r.nextInt(workExperiences.size()))
            ));
        }
        return list;
    }


    public static List<LivingRoom> generateLivingRooms(int count) {
        List<String> rooms = Arrays.asList("101","102","103","201","202","203","301","302","VIP-1","VIP-2");
        List<String> loc = Arrays.asList("1 этаж","2 этаж","3 этаж","Корпус A","Корпус B","Корпус C");

        List<LivingRoom> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new LivingRoom(
                    rooms.get(r.nextInt(rooms.size())),
                    loc.get(r.nextInt(loc.size())),
                    r.nextInt(2)
            ));
        }
        return list;
    }

    public static List<GroupApplication> generateGroupApps(int count) {
        List<GroupApplication> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            LocalDate arr = LocalDate.now().plusDays(r.nextInt(20));
            LocalDate dep = arr.plusDays(r.nextInt(10) + 1);

            list.add(new GroupApplication(
                    r.nextInt(10) + 1,
                    arr,
                    dep,
                    BigDecimal.valueOf(2000 + r.nextInt(6000)),
                    r.nextBoolean(),
                    "Комментарий " + (i + 1)
            ));
        }
        return list;
    }


    public static List<Booking> generateBookings(int count) {
        List<Booking> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            LocalDate arr = LocalDate.now().plusDays(r.nextInt(30));
            LocalDate dep = arr.plusDays(1 + r.nextInt(5));

            list.add(new Booking(
                    r.nextInt(10) + 1,
                    r.nextInt(10) + 1,
                    r.nextInt(10) + 1,
                    r.nextInt(10) + 1,
                    arr,
                    dep,
                    r.nextInt(4) + 1,
                    LocalDateTime.now().minusDays(r.nextInt(15)),
                    r.nextBoolean(),
                    BigDecimal.valueOf(1000 + r.nextInt(7000))
            ));
        }
        return list;
    }
}


