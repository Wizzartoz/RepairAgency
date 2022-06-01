package com.maznichko.services.common;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetBank {
    private static final Logger log = Logger.getLogger(GetBank.class);

    public static String getBank(HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        User user;
        try {
            user = new UserDAOimpl().getUserByLogin(login);
        } catch (DBException e) {
            log.error("<------------ get bank is failed");
            return Path.ERROR;
        }
        httpSession.setAttribute("bank", user.getBank());
        return Path.CUSTOMER_JSP;
    }
}
