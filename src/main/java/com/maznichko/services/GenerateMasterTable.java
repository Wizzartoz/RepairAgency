package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GenerateMasterTable implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        List<Request> requests;
        try {
            requests = new RequestDAO().getRequestByLogin(login);
        } catch (DBException e) {
            req.setAttribute("result",e.getMessage());
            return "/jsp/Error.jsp";
        }
        req.setAttribute("table",requests);
        return "/jsp/Master/masterRequests.jsp";
    }
}
