package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.UserDAO;
import com.maznichko.DAO.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public  class GetMasters {
    public static String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<User> users;
        try {
           users = new UserDAO().findAllUsers();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        List<User> list = users.stream().filter(x->x.getRole().equals("MASTER")).collect(Collectors.toList());
        req.setAttribute("users",list);
        return "";
    }
}
