package com.maznichko.dao;

import com.maznichko.dao.entity.Entity;
import java.util.List;

public abstract class BaseDAO<T extends Entity> {
    protected ManagerDB dao = PostgresDBManager.getInstance();

    public abstract List<T> findAll() throws DBException;

    public abstract T getData(long id) throws DBException;

    public abstract boolean update(T data) throws DBException;

    public abstract boolean delete(T data) throws DBException;

    public abstract boolean insert(T data) throws DBException;
}
