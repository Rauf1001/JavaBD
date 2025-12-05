package com.company.crm.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/BD-atta";
    private static final String USER = "postgres";
    private static final String PASSWORD = "6525";

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("JDBC драйвер PostgreSQL загружен");
        } catch (ClassNotFoundException e) {
            System.err.println("Не удалось загрузить драйвер PostgreSQL");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Ошибка при подключении к БД");
            throw new RuntimeException(e);
        }
    }
}
