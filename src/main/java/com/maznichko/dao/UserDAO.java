package com.maznichko.dao;

import com.maznichko.dao.entity.User;

public abstract class UserDAO extends BaseDAO<User>{
    public abstract User getUserByLogin(String login) throws DBException;
}
