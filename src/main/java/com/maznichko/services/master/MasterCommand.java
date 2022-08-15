package com.maznichko.services.master;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MasterCommand {
    /**
     * This method execute command who we are getting from request
     *
     * @param req - request who we are getting
     * @return - return path to the jsp or servlet
     */
    String execute(HttpServletRequest req, HttpServletResponse resp);
}
