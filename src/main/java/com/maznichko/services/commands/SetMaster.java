package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.services.Path;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetMaster implements Command {
    private final RequestDAO requestDAO;
    public SetMaster(RequestDAO requestDAO){
        this.requestDAO = requestDAO;
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long reqID = Long.parseLong(req.getParameter("ReqID"));
        String login = req.getParameter("usr");
        Request request;
        try {
           request = requestDAO.getData(reqID);
        } catch (DBException e) {
            req.setAttribute("error",e.getMessage());
            return Path.ERROR;
        }
        if (request.getMasterLogin() != null){
            req.setAttribute("result", "request already assigned");
            return Path.MANAGER_SERVLET;
        }
        request.setMasterLogin(login);
        try {
            requestDAO.update(request);
        } catch (DBException e) {
            req.setAttribute("error",e.getMessage());
            return Path.ERROR;
        }
        try {
            requestDAO.insertRequestInUserRequest(login, reqID);
        } catch (DBException e) {
            req.setAttribute("result", "request already assigned");
            return Path.MANAGER_SERVLET;
        }
        return Path.MANAGER_SERVLET;

    }
}
