package com.company.crm.models;

public class LivingRoom {


    private  int id;
    private String room_number;
    private String location;

    private int status;


    public LivingRoom(String room_number, String location, int status) {
        this.room_number = room_number;
        this.location = location;
        this.status = status;

    }

    public LivingRoom(int id, String room_number, String location, int status) {
        this.id = id;
        this.room_number = room_number;
        this.location = location;
        this.status = status;

    }

    public int getId() {
        return id;
    }


    public String getRoom_number() {
        return room_number;
    }


    public String getLocation() {
        return location;
    }

    public int getStatus(){return status;}


    public void setId(int id){this.id = id;}



    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public void setStatus(int status){
        this.status = status;
    }

    @Override
    public String toString() {
        return "Staff {" +
                "ID = " + id +
                ", Номер комнаты = '" + room_number + '\'' +
                ", Местоположение = '" + location + '\'' +
                ", Статус = '" + location + '\'' +
                '}';
    }
}
