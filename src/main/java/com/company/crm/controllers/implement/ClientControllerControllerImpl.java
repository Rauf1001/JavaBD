package com.company.crm.controllers.implement;

import com.company.crm.controllers.interfaces.ClientController;
import com.company.crm.models.Client;
import com.company.crm.services.implement.ClientServiceServiceImpl;
import com.company.crm.utils.DateParser;
import com.company.crm.utils.InputUtils;
import com.company.crm.utils.InputValidator;
import com.company.crm.utils.TableViewer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static com.company.crm.utils.InputUtils.promptDateWithDefault;
import static com.company.crm.utils.InputUtils.promptWithDefault;
import static com.company.crm.utils.TableViewer.showTable;

public class ClientControllerControllerImpl implements ClientController {
    private final ClientServiceServiceImpl clientServiceImpl;
    private final Scanner scanner = new Scanner(System.in);

    public ClientControllerControllerImpl(ClientServiceServiceImpl clientServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
    }


    @Override
    public String getTableName() {
        return "Client";
    }

    @Override
    public void startMenu() {
        boolean running = true;

        while (running) {
            System.out.println("1 - Просмотр таблицы, 2 - Добавление объекта, 3 - Обновление, " + "4 - удаление, 5 - поиск по ID, 0 - выход");


            int num = scanner.nextInt();
            scanner.nextLine();

            switch (num) {
                case 1 -> showAllClient();
                case 2 -> add();
                case 3 -> update();
                case 4 -> delete();
                case 5 -> find();
                case 0 -> running = false;
                default -> System.out.println("Неверный ввод.");
            }
        }

    }


    private void showAllClient() {
        System.out.println("Все таблицы и их данные");
        showTable(clientServiceImpl.getAll());

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

        String email;
        do {
            email = InputUtils.readRequired(scanner, "Введите email");
            if (!InputValidator.isValidEmail(email)) {
                System.out.println("Некорректный email.");
                email = null;
            }
        } while (email == null);

        String phone;
        do {
            phone = InputUtils.readRequired(scanner, "Введите номер телефона");
            if (!InputValidator.isValidPhone(phone)) {
                System.out.println("Некорректный номер телефона.");
                phone = null;
            }
        } while (phone == null);
        phone = InputValidator.normalizePhone(phone);


        String passport;
        do {
            passport = InputUtils.readRequired(scanner, "Введите паспортные данные");
            if (!InputValidator.isValidPassport(passport)) {
                System.out.println("Некорректные паспортные данные.");
                passport = null;
            }
        } while (passport == null);
        passport = InputValidator.normalizePassport(passport);

        LocalDate birthDate = readBirthDate();

        Client newClient = new Client(name, email, phone, passport, birthDate);
        clientServiceImpl.add(newClient);

        System.out.println("Клиент успешно добавлен:");
        TableViewer.showTable(List.of(newClient));
    }

    @Override
    public void update() {

        System.out.print("Введите ID клиента: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Client client = clientServiceImpl.findById(id);
        if (client == null) {
            System.out.println("Клиент с ID " + id + " не найден.");
            return;
        }

        TableViewer.showTable(List.of(client));

        String name;
        do {
            name = InputUtils.promptWithDefault(scanner, "имя", client.getName());
            if (!InputValidator.isValidName(name)) {
                System.out.println("Некорректное имя.");
                name = null;
            }
        } while (name == null);

        String email;
        do {
            email = InputUtils.promptWithDefault(scanner, "email", client.getEmail());
            if (!InputValidator.isValidEmail(email)) {
                System.out.println("Некорректный email.");
                email = null;
            }
        } while (email == null);

        String phone;
        do {
            phone = InputUtils.promptWithDefault(scanner, "номер телефона", client.getPhone_number());
            if (!InputValidator.isValidPhone(phone)) {
                System.out.println("Некорректный номер телефона.");
                phone = null;
            }
        } while (phone == null);

        phone = InputValidator.normalizePhone(phone);

        String passport;
        do {
            passport = InputUtils.promptWithDefault(scanner, "паспортные данные", client.getPassport_data());
            if (!InputValidator.isValidPassport(passport)) {
                System.out.println("Некорректные паспортные данные.");
                passport = null;
            }
        } while (passport == null);

        passport = InputValidator.normalizePassport(passport);


        LocalDate birthDate =
                InputUtils.promptDateWithDefault(scanner, "дату рождения", client.getBirth_date());

        Client updated = new Client(id, name, email, phone, passport, birthDate);
        Client result = clientServiceImpl.update(updated);

        if (result != null) {
            System.out.println("Клиент успешно обновлен:");
            TableViewer.showTable(List.of(result));
        } else {
            System.out.println("Ошибка при обновлении клиента.");
        }
    }


    @Override
    public void delete() {
        System.out.println("Выберите действие: ");
        System.out.println("1 - удалить одного клиента");
        System.out.println("2 - удалить клиентов в диапазоне ID");
        int choice = scanner.nextInt();
        scanner.nextLine();




        if (choice == 1) {
            System.out.print("Введите ID клиента: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Client deleted = clientServiceImpl.delete(id);
            if (deleted != null) {
                System.out.println("Удален клиент:");
                TableViewer.showTable(List.of(deleted));
            } else {
                System.out.println("Клиент не найден");
            }


        } else if (choice == 2) {
            System.out.print("Введите начальный ID: ");
            int startId = scanner.nextInt();

            System.out.print("Введите конечный ID: ");
            int endId = scanner.nextInt();
            scanner.nextLine();

            List<Client> clientsToDelete = new ArrayList<>();

            for ( int id = startId; id <= endId; id++){
                Client client = clientServiceImpl.findById(id);
                if ( client != null){
                    clientsToDelete.add(client);
                }

            }


            if (clientsToDelete.isEmpty()) {
                System.out.println("В указанном диапазоне нет клиентов.");
                return;
            }

            System.out.println("\nНайденные клиенты для удаления:");
            TableViewer.showTable(clientsToDelete);

            System.out.print("Вы уверены, что хотите удалить этих клиентов? (Да/Нет): ");
            String confirm = scanner.nextLine();

            Set<String> yes = Set.of("да","ага","yes","jf");

            if(!yes.contains(confirm.toLowerCase())){
                System.out.println("Удаление отменено");
                return;

            }


            clientServiceImpl.deleteClientsInRange(startId,endId);
            System.out.println("Удалены клиенты:");
            TableViewer.showTable(clientsToDelete);

        }


    }

    @Override
    public void find() {
        System.out.print("Введите ID клиента: ");

        if (!scanner.hasNextInt()) {
            System.out.println("ID должен быть числом.");
            scanner.nextLine();
            return;
        }

        int id = scanner.nextInt();
        scanner.nextLine();

        Client c = clientServiceImpl.findById(id);
        if (c != null) {
            TableViewer.showTable(List.of(c));
        } else {
            System.out.println("Клиент не найден.");
        }
    }


    private LocalDate readBirthDate() {
        LocalDate birthDate = null;
        while (birthDate == null) {
            System.out.print("Введите дату рождения (DD.MM.YYYY): ");
            String input = scanner.nextLine();
            birthDate = DateParser.parserDate(input);
            if (birthDate == null) {
                System.out.print("Неверный формат ввода даты! Пожалуйста введите по примеру 'DD.MM.YYYY': ");
            }
        }
        return birthDate;
    }


}