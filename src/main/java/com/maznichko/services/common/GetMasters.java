package com.maznichko.services.common;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.UserDAOimpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class GetMasters {
    public static List<User> findMasters(HttpServletRequest req) {
        List<User> users;
        try {
           users = new UserDAOimpl().findAll();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        List<User> list = users.stream().filter(x->x.getRole().equals("MASTER")).collect(Collectors.toList());
        req.setAttribute("masters",list);
        return list;
    }
}

