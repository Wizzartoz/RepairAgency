package com.maznichko.services.commands;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;
import com.maznichko.services.Pagination;
import com.maznichko.services.Sort;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


public class Filter implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Request> statusRequests;
        try {
            statusRequests = new RequestDAO().findAllRequest();
        } catch (DBException e) {
            return "/jsp/Error.jsp";
        }
        String[] compStatuses = req.getParameterValues("compStatus");
        List<Request> compRequests = new ArrayList<>();
        if (compStatuses != null) {
            for (String status : compStatuses) {
                compRequests.addAll(statusRequests
                        .stream()
                        .filter(x -> x.getComplicationStatus().equals(status))
                        .collect(Collectors.toList()));
            }
        }
        List<Request> masterRequests = new ArrayList<>();
        String[] setMasterLogins = req.getParameterValues("masterLogin");
        if (setMasterLogins != null) {
            for (String login : setMasterLogins) {
                masterRequests.addAll(statusRequests
                        .stream()
                        .filter(x -> x.getMasterLogin() != null)
                        .filter(x -> x.getMasterLogin().equals(login))
                        .collect(Collectors.toList()));
            }
        }
        List<Request> payRequests = new ArrayList<>();
        String[] payStatuses = req.getParameterValues("payStatus");
        if (payStatuses != null) {
            for (String status : payStatuses) {
                System.out.println(status);
                payRequests.addAll(statusRequests
                        .stream()
                        .filter(x -> x.getPaymentStatus().equals(status))
                        .collect(Collectors.toList()));
            }
        }
        Set<Request> resultRequest = new HashSet<>(compRequests);
        if (!masterRequests.isEmpty()){
            resultRequest.retainAll(masterRequests);
        }
        if(!payRequests.isEmpty()){
            resultRequest.retainAll(payRequests);
        }
        List<Request> result = new ArrayList<>(resultRequest);
        String sort = req.getParameter("sort");
        if (req.getParameter("sort") != null) {
            result = Sort.sort(sort,result);
        }
        List<Request> table = Pagination.pagination(req, result);
        req.setAttribute("table", table);
        return "/jsp/Manager/managerMain.jsp";
    }
}
