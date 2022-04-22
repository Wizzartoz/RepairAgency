package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendRequest implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        if (httpSession.getAttribute("SendRequest") == null) {
            Request request1 = new Request();
            request1.setDescription(req.getParameter("user_message"));
            request1.setPrice(0f);
            request1.setPaymentStatus("unpaid");
            request1.setComplicationStatus("under consideration");
            try {
                new RequestDAO().insertRequest(request1);
            } catch (DBException e) {
                return "false";
            }
            try {
                new RequestDAO().insertRequestInUserRequest((String) httpSession.getAttribute("login"), request1.getRequestID());
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("sendRequest", "you have successfully submitted your request");
            httpSession.setAttribute("SendRequest",true);
            return "true";
        }
        req.setAttribute("sendRequest","you have already applied");
        return "false";
    }
}
