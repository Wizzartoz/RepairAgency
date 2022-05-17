package com.maznichko.services.common;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.UserDAOimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBank {
    public static String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        User user;
        try {
            user = new UserDAOimpl().getUserByLogin(login);
        } catch (DBException e) {
            return "/jsp/Error.jsp";
        }
        httpSession.setAttribute("bank", user.getBank());
        return "/jsp/Customer/customerMain.jsp";
    }
}
