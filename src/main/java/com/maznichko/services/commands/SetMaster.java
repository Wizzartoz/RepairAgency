package com.maznichko.services.commands;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.services.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetMaster implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int reqID = Integer.parseInt(req.getParameter("ReqID"));
        String login = req.getParameter("usr");
        try {
            new RequestDAO().insertRequestInUserRequest(login,reqID);
        } catch (DBException e) {
            return "/jsp/Manager/managerMain.jsp";
        }
        return "/jsp/Manager/managerMain.jsp";

    }
}
