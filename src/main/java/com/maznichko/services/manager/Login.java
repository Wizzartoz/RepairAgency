package com.maznichko.services.manager;

import com.maznichko.dao.DBException;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login implements Command {
    private final UserDAO userDAO;

    public Login(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("pass");
        if (login.isEmpty() || password.isEmpty()) {
            req.setAttribute("result", "Password or Login is empty");
            return Path.LOGIN_SERVLET;
        }
        User user;
        try {
            user = userDAO.getUserByLogin(login);
        } catch (DBException e) {
            req.setAttribute("result", "user didn't exist");
            return Path.LOGIN_SERVLET;
        }
        if (user.getPassword().equals(password)) {
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("login", login);
            httpSession.setAttribute("role", user.getRole());
            if (user.getRole().equals("CUSTOMER")) return Path.CUSTOMER_SERVLET;
            if (user.getRole().equals("MANAGER")) return Path.MANAGER_SERVLET;
            if (user.getRole().equals("MASTER")) return Path.MASTER_SERVLET;
        }
        req.setAttribute("result", "Wrong password");
        return Path.LOGIN_SERVLET;
    }
}
