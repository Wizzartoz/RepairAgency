package com.maznichko.services.master;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoneRequest implements MasterCommand {
    private final RequestDAO requestDAO;
    private static final Logger log = Logger.getLogger(DoneRequest.class);
    public DoneRequest(RequestDAO requestDAO){
        this.requestDAO = requestDAO;

    }

    /**
     * Master marking request if he did
     * @param req - request who we are getting
     * @param resp - servlet response
     * @return - path of servlet
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("doneID"));
        Request request;
        try {
            request = requestDAO.getData(id);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            log.error(e.getMessage() + " done request is failed");
            return Path.ERROR;
        }
        if (!request.getComplicationStatus().equals("in progress")) {
            req.setAttribute("result", "you cannot change the status");
            return Path.MASTER_SERVLET;
        }
        request.setComplicationStatus("done");
        try {
            requestDAO.update(request);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            log.error(e.getMessage() + " done request is failed");
            return Path.ERROR;
        }
        req.setAttribute("result", "status changed successfully");
        return Path.MASTER_SERVLET;
    }
}
