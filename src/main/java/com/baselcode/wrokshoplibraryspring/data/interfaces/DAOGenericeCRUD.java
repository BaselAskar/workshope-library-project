package com.baselcode.wrokshoplibraryspring.data.interfaces;

import java.util.Collection;

public interface DAOGenericeCRUD<T>{

    T findById(int id);
    Collection<T> findAll();
    T create(T t);
    T update(T t);
    void delete(int id);

}
