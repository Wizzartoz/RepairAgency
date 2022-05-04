package com.maznichko.services.commands;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.UserDAO;
import com.maznichko.DAO.entity.User;
import com.maznichko.services.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Replenishment implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login;
        if (req.getParameter("login") == null){
            login = (String) httpSession.getAttribute("login");
            req.setAttribute("path","/GeneralCustomerServlet");
        }else {
            login = req.getParameter("login");
            req.setAttribute("path","/ManagerServlet");
        }
        int money;
        try {
            money = Integer.parseInt(req.getParameter("money"));
        } catch (NumberFormatException e) {
            req.setAttribute("result", "you didn't enter anything");
            return "/ReplenishmentCustomerServlet";
        }

        if (money <= 0) {
            req.setAttribute("result", "Enter a positive number");
            return "/ReplenishmentCustomerServlet";
        }
        UserDAO userDAO = new UserDAO();
        User user;
        try {
            user = userDAO.getUser(login);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return "/jsp/Error.jsp";
        }
        if (user != null) {
            int wallet = user.getBank();
            user.setBank(money + wallet);
            try {
                userDAO.updateUser(user);
                req.setAttribute("result", "payment was successful");
                return "/ReplenishmentCustomerServlet";
            } catch (DBException e) {
                req.setAttribute("result", e.getMessage());
                return "/jsp/Error.jsp";
            }
        }
        return "/jsp/Error.jsp";
    }
}

