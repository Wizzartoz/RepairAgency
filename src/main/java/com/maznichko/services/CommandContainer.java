package com.maznichko.services;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("login", new Login());
    }

    public static Command get(String command) throws Exception {
        if (command == null || !commands.containsKey(command)) {
            throw new Exception("Commands not exist");
        }
        return commands.get(command);
    }

}
