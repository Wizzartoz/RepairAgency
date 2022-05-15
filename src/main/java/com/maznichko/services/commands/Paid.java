package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Paid implements Command {
    private final UserDAO userDAO;
    private final RequestDAO requestDAO;
    public Paid(UserDAO userDAO,RequestDAO requestDAO){
        this.userDAO = userDAO;
        this.requestDAO = requestDAO;
    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //PAYMENT STATUS : unpaid waiting for payment,paid COMPLICATION STATUS : under consideration,consideration,refuse,,in progress , done
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
