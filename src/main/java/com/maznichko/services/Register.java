package com.maznichko.services;

import com.maznichko.MD5;
import com.maznichko.dao.DBException;;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class Register{
    private final UserDAO userDAO;
    private static final Logger log = Logger.getLogger(Register.class);
    public Register(UserDAO userDAO){
        this.userDAO = userDAO;
    }


    /**
     * method registering new user
     * @param req - request servlet
     * @param role - role of user
     * @return - true if register was successful or not if false
     */
    public boolean register(HttpServletRequest req,String role) {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String login = req.getParameter("login");
        String password = req.getParameter("pass");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        if (login.isEmpty() || password.isEmpty()) {
            req.setAttribute("result", "Login or Password is empty");
            return false;
        }
        if (name.isEmpty() || surname.isEmpty()) {
            req.setAttribute("result", "Name or Surname is empty");
            return false;
        }
        if (email.isEmpty()) {
            req.setAttribute("result", "Email is empty");
            return false;
        }

        User user = new User();
        user.setRole(role);
        user.setLogin(login);
        user.setPassword(MD5.getMd5(password));
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setEmail(email);
        try {
            userDAO.insert(user);
        } catch (DBException e) {
            req.setAttribute("result", "what's wrong");
            return false;
        }
        if(!role.equals("MASTER")){
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("login", login);
            httpSession.setAttribute("role", role);
        }
        // get reCAPTCHA request param
        String gRecaptchaResponse = req
                .getParameter("g-recaptcha-response");
        if (gRecaptchaResponse != null){
            boolean verify;
            try {
                verify = VerifyRecaptcha.verify(gRecaptchaResponse);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!verify){
                req.setAttribute("result","captcha is failed");
                return false;
            }
        }
        req.setAttribute("result","user successfully registered");
        log.info("user " + login + " was registering successful");
        return true;
    }
}

