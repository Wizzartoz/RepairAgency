package com.maznichko.services.customer;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.services.Path;

import javax.servlet.http.HttpServletRequest;

public class DeleteRequest implements CustomerCommand {
    private final RequestDAO requestDAO;
    public DeleteRequest(RequestDAO requestDAO){
        this.requestDAO = requestDAO;

    }
    @Override
    public String execute(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            Request request = requestDAO.getData(id);
            if (!request.getComplicationStatus().equals("under consideration") || request.getPaymentStatus().equals("paid")){
                req.setAttribute("result","you cannot delete request");
                return Path.CUSTOMER_SERVLET;
            }
            requestDAO.delete(request);
        } catch (DBException e) {
            req.setAttribute("result",null);
            return Path.CUSTOMER_SERVLET;
        }
        req.setAttribute("result","deletion successful");
        return Path.CUSTOMER_SERVLET;
    }
}
