package com.maznichko.services.commands;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;
import com.maznichko.services.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoneRequest implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("doneID"));
        Request request;
        System.out.println(1);
        try {
            request = new RequestDAO().getRequestByID(id);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return "/jsp/Error.jsp";
        }
        if (!request.getComplicationStatus().equals("in progress")) {
            req.setAttribute("result", "you cannot change the status");
            return "/jsp/Master/masterRequests.jsp";
        }
        request.setComplicationStatus("done");
        try {
            new RequestDAO().updateRequest(request);
        } catch (DBException e) {
            System.out.println(2);
            throw new RuntimeException(e);
        }
        req.setAttribute("result", "status changed successfully");
        return "/jsp/Master/masterRequests.jsp";
    }
}
