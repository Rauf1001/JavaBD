package com.company.crm.mappers;

import com.company.crm.models.GroupApplication;

import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GroupApplicationMapper {

    public static GroupApplication map(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        int idLiving = rs.getInt("ID_Living_room");
        LocalDate arrival = rs.getDate("Arrival_date").toLocalDate();
        LocalDate departure = rs.getDate("Departure_date").toLocalDate();
        BigDecimal price = rs.getBigDecimal("Price");
        boolean status = rs.getBoolean("Status");
        String comment = rs.getString("Comment");

        return new GroupApplication(id, idLiving, arrival, departure, price, status, comment);
    }

    public static void mapToStatement(PreparedStatement stmt, GroupApplication g) throws SQLException {
        stmt.setInt(1, g.getIdLivingRoom());
        stmt.setDate(2, Date.valueOf(g.getArrivalDate()));
        stmt.setDate(3, Date.valueOf(g.getDepartureDate()));
        stmt.setBigDecimal(4, g.getPrice());
        stmt.setBoolean(5, g.isStatus());
        stmt.setString(6, g.getComment());
    }
}
