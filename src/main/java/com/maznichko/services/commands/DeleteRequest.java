package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.services.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteRequest implements Command {
    private final RequestDAO requestDAO;
    public DeleteRequest(RequestDAO requestDAO){
        this.requestDAO = requestDAO;

    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Request request = requestDAO.getData(Long.parseLong(req.getParameter("id")));
            if (!request.getComplicationStatus().equals("under consideration")
                    || request.getPaymentStatus().equals("paid")){
                req.setAttribute("result","you cannot delete request");
                return Path.CUSTOMER_JSP;
            }
            requestDAO.delete(request);
        } catch (DBException e) {
            req.setAttribute("result",null);
            return Path.CUSTOMER_JSP;
        }
        req.setAttribute("result","deletion successful");
        return Path.CUSTOMER_JSP;
    }
}
