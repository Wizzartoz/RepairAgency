package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.impl.RequestDAOimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoneRequest implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("doneID"));
        Request request;
        System.out.println(1);
        try {
            request = new RequestDAOimpl().getData(id);
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
            new RequestDAOimpl().update(request);
        } catch (DBException e) {
            System.out.println(2);
            throw new RuntimeException(e);
        }
        req.setAttribute("result", "status changed successfully");
        return "/jsp/Master/masterRequests.jsp";
    }
}
