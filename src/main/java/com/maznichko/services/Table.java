package com.maznichko.services;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.impl.RequestDAOimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class Table {

    public static String generateTable(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        List<Request> requests;
        try {
            requests = new RequestDAOimpl().getRequestByLogin(login);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return "/jsp/Error.jsp";
        }
        List<Request> table = Pagination.pagination(req,requests);
        req.setAttribute("table", table);
        return "/jsp/Customer/";
    }
}
