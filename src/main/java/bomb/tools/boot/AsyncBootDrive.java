package bomb.tools.boot;

import bomb.tools.pattern.observer.ResetObserver;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Toggle;
import javafx.scene.layout.Region;
import org.javatuples.Pair;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SequencedMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toMap;

public final class AsyncBootDrive implements FxmlBootDrive {
    AsyncBootDrive(){}

    @Override
    public SequencedMap<String, Region> createFXMLMap(ResetObserver resetObserver) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SequencedMap<String, CompletableFuture<Region>> createFXMLMapAsync(ResetObserver resetObserver) {
        var classes = FxmlBootDrive.getAnnotatedClasses();
        BiFunction<FXMLLoader, ResetObserver, CompletableFuture<Region>> regionFunction = (loader, observer) ->
                CompletableFuture.supplyAsync(() -> FxmlBootDrive.loadSingleRegion(loader, observer));

        return new LinkedHashMap<>(classes.stream()
                .map(cls -> FxmlBootDrive.mapClassToType(cls, resetObserver, regionFunction))
                .collect(toMap(Pair::getValue0, Pair::getValue1))
        );
    }

    public static Map<Toggle, Region> linkRegionWhenDone(SequencedMap<String, CompletableFuture<Region>> regionFutures,
                                                         CompletableFuture<SequencedMap<String, Toggle>> radioButtonNameFuture,
                                                         Region emptyView) {
        Map<Toggle, Region> targetMap = new LinkedHashMap<>();
        var concurrentLayer = Collections.synchronizedMap(targetMap);

        Consumer<SequencedMap<String, Toggle>> connectionConsumer = radioButtonMap -> {
            for (var entry : radioButtonMap.sequencedEntrySet()) {
                conductConnection(entry.getKey(), entry.getValue(), concurrentLayer, emptyView, regionFutures);
            }
        };

        radioButtonNameFuture.thenAccept(connectionConsumer);

        return targetMap;
    }

    private static void conductConnection(String buttonNameKey, Toggle toggle, Map<Toggle, Region> concurrentLayer,
                                          Region emptyView, SequencedMap<String, CompletableFuture<Region>> regionFutures) {
        concurrentLayer.putIfAbsent(toggle, emptyView);
        if (regionFutures.containsKey(buttonNameKey)) {
            wireFinishedViewToToggle(toggle, concurrentLayer, regionFutures.get(buttonNameKey));
        }
    }

    private static void wireFinishedViewToToggle(Toggle targetToggle, Map<Toggle, Region> concurrentLayer,
                                                 CompletableFuture<Region> regionFuture) {
        regionFuture.thenAccept(region -> concurrentLayer.put(targetToggle, region));
    }
}
