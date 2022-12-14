package com.maznichko.services.filtration;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateTableRequests extends Filterable {

    private final RequestDAO requestDAO;
    private static final Logger log = Logger.getLogger(GenerateTableRequests.class);

    public GenerateTableRequests(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    /**
     * getting all request from DB
     *
     * @param requests - this set of requests who we are got from DB
     * @param request  - our request
     */
    @Override
    public void action(List<Request> requests, HttpServletRequest request) {
        List<Request> requestList = requests;
        try {
            String login = (String) request.getSession().getAttribute("login");
            requestList = requestDAO.getRequestByLogin(login);
        } catch (DBException e) {
            log.error("<--------generate table is failed", e);
        }
        List<Request> table = requestList.stream()
                .sorted(Comparator.comparing(Request::getDate))
                .collect(Collectors.toList());
        checkNext(table, request);
    }
}
