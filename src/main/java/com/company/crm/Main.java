package com.company.crm;

import com.company.crm.config.ApplicationFactory;
import com.company.crm.controllers.implement.MainMenuControllerImpl;


public class Main {
    public static void main(String[] args) {
        MainMenuControllerImpl app = ApplicationFactory.createApplication();
        app.start();


    }


}

