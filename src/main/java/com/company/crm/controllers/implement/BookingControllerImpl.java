package com.company.crm.controllers.implement;

import com.company.crm.controllers.interfaces.BookingController;

import com.company.crm.services.implement.BookingServiceImpl;
import com.company.crm.models.Booking;
import com.company.crm.utils.DateParser;
import com.company.crm.utils.InputUtils;
import com.company.crm.utils.InputValidator;
import com.company.crm.utils.TableViewer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static com.company.crm.utils.InputUtils.promptDateWithDefault;
import static com.company.crm.utils.InputUtils.promptWithDefault;

public class BookingControllerImpl implements BookingController {
    private final BookingServiceImpl bookingServiceImpl;
    private final Scanner scanner = new Scanner(System.in);

    public BookingControllerImpl(BookingServiceImpl bookingServiceImpl) {
        this.bookingServiceImpl = bookingServiceImpl;
    }


    @Override
    public String getTableName() {
        return "Booking";
    }
    @Override
    public void startMenu() {
        boolean running = true;
        while (running) {
            System.out.println("1 - Просмотр таблицы, 2 - Добавление объекта, 3 - Обновление, " + "4 - удаление, 5 - поиск по ID, 0 - выход");

            int num = scanner.nextInt();
            scanner.nextLine();
            switch (num) {
                case 1:
                    showAll();
                    break;
                case 2:
                    add();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    find();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный ввод.");
                    break;
            }
        }
    }

    public void showAll() {
        TableViewer.showTable(bookingServiceImpl.getAll());
    }




    @Override
    public void add() {

        System.out.print("ID клиента: ");
        int idClient = Integer.parseInt(scanner.nextLine());
        System.out.print("ID комнаты: ");
        int idRoom = Integer.parseInt(scanner.nextLine());
        System.out.print("ID сотрудника: ");
        int idStaff = Integer.parseInt(scanner.nextLine());
        System.out.print("ID заявки группы: ");
        int idGroup = Integer.parseInt(scanner.nextLine());


        LocalDate arrival = DateParser.parserDate("Введите дату заезда (DD.MM.YYYY)");
        LocalDate departure = DateParser.parserDate("Введите дату выезда (DD.MM.YYYY)");

        if (!InputValidator.isValidDateRange(arrival, departure)) {
            System.out.println("Дата выезда не может быть раньше даты заезда.");
            return;
        }

        int guests;
        while (true) {
            System.out.print("Количество гостей: ");
            guests = Integer.parseInt(scanner.nextLine());
            if (InputValidator.isValidGuestCount(guests)) break;
            System.out.println("Количество гостей должно быть от 1 до 100.");
        }

        LocalDateTime bookingTime;
        System.out.print("Время брони (YYYY-MM-DDTHH:MM) или Enter: ");
        String bt = scanner.nextLine();
        try {
            bookingTime = bt.isBlank()
                    ? LocalDateTime.now()
                    : LocalDateTime.parse(bt);
        } catch (Exception e) {
            System.out.println("Неверный формат времени брони.");
            return;
        }

        System.out.print("Статус (да/нет): ");
        boolean status = InputUtils.readBoolean(scanner);

        BigDecimal price;
        while (true) {
            System.out.print("Введите цену: ");
            try {
                price = new BigDecimal(scanner.nextLine());
                if (InputValidator.isValidPrice(price)) break;
            } catch (Exception ignored) {
            }
            System.out.println("Некорректная цена.");
        }

        Booking booking = new Booking(
                idClient, idRoom, idStaff, idGroup,
                arrival, departure, guests,
                bookingTime, status, price
        );

        bookingServiceImpl.add(booking);

        System.out.println("Бронирование успешно добавлено:");
        TableViewer.showTable(List.of(booking));
    }



    @Override
    public void update() {

        System.out.print("Введите ID брони: ");
        int id = Integer.parseInt(scanner.nextLine());

        Booking existing = bookingServiceImpl.findById(id);
        if (existing == null) {
            System.out.println("Бронирование не найдено.");
            return;
        }

        TableViewer.showTable(List.of(existing));

        int idClient = Integer.parseInt(
                promptWithDefault(scanner, "ID клиента: ", String.valueOf(existing.getIdClient()))
        );

        int idRoom = Integer.parseInt(
                promptWithDefault(scanner, "ID комнаты: ", String.valueOf(existing.getIdLivingRoom()))
        );

        int idStaff = Integer.parseInt(
                promptWithDefault(scanner, "ID сотрудника: ", String.valueOf(existing.getIdStaff()))
        );

        int idGroup = Integer.parseInt(
                promptWithDefault(scanner, "ID заявки группы: ", String.valueOf(existing.getIdGroupApplication()))
        );

        LocalDate arrival = InputUtils.promptDateWithDefault(
                scanner, "дату заезда", existing.getArrivalDate()
        );

        LocalDate departure = InputUtils.promptDateWithDefault(
                scanner, "дату выезда", existing.getDepartureDate()
        );

        if (!InputValidator.isValidDateRange(arrival, departure)) {
            System.out.println("Дата выезда не может быть раньше даты заезда.");
            return;
        }

        int guests;
        while (true) {
            String g = InputUtils.promptWithDefault(
                    scanner, "количество гостей",
                    String.valueOf(existing.getNumberGuests())
            );
            try {
                guests = Integer.parseInt(g);
                if (InputValidator.isValidGuestCount(guests)) break;
            } catch (Exception ignored) {}
            System.out.println("Количество гостей должно быть от 1 до 100.");
        }

        System.out.print("Время брони [" + existing.getBookingTime() + "]: ");
        String bt = scanner.nextLine();
        LocalDateTime bookingTime = bt.isBlank()
                ? existing.getBookingTime()
                : LocalDateTime.parse(bt);

        boolean status = InputUtils.askStatusWithDefault(
                scanner, existing.isStatus()
        );

        BigDecimal price;
        while (true) {
            String p = InputUtils.promptWithDefault(
                    scanner, "цену", existing.getPrice().toString()
            );
            try {
                price = new BigDecimal(p);
                if (InputValidator.isValidPrice(price)) break;
            } catch (Exception ignored) {}
            System.out.println("Некорректная цена.");
        }

        existing.setIdClient(idClient);
        existing.setIdLivingRoom(idRoom);
        existing.setIdStaff(idStaff);
        existing.setIdGroupApplication(idGroup);
        existing.setArrivalDate(arrival);
        existing.setDepartureDate(departure);
        existing.setNumberGuests(guests);
        existing.setBookingTime(bookingTime);
        existing.setStatus(status);
        existing.setPrice(price);

        Booking result = bookingServiceImpl.update(existing);

        if (result != null) {
            System.out.println("Бронирование обновлено:");
            TableViewer.showTable(List.of(result));
        } else {
            System.out.println("Ошибка обновления.");
        }
    }


    @Override
    public void delete() {
        System.out.println("Выберите действие: ");
        System.out.println("1 - удалить одного клиента");
        System.out.println("2 - удалить клиентов в диапазоне ID");

        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) {
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            Booking del = bookingServiceImpl.delete(id);
            if (del != null) {
                System.out.println("Удалено:");
                TableViewer.showTable(List.of(del));
            } else System.out.println("Не найдено");
        } else if (choice == 2){
            System.out.print("Начальный ID: ");
            int s = Integer.parseInt(scanner.nextLine());
            System.out.print("Конечный ID: ");
            int e = Integer.parseInt(scanner.nextLine());
            List<Booking> list = bookingServiceImpl.deleteBookingsInRange(s, e);
            if (list.isEmpty()) System.out.println("В диапазоне ничего нет");
            else {
                System.out.println("Найдено для удаления:");
                TableViewer.showTable(list);
                System.out.print("Подтвердить удаление? (да/нет): ");
                String c = scanner.nextLine();
                if (c.equalsIgnoreCase("да")) {

                    System.out.println("Удалены:");
                    TableViewer.showTable(list);
                } else System.out.println("Отменено");
            }
        }
    }

    @Override
    public void find() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Booking b = bookingServiceImpl.findById(id);
        if (b != null) TableViewer.showTable(List.of(b));
        else System.out.println("Не найдено");
    }

}
