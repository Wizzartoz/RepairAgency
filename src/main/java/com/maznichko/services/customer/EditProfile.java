package com.maznichko.services.customer;

import com.maznichko.MD5;
import com.maznichko.dao.DBException;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class EditProfile implements CustomerCommand {
    private final UserDAO userDAO;
    private static final Logger log = Logger.getLogger(EditProfile.class);

    public EditProfile(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Method edit user parameters
     *
     * @param req - request who we are getting
     * @return - path to servlet
     */
    @Override
    public String execute(HttpServletRequest req) {
        String sessionLogin = (String) req.getSession().getAttribute("login");
        User user = getUser(sessionLogin);
        if (user == null) {
            return Path.ERROR;
        }
        String password = req.getParameter("Password");
        if (password != null && !password.isEmpty()) {
            user.setPassword(MD5.getMd5(password));
        }
        String email = req.getParameter("Email");
        if (email != null && !email.isEmpty()) {
            user.setEmail(email);
        }
        String phone = req.getParameter("Phone");
        if (phone != null && !phone.isEmpty()) {
            user.setPhone(phone);
        }
        boolean isUpdate = updateUser(user);
        if (!isUpdate) {
            return Path.ERROR;
        }
        req.setAttribute("result", "profile edit successful");
        log.info(sessionLogin + " <--------- user edit successful");
        return Path.CUSTOMER_SERVLET;

    }

    private User getUser(String login) {
        User user;
        try {
            user = userDAO.getUserByLogin(login);
        } catch (DBException e) {
            log.error(e.getMessage() + "<--------- get user is failed");
            return null;
        }
        return user;
    }

    private boolean updateUser(User user) {
        try {
            userDAO.update(user);
        } catch (DBException e) {
            log.error(e.getMessage() + "<--------- update user is failed");
            return false;
        }
        return true;
    }
}
