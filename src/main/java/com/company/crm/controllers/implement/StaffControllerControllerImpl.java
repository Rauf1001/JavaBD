package com.company.crm.controllers.implement;

import com.company.crm.controllers.interfaces.StaffController;
import com.company.crm.models.Staff;
import com.company.crm.services.implement.StaffServiceServiceImpl;
import com.company.crm.utils.TableViewer;

import java.util.List;
import java.util.Scanner;

import static com.company.crm.utils.InputUtils.promptWithDefault;
import static com.company.crm.utils.TableViewer.showTable;

public class StaffControllerControllerImpl implements StaffController {


    private final StaffServiceServiceImpl staffServiceImpl;
    private final Scanner scanner = new Scanner(System.in);


    public StaffControllerControllerImpl(StaffServiceServiceImpl staffServiceImpl) {
        this.staffServiceImpl = staffServiceImpl;
    }


    @Override
    public String getTableName() {
        return "Staff";
    }

    @Override
    public void startMenu() {
        boolean running = true;

        while (running) {
            System.out.println("1 - Просмотр таблицы, 2 - Добавление объекта, 3 - Обновление, " + "4 - удаление, 5 - поиск по ID, 0 - выход");


            int num = scanner.nextInt();
            scanner.nextLine();

            switch (num) {
                case 1 -> showAllStaff();
                case 2 -> add();
                case 3 -> update();
                case 4 -> delete();
                case 5 -> find();
                case 0 -> running = false;
                default -> System.out.println("Неверный ввод.");
            }
        }

    }


    private void showAllStaff() {
        System.out.println("Все таблицы и их данные");
        showTable(staffServiceImpl.getAll());

    }

    @Override
    public void add() {
        System.out.print("Введите новое имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите новые паспортные данные: ");
        String passport_data = scanner.nextLine();

        System.out.print("Введите новый номер телефона:  ");
        String phone_number = scanner.nextLine();

        System.out.print("Введите номер трудовой книги: ");
        String staff_book_number = scanner.nextLine();

        System.out.print("Введите опыт работы: ");
        String work_experience = scanner.nextLine();


        Staff newStaff = new Staff(name, passport_data, phone_number, staff_book_number, work_experience);
        staffServiceImpl.add(newStaff);

        System.out.println("Персонал успешно добавлен:");
        showTable(List.of(newStaff));
    }

    @Override
    public void update() {
        System.out.print("Введите ID персонала: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Staff staff = staffServiceImpl.findById(id);
        if (staff == null) {
            System.out.println("Персонал с ID " + id + " не найден.");
            return;
        }

        System.out.println("\nТекущие данные персонала:");
        TableViewer.showTable(List.of(staff));


        String name = promptWithDefault(scanner, "имя: ", staff.getName());

        String passport_data = promptWithDefault(scanner, "паспортные данные: ", staff.getPassport_data());

        String phone_number = promptWithDefault(scanner, "номер телефона: ", staff.getPhone_number());

        String staff_book_number = promptWithDefault(scanner, "номер трудовой книги: ", staff.getPassport_data());

        String work_experience = promptWithDefault(scanner, "опыт работы: ", staff.getWork_experience());

        Staff updated= new Staff(id, name, passport_data, phone_number, staff_book_number, work_experience);

        Staff result = staffServiceImpl.update(updated);
        if (result!= null) {
            System.out.println("Персонал успешно обновлен:");
            showTable(List.of(result));
        }else{
            System.out.println("Ошибка при обовлении персонала");
        }

    }

    @Override
    public void delete() {
        System.out.println("Выберите действие: ");
        System.out.println("1 - удалить одного персонала");
        System.out.println("2 - удалить персонал в диапазоне ID");
        int choice = scanner.nextInt();
        scanner.nextLine();




        if (choice == 1) {
            System.out.print("Введите ID персонала: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Staff deleted = staffServiceImpl.delete(id);
            if (deleted != null) {
                System.out.println("Удален персонал:");
                TableViewer.showTable(List.of(deleted));
            } else {
                System.out.println("Персонал не найден");
            }


        } else if (choice == 2) {
            System.out.print("Введите начальный ID: ");
            int startId = scanner.nextInt();
            System.out.print("Введите конечный ID: ");
            int endId = scanner.nextInt();
            scanner.nextLine();

            List<Staff> staffsToDelete = staffServiceImpl.deleteStaffInRange(startId,endId);

            if (staffsToDelete.isEmpty()) {
                System.out.println("В указанном диапазоне нет персонала.");
                return;
            }

            System.out.println("\nНайденные персоналы для удаления:");
            TableViewer.showTable(staffsToDelete);

            System.out.print("Вы уверены, что хотите удалить этих персоналов? (Да/Нет): ");
            String confirm = scanner.nextLine();


            if(!confirm.equalsIgnoreCase("да") || !confirm.equalsIgnoreCase("Да")  ){
                System.out.println("Удаление отменено");
                return;

            }

            System.out.println("Удалены персоналы:");
            TableViewer.showTable(staffsToDelete);

        }


    }

    @Override
    public void find() {
        System.out.print("Введите ID персонала для поиска: ");
        int id = scanner.nextInt();
        Staff c = staffServiceImpl.findById(id);
        if (c != null) {
            System.out.println("Найден персонал: " + c);
            showTable(List.of(c));
        } else {
            System.out.println("Персонал с ID " + id + " не найден");
        }
    }



}
