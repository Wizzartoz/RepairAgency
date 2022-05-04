package com.maznichko.services.commands;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetMaster implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int reqID = Integer.parseInt(req.getParameter("ReqID"));
        String login = req.getParameter("usr");
        Request request;
        try {
           request = new RequestDAO().getRequestByID(reqID);
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
            new RequestDAO().updateRequest(request);
        } catch (DBException e) {
            req.setAttribute("error",e.getMessage());
            return "/jsp/Error.jsp";
        }
        try {
            new RequestDAO().insertRequestInUserRequest(login, reqID);
        } catch (DBException e) {
            req.setAttribute("result", "request already assigned");
            return "/jsp/Manager/managerMain.jsp";
        }
        return "/jsp/Manager/managerMain.jsp";

    }
}
