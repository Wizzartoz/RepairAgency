package com.maznichko.dao;

import com.maznichko.dao.entity.User;

/**
 * Abstract class for user entity
 */
public abstract class UserDAO extends BaseDAO<User> {
    /**
     * get user by login
     *
     * @param login - login of user
     * @return - user
     * @throws DBException - throws if connection to DB didn't allow
     */
    public abstract User getUserByLogin(String login) throws DBException;
}
