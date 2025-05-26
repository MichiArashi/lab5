package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgumentList;

public class ClientRemoveLast extends ClientCommand {

    public ClientRemoveLast() {
        // Команда не требует аргументов
    }

    /**
     * Command 'remove_last' - не требует ввода аргументов
     * @return Пустой CommandArgumentList
     */
    @Override
    public CommandArgumentList input() {
        // Возвращаем пустой список аргументов, так как команда не требует дополнительных данных
        return argumentList;
    }
}