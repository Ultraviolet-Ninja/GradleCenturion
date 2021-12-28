package bomb.modules.c.chords;

import bomb.Widget;
import bomb.modules.s.souvenir.Souvenir;
import bomb.tools.data.structures.ring.ArrayRing;

import java.util.Set;
import java.util.TreeSet;

public class ChordQualities extends Widget {
    public static final String NEW_CHORD = "New Chord: ";

    private static final ArrayRing<String> ALL_NOTES;

    static {
        ALL_NOTES = new ArrayRing<>("A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#");
    }

    public static String solve(String input) throws IllegalArgumentException {
        Set<String> sortedSet = validateInput(input);
        if (isSouvenirActive)
            Souvenir.addRelic("Chord Quality Original Notes", input);

        String[] chordQuality = getNextChordQuality(sortedSet);
        if (chordQuality == null || chordQuality[1] == null)
            throw new IllegalArgumentException("The received notes didn't make a new chord quality");

        return NEW_CHORD + chordQuality[0] + " " + chordQuality[1];
    }

    private static Set<String> validateInput(String input) throws IllegalArgumentException {
        Set<String> sortedSet = new TreeSet<>();
        for (String note : input.split(" ")) {
            if (sortedSet.contains(note))
                throw new IllegalArgumentException("The input can't contain repeated notes");
            if (ALL_NOTES.findAbsoluteIndex(note) == -1)
                throw new IllegalArgumentException("Invalid note was in the input");
            sortedSet.add(note);
        }
        if (sortedSet.size() != 4)
            throw new IllegalArgumentException("There weren't 4 notes in the input");
        return sortedSet;
    }

    private static String[] getNextChordQuality(Set<String> input) {
        ArrayRing<String> inputNoteRing = new ArrayRing<>(input);
        ArrayRing<String> noteDistanceRing = createNoteDistanceRing(inputNoteRing);
        return generateNewChord(inputNoteRing, noteDistanceRing);
    }

    private static ArrayRing<String> createNoteDistanceRing(ArrayRing<String> inputNoteRing) {
        int inputNoteRingSize = inputNoteRing.getSize();
        ArrayRing<String> output = new ArrayRing<>(inputNoteRingSize);
        ALL_NOTES.rotateClockwise(ALL_NOTES.findRelativeIndex(inputNoteRing.getHeadData()));
        inputNoteRing.rotateClockwise();

        for (int i = 0; i < inputNoteRingSize; i++) {
            int distance = ALL_NOTES.findRelativeIndex(inputNoteRing.getHeadData());
            output.add(String.valueOf(distance));
            ALL_NOTES.rotateClockwise(distance);
            inputNoteRing.rotateClockwise();
        }
        inputNoteRing.rotateCounterClockwise();
        return output;
    }

    private static String[] generateNewChord(ArrayRing<String> inputNoteRing, ArrayRing<String> noteDistanceRing) {
        for (int i = 0; i < inputNoteRing.getSize(); i++) {
            String[] results = runOneRotation(inputNoteRing, noteDistanceRing);
            if (results != null)
                return results;
            noteDistanceRing.rotateCounterClockwise(noteDistanceRing.getSize() - 2);
            inputNoteRing.rotateClockwise();
        }
        return null;
    }

    private static String[] runOneRotation(ArrayRing<String> inputNoteRing, ArrayRing<String> noteDistanceRing) {
        StringBuilder attemptedDistances = new StringBuilder();
        int ringSize = noteDistanceRing.getSize() - 1;

        for (int distanceRingCount = 0; distanceRingCount < ringSize; distanceRingCount++) {
            attemptedDistances.append(noteDistanceRing.getHeadData());
            noteDistanceRing.rotateClockwise();
            if (distanceRingCount < ringSize - 1)
                attemptedDistances.append(",");
        }

        String resultingDistanceString = attemptedDistances.toString();

        for (Quality quality : Quality.values()) {
            if (quality.getRelatedDistance().equals(resultingDistanceString)) {
                if (isSouvenirActive)
                    Souvenir.addRelic("Chord Quality Original Chord", quality.getLabel());
                return new String[]{quality.getNextNote(), Root.getNewDistances(inputNoteRing.getHeadData())};
            }
        }
        return null;
    }
}
