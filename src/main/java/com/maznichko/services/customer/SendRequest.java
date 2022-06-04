package com.maznichko.services.customer;

import com.maznichko.SendEmail;
import com.maznichko.Sender;
import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SendRequest implements CustomerCommand {
    private static final Logger log = Logger.getLogger(SendRequest.class);
    private final RequestDAO requestDAO;
    private final Request request;
    private final Sender sender;

    public SendRequest(RequestDAO requestDAO,Sender sender) {
        this.requestDAO = requestDAO;
        request = new Request();
        this.sender = sender;
    }

    /**
     * this method sends a request that the client leaves
     *
     * @param req - request who we are getting
     * @return - path of request
     */
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        String message = req.getParameter("user_message");
        if (message.isEmpty()) {
            req.setAttribute("result", "You sent an empty field");
            return Path.CUSTOMER_SERVLET;
        }
        request.setDescription(message);
        request.setPrice(0F);
        request.setPaymentStatus("unpaid");
        request.setComplicationStatus("under consideration");
        boolean isInsert = insertRequest();
        if (!isInsert) {
            return Path.ERROR;
        }
        //TODO change insert user
        try {
            requestDAO.insertRequestInUserRequest(login, request.getRequestID());
            requestDAO.insertRequestInUserRequest("user3", request.getRequestID());
        } catch (DBException e) {
            log.error("<----------- send request is failed", e);
            return Path.ERROR;
        }
        /*
        sender.send("Request",
                "You're successfully left request, wait for consideration",
                "maznichkogame@gmail.com");

         */
        return Path.CUSTOMER_SERVLET;
    }
    private boolean insertRequest() {
        try {
            requestDAO.insert(request);
        } catch (DBException e) {
            log.error("<---------- send request is failed", e);
            return false;
        }
        return true;
    }
}
