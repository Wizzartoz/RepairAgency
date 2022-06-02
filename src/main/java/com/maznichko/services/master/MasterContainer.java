package com.maznichko.services.master;

import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class MasterContainer {
    private static final Map<String, MasterCommand> commands = new TreeMap<>();
    private static final RequestDAO REQUEST_DAO = new RequestDAOimpl();
    private static final Logger log = Logger.getLogger(MasterContainer.class);



    static {
        commands.put("doneRequest", new DoneRequest(REQUEST_DAO,new UserDAOimpl()));
        commands.put("takeRequest", new TakeRequest(REQUEST_DAO));
        log.info("<--------- master container is set up");

    }
    /**
     * This method by parameter return you object
     * @param command - parameter who we are getting from request
     * @return - object
     */
    //TODO maybe will be better make this method like Optional because I don't check method on null
    public static MasterCommand get(String command) {
        if (command == null || !commands.containsKey(command)) {
            return null;
        }
        return commands.get(command);
    }

}
