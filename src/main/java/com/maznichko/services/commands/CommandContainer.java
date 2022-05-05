package com.maznichko.services.commands;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("login", new Login());
        commands.put("deleteRequest", new DeleteRequest());
        commands.put("doneRequest", new DoneRequest());
        commands.put("getRequest", new GetRequest());
        commands.put("leaveFeedback", new LeaveFeedback());
        commands.put("register", new Register());
        commands.put("paid", new Paid());
        commands.put("filter", new Filter());
        commands.put("replenishment", new Replenishment());
        commands.put("sendRequest", new SendRequest());
        commands.put("takeRequest", new TakeRequest());
        commands.put("setMaster", new SetMaster());
        commands.put("editRequest", new EditRequest());
    }

    public static Command get(String command)  {
        if (command == null || !commands.containsKey(command)) {
            return null;
        }
        return commands.get(command);
    }

}
