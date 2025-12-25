package com.company.crm.mappers;

import com.company.crm.models.Staff;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffMapper {

    public static Staff map(ResultSet rs) throws SQLException {
        return new Staff(
                rs.getInt("ID"),
                rs.getString("Name"),
                rs.getString("Passport_data"),
                rs.getString("Phone_number"),
                rs.getString("Staff_book_number"),
                rs.getInt("Work_experience")
        );


    }

    public static void mapToStatement(PreparedStatement stmt, Staff staff) throws SQLException {
        stmt.setString(1, staff.getName());
        stmt.setString(2, staff.getPassport_data());
        stmt.setString(3, staff.getPhone_number());
        stmt.setString(4, staff.getStaff_book_number());
        stmt.setInt(5, staff.getWork_experience());

    }
}
