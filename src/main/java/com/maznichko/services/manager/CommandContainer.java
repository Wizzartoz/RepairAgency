package com.maznichko.services.manager;


import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.services.filter.GenerateTableRequests;
import org.apache.log4j.Logger;


import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();
    private static final RequestDAO REQUEST_DAO = new RequestDAOimpl();
    private static final Logger log = Logger.getLogger(CommandContainer.class);


    static {
        commands.put("editRequest", new EditRequest(REQUEST_DAO));
        log.info("manager container is set up ");
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
