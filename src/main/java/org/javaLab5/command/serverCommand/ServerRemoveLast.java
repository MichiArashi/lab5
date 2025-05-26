package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Route;
import org.javaLab5.command.serverCommand.ServerResponse;
import org.javaLab5.command.serverCommand.ResponseStatus;

import java.util.Optional;

public class ServerRemoveLast extends ServerCommand {
    private final CustomCollection collection;

    public ServerRemoveLast(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
        if (collection.getCollection().isEmpty()) {
            return new ServerResponse(ResponseStatus.ERROR, "Collection is empty - nothing to remove.");
        }

        // Находим последний элемент (с максимальным ID)
        Optional<Route> lastRoute = collection.getCollection().stream()
                .max((r1, r2) -> Integer.compare(r1.getId(), r2.getId()));

        int lastId = lastRoute.get().getId();
        collection.removeByID(lastId);
        return new ServerResponse(ResponseStatus.OK,
                "Last route with id=" + lastId + " was successfully removed.");

    }
}
