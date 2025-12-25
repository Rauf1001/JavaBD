package com.company.crm.services.implement;

import com.company.crm.dao.interfaces.ClientDao;
import com.company.crm.models.Client;
import com.company.crm.services.interfaces.ClientService;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private final ClientDao repository;

    public ClientServiceImpl(ClientDao repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> deleteClientsInRange(int startId, int endId){
        return repository.deleteClientsInRange(startId,endId);
    }


    public void add(Client client) {
        repository.add(client);
    }

    public Client findById(int id) {
        return repository.findById(id);
    }

    public List<Client> getAll() {
        return repository.getAll();
    }

    public Client update(Client updated) {
        boolean ok = repository.update(updated);
        return ok ? updated : null;

    }

    public Client delete(int id) {
        Client found = repository.findById(id);
        if(found == null) return null;
        return repository.delete(id) ? found : null;

    }

}
