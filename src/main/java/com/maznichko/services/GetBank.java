package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.UserDAO;
import com.maznichko.DAO.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBank {
    public static String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        User user;
        try {
            user = new UserDAO().getUser(login);
        } catch (DBException e) {
            return "/jsp/Error.jsp";
        }
        httpSession.setAttribute("bank", user.getBank());
        return "/jsp/Customer/customerMain.jsp";
    }
}
