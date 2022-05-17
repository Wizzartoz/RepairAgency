package com.maznichko.services.customer;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.services.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SendRequest implements CustomerCommand {
    private final RequestDAO requestDAO;
    public SendRequest(RequestDAO requestDAO){
        this.requestDAO = requestDAO;
    }
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        String message = req.getParameter("user_message");
        if (message.isEmpty()){
            req.setAttribute("result", "you sent an empty field");
            return Path.CUSTOMER_SERVLET;
        }
        Request request = new Request();
        request.setDescription(message);
        request.setPrice(0f);
        request.setPaymentStatus("unpaid");
        request.setComplicationStatus("under consideration");
        try {
            requestDAO.insert(request);
        } catch (DBException e) {
            req.setAttribute("result",e.getMessage());
            return Path.ERROR;
        }
        try {
            requestDAO.insertRequestInUserRequest(login, request.getRequestID());
            requestDAO.insertRequestInUserRequest("user3", request.getRequestID());
        } catch (DBException e) {
            req.setAttribute("result",e.getMessage());
            return Path.ERROR;
        }
        req.setAttribute("result", "you have successfully submitted your request");
        return Path.CUSTOMER_SERVLET;
    }
}
