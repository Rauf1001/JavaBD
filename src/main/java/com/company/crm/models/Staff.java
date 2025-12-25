package com.company.crm.models;

public class Staff implements IModel {

    private  int id;
    private String name;
    private String passport_data;
    private String phone_number;

    private String staff_book_number;
    private int work_experience;


    public Staff(String name, String passport_data, String phone_number, String staff_book_number, int work_experience) {
        this.name = name;
        this.passport_data = passport_data;
        this.phone_number = phone_number;
        this.staff_book_number = staff_book_number;
        this.work_experience = work_experience;
    }

    public Staff(int id,String name, String passport_data, String phone_number, String staff_book_number, int work_experience) {
        this.id = id;
        this.name = name;
        this.passport_data = passport_data;
        this.phone_number = phone_number;
        this.staff_book_number = staff_book_number;
        this.work_experience = work_experience;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getPassport_data() {
        return passport_data;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getStaff_book_number() {
        return staff_book_number;
    }


    public int  getWork_experience() {
        return work_experience;
    }

    public void setId(int id){
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPassport_data(String passport_data) {
        this.passport_data = passport_data;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setStaff_book_number(String staff_book_number) {
        this.staff_book_number = staff_book_number;
    }

    public void setWork_experience(int work_experience) {
        this.work_experience = work_experience;
    }

    @Override
    public String toString() {
        return "Staff {" +
                "ID = " + id +
                ", Имя = '" + name + '\'' +
                ", Паспорт = '" + passport_data + '\'' +
                ", Телефон = '" + phone_number + '\'' +
                ", Номер трудовой книги = '" + staff_book_number + '\'' +
                ", Опыт работы = " + work_experience +
                '}';
    }
}

