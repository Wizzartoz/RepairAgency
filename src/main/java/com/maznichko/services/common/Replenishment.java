package com.maznichko.services.common;

import com.maznichko.dao.DBException;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;


public class Replenishment {
    private static final Logger log = Logger.getLogger(Replenishment.class);
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
            req.setAttribute("result", "You didn't enter anything");
            return true;
        }
        if (money <= 0) {
            req.setAttribute("result", "Enter a positive number");
            return true;
        }
        User user = getUser(login);
        if (user == null) {
            return false;
        }
        int wallet = user.getBank();
        user.setBank(money + wallet);
        boolean isUpdate = updateUser(user);
        if (!isUpdate) {
            return false;
        }
        log.info("<----------- replenishment was successfully");
        return false;
    }

    private boolean updateUser(User user) {
        try {
            userDAO.update(user);
        } catch (DBException e) {
            log.error("<----------- replenishment wa failed", e);
            return false;
        }
        return true;
    }

    private User getUser(String login) {
        User user;
        try {
            user = userDAO.getUserByLogin(login);
        } catch (DBException e) {
            log.error("<----------- replenishment is failed", e);
            return null;
        }
        return user;
    }

}

