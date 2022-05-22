package com.maznichko.services;

import com.maznichko.dao.DBException;;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;
import com.maznichko.services.manager.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Register{
    private final UserDAO userDAO;
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
        user.setPassword(password);
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
        req.setAttribute("result","user successfully registered");
        return true;
    }
}

