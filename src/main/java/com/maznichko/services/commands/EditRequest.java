package com.maznichko.services.commands;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditRequest implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("reqID"));
        Request request;
        try {
            request = new RequestDAO().getRequestByID(id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        float price = Float.parseFloat(req.getParameter("price"));
        String compStatus = req.getParameter("cStatus");
        String payStatus = req.getParameter("pStatus");
        request.setPrice(price);
        request.setComplicationStatus(compStatus);
        request.setPaymentStatus(payStatus);
        try {
            new RequestDAO().updateRequest(request);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        return "/jsp/Manager/managerMain.jsp";
    }
}
