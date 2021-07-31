package bomb.modules.c.chords;

import bomb.Widget;
import bomb.modules.s.souvenir.Souvenir;
import bomb.tools.data.structures.ring.ReadOnlyRing;

import java.util.HashSet;
import java.util.Set;

public class ChordQualities extends Widget {
    public static final String NEW_CHORD = "New Chord: ";

    private static final ReadOnlyRing<String> ALL_NOTES;

    static {
        ALL_NOTES = new ReadOnlyRing<>("A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#");
    }

    public static String solve(String input) throws IllegalArgumentException {
        validateInput(input);
        String[] chordQuality = getNextChordQuality(input);
        if (chordQuality == null || chordQuality[1] == null)
            throw new IllegalArgumentException("The received notes didn't make a new chord quality");
        return NEW_CHORD + chordQuality[0] + " " + chordQuality[1];
    }

    private static void validateInput(String input) throws IllegalArgumentException {
        Set<String> repeatDetector = new HashSet<>();
        for (String note : input.split(" ")) {
            if (repeatDetector.contains(note))
                throw new IllegalArgumentException("The input can't contain repeated notes");
            if (ALL_NOTES.findAbsoluteIndex(note) == -1)
                throw new IllegalArgumentException("Invalid note was in the input");
            repeatDetector.add(note);
        }
        if (repeatDetector.size() != 4)
            throw new IllegalArgumentException("There weren't 4 notes in the input");
    }

    private static String[] getNextChordQuality(String input) {
        if (souvenir) Souvenir.addRelic("Chord Quality Original Notes", input);
        ReadOnlyRing<String> inputNoteRing = new ReadOnlyRing<>(input.split(" "));
        ReadOnlyRing<String> noteDistanceRing = createNoteDistanceRing(inputNoteRing);
        return generateNewChord(inputNoteRing, noteDistanceRing);
    }

    private static ReadOnlyRing<String> createNoteDistanceRing(ReadOnlyRing<String> inputNoteRing) {
        ReadOnlyRing<String> output = new ReadOnlyRing<>(inputNoteRing.getCapacity());
        ALL_NOTES.rotateClockwise(ALL_NOTES.findRelativeIndex(inputNoteRing.getHeadData()));
        inputNoteRing.rotateClockwise();

        for (int i = 0; i < inputNoteRing.getCapacity(); i++) {
            int distance = ALL_NOTES.findRelativeIndex(inputNoteRing.getHeadData());
            output.add(String.valueOf(distance));
            ALL_NOTES.rotateClockwise(distance);
            inputNoteRing.rotateClockwise();
        }
        inputNoteRing.rotateCounterClockwise();
        return output;
    }

    private static String[] generateNewChord(ReadOnlyRing<String> inputNoteRing, ReadOnlyRing<String> noteDistanceRing) {
        for (int i = 0; i < inputNoteRing.getCapacity(); i++) {
            String[] results = runOneRotation(inputNoteRing, noteDistanceRing);
            if (results != null)
                return results;
            noteDistanceRing.rotateCounterClockwise(noteDistanceRing.getCapacity() - 2);
            inputNoteRing.rotateClockwise();
        }
        return null;
    }

    private static String[] runOneRotation(ReadOnlyRing<String> inputNoteRing, ReadOnlyRing<String> noteDistanceRing) {
        StringBuilder attemptedDistances = new StringBuilder();

        for (int distanceRingCount = 0; distanceRingCount < noteDistanceRing.getCapacity() - 1; distanceRingCount++) {
            attemptedDistances.append(noteDistanceRing.getHeadData());
            noteDistanceRing.rotateClockwise();
            if (distanceRingCount < noteDistanceRing.getCapacity() - 2)
                attemptedDistances.append(",");
        }

        String resultingDistanceString = attemptedDistances.toString();

        for (Quality quality : Quality.values()) {
            if (quality.getRelatedDistance().equals(resultingDistanceString)) {
                if (souvenir) Souvenir.addRelic("Chord Quality Original Chord", quality.getLabel());
                return new String[]{quality.getNextNote(), Root.getNewDistances(inputNoteRing.getHeadData())};
            }
        }
        return null;
    }
}
