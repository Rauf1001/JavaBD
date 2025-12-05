package com.company.crm.mappers;

import com.company.crm.models.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class BookingMapper {

    public static Booking map(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        int idClient = rs.getInt("ID_Client");
        int idLivingRoom = rs.getInt("ID_Living_room");
        int idStaff = rs.getInt("ID_Staff");
        int idGroupApp = rs.getInt("ID_Group_application");
        LocalDate arrival = rs.getDate("Arrival_date").toLocalDate();
        LocalDate departure = rs.getDate("Departure_date").toLocalDate();

        int guests = rs.getInt("Number_guests");
        Timestamp ts = rs.getTimestamp("Booking_time");
        LocalDateTime bookingTime = ts != null ? ts.toLocalDateTime() : null;
        boolean status = rs.getBoolean("Status");
        BigDecimal price = rs.getBigDecimal("Price");

        return new Booking(id, idClient, idLivingRoom, idStaff, idGroupApp,
                arrival, departure, guests, bookingTime, status, price);
    }

    public static void mapToStatement(PreparedStatement stmt, Booking b) throws SQLException {

        stmt.setInt(1, b.getIdClient());
        stmt.setInt(2, b.getIdLivingRoom());
        stmt.setInt(3, b.getIdStaff());
        stmt.setInt(4, b.getIdGroupApplication());
        stmt.setDate(5, Date.valueOf(b.getArrivalDate()));
        stmt.setDate(6, Date.valueOf(b.getDepartureDate()));
        stmt.setInt(7, b.getNumberGuests());
        stmt.setTimestamp(8, b.getBookingTime() == null ? null : Timestamp.valueOf(b.getBookingTime()));
        stmt.setBoolean(9, b.isStatus());
        stmt.setBigDecimal(10, b.getPrice());
    }

}
