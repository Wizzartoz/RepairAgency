package com.maznichko.services.master;

import com.maznichko.dao.FeedbackDAO;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.impl.FeedbackDAOimpl;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.manager.*;

import java.util.Map;
import java.util.TreeMap;

public class MasterContainer {
    private static final Map<String, MasterCommand> commands = new TreeMap<>();
    private static final UserDAO USER_DAO = new UserDAOimpl();
    private static final RequestDAO REQUEST_DAO = new RequestDAOimpl();
    private static final FeedbackDAO FEEDBACK_DAO = new FeedbackDAOimpl();


    static {
        commands.put("doneRequest", new DoneRequest(REQUEST_DAO));
        commands.put("takeRequest", new TakeRequest(REQUEST_DAO));
    }
    /**
     * This method by parameter return you object
     * @param command - parameter who we are getting from request
     * @return - object
     */
    public static MasterCommand get(String command) {
        if (command == null || !commands.containsKey(command)) {
            return null;
        }
        return commands.get(command);
    }

}
