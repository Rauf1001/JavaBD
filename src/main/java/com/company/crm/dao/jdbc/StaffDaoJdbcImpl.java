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
        String sql = "INSERT INTO Staff (Name,Passport_data,Phone_number,Staff_book_number,Work_experience) VALUES (?,?,?,?,?) ";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            StaffMapper.mapToStatement(stmt, staff);

            int rows = stmt.executeUpdate();


            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generateId = rs.getInt(1);
                        staff.setId(generateId);
                    }
                }
                System.out.println("Персонал добавлен: " + staff);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении персонала: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public List<Staff> getAll() {
        List<Staff> staffs = new ArrayList<>();
        String sql = "SELECT ID,Name,Passport_data,Phone_number,Staff_book_number,Work_experience From Staff";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                staffs.add(StaffMapper.map(rs));
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка клиентов: " + e.getMessage());
            e.printStackTrace();
        }


        return staffs;
    }

    @Override
    public Staff findById(int id) {
        String sql = "SELECT * FROM Staff WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return StaffMapper.map(rs);
                }

            }

        } catch (SQLException e) {
            System.err.println("Ошибка при поиске клиента: " + e.getMessage());
            e.printStackTrace();

        }


        return null;
    }

    @Override
    public boolean update(Staff staff) {
        String sql = "UPDATE Staff SET Name = ?,Passport_data=?, Phone_number = ?,Staff_book_number=?,Work_experience=? WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            StaffMapper.mapToStatement(stmt, staff);
            stmt.setInt(6, staff.getId());

            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;


        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении персонала: " + e.getMessage());
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Staff WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();


            return rows > 0;


        } catch (SQLException e) {
            System.err.println("Ошибка при удалении персонала: " + e.getMessage());
            e.printStackTrace();
        }


        return false;
    }


}
