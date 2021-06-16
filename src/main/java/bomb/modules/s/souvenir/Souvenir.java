package bomb.modules.s.souvenir;

import bomb.Widget;

import java.util.ArrayList;

public class Souvenir extends Widget {
    //TODO - Check out how Souvenir handles 2+ modules of the same type
    //TODO - CENTURION SOUVENIR IS DIFFERENT, CHANGE SOME SHITE, M8
    //TODO - Consider changing how souvenirs are stored
    private static int prevSize = 0;
    private static final ArrayList<String> MODULE_ARTIFACTS = new ArrayList<>();

    /**
     *
     *
     * @param key
     * @param clue
     */
    public static void addRelic(String key, String clue){
        MODULE_ARTIFACTS.add(key + " - " + clue + "\n");
    }

    /**
     *
     *
     * @return
     */
    public static String flush() {
        StringBuilder builder = new StringBuilder();
        if (MODULE_ARTIFACTS.size() != prevSize){
            prevSize = MODULE_ARTIFACTS.size();
            for (String key : MODULE_ARTIFACTS) builder.append(key);
            return builder.toString();
        } return null;
    }

    /**
     *
     *
     * @return
     */
    public static boolean getSet(){
        return souvenir;
    }

    //private static
}