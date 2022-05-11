package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Paid implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //PAYMENT STATUS : unpaid waiting for payment,paid COMPLICATION STATUS : under consideration,consideration,refuse,,in progress , done
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        float price = Float.parseFloat(req.getParameter("price"));
        long id = Long.parseLong(req.getParameter("paymentID"));
        RequestDAOimpl requestDAOimpl = new RequestDAOimpl();
        UserDAOimpl userDAOimpl = new UserDAOimpl();
        String paidStatus = req.getParameter("payment");
        if (paidStatus.equals("waiting for payment")) {
            User user;
            try {
                user = userDAOimpl.getUserByLogin(login);
            } catch (DBException e) {
                req.setAttribute("result", e.getMessage());
                return "/jsp/Error.jsp";
            }
            if (user.getBank() >= price) {
                user.setBank(user.getBank() - (int) price);
                try {
                    userDAOimpl.update(user);
                } catch (DBException e) {
                    req.setAttribute("result", e.getMessage());
                    return "/jsp/Customer/customerMain.jsp";
                }
            } else {
                req.setAttribute("result", "not enough money");
                return "/jsp/Error.jsp";
            }
            Request request;
            try {
                request = requestDAOimpl.getData(id);
            } catch (DBException e) {
                req.setAttribute("result", e.getMessage());
                return "/jsp/Error.jsp";
            }
            request.setPaymentStatus("paid");
            try {
                requestDAOimpl.update(request);
            } catch (DBException e) {
                req.setAttribute("result", null);
                return "/jsp/Customer/customerMain.jsp";
            }
            req.setAttribute("result", "payment was successful");
            return "/jsp/Customer/customerMain.jsp";
        }
        req.setAttribute("result", "payment failed");
        return "/jsp/Customer/customerMain.jsp";
    }
}
