package com.maznichko.services;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("login", new Login());
        commands.put("DeleteRequest", new Login());
        commands.put("DoneRequest", new Login());
        commands.put("GenerateTable", new Login());
        commands.put("GenerateMasterTable", new Login());
        commands.put("GenerateManagerTable", new Login());
        commands.put("GetBank", new Login());
        commands.put("GetMasters", new Login());
        commands.put("GetRequest", new Login());
        commands.put("LeaveFeedback", new Login());
        commands.put("Register", new Login());
        commands.put("Paid", new Login());
        commands.put("Filter", new Login());
        commands.put("Replenishment", new Login());
        commands.put("SendRequest", new Login());
        commands.put("Sort", new Login());
        commands.put("TakeRequest", new Login());
    }

    public static Command get(String command) throws Exception {
        if (command == null || !commands.containsKey(command)) {
            throw new Exception("Commands not exist");
        }
        return commands.get(command);
    }

}
