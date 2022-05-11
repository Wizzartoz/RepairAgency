package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.impl.RequestDAOimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRequest implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("req"));
        Request request;
        try {
            request = new RequestDAOimpl().getData(id);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("request",request);
        return "";
    }
}
