package bomb.modules.s.souvenir;

import bomb.Widget;
import bomb.annotation.Puzzle;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Puzzle(resource = "souvenir.fxml", buttonLinkerName = "Souvenir")
public final class Souvenir extends Widget {
    private static final Map<String, String> MODULE_ARTIFACTS;

    static {
        MODULE_ARTIFACTS = new LinkedHashMap<>();
    }

    public static void addRelic(String key, String answer) {
        MODULE_ARTIFACTS.put(key, answer);
    }

    public static @NotNull List<Pair<String, String>> getPuzzleArtifacts() {
        return MODULE_ARTIFACTS.entrySet()
                .stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .toList();
    }

    public static void reset() {
        MODULE_ARTIFACTS.clear();
    }
}
