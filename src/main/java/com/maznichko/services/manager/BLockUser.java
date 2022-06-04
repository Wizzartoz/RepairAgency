package com.maznichko.services.manager;

import com.maznichko.Sender;
import com.maznichko.dao.DBException;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

public class BLockUser {
    private final UserDAO userDAO;
    private final Sender sender;
    private static final Logger log = Logger.getLogger(BLockUser.class);

    public BLockUser(UserDAO userDAO,Sender sender){
        this.userDAO = userDAO;
        this.sender = sender;

    }
    public String block(String userLogin){
        //Getting user
        User user = getUser(userLogin);
        if (user == null){
            return Path.ERROR;
        }
        user.setRole("BLOCK");
        //Updating user
        boolean isUpdate = updateUser(user);
        if (isUpdate){
            log.info(user + " " + user.getLogin() + " is block <------");
            /*
            sender.send(
                    "RepairAgent",
                    "Sorry, but you have been block",
                    "maznichkogame@gmail.com"
            );

             */
            return Path.REPORT_SERVLET;
        }
        return Path.ERROR;
    }
    public String unblock(String userLogin){
        //Getting user
        User user = getUser(userLogin);
        if (user == null){
            return Path.ERROR;
        }
        user.setRole("CUSTOMER");
        //Updating user
        boolean isUpdate = updateUser(user);
        if (isUpdate){
            log.info(user + " " + user.getLogin() + " is unblock <------");
            /*
            sender.send(
                    "RepairAgent",
                    "Congratulation, you have been unblock",
                    "maznichkogame@gmail.com"
            );

             */
            return Path.REPORT_SERVLET;
        }
        return Path.ERROR;
    }
    private boolean updateUser(User user){
        try {
            userDAO.update(user);
        } catch (DBException e) {
            log.error("<------ update user is failed login: " + user,e);
            return false;
        }
        return true;
    }
    private User getUser(String userLogin){
        User user = null;
        try {
            user = userDAO.getUserByLogin(userLogin);
        } catch (DBException e) {
            log.error("<------ get user by login is failed, login: " + userLogin,e);
        }
        return user;
    }


}
