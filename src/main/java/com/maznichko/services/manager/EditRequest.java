package com.maznichko.services.manager;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;
import com.maznichko.services.common.GetMasters;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditRequest implements Command {
    private final RequestDAO requestDAO;
    private static final Logger log = Logger.getLogger(EditRequest.class);

    public EditRequest(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;

    }

    /**
     * Editing request which customer left
     *
     * @param req  - request who we are getting
     * @param resp - servlet response
     * @return - path of servlet
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("id"));
        if (req.getParameter("price") == null) {
            req.setAttribute("result", "Request already added");
            return Path.MANAGER_SERVLET;
        }
        //Getting request
        Request request = getRequest(id);
        if (request == null) {
            return Path.ERROR;
        }
        if (req.getParameter("cStatus").equals("refuse")) {
            request.setPrice(0F);
            request.setPaymentStatus("unpaid");
            request.setComplicationStatus("refuse");
        } else {
            //Checking price
            float price;
            try {
                price = Float.parseFloat(req.getParameter("price"));
            } catch (NumberFormatException e) {
                req.setAttribute("result", "Incorrect price value");
                return Path.MANAGER_SERVLET;
            }
            if (price <= 0) {
                req.setAttribute("result", "Incorrect price value");
                return Path.MANAGER_SERVLET;
            }
            //Checking payment status
            String paymentStatus = req.getParameter("pStatus");
            if (paymentStatus == null || !paymentStatus.equals("waiting for payment")) {
                req.setAttribute("result", "Incorrect payment status");
                return Path.MANAGER_SERVLET;
            }
            if (!request.getPaymentStatus().equals("unpaid")) {
                req.setAttribute("result", "Incorrect payment status");
                return Path.MANAGER_SERVLET;
            }
            //Checking master
            String masterLogin = req.getParameter("usr");
            if (masterLogin == null) {
                req.setAttribute("result", "You must be select the master");
                return Path.MANAGER_SERVLET;
            }
            //Getting masters
            List<User> masters = GetMasters.findMasters(req);
            if (masters == null) {
                return Path.ERROR;
            }
            User master = new User();
            master.setLogin(masterLogin);
            if (!masters.contains(master)) {
                req.setAttribute("result", "Master didn't exist");
                return Path.MANAGER_SERVLET;
            }
            //Checking complication status
            String complicationStatus = req.getParameter("cStatus");
            if (complicationStatus == null) {
                req.setAttribute("result", "Wrong complication status");
                return Path.MANAGER_SERVLET;
            }
            if (!request.getComplicationStatus().equals("under consideration")) {
                req.setAttribute("result", "Wrong complication status");
                return Path.MANAGER_SERVLET;
            }
            if (!(complicationStatus.equals("refuse") || complicationStatus.equals("consideration"))) {
                req.setAttribute("result", "wrong complication status");
                return Path.MANAGER_SERVLET;
            }
            if (complicationStatus.equals("refuse")) {
                masterLogin = null;
            }
            //Set parameters
            request.setPrice(price);
            request.setPaymentStatus(paymentStatus);
            request.setComplicationStatus(complicationStatus);
            request.setMasterLogin(masterLogin);
            //Inserting masters
            boolean isInsert = insertRequest(masterLogin, id);
            if (!isInsert) {
                return Path.ERROR;
            }
        }
        //Updating request
        boolean isUpdate = updateRequest(request);
        if (!isUpdate) {
            return Path.ERROR;
        }
        return Path.MANAGER_SERVLET;
    }

    private boolean updateRequest(Request request) {
        try {
            requestDAO.update(request);
        } catch (DBException e) {
            log.error("<--------- edit request is failed", e);
            return false;
        }
        return true;
    }

    private boolean insertRequest(String masterLogin, long id) {
        try {
            requestDAO.insertRequestInUserRequest(masterLogin, id);
        } catch (DBException e) {
            log.error("<------ edit request is failed", e);
            return false;
        }
        return true;
    }

    private Request getRequest(long id) {
        Request request = null;
        try {
            request = requestDAO.getData(id);
        } catch (DBException e) {
            log.error("<-------- edit request is failed", e);
        }
        return request;
    }
}
