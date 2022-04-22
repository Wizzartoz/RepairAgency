package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.UserDAO;
import com.maznichko.DAO.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Replenishment implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
            Integer money = Integer.valueOf(req.getParameter("money"));
            if (money <= 0) {
                req.setAttribute("replenishment", "enter a positive number");
                return "false";
            }
            UserDAO userDAO = new UserDAO();
            User user;
            try {
                user = userDAO.getUser((String) httpSession.getAttribute("login"));
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
            if (user != null) {
                int wallet = user.getBank();
                user.setBank(money + wallet);
                try {
                    userDAO.updateUser(user);
                    return "true";
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
            }
            return "false";
        }
    }

