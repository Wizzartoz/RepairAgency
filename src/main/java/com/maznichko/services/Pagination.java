package com.maznichko.services;

import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class Pagination {
    public static List<Request> pagination(HttpServletRequest req, List<Request> requests){
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
        return requests.stream().skip(offset).limit(countPage).collect(Collectors.toList());
    }
}
