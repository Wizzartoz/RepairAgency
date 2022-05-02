package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


public class Filter implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String status1 = req.getParameter("done");
        String status2 = req.getParameter("progress");
        String status3 = req.getParameter("consideration");
        List<Request> resultRequest = new ArrayList<>();
        List<Request> masterRequest = new ArrayList<>();
        List<Request> statusRequests;
        try {
            statusRequests = new RequestDAO().findAllRequest();
        } catch (DBException e) {
            return "/jsp/Error.jsp";
        }
        if (status1 != null) {
            resultRequest.addAll(statusRequests.stream()
                    .filter(x -> x.getComplicationStatus().equals("done"))
                    .collect(Collectors.toList()));
        }
        if (status2 != null) {
            resultRequest.addAll(statusRequests.stream()
                    .filter(x -> x.getComplicationStatus().equals("in progress"))
                    .collect(Collectors.toList()));
        }
        if (status3 != null) {
            resultRequest.addAll(statusRequests.stream()
                    .filter(x -> x.getComplicationStatus().equals("under consideration"))
                    .collect(Collectors.toList()));
        }
        if (req.getParameterValues("masterLogin") != null) {
            for (String request : req.getParameterValues("masterLogin")) {
                List<Request> requests;
                try {
                    requests = new RequestDAO().getRequestByLogin(request);
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
                masterRequest.addAll(requests);
            }
            resultRequest = masterRequest.stream().filter(resultRequest::contains).collect(Collectors.toList());
        }
        double size = resultRequest.size();
        int countPage = 8;
        int pages = (int) Math.ceil(size / countPage);
        int offset;
        if (req.getParameter("offset") == null) {
            offset = 0;
        } else {
            offset = Integer.parseInt(req.getParameter("offset"));
        }
        req.setAttribute("pages", pages);
        String sort = req.getParameter("sort");
        if (req.getParameter("sort") != null) {
            resultRequest = new Sort().sort(sort, resultRequest);
        }
        List<Request> table = resultRequest.stream().skip(offset).limit(countPage).collect(Collectors.toList());
        req.setAttribute("table", table);
        return "/jsp/Manager/managerMain.jsp";
    }
}
