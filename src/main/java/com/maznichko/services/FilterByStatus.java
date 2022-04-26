package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class FilterByStatus implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String status = req.getParameter("status");
        List<Request> requests;
        try {
            requests = new RequestDAO().findAllRequest();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        if (status.equals("progress")) {
            List<Request> filterByProgress = requests.stream()
                    .filter(x->x.getComplicationStatus().equals("in progress"))
                    .collect(Collectors.toList());
            req.setAttribute("table",filterByProgress);
        }
        else if (status.equals("consideration")){
            List<Request> filterByProgress = requests.stream()
                    .filter(x->x.getComplicationStatus().equals("under consideration"))
                    .collect(Collectors.toList());
            req.setAttribute("table",filterByProgress);
        }
        else {
            List<Request> filterByDone = requests.stream()
                    .filter(x->x.getComplicationStatus().equals("done"))
                    .collect(Collectors.toList());
            req.setAttribute("table",filterByDone);
        }
        return "";
    }
}
