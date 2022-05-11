package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.impl.RequestDAOimpl;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetMaster implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long reqID = Long.parseLong(req.getParameter("ReqID"));
        String login = req.getParameter("usr");
        Request request;
        try {
           request = new RequestDAOimpl().getData(reqID);
        } catch (DBException e) {
            req.setAttribute("error",e.getMessage());
            return "/jsp/Error.jsp";
        }
        if (request.getMasterLogin() != null){
            req.setAttribute("result", "request already assigned");
            return "/jsp/Manager/managerMain.jsp";
        }
        request.setMasterLogin(login);
        try {
            new RequestDAOimpl().update(request);
        } catch (DBException e) {
            req.setAttribute("error",e.getMessage());
            return "/jsp/Error.jsp";
        }
        try {
            new RequestDAOimpl().insertRequestInUserRequest(login, reqID);
        } catch (DBException e) {
            req.setAttribute("result", "request already assigned");
            return "/jsp/Manager/managerMain.jsp";
        }
        return "/jsp/Manager/managerMain.jsp";

    }
}
