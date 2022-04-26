package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MasterRequest  implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("master");
        List<Request>requests;
        try {
            requests = new RequestDAO().getRequestByLogin(login);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("table",requests);
        return "";
    }
}
