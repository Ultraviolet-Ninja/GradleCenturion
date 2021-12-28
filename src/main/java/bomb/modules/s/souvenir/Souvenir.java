package bomb.modules.s.souvenir;

import org.javatuples.Pair;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Souvenir {
    private static final Map<String, String> MODULE_ARTIFACTS;

    static {
        MODULE_ARTIFACTS = new LinkedHashMap<>();
    }

    public static void addRelic(String key, String answer) {
        MODULE_ARTIFACTS.put(key, answer);
    }

    public static List<Pair<String, String>> getPuzzleArtifacts() {
        return MODULE_ARTIFACTS.entrySet()
                .stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .collect(toList());
    }

    public static void reset() {
        MODULE_ARTIFACTS.clear();
    }
}
