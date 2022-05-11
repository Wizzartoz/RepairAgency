package com.maznichko.dao;

import com.maznichko.dao.entity.Request;

import java.util.List;

public abstract class RequestDAO extends BaseDAO<Request>{
    public  abstract boolean insertRequestInUserRequest(String login, long requestID) throws DBException;
    public  abstract List<Request> getRequestByLogin(String login) throws DBException;

}
