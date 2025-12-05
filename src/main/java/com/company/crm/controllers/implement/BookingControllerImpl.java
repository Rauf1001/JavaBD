package com.company.crm.controllers.implement;

import com.company.crm.controllers.interfaces.BookingController;

import com.company.crm.services.implement.BookingServiceServiceImpl;
import com.company.crm.models.Booking;
import com.company.crm.utils.DateParser;
import com.company.crm.utils.TableViewer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static com.company.crm.utils.InputUtils.promptDateWithDefault;
import static com.company.crm.utils.InputUtils.promptWithDefault;

public class BookingControllerImpl implements BookingController {
    private final BookingServiceServiceImpl bookingServiceImpl;
    private final Scanner scanner = new Scanner(System.in);

    public BookingControllerImpl(BookingServiceServiceImpl bookingServiceImpl) {
        this.bookingServiceImpl = bookingServiceImpl;
    }


    @Override
    public String getTableName() {
        return "Booking";
    }
    @Override
    public void startMenu() {
        boolean run = true;
        while (run) {
            System.out.println("1 - Просмотр таблицы, 2 - Добавление объекта, 3 - Обновление, " + "4 - удаление, 5 - поиск по ID, 0 - выход");

            int opt = scanner.nextInt();
            scanner.nextLine();
            switch (opt) {
                case 1 -> showAll();
                case 2 -> add();
                case 3 -> update();
                case 4 -> delete();
                case 5 -> find();
                case 0 -> run = false;
                default -> System.out.println("Неверный ввод");
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
        System.out.print("Дата заезда DD.MM.YYYY: ");
        LocalDate arrival = DateParser.parserDate(scanner.nextLine());
        System.out.print("Дата выезда DD.MM.YYYY: ");
        LocalDate departure = DateParser.parserDate(scanner.nextLine());
        System.out.print("Количество гостей: ");
        int guests = Integer.parseInt(scanner.nextLine());
        System.out.print("Время брони (YYYY-MM-DDTHH:MM) или пусто: ");
        String bt = scanner.nextLine();
        LocalDateTime bookingTime = bt.isBlank() ? LocalDateTime.now() : LocalDateTime.parse(bt);
        System.out.print("Статус (да/нет): ");
        boolean status = readBoolean(scanner);
        System.out.print("Цена (например 1000.00): ");
        BigDecimal price = new BigDecimal(scanner.nextLine());

        Booking b = new Booking(idClient, idRoom, idStaff, idGroup, arrival, departure, guests, bookingTime, status, price);
        bookingServiceImpl.add(b);
        System.out.println("Добавлено:");
        TableViewer.showTable(List.of(b));
    }

    @Override
    public void update() {
        System.out.print("Введите ID брони: ");
        int id = Integer.parseInt(scanner.nextLine());
        Booking existing = bookingServiceImpl.findById(id);
        if (existing == null) {
            System.out.println("Не найдено");
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

        LocalDate arrival = promptDateWithDefault(scanner, "Дата заезда: ", existing.getArrivalDate());
        LocalDate departure = promptDateWithDefault(scanner, "Дата выезда: ", existing.getDepartureDate());

        int guests = Integer.parseInt(
                promptWithDefault(scanner, "Количество гостей: ", String.valueOf(existing.getNumberGuests()))
        );

        System.out.print("Время брони (YYYY-MM-DDTHH:MM) [" +
                (existing.getBookingTime() == null ? "" : existing.getBookingTime()) + "]: ");

        String bt = scanner.nextLine();
        LocalDateTime bookingTime = bt.isBlank()
                ? existing.getBookingTime()
                : LocalDateTime.parse(bt);

        boolean status = askStatusWithDefault(scanner, existing.isStatus());

        BigDecimal price = new BigDecimal(
                promptWithDefault(scanner, "Цена: ", existing.getPrice().toString())
        );
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
            System.out.println("Обновлено:");
            TableViewer.showTable(List.of(result));
        } else System.out.println("Ошибка обновления");
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



    private static boolean readBoolean(Scanner scanner) {
        while (true) {
            String s = scanner.nextLine().trim().toLowerCase();
            if (s.equals("да") || s.equals("y") || s.equals("yes") || s.equals("true") || s.equals("1")) return true;
            if (s.equals("нет") || s.equals("n") || s.equals("no") || s.equals("false") || s.equals("0")) return false;
            System.out.print("Введите да/нет: ");
        }
    }

    private static boolean askStatusWithDefault(Scanner scanner, boolean def) {
        System.out.print("Статус (да/нет) [" + (def ? "да" : "нет") + "]: ");
        String s = scanner.nextLine().trim();
        if (s.isEmpty()) return def;
        return s.equalsIgnoreCase("да") || s.equalsIgnoreCase("y") || s.equalsIgnoreCase("1") || s.equalsIgnoreCase("true");
    }
}
