package com.company.crm.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GroupApplication implements IModel {
    private int id;
    private int idLivingRoom;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private BigDecimal price;
    private boolean status; // tinyint(1) -> boolean
    private String comment;

    public GroupApplication(int idLivingRoom, LocalDate arrivalDate, LocalDate departureDate,
                            BigDecimal price, boolean status, String comment) {
        this.idLivingRoom = idLivingRoom;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.price = price;
        this.status = status;
        this.comment = comment;
    }

    public GroupApplication(int id, int idLivingRoom, LocalDate arrivalDate, LocalDate departureDate,
                            BigDecimal price, boolean status, String comment) {
        this(idLivingRoom, arrivalDate, departureDate, price, status, comment);
        this.id = id;
        this.idLivingRoom = idLivingRoom;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLivingRoom() {
        return idLivingRoom;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public void setIdLivingRoom(int idLivingRoom) {
        this.idLivingRoom = idLivingRoom;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Group_application{" +
                "ID=" + id +
                ", ID_LivingRoom=" + idLivingRoom +
                ", Arrival=" + arrivalDate +
                ", Departure=" + departureDate +
                ", Price=" + price +
                ", Status=" + status +
                ", Comment='" + comment + '\'' +
                '}';
    }
}