package com.company.crm.mappers;

import com.company.crm.models.LivingRoom;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivingRoomMapper {

    public static LivingRoom map(ResultSet rs) throws SQLException {
        return new LivingRoom(
                rs.getInt("ID"),
                rs.getString("Room_number"),
                rs.getString("Location"),
                rs.getInt("Status")

        );


    }

    public static void mapToStatement(PreparedStatement stmt, LivingRoom living_room) throws SQLException {
        stmt.setString(1, living_room.getRoom_number());
        stmt.setString(2, living_room.getLocation());
        stmt.setInt(3, living_room.getStatus());


    }
}
