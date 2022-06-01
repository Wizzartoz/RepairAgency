package com.maznichko.services.manager;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetMasterRequests {
    private static final Logger log = Logger.getLogger(GetMasterRequests.class);
    private final RequestDAO requestDAO;

    public GetMasterRequests(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    /**
     * method, need-able for get requests by master's login
     * @param request - request of servlet
     * @param login - master's login
     * @return - path of servlet or jsp page
     */
    public String getRequests(HttpServletRequest request, String login) {
        List<Request> requests;
        try {
            requests = requestDAO.getRequestByLogin(login);
        } catch (DBException e) {
            log.error("<------ get request by login is failed",e);
            return Path.ERROR;
        }
        request.setAttribute("table", requests);
        return Path.REQUESTS_JSP;
    }
}
