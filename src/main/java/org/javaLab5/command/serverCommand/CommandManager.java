package org.javaLab5.command.serverCommand;

import lombok.Getter;
import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages server commands and their execution.
 */
@Getter
public class CommandManager {
    private final Map<String, ServerCommand> commandList;
    private final CustomCollection collection;

    public CommandManager(CustomCollection collection) {
        this.collection = collection;
        this.commandList = initializeCommands();
    }

    private Map<String, ServerCommand> initializeCommands() {
        Map<String, ServerCommand> commands = new HashMap<>();

        // Collection info commands
        commands.put("info", new ServerInfo(collection));
        commands.put("show", new ServerShow(collection));
        commands.put("clear", new ServerClear(collection));

        // System commands
        commands.put("exit", new ServerExit());
        commands.put("help", new ServerHelp());

        // Collection query commands
        commands.put("count_by_distance", new ServerGroupCountingByDistance(collection));
        commands.put("count_greater_than_distance", new ServerCountGreaterThanDistance(collection));
        commands.put("max_by_creation_date", new ServerMaxByCreationDate(collection));

        // Collection modification commands
        commands.put("add", new ServerAdd(collection));
        commands.put("update", new ServerUpdate(collection));
        commands.put("remove_by_id", new ServerRemoveById(collection));
        commands.put("add_if_max", new ServerAddIfMax(collection));
        commands.put("add_if_min", new ServerAddIfMin(collection));
        commands.put("remove_lower", new ServerRemoveLower(collection));
        commands.put("remove_last", new ServerRemoveLast(collection));

        // Persistence command
        commands.put("save", new ServerSave(collection));

        return Collections.unmodifiableMap(commands);
    }

    /**
     * Executes the command specified in the command argument list.
     *
     * @param commandArgList the command and its arguments
     * @return ServerResponse containing the result of command execution
     * @throws IllegalArgumentException if command is not found
     * @throws Exception if command execution fails
     */
    public ServerResponse executeCommand(CommandArgumentList commandArgList) throws Exception {
        if (commandArgList == null || commandArgList.getCommand() == null) {
            throw new IllegalArgumentException("Command argument list cannot be null");
        }

        String commandName = commandArgList.getCommand().toString().toLowerCase();
        ServerCommand serverCommand = commandList.get(commandName);

        if (serverCommand == null) {
            throw new IllegalArgumentException("Unknown command: '" + commandName + "'");
        }

        try {
            return serverCommand.execute(commandArgList);
        } catch (Exception e) {
            throw new Exception("Failed to execute command '" + commandName + "'", e);
        }
    }

}