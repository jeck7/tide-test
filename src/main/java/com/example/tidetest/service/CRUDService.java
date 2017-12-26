package com.example.tidetest.service;

import java.io.Serializable;
import java.util.List;


public interface CRUDService<E> {

    E save(E entity);

    E getById(Serializable id);

    List<E> findAll();

    void delete(Serializable id);
}
