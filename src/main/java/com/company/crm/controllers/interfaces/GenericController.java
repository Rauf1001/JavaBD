package com.company.crm.controllers.interfaces;

import java.util.List;

public interface GenericController {
    void startMenu();

    void add();

    void delete();

    void update();

    void find();

    String getTableName();
}
