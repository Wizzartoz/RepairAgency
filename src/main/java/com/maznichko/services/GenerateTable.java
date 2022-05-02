package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateTable {

    public static String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        List<Request> requests;
        try {
            requests = new RequestDAO().getRequestByLogin(login);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return "/jsp/Error.jsp";
        }
        double size = requests.size();
        int countPage = 8;
        int pages = (int) Math.ceil(size / countPage);
        int offset;
        if (req.getParameter("offset") == null) {
            offset = 0;
        } else {
            offset = Integer.parseInt(req.getParameter("offset"));
        }
        req.setAttribute("pages", pages);
        List<Request> table = requests.stream().sorted((x, y) -> x.getDate().compareTo(y.getDate())).skip(offset).limit(countPage).collect(Collectors.toList());
        req.setAttribute("table", table);
        return "/jsp/Customer/customerRequests.jsp";
    }
}
