package bomb.modules.s.souvenir;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class NewSouvenir {
    private static final List<String> MODULE_ARTIFACTS;
    private static int previousEndpoint;

    static {
        MODULE_ARTIFACTS = new ArrayList<>();
        previousEndpoint = 0;
    }

    public static void addRelic(String key, String answer){
        MODULE_ARTIFACTS.add(key + " - " +answer);
    }

    public static List<Pair<String, String>> updateTableView() {
        List<Pair<String, String>> output = new ArrayList<>();
        while (previousEndpoint < MODULE_ARTIFACTS.size()){
            String[] split = MODULE_ARTIFACTS.get(previousEndpoint++).split(" - ");
            output.add(new Pair<>(split[0], split[1]));
        }
        return output;
    }

    public static void reset(){
        MODULE_ARTIFACTS.clear();
        previousEndpoint = 0;
    }
}
