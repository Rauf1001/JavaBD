package com.company.crm.controllers.implement;

import com.company.crm.controllers.interfaces.GroupApplicationController;

import com.company.crm.services.implement.GroupApplicationServiceServiceImpl;
import com.company.crm.models.GroupApplication;
import com.company.crm.utils.DateParser;
import com.company.crm.utils.InputUtils;
import com.company.crm.utils.TableViewer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static com.company.crm.utils.InputUtils.promptDateWithDefault;
import static com.company.crm.utils.InputUtils.promptWithDefault;

public class GroupApplicationControllerImpl implements GroupApplicationController {
    private final GroupApplicationServiceServiceImpl service;
    private final Scanner scanner = new Scanner(System.in);

    public GroupApplicationControllerImpl(GroupApplicationServiceServiceImpl service) {
        this.service = service;
    }

    @Override
    public String getTableName() {
        return "Group_application";
    }
    @Override
    public void startMenu() {
        boolean run = true;
        while (run) {
            System.out.println("1 - Просмотр таблицы, 2 - Добавление объекта, 3 - Обновление, " + "4 - удаление, 5 - поиск по ID, 0 - выход");

            int opt = Integer.parseInt(scanner.nextLine());
            switch (opt) {
                case 1 -> TableViewer.showTable(service.getAll());
                case 2 -> add();
                case 3 -> update();
                case 4 -> delete();
                case 5 -> find();
                case 0 -> run = false;
                default -> System.out.println("Неверный ввод");
            }
        }
    }

    @Override
    public void add() {
        System.out.print("ID комнаты: ");
        int idRoom = Integer.parseInt(scanner.nextLine());
        System.out.print("Дата заезда (DD.MM.YYYY): ");
        LocalDate arrival = DateParser.parserDate(scanner.nextLine());
        System.out.print("Дата выезда (DD.MM.YYYY): ");
        LocalDate departure = DateParser.parserDate(scanner.nextLine());
        System.out.print("Цена: ");
        BigDecimal price = new BigDecimal(scanner.nextLine());
        System.out.print("Статус (да/нет): ");
        boolean status = InputUtils.readBoolean(scanner);
        System.out.print("Комментарий: ");
        String comment = scanner.nextLine();

        GroupApplication g = new GroupApplication(idRoom, arrival, departure, price, status, comment);
        service.add(g);
        System.out.println("Добавлено:");
        TableViewer.showTable(List.of(g));
    }

    @Override
    public void update() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        GroupApplication existing = service.findById(id);
        if (existing == null) {
            System.out.println("Не найдено");
            return;
        }

        TableViewer.showTable(List.of(existing));

        int idRoom = Integer.parseInt(
                promptWithDefault(scanner, "ID комнаты: ", String.valueOf(existing.getIdLivingRoom()))
        );

        LocalDate arrival = promptDateWithDefault(scanner, "Дата заезда: ", existing.getArrivalDate());
        LocalDate departure = promptDateWithDefault(scanner, "Дата выезда: ", existing.getDepartureDate());

        BigDecimal price = new BigDecimal(
                promptWithDefault(scanner, "Цена: ", existing.getPrice().toString())
        );

        boolean status = InputUtils.askStatusWithDefault(scanner, existing.isStatus());

        String comment = promptWithDefault(scanner, "Комментарий: ", existing.getComment());


        existing.setIdLivingRoom(idRoom);
        existing.setArrivalDate(arrival);
        existing.setDepartureDate(departure);
        existing.setPrice(price);
        existing.setStatus(status);
        existing.setComment(comment);


        GroupApplication result = service.update(existing);

        if (result != null) {
            System.out.println("Обновлено:");
            TableViewer.showTable(List.of(result));
        } else {
            System.out.println("Ошибка");
        }
    }


    @Override
    public void delete() {
        System.out.println("1 - один, 2 - диапазон");
        int ch = Integer.parseInt(scanner.nextLine());
        if (ch == 1) {
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            GroupApplication del = service.delete(id);
            if (del != null) {
                System.out.println("Удалено:");
                TableViewer.showTable(List.of(del));
            } else System.out.println("Не найдено");
        } else {
            System.out.print("Начальный ID: ");
            int s = Integer.parseInt(scanner.nextLine());
            System.out.print("Конечный ID: ");
            int e = Integer.parseInt(scanner.nextLine());
            List<GroupApplication> list = service.deleteGroup_applicationInRange(s, e);
            if (list.isEmpty()) System.out.println("В диапазоне нет");
            else {
                System.out.println("Найдено:");
                TableViewer.showTable(list);
                System.out.print("Подтвердить удаление? (да/нет): ");
                String c = scanner.nextLine();
                if (c.equalsIgnoreCase("да")) System.out.println("Удалены:");
                TableViewer.showTable(list);
            }
        }
    }

    @Override
    public void find() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        GroupApplication g = service.findById(id);
        if (g != null) TableViewer.showTable(List.of(g));
        else System.out.println("Не найдено");
    }


}
