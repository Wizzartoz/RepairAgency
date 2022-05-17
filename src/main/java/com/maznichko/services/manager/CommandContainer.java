package com.maznichko.services.manager;

import com.maznichko.dao.FeedbackDAO;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.impl.FeedbackDAOimpl;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;


import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();
    private static final UserDAO USER_DAO = new UserDAOimpl();
    private static final RequestDAO REQUEST_DAO = new RequestDAOimpl();
    private static final FeedbackDAO FEEDBACK_DAO = new FeedbackDAOimpl();


    static {
        commands.put("login", new Login(USER_DAO));
        commands.put("doneRequest", new DoneRequest(REQUEST_DAO));
        commands.put("register", new Register(USER_DAO));
        commands.put("takeRequest", new TakeRequest(REQUEST_DAO));
        commands.put("setMaster", new SetMaster(REQUEST_DAO));
        commands.put("editRequest", new EditRequest(REQUEST_DAO));
    }
    /**
     * This method by parameter return you object
     * @param command - parameter who we are getting from request
     * @return - object
     */
    public static Command get(String command) {
        if (command == null || !commands.containsKey(command)) {
            return null;
        }
        return commands.get(command);
    }

}
