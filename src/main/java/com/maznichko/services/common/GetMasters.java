package com.maznichko.services.common;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.UserDAOimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public  class GetMasters {
    public static String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<User> users;
        try {
           users = new UserDAOimpl().findAll();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        List<User> list = users.stream().filter(x->x.getRole().equals("MASTER")).collect(Collectors.toList());
        req.setAttribute("masters",list);
        return "";
    }
}
