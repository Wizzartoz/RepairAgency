package com.maznichko.services.commands;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;
import com.maznichko.services.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TakeRequest implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Request request;
        try {
            request = new RequestDAO().getRequestByID(id);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return "/jsp/Error.jsp";
        }
        if (!request.getPaymentStatus().equals("paid")) {
            req.setAttribute("result", "request must be paid");
            return "/jsp/Master/masterRequests.jsp";
        }
        if (request.getComplicationStatus().equals("done")) {
            req.setAttribute("result", "order already placed");
            return "/jsp/Master/masterRequests.jsp";
        }
        if (request.getComplicationStatus().equals("under consideration")) {
            req.setAttribute("result", "request must be considered");
            return "/jsp/Master/masterRequests.jsp";
        }
        request.setComplicationStatus("in progress");
        try {
            new RequestDAO().updateRequest(request);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return "/jsp/Error.jsp";
        }
        req.setAttribute("result", "status changed successfully");
        return "/jsp/Master/masterRequests.jsp";
    }
}
