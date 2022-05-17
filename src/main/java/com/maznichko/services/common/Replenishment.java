package com.maznichko.services.common;

import com.maznichko.dao.DBException;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;


import javax.servlet.http.HttpServletRequest;


public class Replenishment {
    private final UserDAO userDAO;

    public Replenishment(UserDAO userDAO) {
        this.userDAO = userDAO;

    }

    public boolean replenishment(HttpServletRequest req) {
        String login = req.getParameter("login");
        if (req.getParameter("login") == null) {
            login = (String) req.getSession().getAttribute("login");
        }
        int money;
        try {
            money = Integer.parseInt(req.getParameter("money"));
        } catch (NumberFormatException e) {
            req.setAttribute("result", "you didn't enter anything");
            return true;
        }

        if (money <= 0) {
            req.setAttribute("result", "Enter a positive number");
            return true;
        }
        User user;
        try {
            user = userDAO.getUserByLogin(login);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return false;
        }
        if (user != null) {
            int wallet = user.getBank();
            user.setBank(money + wallet);
            try {
                userDAO.update(user);
                req.setAttribute("result", "payment was successful");
                return true;
            } catch (DBException e) {
                req.setAttribute("result", e.getMessage());
                return false;
            }
        }
        return false;
    }
}

