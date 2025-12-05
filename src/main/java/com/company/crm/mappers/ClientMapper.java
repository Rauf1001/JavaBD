package com.company.crm.mappers;

import com.company.crm.models.Client;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper {
    public static Client map(ResultSet rs) throws SQLException {
        return new Client(
                rs.getInt("ID"),
                rs.getString("Name"),
                rs.getString("Email"),
                rs.getString("Phone_number"),
                rs.getString("Passport_data"),
                rs.getDate("Birth_date").toLocalDate()
        );


    }

    public static void mapToStatement(PreparedStatement stmt, Client client) throws SQLException {
        stmt.setString(1, client.getName());
        stmt.setString(2, client.getEmail());
        stmt.setString(3, client.getPhone_number());
        stmt.setString(4, client.getPassport_data());
        stmt.setDate(5, Date.valueOf(client.getBirth_date()));

    }


}
