package com.company.crm.controllers.implement;

import com.company.crm.controllers.interfaces.StaffController;
import com.company.crm.models.Staff;
import com.company.crm.services.implement.StaffServiceImpl;
import com.company.crm.utils.InputUtils;
import com.company.crm.utils.InputValidator;
import com.company.crm.utils.TableViewer;

import java.util.List;
import java.util.Scanner;

import static com.company.crm.utils.InputUtils.promptWithDefault;
import static com.company.crm.utils.TableViewer.showTable;

public class StaffControllerImpl implements StaffController {


    private final StaffServiceImpl staffServiceImpl;
    private final Scanner scanner = new Scanner(System.in);


    public StaffControllerImpl(StaffServiceImpl staffServiceImpl) {
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
        showTable(staffServiceImpl.getAll());

    }

    @Override
    public void add() {

        String name;
        do {
            name = InputUtils.readRequired(scanner, "Введите имя");
            if (!InputValidator.isValidName(name)) {
                System.out.println("Имя введено некорректно.");
                name = null;
            }
        } while (name == null);

        String passport;
        do {
            passport = InputUtils.readRequired(scanner, "Введите паспортные данные");
            if (!InputValidator.isValidPassport(passport)) {
                System.out.println("Паспорт должен содержать 10 цифр.");
                passport = null;
            }
        } while (passport == null);
        passport = InputValidator.normalizePassport(passport);

        String phone;
        do {
            phone = InputUtils.readRequired(scanner, "Введите номер телефона");
            if (!InputValidator.isValidPhone(phone)) {
                System.out.println("Некорректный номер телефона.");
                phone = null;
            }
        } while (phone == null);
        phone = InputValidator.normalizePhone(phone);

        String staffBook;
        do {
            staffBook = InputUtils.readRequired(scanner, "Введите номер трудовой книги");
            if (!InputValidator.isValidWorkBook(staffBook)) {
                System.out.println("Номер трудовой книги должен содержать 7–8 цифр.");
                staffBook = null;
            }
        } while (staffBook == null);

        int workExperience =
                InputUtils.readIntRequired(
                        scanner, "Введите опыт работы (в годах)"
                );
        Staff newStaff =
                new Staff(name, passport, phone, staffBook, workExperience);

        staffServiceImpl.add(newStaff);

        System.out.println("Персонал успешно добавлен:");
        showTable(List.of(newStaff));
    }


    @Override
    public void update() {

        int id;
        while (true) {
            System.out.print("Введите ID персонала: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                if (id > 0) break;
            } else {
                scanner.nextLine();
            }
            System.out.println("ID должен быть положительным числом.");
        }

        Staff staff = staffServiceImpl.findById(id);
        if (staff == null) {
            System.out.println("Персонал с ID " + id + " не найден.");
            return;
        }

        System.out.println("\nТекущие данные персонала:");
        TableViewer.showTable(List.of(staff));

        String name;
        do {
            name = InputUtils.promptWithDefault(scanner, "имя", staff.getName());
            if (!InputValidator.isValidName(name)) {
                System.out.println("Некорректное имя.");
                name = null;
            }
        } while (name == null);

        String passport;
        do {
            passport = InputUtils.promptWithDefault(
                    scanner, "паспортные данные", staff.getPassport_data()
            );
            if (!InputValidator.isValidPassport(passport)) {
                System.out.println("Паспорт должен содержать 10 цифр.");
                passport = null;
            }
        } while (passport == null);
        passport = InputValidator.normalizePassport(passport);

        String phone;
        do {
            phone = InputUtils.promptWithDefault(
                    scanner, "номер телефона", staff.getPhone_number()
            );
            if (!InputValidator.isValidPhone(phone)) {
                System.out.println("Некорректный номер телефона.");
                phone = null;
            }
        } while (phone == null);
        phone = InputValidator.normalizePhone(phone);

        String staffBook;
        do {
            staffBook = InputUtils.promptWithDefault(
                    scanner, "номер трудовой книги", staff.getStaff_book_number()
            );
            if (!InputValidator.isValidWorkBook(staffBook)) {
                System.out.println("Номер трудовой книги должен содержать 7–8 цифр.");
                staffBook = null;
            }
        } while (staffBook == null);

        int workExperience =
                InputUtils.promptIntWithDefault(
                        scanner, "опыт работы", staff.getWork_experience()
                );


        Staff updated =
                new Staff(id, name, passport, phone, staffBook, workExperience);

        Staff result = staffServiceImpl.update(updated);

        if (result != null) {
            System.out.println("Персонал успешно обновлен:");
            showTable(List.of(result));
        } else {
            System.out.println("Ошибка при обновлении персонала.");
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

        int id;
        while (true) {
            System.out.print("Введите ID персонала: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine();
                if (id > 0) break;
            } else {
                scanner.nextLine();
            }
            System.out.println("ID должен быть положительным числом.");
        }

        Staff staff = staffServiceImpl.findById(id);

        if (staff != null) {
            showTable(List.of(staff));
        } else {
            System.out.println("Персонал с ID " + id + " не найден.");
        }
    }




}
