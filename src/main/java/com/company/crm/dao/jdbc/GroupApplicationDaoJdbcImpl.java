package com.company.crm.dao.jdbc;

import com.company.crm.dao.interfaces.GroupApplicationDao;
import com.company.crm.mappers.GroupApplicationMapper;
import com.company.crm.models.GroupApplication;
import com.company.crm.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupApplicationDaoJdbcImpl implements GroupApplicationDao {


    public List<GroupApplication> deleteGroup_applicationInRange(int startId, int endId) {
        List<GroupApplication> deleted = new ArrayList<>();
        String select = "SELECT * FROM Group_application WHERE ID BETWEEN ? AND ?";
        String delete = "DELETE FROM Group_application WHERE ID BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement sel = conn.prepareStatement(select);
             PreparedStatement del = conn.prepareStatement(delete)) {

            sel.setInt(1, startId);
            sel.setInt(2, endId);
            try (ResultSet rs = sel.executeQuery()) {
                while (rs.next()) deleted.add(GroupApplicationMapper.map(rs));
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
    public void add(GroupApplication g) {
        String sql = "INSERT INTO Group_application (ID_Living_rooms, Arrival_date, Departure_date, Price, Status, Comment) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            GroupApplicationMapper.mapToStatement(stmt, g);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) g.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<GroupApplication> getAll() {
        List<GroupApplication> res = new ArrayList<>();
        String sql = "SELECT * FROM Group_application";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) res.add(GroupApplicationMapper.map(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public GroupApplication findById(int id) {
        String sql = "SELECT * FROM Group_application WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return GroupApplicationMapper.map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(GroupApplication g) {
        String sql = "UPDATE Group_application SET ID_Living_rooms=?, Arrival_date=?, Departure_date=?, Price=?, Status=?, Comment=? WHERE ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            GroupApplicationMapper.mapToStatement(stmt, g);
            stmt.setInt(7, g.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Group_application WHERE ID = ?";
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
