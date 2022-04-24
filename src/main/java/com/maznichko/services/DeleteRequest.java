package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteRequest implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        RequestDAO requestDAO = new RequestDAO();
        try {
            Request request = requestDAO.getRequestByID(Integer.parseInt(req.getParameter("id")));
            if (!request.getComplicationStatus().equals("under consideration") || request.getPaymentStatus().equals("paid")){
                req.setAttribute("result","you cannot delete request");
                return "/jsp/Customer/customerRequests.jsp";
            }
            requestDAO.deleteRequest(Integer.parseInt(req.getParameter("id")));
        } catch (DBException e) {
            req.setAttribute("result",null);
            return "/jsp/Customer/customerRequests.jsp";
        }
        req.setAttribute("result","deletion successful");
        return "/jsp/Customer/customerRequests.jsp";
    }
}
