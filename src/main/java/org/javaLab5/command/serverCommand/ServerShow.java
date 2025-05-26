package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Route;
import java.util.stream.Collectors;

public class ServerShow extends ServerCommand {
    private final CustomCollection collection;

    public ServerShow(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
        if (collection == null || collection.getCollection() == null) {
            return new ServerResponse(ResponseStatus.ERROR, "Collection is not initialized");
        }

        if (collection.getCollection().isEmpty()) {
            return new ServerResponse(ResponseStatus.TEXT, "No routes in collection");
        }

        String routesInfo = collection.getCollection().stream()
                .map(this::formatRouteInfo)
                .collect(Collectors.joining("\n\n"));

        return new ServerResponse(ResponseStatus.TEXT, routesInfo);
    }

    private String formatRouteInfo(Route route) {
        return String.format(
                "Route #%d: %s\n" +
                        "Distance: %.2f\n" +
                        "From: %s\n" +
                        "To: %s\n" +
                        "Coordinates: %s\n" +
                        "Created: %s",
                route.getId(),
                route.getName(),
                route.getDistance(),
                route.getFrom() != null ? route.getFrom() : "Not specified",
                route.getTo() != null ? route.getTo() : "Not specified",
                route.getCoordinates(),
                route.getCreationDate()
        );
    }
}