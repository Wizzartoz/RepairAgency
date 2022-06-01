package com.maznichko.services;

import com.maznichko.MD5;
import com.maznichko.dao.DBException;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Login {
    private final UserDAO userDAO;
    private static final Logger log = Logger.getLogger(Login.class);

    public Login(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * log in user by login and password
     *
     * @param req - servlet request
     * @return - path of servlet
     */
    public String execute(HttpServletRequest req) {
        //Validation
        String login = req.getParameter("login");
        if (login == null || login.isEmpty()) {
            req.setAttribute("result", "Login is empty");
            return Path.LOGIN_SERVLET;
        }
        String password = req.getParameter("pass");
        if (password == null || password.isEmpty()) {
            req.setAttribute("result", "Password is empty");
            return Path.LOGIN_SERVLET;
        }
        User user = getUser(login);
        if (user == null) {
            req.setAttribute("result", "User didn't exist");
            return Path.LOGIN_SERVLET;
        }
        //Check captcha
        boolean isCaptcha = checkCaptcha(req);
        if (!isCaptcha) {
            req.setAttribute("result", "Captcha is failed");
            return Path.LOGIN_SERVLET;
        }
        //Checking conditions before enter
        if (user.getPassword().equals(MD5.getMd5(password))) {
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("login", login);
            httpSession.setAttribute("role", user.getRole());
            log.info("user " + login + " <------- logged in successfully");
            return Path.LOGIN_SERVLET;
        }
        req.setAttribute("result", "Wrong password");
        return Path.LOGIN_SERVLET;
    }

    private User getUser(String login) {
        User user;
        try {
            user = userDAO.getUserByLogin(login);
        } catch (DBException e) {
            return null;
        }
        return user;
    }

    private boolean checkCaptcha(HttpServletRequest request) {
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        if (gRecaptchaResponse != null) {
            try {
                VerifyRecaptcha.verify(gRecaptchaResponse);
                if (gRecaptchaResponse.isEmpty()) {
                    throw new IOException();
                }
            } catch (IOException e) {
                log.warn("<---------- captcha is failed", e);
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
