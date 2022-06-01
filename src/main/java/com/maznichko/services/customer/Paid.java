package com.maznichko.services.customer;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Paid implements CustomerCommand {
    private final UserDAO userDAO;
    private final RequestDAO requestDAO;
    private static final Logger log = Logger.getLogger(Paid.class);

    public Paid(UserDAO userDAO, RequestDAO requestDAO) {
        this.userDAO = userDAO;
        this.requestDAO = requestDAO;
    }

    /**
     * this method implement pay of request by customer
     *
     * @param req - request who we are getting
     * @return - path of servlet
     */
    @Override
    public String execute(HttpServletRequest req) {
        String paidStatus = req.getParameter("payment");
        if (!paidStatus.equals("waiting for payment")) {
            req.setAttribute("result", "Payment failed");
            return Path.CUSTOMER_SERVLET;
        }
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        User user = getUser(login);
        if (user == null) {
            return Path.ERROR;
        }
        float price = Float.parseFloat(req.getParameter("price"));
        if (user.getBank() < price) {
            req.setAttribute("result", "Not enough money");
            return Path.CUSTOMER_SERVLET;
        }
        //Setting bank
        user.setBank(user.getBank() - (int) price);
        boolean isUpdate = updateUser(user);
        if (!isUpdate) {
            return Path.ERROR;
        }
        //Getting request
        long id = Long.parseLong(req.getParameter("paymentID"));
        Request request = getRequest(id);
        if (request == null) {
            return Path.ERROR;
        }
        //Updating request
        request.setPaymentStatus("paid");
        boolean isUpdateReq = updateRequest(request);
        if (!isUpdateReq) {
            return Path.ERROR;
        }
        req.setAttribute("result", "Payment was successful");
        return Path.CUSTOMER_SERVLET;
    }

    private boolean updateRequest(Request request) {
        try {
            requestDAO.update(request);
        } catch (DBException e) {
            log.error("<---------- update request is failed");
            return false;
        }
        return true;
    }

    private Request getRequest(long id) {
        Request request;
        try {
            request = requestDAO.getData(id);
        } catch (DBException e) {
            log.error("<---------- paid is failed", e);
            return null;
        }
        return request;
    }

    private User getUser(String login) {
        User user;
        try {
            user = userDAO.getUserByLogin(login);
        } catch (DBException e) {
            log.error("<-------- paid is failed", e);
            return null;
        }
        return user;
    }

    private boolean updateUser(User user) {
        try {
            userDAO.update(user);
        } catch (DBException e) {
            log.error("<---------- update user is failed", e);
            return false;
        }
        return true;
    }
}
