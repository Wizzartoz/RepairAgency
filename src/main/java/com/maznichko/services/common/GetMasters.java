package com.maznichko.services.common;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.UserDAOimpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class GetMasters {
    private static final Logger log = Logger.getLogger(GetMasters.class);
    public static List<User> findMasters(HttpServletRequest req) {
        List<User> users;
        try {
           users = new UserDAOimpl().findAll();
        } catch (DBException e) {
            log.error("<---------- find all users was failed",e);
            return null;
        }
        List<User> list = users.stream().filter(x->x.getRole().equals("MASTER")).collect(Collectors.toList());
        req.setAttribute("masters",list);
        log.info("<--------- find all users was successful");
        return list;
    }
}

