package com.maznichko.services.common;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.FeedbackDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;
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
            log.error(e.getMessage() + " get bank was failed");
            return "/jsp/Error.jsp";
        }
        httpSession.setAttribute("bank", user.getBank());
        log.info("get bank was successful bank:" + user.getBank());
        return "/jsp/Customer/customerMain.jsp";
    }
}
