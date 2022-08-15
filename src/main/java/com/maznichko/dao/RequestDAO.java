package com.maznichko.dao;

import com.maznichko.dao.entity.Request;

import java.util.List;

/**
 * Abstract class for request entity
 */
public abstract class RequestDAO extends BaseDAO<Request> {
    /**
     * insert request into user_request table
     *
     * @param login     - login of user
     * @param requestID - id of request
     * @return - true if we added element to db or false if not
     * @throws DBException - throws if connection to DB didn't allow
     */
    public abstract boolean insertRequestInUserRequest(String login, long requestID) throws DBException;

    /**
     * get request by login
     *
     * @param login - login of user
     * @return - request
     * @throws DBException - throws if connection to DB didn't allow
     */
    public abstract List<Request> getRequestByLogin(String login) throws DBException;

}
