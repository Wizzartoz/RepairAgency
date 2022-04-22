package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.UserDAO;
import com.maznichko.DAO.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBank implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        User user;
        try {
            user = new UserDAO().getUser(login);
        } catch (DBException e) {
            return "false";
        }
        if (user.getBank() != null) {
            req.setAttribute("bank", user.getBank());
            return "true";
        }
        return "false";
    }
}
