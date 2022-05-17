package com.maznichko.services.customer;

import javax.servlet.http.HttpServletRequest;

public interface CustomerCommand {
    /**
     * This method execute command who we are getting from request
     *
     * @param req - request who we are getting
     * @return - return path to the jsp or servlet
     */
    String execute(HttpServletRequest req);
}
