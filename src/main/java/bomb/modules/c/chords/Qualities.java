package bomb.modules.c.chords;

import bomb.abstractions.Labeled;

public enum Qualities implements Labeled {
    SEVENTH("G"), NEG_SEVETH( "G#"),
    TRIAD_SEVENTH("A#"),
    NEG_TRIAD_SEVENTH("F"),
    SEVENTH_SHARP_NINTH("A"),
    HALF_DIMINISHED("C#"),
    ADD_NINTH("D#"),
    NEG_ADD_NINTH("E"),
    SEVENTH_SHARP_FIFTH("F#"), TRIAD_SEVENTH_SHARP_FIFTH("C"),
    SEVENTH_SUSTAIN("D"),
    NEG_TRIAD_SEVENTH_SHARP_FIFTH("B");

    private final String newNote;

    @Override
    public String getLabel() {
        return newNote;
    }

    Qualities(String note){
        newNote = note;
    }
}
