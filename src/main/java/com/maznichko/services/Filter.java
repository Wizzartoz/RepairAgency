package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class Filter implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String master = req.getParameter("master");
        String status = req.getParameter("status");
        List<Request> resultRequest;
        List<Request> masterRequests = null;
        List<Request> statusRequests = null;
        if (status != null){
            try {
                statusRequests = new RequestDAO().findAllRequest();
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        }
        if (master != null) {
            try {
                masterRequests = new RequestDAO().getRequestByLogin(master);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        }
        if (status.equals("progress")) {
            List<Request> filterByProgress = statusRequests.stream()
                    .filter(x->x.getComplicationStatus().equals("in progress"))
                    .collect(Collectors.toList());
            req.setAttribute("table",filterByProgress);
        }
        else if (status.equals("consideration")){
            List<Request> filterByProgress = statusRequests.stream()
                    .filter(x->x.getComplicationStatus().equals("under consideration"))
                    .collect(Collectors.toList());
            req.setAttribute("table",filterByProgress);
        }
        else {
            List<Request> filterByDone = statusRequests.stream()
                    .filter(x->x.getComplicationStatus().equals("done"))
                    .collect(Collectors.toList());
            req.setAttribute("table",filterByDone);
        }
        return "";
    }
}
