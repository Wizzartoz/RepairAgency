package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.UserDAOimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login = req.getParameter("login");
        String password = req.getParameter("pass");
        if (login.isEmpty() || password.isEmpty()) {
            req.setAttribute("result", "Password or Login is empty");
            return "/LoginServlet";
        }
        User user;
        try {
            user = new UserDAOimpl().getUserByLogin(login);
        } catch (DBException e) {
            req.setAttribute("result", "user didn't exist");
            return "/LoginServlet";
        }
        if (user.getPassword().equals(password)) {
            httpSession.setAttribute("login", login);
            httpSession.setAttribute("role", user.getRole());
            if (user.getRole().equals("CUSTOMER")) return "/GeneralCustomerServlet";
            if (user.getRole().equals("MANAGER")) return "/ManagerServlet";
            if (user.getRole().equals("MASTER")) return "/MasterServlet";
        }
        req.setAttribute("result", "Wrong password");
        return "/LoginServlet";
    }
}
