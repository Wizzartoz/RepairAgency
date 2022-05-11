package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.impl.RequestDAOimpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteRequest implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        RequestDAO requestDAO = new RequestDAOimpl();
        try {
            Request request = requestDAO.getData(Long.parseLong(req.getParameter("id")));
            if (!request.getComplicationStatus().equals("under consideration") || request.getPaymentStatus().equals("paid")){
                req.setAttribute("result","you cannot delete request");
                return "/jsp/Customer/customerMain.jsp";
            }
            requestDAO.delete(request);
        } catch (DBException e) {
            req.setAttribute("result",null);
            return "/jsp/Customer/customerMain.jsp";
        }
        req.setAttribute("result","deletion successful");
        return "/jsp/Customer/customerMain.jsp";
    }
}
