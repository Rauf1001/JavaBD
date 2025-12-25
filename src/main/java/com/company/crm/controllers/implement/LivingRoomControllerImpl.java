package com.company.crm.controllers.implement;

import com.company.crm.controllers.interfaces.LivingRoomController;
import com.company.crm.models.LivingRoom;
import com.company.crm.services.implement.LivingRoomServiceImpl;
import com.company.crm.utils.InputUtils;

import java.util.List;
import java.util.Scanner;

import static com.company.crm.utils.InputUtils.promptWithDefault;
import static com.company.crm.utils.TableViewer.showTable;

public class LivingRoomControllerImpl implements LivingRoomController {

    private final LivingRoomServiceImpl living_roomServiceImpl;
    private final Scanner scanner = new Scanner(System.in);

    public LivingRoomControllerImpl(LivingRoomServiceImpl living_roomServiceImpl) {
        this.living_roomServiceImpl = living_roomServiceImpl;
    }

    @Override
    public String getTableName() {
        return "Living_room";
    }

    @Override
    public void startMenu() {
        boolean running = true;

        while (running) {
            System.out.println("1 - Просмотр, 2 - Добавление, 3 - Обновление, 4 - Удаление, 5 - Поиск, 0 - Выход");

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

    private void showAll() {
        System.out.println("Все таблицы и их данные");
        showTable(living_roomServiceImpl.getAll());

    }

    @Override
    public void add() {

        String roomNumber;
        do {
            roomNumber = InputUtils.readRequired(scanner, "Введите номер комнаты");
        } while (roomNumber.isBlank());

        String location;
        do {
            location = InputUtils.readRequired(scanner, "Введите местоположение");
        } while (location.isBlank());

        int status;
        while (true) {
            System.out.print("Введите статус (0 — свободна, 1 — занята, 2 — ремонт, 3 — недоступна): ");
            if (scanner.hasNextInt()) {
                status = scanner.nextInt();
                scanner.nextLine();
                if (status >= 0 && status <= 3) break;
            } else {
                scanner.nextLine();
            }
            System.out.println("Статус должен быть числом от 0 до 3.");
        }

        LivingRoom newRoom = new LivingRoom(roomNumber, location, status);
        living_roomServiceImpl.add(newRoom);

        System.out.println("Комната успешно добавлена:");
        showTable(List.of(newRoom));
    }


    @Override
    public void update() {

        int id;
        while (true) {
            System.out.print("Введите ID комнаты: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                if (id > 0) break;
            } else {
                scanner.nextLine();
            }
            System.out.println("ID должен быть положительным числом.");
        }

        LivingRoom existing = living_roomServiceImpl.findById(id);
        if (existing == null) {
            System.out.println("Комната не найдена.");
            return;
        }

        showTable(List.of(existing));

        String roomNumber;
        do {
            roomNumber = InputUtils.promptWithDefault(
                    scanner, "номер комнаты", existing.getRoom_number()
            );
            if (roomNumber.isBlank()) {
                System.out.println("Номер комнаты не может быть пустым.");
            }
        } while (roomNumber.isBlank());

        String location;
        do {
            location = InputUtils.promptWithDefault(
                    scanner, "местоположение", existing.getLocation()
            );
            if (location.isBlank()) {
                System.out.println("Местоположение не может быть пустым.");
            }
        } while (location.isBlank());

        int status;
        while (true) {
            String statusStr = InputUtils.promptWithDefault(
                    scanner, "статус (0/1/2/3)", String.valueOf(existing.getStatus())
            );
            try {
                status = Integer.parseInt(statusStr);
                if (status >= 0 && status <= 3) break;
            } catch (Exception ignored) {}
            System.out.println("Статус должен быть числом от 0 до 3.");
        }

        LivingRoom updated = new LivingRoom(id, roomNumber, location, status);
        LivingRoom result = living_roomServiceImpl.update(updated);

        if (result != null) {
            System.out.println("Комната успешно обновлена:");
            showTable(List.of(result));
        } else {
            System.out.println("Ошибка при обновлении комнаты.");
        }
    }


    @Override
    public void delete() {
        System.out.println("1 — удалить одну комнату");
        System.out.println("2 — удалить по диапазону ID");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1) {
            System.out.print("Введите ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            LivingRoom deleted = living_roomServiceImpl.delete(id);
            if (deleted != null) {
                System.out.println("Удалено:");
                showTable(List.of(deleted));
            } else {
                System.out.println("Не найдено");
            }

        } else if (choice == 2) {
            System.out.print("Начальный ID: ");
            int s = Integer.parseInt(scanner.nextLine());
            System.out.print("Конечный ID: ");
            int e = Integer.parseInt(scanner.nextLine());

            List<LivingRoom> list = living_roomServiceImpl.deleteLivingRoomsInRange(s, e);

            if (list.isEmpty()) {
                System.out.println("В диапазоне нет комнат");
                return;
            }

            System.out.println("Найдено:");
            showTable(list);

            System.out.print("Подтвердить удаление (да/нет): ");
            String c = scanner.nextLine();

            if (c.equalsIgnoreCase("да")) {
                System.out.println("Удалены:");
                showTable(list);
            } else {
                System.out.println("Отменено");
            }
        }
    }

    @Override
    public void find() {

        int id;
        while (true) {
            System.out.print("Введите ID комнаты: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                if (id > 0) break;
            } else {
                scanner.nextLine();
            }
            System.out.println("ID должен быть положительным числом.");
        }

        LivingRoom room = living_roomServiceImpl.findById(id);

        if (room != null) {
            showTable(List.of(room));
        } else {
            System.out.println("Комната не найдена.");
        }
    }

}
