package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Replenishment implements Command {
    private final UserDAO userDAO;
    public Replenishment(UserDAO userDAO){
        this.userDAO = userDAO;

    }
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login;
        if (req.getParameter("login") == null){
            login = (String) httpSession.getAttribute("login");
            req.setAttribute("path",Path.CUSTOMER_SERVLET);
        }else {
            login = req.getParameter("login");
            req.setAttribute("path", Path.MANAGER_SERVLET);
        }
        int money;
        try {
            money = Integer.parseInt(req.getParameter("money"));
        } catch (NumberFormatException e) {
            req.setAttribute("result", "you didn't enter anything");
            return Path.REPLENISHMENT_SERVLET;
        }

        if (money <= 0) {
            req.setAttribute("result", "Enter a positive number");
            return Path.REPLENISHMENT_SERVLET;
        }
        User user;
        try {
            user = userDAO.getUserByLogin(login);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return Path.ERROR;
        }
        if (user != null) {
            int wallet = user.getBank();
            user.setBank(money + wallet);
            try {
                userDAO.update(user);
                req.setAttribute("result", "payment was successful");
                return Path.REPLENISHMENT_SERVLET;
            } catch (DBException e) {
                req.setAttribute("result", e.getMessage());
                return Path.ERROR;
            }
        }
        return Path.ERROR;
    }
}

