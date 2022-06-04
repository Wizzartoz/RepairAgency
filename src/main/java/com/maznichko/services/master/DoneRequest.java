package com.maznichko.services.master;

import com.maznichko.Sender;
import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DoneRequest implements MasterCommand {
    private final RequestDAO requestDAO;
    private final UserDAO userDAO;
    private final Sender sender;
    private static final Logger log = Logger.getLogger(DoneRequest.class);

    public DoneRequest(RequestDAO requestDAO,UserDAO userDAO,Sender sender) {
        this.requestDAO = requestDAO;
        this.userDAO = userDAO;
        this.sender = sender;

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
        //Replenishment master's bank
        User master = getUser(req);
        if (master == null){
            return Path.ERROR;
        }
        master.setBank(((master.getBank() + request.getPrice().intValue())));
        //Updating master
        boolean isUpdateMaster = updateUser(master);
        if (!isUpdateMaster){
            return Path.ERROR;
        }
        /*
        sender.send(
                "Request",
                "The master did your order",
                "maznichkogame@gmail.com"
        );

         */
        req.setAttribute("result", "Status changed successfully");
        return Path.MASTER_SERVLET;
    }
    private boolean updateUser(User user){
        try {
            userDAO.update(user);
        } catch (DBException e) {
            log.error("<------------ update master is failed",e);
            return false;
        }
        return true;
    }

    private User getUser(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        User user;
        try {
           user =  userDAO.getUserByLogin((String) httpSession.getAttribute("login"));
        } catch (DBException e) {
            log.error("<------------ get master is failed",e);
            return null;
        }
        return user;
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
