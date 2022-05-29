package com.maznichko.services.common;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.UserDAOimpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetUsers  {
    private static final Logger log = Logger.getLogger(GetUsers.class);

    public static List<User> execute(HttpServletRequest req) {
        List<User> users;
        try {
            users = new UserDAOimpl().findAll();
        } catch (DBException e) {
            log.error(e.getMessage() + " get users wasn't successful");
            throw new RuntimeException(e);
        }
        req.setAttribute("users",users);
        log.info("get users wasn't successful");
        return users;
    }
}
