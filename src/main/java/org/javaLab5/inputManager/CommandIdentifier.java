package org.javaLab5.inputManager;

import lombok.Getter;
import lombok.Setter;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.command.clientCommand.*;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class CommandIdentifier {
    private Map<String, ClientCommand> commandList = new HashMap<>();

    public CommandIdentifier(ScannerManager scannerManager, ArgumentRequester argumentRequester){
        commandList.put("info", new ClientInfo());
        commandList.put("show", new ClientShow());
        commandList.put("clear", new ClientClear());
        commandList.put("exit", new ClientExit());
        commandList.put("help", new ClientHelp());
        commandList.put("save", new ClientSave());

        commandList.put("count_by_distance", new ClientGroupCountingByDistance());
        commandList.put("remove_by_id", new ClientRemoveById());
        commandList.put("execute_script", new ClientExecuteScript(scannerManager));

        commandList.put("add", new ClientAdd(argumentRequester));
        commandList.put("update", new ClientUpdate(argumentRequester));
        commandList.put("add_if_max", new ClientAddIfMax(argumentRequester));
        commandList.put("add_if_min", new ClientAddIfMin(argumentRequester));
        commandList.put("remove_lower", new ClientRemoveLower(argumentRequester));
        commandList.put("count_greater_than_distance", new ClientCountGreaterThanDistance());
        commandList.put("remove_last", new ClientRemoveLast());
        commandList.put("max_by_creation_date", new ClientMaxByCreationDate());
    }

    public ClientCommand getCommand(String commandLine) throws UndefinedCommandException {
        CommandArgumentList arguments = CommandAndArgumentsParser.parseCommandAndArguments(commandLine);
        String command = (String) arguments.getCommand().getValue();
        if (!commandList.containsKey(command)){
            throw new UndefinedCommandException("Unexpected command: '" + command + "'. Try write 'help'");
        }
        ClientCommand clientCommand = commandList.get(command);
        clientCommand.createArgumentListForInput(arguments);
        return clientCommand;
    }

}
