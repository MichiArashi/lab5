package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgumentList;

public class ClientMaxByCreationDate extends ClientCommand {

    public ClientMaxByCreationDate() {
        // Команда не требует аргументов
    }

    /**
     * Command 'max_by_creation_date' - не требует ввода аргументов
     * @return Пустой CommandArgumentList
     */
    @Override
    public CommandArgumentList input() {
        // Возвращаем пустой список аргументов
        return argumentList;
    }
}
