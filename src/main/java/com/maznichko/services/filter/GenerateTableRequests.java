package com.maznichko.services.filter;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateTableRequests extends Filterable {

    private final RequestDAO requestDAO;

    public GenerateTableRequests(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    @Override
    public void action(List<Request> requests, HttpServletRequest request) {
        List<Request> requestList = requests;
        try {
            String login = (String) request.getSession().getAttribute("login");
            requestList = requestDAO.getRequestByLogin(login);
        } catch (DBException e) {
            request.setAttribute("result", e.getMessage());
        }
        List<Request> table = requestList.stream()
                .sorted(Comparator.comparing(Request::getDate))
                .collect(Collectors.toList());
        checkNext(table, request);
    }
}
