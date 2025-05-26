package org.javaLab5.command.serverCommand;

import org.javaLab5.collection.CustomCollection;
import org.javaLab5.command.CommandArgumentList;
import org.javaLab5.model.Route;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerGroupCountingByDistance extends ServerCommand {
    private final CustomCollection collection;

    public ServerGroupCountingByDistance(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) throws Exception {
        // Группируем элементы по distance и подсчитываем количество в каждой группе
        Map<Double, Long> distanceGroups = collection.getCollection().stream()
                .collect(Collectors.groupingBy(
                        Route::getDistance,
                        Collectors.counting()
                ));

        // Форматируем результат для вывода
        StringBuilder result = new StringBuilder("Group counts by distance:\n");
        distanceGroups.forEach((distance, count) ->
                result.append(String.format("- Distance %.2f: %d elements\n", distance, count))
        );

        return new ServerResponse(ResponseStatus.DATA, result.toString());
    }
}
