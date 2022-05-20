package com.maznichko.services.customer;

import com.maznichko.dao.FeedbackDAO;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.impl.FeedbackDAOimpl;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;


import java.util.Map;
import java.util.TreeMap;

public class CustomerContainer {
    private static final Map<String, CustomerCommand> commands = new TreeMap<>();
    private static final UserDAO USER_DAO = new UserDAOimpl();
    private static final RequestDAO REQUEST_DAO = new RequestDAOimpl();
    private static final FeedbackDAO FEEDBACK_DAO = new FeedbackDAOimpl();


    static {
        commands.put("deleteRequest", new DeleteRequest(REQUEST_DAO));
        commands.put("leaveFeedback", new LeaveFeedback(FEEDBACK_DAO,REQUEST_DAO));
        commands.put("paid", new Paid(USER_DAO,REQUEST_DAO));
        commands.put("sendRequest", new SendRequest(REQUEST_DAO));
    }

    /**
     * This method by parameter return you object
     * @param command - parameter who we are getting from request
     * @return - object
     */
    public static CustomerCommand get(String command) {
        if (command == null || !commands.containsKey(command)) {
            return null;
        }
        return commands.get(command);
    }

}