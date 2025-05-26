package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Route;
import org.javaLab5.command.serverCommand.ServerResponse;
import org.javaLab5.command.serverCommand.ResponseStatus;

import java.util.Comparator;
import java.util.Optional;

public class ServerMaxByCreationDate extends ServerCommand {
    private final CustomCollection collection;

    public ServerMaxByCreationDate(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
        if (collection.getCollection().isEmpty()) {
            return new ServerResponse(ResponseStatus.ERROR, "Collection is empty - no elements to compare.");
        }

        // Находим элемент с максимальной датой создания
        Optional<Route> maxDateRoute = collection.getCollection().stream()
                .max(Comparator.comparing(Route::getCreationDate));

        if (maxDateRoute.isPresent()) {
            Route route = maxDateRoute.get();
            return new ServerResponse(ResponseStatus.OK,
                    "Route with latest creation date:\n" + route.toString());
        }

        return new ServerResponse(ResponseStatus.ERROR, "Failed to find element with maximum creation date.");
    }
}