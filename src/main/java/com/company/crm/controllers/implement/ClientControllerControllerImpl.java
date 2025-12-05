package com.company.crm.controllers.implement;

import com.company.crm.controllers.interfaces.ClientController;
import com.company.crm.models.Client;
import com.company.crm.services.implement.ClientServiceServiceImpl;
import com.company.crm.utils.DateParser;
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
        System.out.print("Введите новое имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите новый email: ");
        String email = scanner.nextLine();
        System.out.print("Введите новый номер телефона: ");
        String phone_number = scanner.nextLine();
        System.out.print("Введите новые паспортные данные: ");
        String passport_data = scanner.nextLine();
        System.out.print("Введите новую дату рождения в формате 'DD.MM.YYYY': ");
        LocalDate birth_date = readBirthDate();

        Client newClient = new Client(name, email, phone_number, passport_data, birth_date);
        clientServiceImpl.add(newClient);

        System.out.println("Клиент успешно добавлен:");
        showTable(List.of(newClient));
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

        System.out.println("\nТекущие данные клиента:");
        TableViewer.showTable(List.of(client));


        String name = promptWithDefault(scanner, "имя: ", client.getName());

        String email = promptWithDefault(scanner, "email: ", client.getEmail());

        String phone_number = promptWithDefault(scanner, "номер телефона: ", client.getPhone_number());

        String passport_data = promptWithDefault(scanner, "паспортные данные: ", client.getPassport_data());

        LocalDate birth_date = promptDateWithDefault(scanner, "дату рождения: ", client.getBirth_date());

        Client updated = new Client(id, name, email, phone_number, passport_data, birth_date);
        Client result = clientServiceImpl.update(updated);
        if (result!=null){
            System.out.println("Клиент успешно обновлен:");
            showTable(List.of(result));
        }else{
            System.out.println("Ошибка при обновлении Клиента");
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
        System.out.print("Введите ID клиента для поиска: ");
        int id = scanner.nextInt();
        Client c = clientServiceImpl.findById(id);
        if (c != null) {
            System.out.println("Найден клиент: " + c);
            showTable(List.of(c));
        } else {
            System.out.println("Клиент с ID " + id + " не найден");
        }
    }

    private LocalDate readBirthDate() {
        LocalDate birthDate = null;
        while (birthDate == null) {

            String input = scanner.nextLine();
            birthDate = DateParser.parserDate(input);
            if (birthDate == null) {
                System.out.print("Неверный формат ввода даты! Пожалуйста введите по примеру 'DD.MM.YYYY': ");
            }
        }
        return birthDate;
    }


}