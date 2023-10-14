package bomb.tools.boot;

import bomb.tools.pattern.observer.ResetObserver;
import javafx.scene.layout.Region;
import org.javatuples.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.SequencedMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toMap;

public final class VirtualThreadFxmlBootDrive implements FxmlBootDrive {
    private static Pair<String, Region> extractFromFuture(Future<Pair<String, Region>> future) {
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            LOG.error("", e);
            throw new IllegalStateException("Unexpected Boot Exception");
        }
    }

    @Contract("_ -> new")
    @Override
    public @NotNull SequencedMap<String, Region> createFXMLMap(ResetObserver resetObserver) {
        var annotatedClasses = FxmlBootDrive.getAnnotatedClasses();
        var virtualThreadList = new ArrayList<Future<Pair<String, Region>>>(annotatedClasses.size());

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (var annotatedClass : annotatedClasses) {
                var future = executor.submit(() -> FxmlBootDrive.mapClassToRegion(annotatedClass, resetObserver));
                virtualThreadList.add(future);
            }
        }

        return new LinkedHashMap<>(virtualThreadList.stream()
                .map(VirtualThreadFxmlBootDrive::extractFromFuture)
                .collect(toMap(Pair::getValue0, Pair::getValue1)));
    }

    VirtualThreadFxmlBootDrive(){}
}
