package com.maznichko.services.common;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.services.manager.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MasterRequest  implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("master");
        List<Request>requests;
        try {
            requests = new RequestDAOimpl().getRequestByLogin(login);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("table",requests);
        return "";
    }
}
