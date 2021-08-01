package bomb.modules.c.chords;

import bomb.abstractions.Labeled;

public enum Root implements Labeled {
    A("A", "+3 +5 +3"), A_SHARP("A#","+4 +4 +3"), B("B","+3 +4 +3"),
    C("C","+3 +3 +4"), C_SHARP("C#","+2 +1 +4"), D("D","+4 +3 +4"),
    D_SHARP("D#","+3 +1 +6"), E("E","+5 +2 +3"), F("F","+2 +2 +3"),
    F_SHARP("F#","+4 +3 +3"), G("G","+3 +4 +4"), G_SHARP("G#","+4 +4 +2");

    private final String note, newDistances;

    @Override
    public String getLabel() {
        return newDistances;
    }

    public static String getNewDistances(String note){
        String distances = null;
        for (Root root : Root.values()) {
            if (root.note.equals(note))
                distances = root.newDistances;
        }
        return distances;
    }

    Root(String note, String newDistances){
        this.note = note;
        this.newDistances = newDistances;
    }
}
