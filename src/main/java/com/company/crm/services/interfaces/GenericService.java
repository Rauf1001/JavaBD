package com.company.crm.services.interfaces;

import java.util.List;

public interface GenericService<T> {

    void add(T entity);

    List<T> getAll();

    T findById(int id);

    T update(T entity);


    T delete(int id);


}
