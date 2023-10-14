package bomb.tools.boot;

import bomb.tools.pattern.observer.ResetObserver;
import javafx.scene.layout.Region;
import org.javatuples.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.SequencedMap;

import static java.util.stream.Collectors.toMap;

public final class StandardFxmlBootDrive implements FxmlBootDrive {
    @SuppressWarnings("DataFlowIssue")
    @Contract("_ -> new")
    @Override
    public @NotNull SequencedMap<String, Region> createFXMLMap(ResetObserver resetObserver) {
        var displayClassStream = FxmlBootDrive.getAnnotatedClasses().parallelStream();

        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            displayClassStream = displayClassStream.sequential();
        }

        return new LinkedHashMap<>(displayClassStream
                .map(cls -> FxmlBootDrive.mapClassToRegion(cls, resetObserver))
                .collect(toMap(Pair::getValue0, Pair::getValue1)));
    }

    StandardFxmlBootDrive(){}
}
