package com.company.crm.dao.jdbc;

import com.company.crm.dao.interfaces.LivingRoomDao;
import com.company.crm.mappers.LivingRoomMapper;
import com.company.crm.models.LivingRoom;
import com.company.crm.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LivingRoomDaoJdbcImpl implements LivingRoomDao {

    public boolean existsByRoomNumber(String roomnumber) {
        String sql = "SELECT COUNT(*) FROM Living_room WHERE Room_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, roomnumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<LivingRoom> deleteLivingRoomsInRange(int startId, int endId) {
        List<LivingRoom> deletedLiving_rooms = new ArrayList<>();

        String selectsql = "SELECT * FROM Living_room WHERE ID BETWEEN ? AND ?";
        String deletesql = "DELETE FROM Living_room WHERE ID BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectsql);
             PreparedStatement deleteStmt = conn.prepareStatement(deletesql)) {

            selectStmt.setInt(1, startId);
            selectStmt.setInt(2, endId);
            try (ResultSet rs = selectStmt.executeQuery()) {
                while (rs.next()) {
                    LivingRoom living_room = LivingRoomMapper.map(rs);
                    deletedLiving_rooms.add(living_room);
                }
            }
            if (deletedLiving_rooms.isEmpty()) {
                return Collections.emptyList();
            }
            deleteStmt.setInt(1, startId);
            deleteStmt.setInt(2, endId);
            deleteStmt.executeUpdate();


            return deletedLiving_rooms;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();

    }

    @Override
    public void add(LivingRoom living_room) {
        String sql = "INSERT INTO Living_room (Room_number,Location,Status) VALUES (?,?,?) ";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            LivingRoomMapper.mapToStatement(stmt, living_room);

            int rows = stmt.executeUpdate();


            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generateId = rs.getInt(1);
                        living_room.setId(generateId);
                    }
                }
                System.out.println("Жилая комната добавлена: " + living_room);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении жилой комнаты: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public List<LivingRoom> getAll() {
        List<LivingRoom> living_rooms = new ArrayList<>();
        String sql = "SELECT ID,Room_number,Location,Status From Living_room";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                living_rooms.add(LivingRoomMapper.map(rs));
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка жилых комнат: " + e.getMessage());
            e.printStackTrace();
        }


        return living_rooms;
    }

    @Override
    public LivingRoom findById(int id) {
        String sql = "SELECT * FROM Living_room WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return LivingRoomMapper.map(rs);
                }

            }

        } catch (SQLException e) {
            System.err.println("Ошибка при поиске жилых комнат: " + e.getMessage());
            e.printStackTrace();

        }


        return null;
    }

    @Override
    public boolean update(LivingRoom living_room) {
        // SQL содержит 4 знака вопроса
        String sql = "UPDATE Living_room SET Room_number = ?, Location = ?, Status = ? WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Устанавливаем параметры строго по порядку
            stmt.setString(1, living_room.getRoom_number());
            stmt.setString(2, living_room.getLocation());
            stmt.setInt(3, living_room.getStatus());
            stmt.setInt(4, living_room.getId()); // Это 4-й параметр!

            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;

        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении жилой комнаты: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Living_room WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();


            return rows > 0;


        } catch (SQLException e) {
            System.err.println("Ошибка при удалении жилой комнаты: " + e.getMessage());
            e.printStackTrace();
        }


        return false;
    }


}