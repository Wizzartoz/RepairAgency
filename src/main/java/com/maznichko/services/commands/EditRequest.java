package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.impl.RequestDAOimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditRequest implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("reqID"));
        Request request;
        try {
            request = new RequestDAOimpl().getData(id);
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
            new RequestDAOimpl().update(request);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        return "/jsp/Manager/managerMain.jsp";
    }
}
