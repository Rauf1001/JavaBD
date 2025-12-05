package com.company.crm.services.interfaces;

import com.company.crm.models.Client;

import java.util.List;

public interface ClientService extends GenericService<Client> {


    List<Client> deleteClientsInRange(int startId, int endId);

}
