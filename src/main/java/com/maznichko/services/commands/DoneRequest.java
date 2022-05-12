package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.services.Path;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoneRequest implements Command {
    private final RequestDAO requestDAO;
    public DoneRequest(RequestDAO requestDAO){
        this.requestDAO = requestDAO;

    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("doneID"));
        Request request;
        try {
            request = requestDAO.getData(id);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return Path.ERROR;
        }
        if (!request.getComplicationStatus().equals("in progress")) {
            req.setAttribute("result", "you cannot change the status");
            return Path.MASTER_JSP;
        }
        request.setComplicationStatus("done");
        try {
            requestDAO.update(request);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return Path.ERROR;
        }
        req.setAttribute("result", "status changed successfully");
        return Path.MASTER_JSP;
    }
}
