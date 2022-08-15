package com.maznichko.dao;

import com.maznichko.dao.entity.Entity;

import java.util.List;

/**
 * It's basically crud interface
 *
 * @param <T> parameterizable type of entity
 */
public abstract class BaseDAO<T extends Entity> {

    /**
     * get all element from DB
     *
     * @return - all element
     * @throws DBException - if element don't exist or connection to DB was failed
     */
    public abstract List<T> findAll() throws DBException;

    /**
     * get element by id
     *
     * @param id - id of column
     * @return - element
     * @throws DBException - if element don't exist or connection to DB was failed
     */
    public abstract T getData(long id) throws DBException;

    /**
     * update element of DB
     *
     * @param data - request or the similar entity
     * @return - element
     * @throws DBException - if element don't exist or connection to DB was failed
     */
    public abstract boolean update(T data) throws DBException;

    /**
     * delete element of DB
     *
     * @param data - request or the similar entity
     * @return - element
     * @throws DBException - if element don't exist or connection to DB was failed
     */

    public abstract boolean delete(T data) throws DBException;

    /**
     * insert element into DB
     *
     * @param data - request or the similar entity
     * @return - element
     * @throws DBException - if element don't exist or connection to DB was failed
     */
    public abstract boolean insert(T data) throws DBException;
}
