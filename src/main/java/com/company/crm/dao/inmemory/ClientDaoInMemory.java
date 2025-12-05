package com.company.crm.dao.inmemory;

import com.company.crm.dao.interfaces.ClientDao;
import com.company.crm.models.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientDaoInMemory implements ClientDao {

    private final List<Client> database = new ArrayList<>();
    private int idCounter = 1;


    public void addAll(List<Client> list) {
        for (Client client : list) add(client);
    }

    @Override
    public void add(Client client) {
        client.setId(idCounter++);
        database.add(client);
    }

    @Override
    public List<Client> getAll() {
        return new ArrayList<>(database);
    }

    @Override
    public Client findById(int id) {
        return database.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(Client updated) {
        Client existing = findById(updated.getId());
        if (existing == null) return false;

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone_number(updated.getPhone_number());
        existing.setPassport_data(updated.getPassport_data());
        existing.setBirth_date(updated.getBirth_date());
        return true;
    }

    @Override
    public boolean delete(int id) {
        Client existing = findById(id);
        if (existing == null) return false;
        return database.remove(existing);
    }

    @Override
    public List<Client> findClientsInRange(int startId, int endId) {
        List<Client> result = new ArrayList<>();
        for (Client c : database) {
            if (c.getId() >= startId && c.getId() <= endId) {
                result.add(c);
            }
        }
        return result;
    }

    @Override
    public List<Client> deleteClientsInRange(int startId, int endId) {
        List<Client> found = findClientsInRange(startId, endId);
        database.removeAll(found);
        return found;
    }
}
