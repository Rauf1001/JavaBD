package com.company.crm.dao.jdbc;

import com.company.crm.dao.interfaces.BookingDao;
import com.company.crm.mappers.BookingMapper;
import com.company.crm.models.Booking;
import com.company.crm.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingDaoJdbcImpl implements BookingDao {


    public List<Booking> deleteBookingInRange(int startId, int endId) {
        List<Booking> deleted = new ArrayList<>();
        String select = "SELECT * FROM Booking WHERE ID BETWEEN ? AND ?";
        String delete = "DELETE FROM Booking WHERE ID BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement sel = conn.prepareStatement(select);
             PreparedStatement del = conn.prepareStatement(delete)) {

            sel.setInt(1, startId);
            sel.setInt(2, endId);
            try (ResultSet rs = sel.executeQuery()) {
                while (rs.next()) deleted.add(BookingMapper.map(rs));
            }

            if (deleted.isEmpty()) return Collections.emptyList();

            del.setInt(1, startId);
            del.setInt(2, endId);
            del.executeUpdate();
            return deleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void add(Booking b) {
        String sql = "INSERT INTO Booking (ID_Client, ID_Living_room, ID_Staff, ID_Group_application, " +
                "Arrival_date, Departure_date, Number_guests, Booking_time, Status, Price) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            BookingMapper.mapToStatement(stmt, b);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        b.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Booking> getAll() {
        List<Booking> res = new ArrayList<>();
        String sql = "SELECT * FROM Booking";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                res.add(BookingMapper.map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Booking findById(int id) {
        String sql = "SELECT * FROM Booking WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return BookingMapper.map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Booking b) {
        String sql = "UPDATE Booking SET ID_Client=?, ID_Living_room=?, ID_Staff=?, ID_Group_application=?, " +
                "Arrival_date=?, Departure_date=?, Number_guests=?, Booking_time=?, Status=?, Price=? WHERE ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            BookingMapper.mapToStatement(stmt, b);
            stmt.setInt(11, b.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Booking WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
