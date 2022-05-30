package com.maznichko.services.manager;

import com.maznichko.dao.DBException;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

public class BLockUser {
    private final UserDAO userDAO;
    private static final Logger log = Logger.getLogger(BLockUser.class);

    public BLockUser(UserDAO userDAO){
        this.userDAO = userDAO;

    }
    public String block(String userLogin){
        User user = getUser(userLogin);
        if (user == null){
            return Path.ERROR;
        }
        user.setRole("BLOCK");
        System.out.println(user.getRole() + "<--------------role");
        boolean isUpdate = updateUser(user);
        if (isUpdate){
            return Path.REPORT_SERVLET;
        }
        return Path.ERROR;
    }
    public String unblock(String userLogin){
        User user = getUser(userLogin);
        if (user == null){
            return Path.ERROR;
        }
        user.setRole("CUSTOMER");
        boolean isUpdate = updateUser(user);
        if (isUpdate){
            return Path.REPORT_SERVLET;
        }
        return Path.ERROR;
    }
    private boolean updateUser(User user){
        try {
            userDAO.update(user);
        } catch (DBException e) {
            log.error(e.getMessage() + "<------ update user is failed login: " + user);
            return false;
        }
        return true;
    }
    private User getUser(String userLogin){
        User user = null;
        try {
            user = userDAO.getUserByLogin(userLogin);
        } catch (DBException e) {
            log.error(e.getMessage() + "<------ Get user by login is failed login: " + userLogin);
        }
        return user;
    }


}
