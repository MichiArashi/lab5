package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Route;
import org.javaLab5.model.CreateRouteDTO;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerRemoveLower extends ServerCommand {
    private final CustomCollection collection;

    public ServerRemoveLower(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) {
        Route newRoute = handleNewRoute(args);

        Set<Integer> lowerIds = this.collection.getCollection().stream()
                .filter(route -> route.compareTo(newRoute) < 0)  // Изменено условие сравнения
                .map(Route::getId)
                .collect(Collectors.toSet());

        StringBuilder response = new StringBuilder();

        for (int id : lowerIds) {
            collection.removeByID(id);
            response.append("Route with id=").append(id).append(" was deleted!\n");
        }

        if (lowerIds.isEmpty()) {
            response.append("No routes were found that are lower than the specified one.\n");
        }

        return new ServerResponse(ResponseStatus.OK, response.toString());
    }

    private Route handleNewRoute(CommandArgumentList args) {
        CreateRouteDTO routeDTO = (CreateRouteDTO) args.getSecondArgument().getValue();
        Route newRoute = new Route();

        newRoute.setId(collection.getNewID());
        newRoute.setCreationDate(new Date());
        newRoute.setFromRouteDataTransferObject(routeDTO);

        return newRoute;
    }
}