package bomb.modules.s.souvenir;

import java.util.LinkedHashMap;
import java.util.Map;

public class Souvenir {
    private static final Map<String, String> MODULE_ARTIFACTS;

    static {
        MODULE_ARTIFACTS = new LinkedHashMap<>();
    }

    public static void addRelic(String key, String answer){
        MODULE_ARTIFACTS.put(key, answer);
    }

    public static Map<String, String> getPuzzleArtifacts() {
        return MODULE_ARTIFACTS;
    }

    public static void reset(){
        MODULE_ARTIFACTS.clear();
    }
}
