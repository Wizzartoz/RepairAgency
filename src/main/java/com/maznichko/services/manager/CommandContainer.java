package com.maznichko.services.manager;


import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.impl.RequestDAOimpl;




import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();
    private static final RequestDAO REQUEST_DAO = new RequestDAOimpl();


    static {
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
