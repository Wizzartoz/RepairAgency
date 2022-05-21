package com.maznichko.services.manager;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;
import com.maznichko.services.common.GetMasters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditRequest implements Command {
    private final RequestDAO requestDAO;

    public EditRequest(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;

    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("id"));
        //get request
        if (req.getParameter("price") == null){
            req.setAttribute("result", "request already added");
            return Path.MANAGER_SERVLET;
        }
        Request request;
        try {
            request = requestDAO.getData(id);
        } catch (DBException e) {
            req.setAttribute("result", e);
            return Path.ERROR;
        }
        if (req.getParameter("cStatus").equals("refuse")) {
            request.setPrice(0F);
            request.setPaymentStatus("unpaid");
            request.setComplicationStatus("refuse");
        } else {
            //checking price
            float price;
            try {
                price = Float.parseFloat(req.getParameter("price"));
            } catch (NumberFormatException e) {
                req.setAttribute("result", "incorrect price value");
                return Path.MANAGER_SERVLET;
            }
            if (price <= 0) {
                req.setAttribute("result", "incorrect price value");
                return Path.MANAGER_SERVLET;
            }
            //checking payment status
            String paymentStatus = req.getParameter("pStatus");
            if (paymentStatus == null || !paymentStatus.equals("waiting for payment")) {
                req.setAttribute("result", "incorrect payment status");
                return Path.MANAGER_SERVLET;
            }
            if (!request.getPaymentStatus().equals("unpaid")) {
                req.setAttribute("result", "incorrect payment status");
                return Path.MANAGER_SERVLET;
            }
            //checking master
            String masterLogin = req.getParameter("usr");
            if (masterLogin == null) {
                req.setAttribute("result", "you must be select the master");
                return Path.MANAGER_SERVLET;
            }
            List<User> masters = GetMasters.findMasters(req);
            User master = new User();
            master.setLogin(masterLogin);
            if (!masters.contains(master)) {
                req.setAttribute("result", "master didn't exist");
                return Path.MANAGER_SERVLET;
            }
            //checking complication status
            String complicationStatus = req.getParameter("cStatus");
            if (complicationStatus == null) {
                req.setAttribute("result", "wrong complication status");
                return Path.MANAGER_SERVLET;
            }
            if (!request.getComplicationStatus().equals("under consideration")) {
                req.setAttribute("result", "wrong complication status");
                return Path.MANAGER_SERVLET;
            }
            if (!(complicationStatus.equals("refuse") || complicationStatus.equals("consideration"))) {
                req.setAttribute("result", "wrong complication status");
                return Path.MANAGER_SERVLET;
            }
            if (complicationStatus.equals("refuse")) {
                masterLogin = null;
            }
            //set parameters
            request.setPrice(price);
            request.setPaymentStatus(paymentStatus);
            request.setComplicationStatus(complicationStatus);
            request.setMasterLogin(masterLogin);
            try {
                requestDAO.insertRequestInUserRequest(masterLogin, id);
            } catch (DBException e) {
                req.setAttribute("result", e);
                return Path.ERROR;
            }
        }
        //set data to db
        try {
            requestDAO.update(request);
        } catch (DBException e) {
            req.setAttribute("result", e);
            return Path.ERROR;
        }
        return Path.MANAGER_SERVLET;
    }
}
