package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class Sort implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Request> request;
        try {
            request = new RequestDAO().findAllRequest();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        switch (sort) {
            case "date": {
                List<Request> list = request.stream()
                        .sorted((x, y) -> x.getDate().compareTo(y.getDate()))
                        .collect(Collectors.toList());
                req.setAttribute("table", list);
                break;
            }
            case "status": {
                List<Request> list = request.stream()
                        .sorted((x, y) -> x.getComplicationStatus().compareTo(y.getComplicationStatus()))
                        .collect(Collectors.toList());
                req.setAttribute("table", list);
                break;
            }
            case "payStatus": {
                List<Request> list = request.stream()
                        .sorted((x, y) -> x.getPaymentStatus().compareTo(y.getPaymentStatus()))
                        .collect(Collectors.toList());
                req.setAttribute("table", list);
                break;
            }
            default: {
                List<Request> list;
                if (order.equals("decrease")){
                    list = request.stream()
                            .sorted((x, y) -> x.getPrice().compareTo(y.getPrice()))
                            .collect(Collectors.toList());
                }else {
                    list = request.stream()
                            .sorted((x, y) -> -x.getPrice().compareTo(y.getPrice()))
                            .collect(Collectors.toList());
                }
                req.setAttribute("table", list);
                break;
            }
        }
        return "";

    }
}
