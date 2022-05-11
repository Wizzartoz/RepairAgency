package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.impl.RequestDAOimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendRequest implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        String message = req.getParameter("user_message");
        if (message.isEmpty()){
            req.setAttribute("result", "you sent an empty field");
            return "/RequestCustomerServlet";
        }
        Request request = new Request();
        request.setDescription(message);
        request.setPrice(0f);
        request.setPaymentStatus("unpaid");
        request.setComplicationStatus("under consideration");
        try {
            new RequestDAOimpl().insert(request);
        } catch (DBException e) {
            req.setAttribute("result",e.getMessage());
            return "/jsp/Error.jsp";
        }
        try {
            new RequestDAOimpl().insertRequestInUserRequest(login, request.getRequestID());
        } catch (DBException e) {
            req.setAttribute("result",e.getMessage());
            return "/jsp/Error.jsp";
        }
        req.setAttribute("result", "you have successfully submitted your request");
        return "/RequestCustomerServlet";
    }
}
