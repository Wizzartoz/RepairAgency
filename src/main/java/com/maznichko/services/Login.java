package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.UserDAO;
import com.maznichko.DAO.entity.User;

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
            req.setAttribute("result","Password or Login is empty");
            return "/jsp/login.jsp";
        }
        User user;
        try {
            user = new UserDAO().getUser(login);
        } catch (DBException e) {
            req.setAttribute("result","user didn't exist");
            return "/jsp/login.jsp";
        }
        if (user.getPassword().equals(password)){
            httpSession.setAttribute("login",login);
            httpSession.setAttribute("role",user.getRole());
            if (user.getRole().equals("CUSTOMER")) return "/GeneralCustomerServlet";
            if (user.getRole().equals("MANAGER")) return "/ManagerServlet";
            if (user.getRole().equals("MASTER")) return "/MasterServlet";
        }
        req.setAttribute("result","Wrong password");
        return "/jsp/login.jsp";
    }
}
