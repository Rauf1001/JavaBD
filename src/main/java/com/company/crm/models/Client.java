package com.company.crm.models;

import java.time.LocalDate;

public class Client implements IModel{
    private  int id;

    private String name;
    private String email;
    private String phone_number;

    private String passport_data;
    private LocalDate birth_date;


    public Client(String name,  String email, String phone_number, String passport_data, LocalDate birth_date) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.passport_data = passport_data;
        this.birth_date = birth_date;
    }

    public Client(int id,String name,  String email, String phone_number, String passport_data, LocalDate birth_date){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.passport_data = passport_data;
        this.birth_date = birth_date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getPassport_data() {
        return passport_data;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }


    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassport_data(String passport_data) {
        this.passport_data = passport_data;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }


    @Override
    public String toString() {
        return "Client {" +
                "ID = " + id +
                ", Имя = '" + name + '\'' +
                ", Email = '" + email + '\'' +
                ", Телефон = '" + phone_number + '\'' +
                ", Паспорт = '" + passport_data + '\'' +
                ", Дата рождения = " + birth_date +
                '}';
    }
}



