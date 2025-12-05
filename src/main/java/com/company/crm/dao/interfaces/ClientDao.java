package com.company.crm.dao.interfaces;

import com.company.crm.models.Client;

import java.util.List;

public interface ClientDao extends GenericDao<Client>{


    List<Client> findClientsInRange( int startId, int endId);
    List<Client> deleteClientsInRange(int startId, int endId);

}
