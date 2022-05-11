package com.maznichko.services;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.UserDAOimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetUsers  {

    public static String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<User> users;
        try {
            users = new UserDAOimpl().findAll();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("users",users);
        return "";
    }
}
