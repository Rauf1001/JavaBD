package com.company.crm.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static  String URL;
    private static  String USER;
    private static  String PASSWORD;

    static {
        try {
            Properties properties = new Properties();
            InputStream input = DatabaseConnection.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("Файл bd.properties не найден");
            }
            properties.load(input);

            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");

            Class.forName("org.postgresql.Driver");


        } catch (Exception e) {
            System.err.println("Не удалось загрузить драйвер PostgreSQL");
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при подключении к БД", e);
        }
    }
}
