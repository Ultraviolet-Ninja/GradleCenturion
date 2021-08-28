package bomb.modules.s.souvenir;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Souvenir {
    private static final Map<String, String> MODULE_ARTIFACTS;

    static {
        MODULE_ARTIFACTS = new LinkedHashMap<>();
    }

    public static void addRelic(String key, String answer) {
        MODULE_ARTIFACTS.put(key, answer);
    }

    public static List<Pair<String, String>> getPuzzleArtifacts() {
        List<Pair<String, String>> output = new ArrayList<>();
        for (String key : MODULE_ARTIFACTS.keySet())
            output.add(new Pair<>(key, MODULE_ARTIFACTS.get(key)));
        return output;
    }

    public static void reset() {
        MODULE_ARTIFACTS.clear();
    }
}
