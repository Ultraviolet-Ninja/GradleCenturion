package bomb.modules.c.chords;

import bomb.modules.s.souvenir.Souvenir;
import bomb.Widget;

import static bomb.tools.filter.Filter.NUMBER_PATTERN;
//TODO - Probably rename some variables and make the Javadocs

/**
 * This class deals with the Chord Qualities module.
 */
@Deprecated
public class OldChordQualities extends Widget {
    public static final String NEW_CHORD = "New Chord: ";

    private static final String[]
            ALL_NOTES = new String[]{"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"},

    DISTANCES = new String[]{"4,3,3", "3,4,3", "4,3,4", "3,4,4",
            "3,1,6", "3,3,4", "2,2,3", "2,1,4", "4,4,2", "4,4,3", "5,2,3", "3,5,3"};

    /**
     *
     * @param input
     * @return
     * @throws IllegalArgumentException
     */
    public static String solve(String input) throws IllegalArgumentException {
        String[] og = originalInfo(input);
        return NEW_CHORD + getNextRoot(Integer.parseInt(og[1])) + " " + getNextQuality(og[0]);
    }

    /**
     * @param input
     * @return
     * @throws IllegalArgumentException
     */
    private static String[] originalInfo(String input) throws IllegalArgumentException {
        if (souvenir) Souvenir.addRelic("Chord Quality Original Notes", input);

        String temp = noteConvert(input);
        String[] outputs = new String[2];
        int[] traits = originalQuality(combinations(temp));
        outputs[0] = getOriginalRoot(traits[1], input);
        outputs[1] = String.valueOf(traits[0]);
        if (souvenir) Souvenir.addRelic("Chord Quality Original Chord", getChord(traits[0]));
        return outputs;
    }

    /**
     * @param notes
     * @return
     * @throws IllegalArgumentException
     */
    private static String noteConvert(String notes) throws IllegalArgumentException {
        //TODO - Break down
        StringBuilder output = new StringBuilder();
        String[] noteArray = notes.split(" "),
                order = newOrder(findFirst(noteArray[0]));

        int distance = 1, noteCount = 1, totalDistance = 0;
        for (int i = 1; i < order.length; i++) {
            if (noteArray[noteCount].equals(order[i])) {
                output.append(distance).append(",");
                totalDistance += distance;
                distance = 1;
                noteCount++;
            } else
                distance++;

            if (noteCount == 4) {
                i = order.length;
                output.append(order.length - totalDistance);
            }
        }

        NUMBER_PATTERN.loadText(output.toString());
        if (NUMBER_PATTERN.toNewString().length() != 4)
            throw new IllegalArgumentException("There shouldn't be any duplicate notes");
        return output.toString();
    }

    /**
     * @param given
     * @return
     * @throws IllegalArgumentException
     */
    private static int findFirst(String given) throws IllegalArgumentException {
        for (int i = 0; i < ALL_NOTES.length; i++) {
            if (given.equals(ALL_NOTES[i]))
                return i;
        }
        throw new IllegalArgumentException();
    }

    /**
     * @param first
     * @return
     */
    private static String[] newOrder(int first) {
        String[] order = new String[ALL_NOTES.length];
        for (int i = 0; i < order.length; i++) {
            order[i] = ALL_NOTES[first++];
            if (first == ALL_NOTES.length)
                first = 0;
        }
        return order;
    }

    /**
     * @param distances
     * @return
     */
    private static String[] combinations(String distances) {
        String[] outputs = new String[4];
        outputs[0] = distances;
        outputs[1] = reorder(distances);
        outputs[2] = reorder(outputs[1]);
        outputs[3] = reorder(outputs[2]);
        return outputs;
    }

    /**
     * @param sample
     * @return
     */
    private static String reorder(String sample) {
        return sample.substring(2) + "," + sample.charAt(0);
    }

    /**
     * @param combos
     * @return
     * @throws IllegalArgumentException
     */
    private static int[] originalQuality(String[] combos) throws IllegalArgumentException {
        for (int idx = 0; idx < DISTANCES.length; idx++) {
            for (int jdx = 0; jdx < combos.length; jdx++)
                if (combos[jdx].startsWith(DISTANCES[idx]))
                    return new int[]{idx, jdx};
        }
        throw new IllegalArgumentException("This shouldn't occur in originalQuality()");
    }

    private static String getOriginalRoot(int which, String notes) {
        return notes.split(" ")[which];
    }

    /**
     * @param note
     * @return
     * @throws IllegalArgumentException
     */
    private static String getNextQuality(String note) throws IllegalArgumentException {
        for (Root current : Root.values()) {
            if (note.equals(current.getNote()))
                return current.getLabel();
        }
        throw new IllegalArgumentException("This shouldn't reach this exception");
    }

    /**
     * @param quality
     * @return
     */
    private static String getNextRoot(int quality) {
        return Quality.values()[quality].getLabel();
    }

    /**
     * @param position
     * @return
     */
    private static String getChord(int position) {
        switch (position) {
            case 0: return "7";
            case 1: return "-7";
            case 2: return "Δ7";
            case 3: return "-Δ7";
            case 4: return "7#9";
            case 5: return "Ø";
            case 6: return "add9";
            case 7: return "-add9";
            case 8: return "7#5";
            case 9: return "Δ7#5";
            case 10: return "7sus";
            default: return "-Δ7#5";
        }
    }
}