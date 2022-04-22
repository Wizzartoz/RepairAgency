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
            return "Password or Login is empty";
        }
        User user;
        try {
            user = new UserDAO().getUser(login);
        } catch (DBException e) {
            req.setAttribute("error",e.getMessage());
            return "error";
        }
        if (user == null){
            return "User didn't exist";
        }
        if (user.getPassword().equals(password)){
            httpSession.setAttribute("login",login);
            httpSession.setAttribute("role",user.getRole());
            if (user.getRole().equals("CUSTOMER")) return "/CustomerServlet";
            if (user.getRole().equals("MANAGER")) return "/ManagerServlet";
            if (user.getRole().equals("MASTER")) return "/MasterServlet";
        }
        return "Wrong password";
    }
}
