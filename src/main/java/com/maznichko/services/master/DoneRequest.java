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

    public DoneRequest(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;

    }

    /**
     * Master marking request if he did
     *
     * @param req  - request who we are getting
     * @param resp - servlet response
     * @return - path of servlet
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("doneID"));
        //Getting request
        Request request = getRequest(id);
        if (request == null) {
            return Path.ERROR;
        }
        //Checking complication status
        if (!request.getComplicationStatus().equals("in progress")) {
            req.setAttribute("result", "You cannot change the status");
            return Path.MASTER_SERVLET;
        }
        request.setComplicationStatus("done");
        //Updating request
        boolean isUpdate = updateRequest(request);
        if (!isUpdate) {
            return Path.ERROR;
        }
        req.setAttribute("result", "Status changed successfully");
        return Path.MASTER_SERVLET;
    }

    private Request getRequest(long id) {
        Request request;
        try {
            request = requestDAO.getData(id);
        } catch (DBException e) {
            log.error("<-------- done request is failed", e);
            return null;
        }
        return request;
    }

    private boolean updateRequest(Request request) {
        try {
            requestDAO.update(request);
        } catch (DBException e) {
            log.error("<-------- done request is failed", e);
            return false;
        }
        return true;
    }
}
