package com.maznichko.services.customer;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteRequest implements CustomerCommand {
    private final RequestDAO requestDAO;
    private static final Logger log = Logger.getLogger(DeleteRequest.class);

    public DeleteRequest(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;

    }

    /**
     * Delete request from DB
     *
     * @param req - request who we are getting
     * @return - path of servlet
     */
    @Override
    public String execute(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            Request request = requestDAO.getData(id);
            if (checkPermission(request)) {
                req.setAttribute("result", "You cannot delete request");
                return Path.CUSTOMER_SERVLET;
            }
            requestDAO.delete(request);
        } catch (DBException e) {
            log.error(e.getMessage() + "<-------- delete request is failed");
            return Path.ERROR;
        }
        req.setAttribute("result", "Deletion successful");
        return Path.CUSTOMER_SERVLET;
    }

    private boolean checkPermission(Request request) {
        String compStatus = request.getComplicationStatus();
        String paymentStatus = request.getPaymentStatus();
        if (!compStatus.equals("under consideration")) return true;
        return paymentStatus.equals("paid");

    }
}
