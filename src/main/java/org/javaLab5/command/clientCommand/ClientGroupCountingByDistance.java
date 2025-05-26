package org.javaLab5.command.clientCommand;

import org.javaLab5.command.CommandArgumentList;

/**
 * Client command for group_counting_by_distance operation.
 * No arguments required as it performs grouping of all elements by distance.
 */
public class ClientGroupCountingByDistance extends ClientCommand {
    @Override
    public CommandArgumentList input() {
        // Эта команда не требует ввода аргументов, так как работает со всей коллекцией
        return argumentList;
    }
}
