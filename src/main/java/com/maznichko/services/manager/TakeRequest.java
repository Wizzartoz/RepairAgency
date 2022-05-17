package com.maznichko.services.manager;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.services.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TakeRequest implements Command {
    private final RequestDAO requestDAO;
    public TakeRequest(RequestDAO requestDAO){
        this.requestDAO = requestDAO;
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("id"));
        Request request;
        try {
            request = requestDAO.getData(id);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return Path.ERROR;
        }
        if (!request.getPaymentStatus().equals("paid")) {
            req.setAttribute("result", "request must be paid");
            return Path.MASTER_SERVLET;
        }
        if (request.getComplicationStatus().equals("done")) {
            req.setAttribute("result", "order already placed");
            return Path.MASTER_SERVLET;
        }
        if (request.getComplicationStatus().equals("under consideration")) {
            req.setAttribute("result", "request must be considered");
            return Path.MASTER_SERVLET;
        }
        request.setComplicationStatus("in progress");
        try {
           requestDAO.update(request);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return Path.ERROR;
        }
        req.setAttribute("result", "status changed successfully");
        return Path.MASTER_SERVLET;
    }
}
