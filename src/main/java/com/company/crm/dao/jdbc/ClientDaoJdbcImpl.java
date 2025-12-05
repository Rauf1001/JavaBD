package com.company.crm.dao.jdbc;

import com.company.crm.dao.interfaces.ClientDao;
import com.company.crm.mappers.ClientMapper;
import com.company.crm.models.Client;
import com.company.crm.utils.DatabaseConnection;

import java.sql.*;

import java.util.*;

import java.util.List;


public class ClientDaoJdbcImpl implements ClientDao {

    public List<Client> findClientsInRange(int startId, int endId){
        List<Client> clients = new ArrayList<>();

        String sql = "SELECT * FROM client WHERE id BETWEEN ? AND ?";


        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql) ){

            stmt.setInt(1, startId);
            stmt.setInt(2,endId);

            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    clients.add(ClientMapper.map(rs));
                }
            }

        } catch (SQLException e){
            System.err.println("Ошибка при поиске клиентов в диапазоне: " + e.getMessage());

        }
        return clients;

    }

    public List<Client> deleteClientsInRange(int startId, int endId) {
        List<Client> deletedClients = new ArrayList<>();

        String selectsql = "SELECT * FROM client WHERE ID BETWEEN ? AND ?";
        String deletesql = "DELETE FROM client WHERE ID BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectsql);
             PreparedStatement deleteStmt = conn.prepareStatement(deletesql)) {

            selectStmt.setInt(1, startId);
            selectStmt.setInt(2, endId);
            try (ResultSet rs = selectStmt.executeQuery()) {
                while (rs.next()) {
                    Client client = ClientMapper.map(rs);
                    deletedClients.add(client);
                }
            }
            if (deletedClients.isEmpty()) {
                return Collections.emptyList();
            }
            deleteStmt.setInt(1, startId);
            deleteStmt.setInt(2, endId);
            deleteStmt.executeUpdate();


            return deletedClients;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();

    }


    @Override
    public void add(Client client) {
        String sql = "INSERT INTO Client (Name,Email,Phone_number,Passport_data,Birth_date) VALUES (?,?,?,?,?) ";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            ClientMapper.mapToStatement(stmt, client);

            int rows = stmt.executeUpdate();


            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generateId = rs.getInt(1);
                        client.setId(generateId);
                    }
                }
                System.out.println("Клиент добавлен: " + client);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении клиента: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT ID,Name,Email,Phone_number,Passport_data,Birth_date From Client";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                clients.add(ClientMapper.map(rs));
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка клиентов: " + e.getMessage());
            e.printStackTrace();
        }


        return clients;
    }

    @Override
    public Client findById(int id) {
        String sql = "SELECT * FROM client WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return ClientMapper.map(rs);
                }

            }

        } catch (SQLException e) {
            System.err.println("Ошибка при поиске клиента: " + e.getMessage());
            e.printStackTrace();

        }


        return null;
    }

    @Override
    public boolean update(Client client) {
        String sql = "UPDATE client SET Name = ?,Email=?, Phone_number = ?,Passport_data=?,Birth_date=? WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            ClientMapper.mapToStatement(stmt, client);
            stmt.setInt(6, client.getId());

            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;


        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении клиента: " + e.getMessage());
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM client WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();


            return rows > 0;


        } catch (SQLException e) {
            System.err.println("Ошибка при удалении клиента: " + e.getMessage());
            e.printStackTrace();
        }


        return false;
    }


}



