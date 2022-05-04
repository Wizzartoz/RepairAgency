package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.UserDAO;
import com.maznichko.DAO.entity.User;
import com.maznichko.services.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetUsers  {

    public static String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<User> users;
        try {
            users = new UserDAO().findAllUsers();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("users",users);
        return "";
    }
}
