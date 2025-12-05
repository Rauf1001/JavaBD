package com.company.crm.controllers.implement;

import com.company.crm.controllers.interfaces.LivingRoomController;
import com.company.crm.models.LivingRoom;
import com.company.crm.services.implement.LivingRoomServiceServiceImpl;

import java.util.List;
import java.util.Scanner;

import static com.company.crm.utils.InputUtils.promptWithDefault;
import static com.company.crm.utils.TableViewer.showTable;

public class LivingRoomControllerControllerImpl implements LivingRoomController {

    private final LivingRoomServiceServiceImpl living_roomServiceImpl;
    private final Scanner scanner = new Scanner(System.in);

    public LivingRoomControllerControllerImpl(LivingRoomServiceServiceImpl living_roomServiceImpl) {
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
                case 1 -> showAll();
                case 2 -> add();
                case 3 -> update();
                case 4 -> delete();
                case 5 -> find();
                case 0 -> running = false;
                default -> System.out.println("Неверный ввод.");
            }
        }
    }

    private void showAll() {
        showTable(living_roomServiceImpl.getAll());
    }

    @Override
    public void add() {
        System.out.print("Введите номер комнаты: ");
        String room_number = scanner.nextLine();

        System.out.print("Введите местоположение: ");
        String location = scanner.nextLine();

        System.out.print("Введите статус (0 — свободна, 1 — занята, 2 — ремонт, 3 — недоступна): ");
        int status = Integer.parseInt(scanner.nextLine());

        LivingRoom newLiving_room = new LivingRoom(room_number, location, status);
        living_roomServiceImpl.add(newLiving_room);

        System.out.println("Комната добавлена:");
        showTable(List.of(newLiving_room));
    }

    @Override
    public void update() {
        System.out.print("Введите ID комнаты: ");
        int id = Integer.parseInt(scanner.nextLine());

        LivingRoom existing = living_roomServiceImpl.findById(id);
        if (existing == null) {
            System.out.println("Комната не найдена.");
            return;
        }

        showTable(List.of(existing));

        String room_number = promptWithDefault(scanner, "номер комнаты: ", existing.getRoom_number());
        String location = promptWithDefault(scanner, "местоположение: ", existing.getLocation());
        String statusStr = promptWithDefault(scanner, "статус (0/1/2/3): ", String.valueOf(existing.getStatus()));

        int status = Integer.parseInt(statusStr);

        LivingRoom updated = new LivingRoom(id, room_number, location, status);
        LivingRoom result = living_roomServiceImpl.update(updated);

        if (result != null) {
            System.out.println("Обновлено:");
            showTable(List.of(result));
        } else {
            System.out.println("Ошибка обновления");
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
        System.out.print("Введите ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        LivingRoom lr = living_roomServiceImpl.findById(id);
        if (lr != null) {
            showTable(List.of(lr));
        } else {
            System.out.println("Не найдено");
        }
    }
}
