package bomb.modules.s.simon.states;

import bomb.Widget;
import bomb.modules.s.simon.Simon;
import bomb.modules.s.souvenir.Souvenir;

import static bomb.modules.s.simon.Simon.States.BLUE;
import static bomb.modules.s.simon.Simon.States.GREEN;
import static bomb.modules.s.simon.Simon.States.RED;
import static bomb.modules.s.simon.Simon.States.YELLOW;

/**
 *
 */
public class SimonStates extends Widget {
    private static int prior;
    private static StringBuilder toPress = new StringBuilder();

    private static final Simon.States[][] PRIORITY_ORDERS = new Simon.States[][]{
            {RED, BLUE, GREEN, YELLOW}, //Highest to Lowest
            {BLUE, YELLOW, RED, GREEN},
            {GREEN, RED, YELLOW, BLUE},
            {YELLOW, GREEN, BLUE, RED}
    };

    enum Priorities {
        HIGHEST, HIGH, LOW, LOWEST
    }

    /**
     *
     *
     * @param color
     */
    public static void setPriority(Simon.States color){
        prior = color.ordinal();
    }

    /**
     *
     *
     * @param colors
     * @param stage
     * @return
     */
    public static String add(Simon.States[] colors, int stage){
        if (isSouvenirActive)
            Souvenir.addRelic("Simon States - Stage " + stage, writeOut(colors));

        if (stage == 1)
            toPress.append(stageOne(colors)).append(" ");
        else if (stage == 2)
            toPress.append(stageTwo(colors)).append(" ");
        else if (stage == 3)
            toPress.append(stageThree(colors)).append(" ");
        else
            toPress.append(stageFour(colors)).append(" ");
        return toPress.toString();
    }

    /**
     *
     *
     * @param colors
     * @return
     */
    private static String stageOne(Simon.States[] colors){
        if (colors.length == 1)
            return firstCap(colors[0]);
        else if(colors.length == 2 && contains(colors, BLUE))
            return firstCap(highest(colors));
        else if (colors.length == 2)
            return firstCap(BLUE);
        else if (colors.length == 3 && contains(colors, RED))
            return firstCap(lowest(colors));
        else if (colors.length == 3)
            return firstCap(RED);
        return firstCap(PRIORITY_ORDERS[prior][Priorities.HIGH.ordinal()]);
    }

    /**
     *
     *
     * @param colors
     * @return
     */
    private static String stageTwo(Simon.States[] colors){
        if (colors.length == 2 && contains(colors, BLUE) && contains(colors, RED))
            return firstCap(highestNotFlashed(colors));
        else if (colors.length == 2)
            return firstCap(lowestNotFlashed(colors));
        else if (colors.length == 1 && !contains(colors, BLUE))
            return firstCap(BLUE);
        else if (colors.length == 1)
            return firstCap(YELLOW);
        else if (colors.length == 4)
            return toPress.toString().split(" ")[0];
        else
            return firstCap(oddOneOut(colors));
    }

    /**
     *
     *
     * @param colors
     * @return
     */
    private static String stageThree(Simon.States[] colors){
        if (colors.length == 3 && previouslyPressed(colors))
            return firstCap(highestNotPressed(colors));
        else if (colors.length == 3)
            return firstCap(highest(colors));
        else if (colors.length == 2)
            return (bothPreviouslyPressed(colors))?
                    firstCap(lowestNotFlashed(colors)):
                    toPress.toString().split(" ")[0];
        else if (colors.length == 1)
            return firstCap(colors[0]);
        else
            return firstCap(PRIORITY_ORDERS[prior][Priorities.LOW.ordinal()]);
    }

    /**
     *
     *
     * @param colors
     * @return
     */
    private static String stageFour(Simon.States[] colors){
        if (allUnique())
            return oddOneOut();
        else if (oneNotPressed(colors))
            return pressOneNot(colors);
        else if (colors.length == 3)
            return firstCap(PRIORITY_ORDERS[prior][Priorities.LOWEST.ordinal()]);
        else if (colors.length == 1)
            return firstCap(colors[0]);
        else
            return firstCap(GREEN);
    }

    /**
     *
     *
     * @param colors
     * @return
     * @throws IllegalArgumentException
     */
    private static Simon.States highest(Simon.States[] colors) throws IllegalArgumentException{
        for (Simon.States current : PRIORITY_ORDERS[prior]){
            if(contains(colors, current)) return current;
        }
        throw new IllegalArgumentException("Unreachable section of highest() was reached");
    }

    /**
     *
     *
     * @param colors
     * @return
     * @throws IllegalArgumentException
     */
    private static Simon.States highestNotFlashed(Simon.States[] colors) throws IllegalArgumentException{
        for (Simon.States current : PRIORITY_ORDERS[prior]){
            if(!contains(colors, current)) return current;
        }
        throw new IllegalArgumentException("Unreachable section of highestNotFlashed() was reached");
    }

    /**
     *
     *
     * @param colors
     * @return
     */
    private static Simon.States highestNotPressed(Simon.States[] colors){
        for (Simon.States current : PRIORITY_ORDERS[prior]){
            if (contains(colors, current) && containsNotPressed(colors)) return current;
        }
        throw new IllegalArgumentException("Unreachable section of highestNotPressed() was reached");
    }

    /**
     *
     *
     * @param colors
     * @return
     * @throws IllegalArgumentException
     */
    private static Simon.States lowest(Simon.States[] colors) throws IllegalArgumentException{
        for (int i = 3; i >= 0; i--){
            if (contains(colors, PRIORITY_ORDERS[prior][i])) return PRIORITY_ORDERS[prior][i];
        }
        throw new IllegalArgumentException("Unreachable section of lowest() was reached");
    }

    /**
     *
     *
     * @param colors
     * @return
     * @throws IllegalArgumentException
     */
    private static Simon.States lowestNotFlashed(Simon.States[] colors) throws IllegalArgumentException{
        for (int i = 3; i >= 0; i--){
            if (!contains(colors, PRIORITY_ORDERS[prior][i])) return PRIORITY_ORDERS[prior][i];
        }
        throw new IllegalArgumentException("Unreachable section of lowestNotFlashed() was reached");
    }

    /**
     *
     *
     * @param colors
     * @param has
     * @return
     */
    private static boolean contains(Simon.States[] colors, Simon.States has){
        for (Simon.States current : colors) {
            if (current == has) return true;
        }
        return false;
    }

    /**
     *
     *
     * @param colors
     * @return
     */
    private static boolean containsNotPressed(Simon.States[] colors){
        for (Simon.States color : colors) {
            for (String sample : toPress.toString().split(" ")) {
                if (sample.equals(firstCap(color))) return false;
            }
        }
        return true;
    }

    /**
     *
     *
     * @param colors
     * @return
     */
    private static boolean bothPreviouslyPressed(Simon.States[] colors){
        boolean[] firstSet = new boolean[2], secondSet = new boolean[2];
        String[] temp = toPress.toString().split(" ");
        firstSet[0] = temp[0].equals(firstCap(colors[0]));
        firstSet[1] = temp[1].equals(firstCap(colors[1]));

        secondSet[0] = temp[0].equals(firstCap(colors[1]));
        secondSet[1] = temp[1].equals(firstCap(colors[0]));
        return (firstSet[0] && firstSet[1]) || (secondSet[0] && secondSet[1]);
    }

    /**
     *
     *
     * @param colors
     * @return
     */
    private static boolean previouslyPressed(Simon.States[] colors){
        for (Simon.States current : colors){
            for (String sample : toPress.toString().split(" ")){
                if (sample.equals(firstCap(current))) return true;
            }
        } return false;
    }

    /**
     *
     *
     * @param colors
     * @return
     */
    private static Simon.States oddOneOut(Simon.States[] colors){
        if (!contains(colors, RED)) return RED;
        else if (!contains(colors, YELLOW)) return YELLOW;
        else if (!contains(colors, GREEN)) return GREEN;
        else return BLUE;
    }

    /**
     *
     *
     * @return
     */
    private static String oddOneOut(){
        if (!toPress.toString().contains("Red"))
            return "Red";
        else if (!toPress.toString().contains("Yellow"))
            return "Yellow";
        else if (!toPress.toString().contains("Green"))
            return "Green";
        return "Blue";
    }

    /**
     *
     *
     * @return
     */
    private static boolean allUnique(){
        String[] temp = toPress.toString().split(" ");
        return !((temp[0].equals(temp[1])) || (temp[0].equals(temp[2])) || (temp[1].equals(temp[2])));
    }

    /**
     *
     *
     * @param colors
     * @return
     */
    private static boolean oneNotPressed(Simon.States[] colors){
       int counter = 4;
       for (Simon.States current : colors){
           for (String sample : toPress.toString().split(" ")){
               if (sample.equals(firstCap(current))) counter--;
           }
       }
        return counter == 3;
    }

    /**
     *
     *
     * @param colors
     * @return
     */
    private static String pressOneNot(Simon.States[] colors){
        for (Simon.States current : colors){
            for (String sample : toPress.toString().split(" ")){
                if (sample.equals(firstCap(current))) return firstCap(current);
            }
        }
        return null;
    }

    /**
     *
     */
    public static void resetMod(){
        toPress = new StringBuilder();
    }

    /**
     *
     *
     * @param text
     * @return
     */
    private static String firstCap(Simon.States text){
        return (text.name().length() != 1)?
                text.name().substring(0,1).toUpperCase() + text.name().substring(1):
                text.name().toUpperCase();
    }

    /**
     *
     *
     * @param colors
     * @return
     */
    private static String writeOut(Simon.States[] colors){
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < colors.length; i++){
            temp.append(firstCap(colors[i]));
            if (i < colors.length-1)
                temp.append(" ");
        }
        return temp.toString();
    }
}