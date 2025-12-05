package com.company.crm.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking implements IModel {
    private int id;
    private int idClient;
    private int idLivingRoom;
    private int idStaff;
    private int idGroupApplication;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private int numberGuests;
    private LocalDateTime bookingTime;
    private boolean status; // tinyint(1) -> boolean
    private BigDecimal price;

    public Booking(int idClient, int idLivingRoom, int idStaff, int idGroupApplication,
                   LocalDate arrivalDate, LocalDate departureDate, int numberGuests,
                   LocalDateTime bookingTime, boolean status, BigDecimal price) {
        this.idClient = idClient;
        this.idLivingRoom = idLivingRoom;
        this.idStaff = idStaff;
        this.idGroupApplication = idGroupApplication;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.numberGuests = numberGuests;
        this.bookingTime = bookingTime;
        this.status = status;
        this.price = price;
    }

    public Booking(int id, int idClient, int idLivingRoom, int idStaff, int idGroupApplication,
                   LocalDate arrivalDate, LocalDate departureDate, int numberGuests,
                   LocalDateTime bookingTime, boolean status, BigDecimal price) {
        this(arrivalDate == null ? 0 : idClient, idLivingRoom, idStaff, idGroupApplication, arrivalDate, departureDate, numberGuests, bookingTime, status, price);
        this.id = id;
        this.idClient = idClient;
        this.idLivingRoom = idLivingRoom;
        this.idStaff = idStaff;
        this.idGroupApplication = idGroupApplication;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdLivingRoom() {
        return idLivingRoom;
    }

    public int getIdStaff() {
        return idStaff;
    }

    public int getIdGroupApplication() {
        return idGroupApplication;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public int getNumberGuests() {
        return numberGuests;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public boolean isStatus() {
        return status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdLivingRoom(int idLivingRoom) {
        this.idLivingRoom = idLivingRoom;
    }

    public void setIdStaff(int idStaff) {
        this.idStaff = idStaff;
    }

    public void setIdGroupApplication(int idGroupApplication) {
        this.idGroupApplication = idGroupApplication;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public void setNumberGuests(int numberGuests) {
        this.numberGuests = numberGuests;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "ID=" + id +
                ", ID_Client=" + idClient +
                ", ID_LivingRoom=" + idLivingRoom +
                ", ID_Staff=" + idStaff +
                ", ID_GroupApplication=" + idGroupApplication +
                ", Arrival=" + arrivalDate +
                ", Departure=" + departureDate +
                ", Guests=" + numberGuests +
                ", BookingTime=" + bookingTime +
                ", Status=" + status +
                ", Price=" + price +
                '}';
    }
}
