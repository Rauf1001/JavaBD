package com.company.crm.dao.jdbc;

import com.company.crm.dao.interfaces.StaffDao;
import com.company.crm.mappers.StaffMapper;
import com.company.crm.models.Staff;
import com.company.crm.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StaffDaoJdbcImpl implements StaffDao {

    public List<Staff> deleteStaffInRange(int startId, int endId) {
        List<Staff> deletedStaffs = new ArrayList<>();

        String selectsql = "SELECT * FROM Staff WHERE ID BETWEEN ? AND ?";
        String deletesql = "DELETE FROM Staff WHERE ID BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectsql);
             PreparedStatement deleteStmt = conn.prepareStatement(deletesql)) {

            selectStmt.setInt(1, startId);
            selectStmt.setInt(2, endId);
            try (ResultSet rs = selectStmt.executeQuery()) {
                while (rs.next()) {
                    Staff staff = StaffMapper.map(rs);
                    deletedStaffs.add(staff);
                }
            }
            if (deletedStaffs.isEmpty()) {
                return Collections.emptyList();
            }
            deleteStmt.setInt(1, startId);
            deleteStmt.setInt(2, endId);
            deleteStmt.executeUpdate();


            return deletedStaffs;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();

    }

    @Override
    public void add(Staff staff) {
        // ID не указываем, он авто-генерируемый
        String sql = "INSERT INTO Staff (Name, Passport_data, Phone_number, Staff_book_number, Work_experience) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            StaffMapper.mapToStatement(stmt, staff);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        staff.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public boolean update(Staff staff) {
        // ВАЖНО: ID обновлять нельзя, он только в WHERE
        String sql = "UPDATE Staff SET Name = ?, Passport_data = ?, Phone_number = ?, Staff_book_number = ?, Work_experience = ? WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            StaffMapper.mapToStatement(stmt, staff); // Заполняет параметры с 1 по 5
            stmt.setInt(6, staff.getId());           // 6-й параметр - это ID для поиска строки

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Staff> getAll() {
        List<Staff> staffs = new ArrayList<>();
        // ИСПРАВЛЕНИЕ: Добавлен ORDER BY ID, чтобы строки не прыгали после редактирования
        String sql = "SELECT * FROM Staff ORDER BY ID ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                staffs.add(StaffMapper.map(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return staffs;
    }

    @Override
    public Staff findById(int id) {
        String sql = "SELECT * FROM Staff WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return StaffMapper.map(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Staff WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}


