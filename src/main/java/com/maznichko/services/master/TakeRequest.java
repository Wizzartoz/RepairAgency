package com.maznichko.services.master;

import com.maznichko.Sender;
import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TakeRequest implements MasterCommand {
    private final RequestDAO requestDAO;
    private final Sender sender;
    private static final Logger log = Logger.getLogger(TakeRequest.class);

    public TakeRequest(RequestDAO requestDAO, Sender sender) {
        this.requestDAO = requestDAO;
        this.sender = sender;
    }

    /**
     * Master marking request if he took it
     *
     * @param req  - request who we are getting
     * @param resp - response servlet
     * @return - path of servlet
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("id"));
        //Getting request
        Request request = getRequest(id);
        if (request == null) {
            return Path.ERROR;
        }
        //Validation
        if (!request.getPaymentStatus().equals("paid")) {
            req.setAttribute("result", "Request must be paid");
            return Path.MASTER_SERVLET;
        }
        if (request.getComplicationStatus().equals("done")) {
            req.setAttribute("result", "Order already placed");
            return Path.MASTER_SERVLET;
        }
        if (request.getComplicationStatus().equals("under consideration")) {
            req.setAttribute("result", "Request must be considered");
            return Path.MASTER_SERVLET;
        }
        request.setComplicationStatus("in progress");
        //Updating request
        boolean isUpdate = updateRequest(request);
        if (!isUpdate) {
            return Path.ERROR;
        }
        sender.send(
                "Request",
                "The master took your order",
                "maznichkogame@gmail.com"
        );
        req.setAttribute("result", "Status changed successfully");
        return Path.MASTER_SERVLET;
    }

    private boolean updateRequest(Request request) {
        try {
            requestDAO.update(request);
        } catch (DBException e) {
            log.error("<---------- take request is failed");
            return false;
        }
        return true;
    }

    private Request getRequest(long id) {
        Request request;
        try {
            request = requestDAO.getData(id);
        } catch (DBException e) {
            log.error("<-------- take request is failed", e);
            return null;
        }
        return request;
    }
}
