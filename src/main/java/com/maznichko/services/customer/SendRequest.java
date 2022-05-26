package com.maznichko.services.customer;

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
    public SendRequest(RequestDAO requestDAO){
        this.requestDAO = requestDAO;
    }

    /**
     * this method sends a request that the client leaves
     * @param req - request who we are getting
     * @return - path of request
     */
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        String message = req.getParameter("user_message");
        System.out.println(message);
        if (message.isEmpty()){
            req.setAttribute("result", "you sent an empty field");
            return Path.CUSTOMER_SERVLET;
        }
        Request request = new Request();
        request.setDescription(message);
        request.setPrice(0F);
        request.setPaymentStatus("unpaid");
        request.setComplicationStatus("under consideration");
        try {
            requestDAO.insert(request);
        } catch (DBException e) {
            req.setAttribute("result",e.getMessage());
            log.error(e.getMessage() + " send request is failed");
            return Path.ERROR;
        }
        try {
            requestDAO.insertRequestInUserRequest(login, request.getRequestID());
            requestDAO.insertRequestInUserRequest("user3", request.getRequestID());
        } catch (DBException e) {
            req.setAttribute("result",e.getMessage());
            log.error(e.getMessage() + " send request is failed");
            return Path.ERROR;
        }
        req.setAttribute("result", "you have successfully submitted your request");
        return Path.CUSTOMER_SERVLET;
    }
}
