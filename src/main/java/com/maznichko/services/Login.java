package com.maznichko.services;

import com.maznichko.dao.DBException;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Login {
    private final UserDAO userDAO;
    private static final Logger log = Logger.getLogger(Login.class);

    public Login(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * log in user by login and password
     * @param req - servlet request
     * @return - path of servlet
     */
    public String execute(HttpServletRequest req) {
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
            log.info("user " + login + " logged in successfully");
            return Path.LOGIN_SERVLET;
        }
        req.setAttribute("result", "Wrong password");
        return Path.LOGIN_SERVLET;
    }
}
