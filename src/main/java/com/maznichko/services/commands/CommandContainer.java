package com.maznichko.services.commands;

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
        commands.put("deleteRequest", new DeleteRequest(REQUEST_DAO));
        commands.put("doneRequest", new DoneRequest(REQUEST_DAO));
        commands.put("leaveFeedback", new LeaveFeedback(FEEDBACK_DAO));
        commands.put("register", new Register(USER_DAO));
        commands.put("paid", new Paid(USER_DAO,REQUEST_DAO));
        commands.put("filter", new Filter(REQUEST_DAO));
        commands.put("replenishment", new Replenishment(USER_DAO));
        commands.put("sendRequest", new SendRequest(REQUEST_DAO));
        commands.put("takeRequest", new TakeRequest(REQUEST_DAO));
        commands.put("setMaster", new SetMaster(REQUEST_DAO));
        commands.put("editRequest", new EditRequest(REQUEST_DAO));
    }

    public static Command get(String command) {
        if (command == null || !commands.containsKey(command)) {
            return null;
        }
        return commands.get(command);
    }

}
