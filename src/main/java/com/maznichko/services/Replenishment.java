package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.UserDAO;
import com.maznichko.DAO.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Replenishment implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        int money;
        try {
            money = Integer.parseInt(req.getParameter("money"));
        } catch (NumberFormatException e) {
            req.setAttribute("result", "you didn't enter anything");
            return "/GeneralCustomerServlet";
        }

        if (money <= 0) {
            req.setAttribute("result", "Enter a positive number");
            return "/GeneralCustomerServlet";
        }
        UserDAO userDAO = new UserDAO();
        User user;
        try {
            user = userDAO.getUser(login);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return "/jsp/Error.jsp";
        }
        if (user != null) {
            int wallet = user.getBank();
            user.setBank(money + wallet);
            try {
                userDAO.updateUser(user);
                return "/GeneralCustomerServlet";
            } catch (DBException e) {
                req.setAttribute("result", e.getMessage());
                return "/jsp/Error.jsp";
            }
        }
        return "/jsp/Error.jsp";
    }
}

