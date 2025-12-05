package com.company.crm.dao.interfaces;

import java.util.List;

public interface GenericDao<T> {

    void add(T entity);

    List<T> getAll();

    T findById(int id);

    boolean update(T entity);

    boolean delete(int id);


}
