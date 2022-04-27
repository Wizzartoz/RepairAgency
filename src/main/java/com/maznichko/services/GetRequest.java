package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRequest implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("req"));
        Request request;
        try {
            request = new RequestDAO().getRequestByID(id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("request",request);
        return "";
    }
}
