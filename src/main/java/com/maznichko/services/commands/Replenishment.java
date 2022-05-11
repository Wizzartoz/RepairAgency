package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.UserDAOimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Replenishment implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login;
        if (req.getParameter("login") == null){
            login = (String) httpSession.getAttribute("login");
            req.setAttribute("path","/GeneralCustomerServlet");
        }else {
            login = req.getParameter("login");
            req.setAttribute("path","/ManagerServlet");
        }
        int money;
        try {
            money = Integer.parseInt(req.getParameter("money"));
        } catch (NumberFormatException e) {
            req.setAttribute("result", "you didn't enter anything");
            return "/ReplenishmentCustomerServlet";
        }

        if (money <= 0) {
            req.setAttribute("result", "Enter a positive number");
            return "/ReplenishmentCustomerServlet";
        }
        UserDAOimpl userDAOimpl = new UserDAOimpl();
        User user;
        try {
            user = userDAOimpl.getUserByLogin(login);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return "/jsp/Error.jsp";
        }
        if (user != null) {
            int wallet = user.getBank();
            user.setBank(money + wallet);
            try {
                userDAOimpl.update(user);
                req.setAttribute("result", "payment was successful");
                return "/ReplenishmentCustomerServlet";
            } catch (DBException e) {
                req.setAttribute("result", e.getMessage());
                return "/jsp/Error.jsp";
            }
        }
        return "/jsp/Error.jsp";
    }
}

