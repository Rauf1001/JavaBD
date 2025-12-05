package com.company.crm.controllers.implement;

import com.company.crm.controllers.interfaces.GenericController;


import java.util.List;
import java.util.Scanner;

public class MainMenuControllerImpl {
    private final List<GenericController> controllers;
    private final Scanner scanner = new Scanner(System.in);


    public MainMenuControllerImpl(List<GenericController> controllers) {
        this.controllers = controllers;
    }

    public void start() {
        while (true) {
            System.out.println("\n   Главное меню  ");

            for (int i = 0; i < controllers.size(); i++) {
                System.out.printf("%d - %s%n", i + 1, controllers.get(i).getTableName());
            }

            System.out.println("0 - Выход");

            System.out.print("Выберите таблицу: ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                break;

            }
            if(choice < 1 || choice > controllers.size()){
                System.out.println("Некорректный выбор!");
                continue;
            }
            controllers.get(choice-1).startMenu();


        }
        System.out.println("Завершение работы программы");


    }


}
