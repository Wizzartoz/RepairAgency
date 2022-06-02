package com.maznichko.services;

import com.maznichko.MD5;
import com.maznichko.dao.DBException;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class Register {
    private final UserDAO userDAO;
    private static final Logger log = Logger.getLogger(Register.class);
    private final User user;

    public Register(UserDAO userDAO) {
        this.userDAO = userDAO;
        user = new User();
    }


    /**
     * method registering new user
     *
     * @param req  - request servlet
     * @param role - role of user
     * @return - true if register was successful or not if false
     */
    public boolean register(HttpServletRequest req, String role,boolean captcha) {
        String name = req.getParameter("name");
        if (name == null || name.isEmpty()) {
            req.setAttribute("result", "Name is empty");
            return false;
        }
        user.setName(name);
        String surname = req.getParameter("surname");
        if (surname == null || surname.isEmpty()) {
            req.setAttribute("result", "Surname is empty");
            return false;
        }
        user.setSurname(surname);
        String login = req.getParameter("login");
        if (login == null || login.isEmpty()) {
            req.setAttribute("result", "Login is empty");
            return false;
        }
        user.setLogin(login);
        String password = req.getParameter("pass");
        if (password == null || password.isEmpty()) {
            req.setAttribute("result", "Password is empty");
            return false;
        }
        user.setPassword(MD5.getMd5(password));
        String email = req.getParameter("email");
        if (email == null || email.isEmpty()) {
            req.setAttribute("result", "Email is empty");
            return false;
        }
        user.setEmail(email);
        String phone = req.getParameter("phone");
        if (phone != null && !phone.isEmpty()) {
            user.setPhone(phone);
        }
        user.setRole(role);
        //Bad practice
        if (!role.equals("MASTER")) {
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("login", login);
            httpSession.setAttribute("role", role);
        }
        if (captcha){
            boolean isCaptcha = checkCaptcha(req);
            if (!isCaptcha) {
                req.setAttribute("result", "captcha is failed");
                return false;
            }
        }
        boolean isInsert = insertUser(req);
        if (!isInsert) {
            return false;
        }
        req.setAttribute("result", "user successfully registered");
        log.info("user " + login + "<-------- was registering successful");
        return true;
    }

    private boolean insertUser(HttpServletRequest request) {
        try {
            userDAO.insert(user);
        } catch (DBException e) {
            request.setAttribute("result", "login is already taken");
            log.error("<---------- insert user is failed", e);
            return false;
        }
        return true;
    }

    private boolean checkCaptcha(HttpServletRequest request) {
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        if (gRecaptchaResponse != null) {
            try {
                VerifyRecaptcha.verify(gRecaptchaResponse);
                if (gRecaptchaResponse.isEmpty()){
                    throw new IOException();
                }
            } catch (IOException e) {
                log.warn("<---------- captcha is failed", e);
                return false;
            }
        }else {
            return false;
        }
        return true;
    }
}

