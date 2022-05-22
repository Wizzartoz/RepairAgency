package com.maznichko.services.customer;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Paid implements CustomerCommand {
    private final UserDAO userDAO;
    private final RequestDAO requestDAO;
    public Paid(UserDAO userDAO,RequestDAO requestDAO){
        this.userDAO = userDAO;
        this.requestDAO = requestDAO;
    }

    /**
     * this method implement pay of request by customer
     * @param req - request who we are getting
     * @return - path of servlet
     */
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        float price = Float.parseFloat(req.getParameter("price"));
        long id = Long.parseLong(req.getParameter("paymentID"));
        String paidStatus = req.getParameter("payment");
        if (paidStatus.equals("waiting for payment")) {
            User user;
            try {
                user = userDAO.getUserByLogin(login);
            } catch (DBException e) {
                req.setAttribute("result", e.getMessage());
                return Path.ERROR;
            }
            if (user.getBank() >= price) {
                user.setBank(user.getBank() - (int) price);
                try {
                    userDAO.update(user);
                } catch (DBException e) {
                    req.setAttribute("result", e.getMessage());
                    return Path.CUSTOMER_SERVLET;
                }
            } else {
                req.setAttribute("result", "not enough money");
                return Path.ERROR;
            }
            Request request;
            try {
                request = requestDAO.getData(id);
            } catch (DBException e) {
                req.setAttribute("result", e.getMessage());
                return Path.ERROR;
            }
            request.setPaymentStatus("paid");
            try {
                requestDAO.update(request);
            } catch (DBException e) {
                req.setAttribute("result", null);
                return Path.CUSTOMER_SERVLET;
            }
            req.setAttribute("result", "payment was successful");
            return Path.CUSTOMER_SERVLET;
        }
        req.setAttribute("result", "payment failed");
        return Path.CUSTOMER_SERVLET;
    }
}
