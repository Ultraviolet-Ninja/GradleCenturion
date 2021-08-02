package bomb.modules.c.chords;

import bomb.abstractions.Labeled;

public enum Quality implements Labeled {
    SEVENTH("7","4,3,3","G"), NEG_SEVENTH("-7","3,4,3","G#"),
    TRIAD_SEVENTH("Δ7","4,3,4","A#"),
    NEG_TRIAD_SEVENTH("-Δ7","3,4,4","F"),
    SEVENTH_SHARP_NINTH("7#9","3,1,6","A"),
    HALF_DIMINISHED("Ø","3,3,4","C#"),
    ADD_NINTH("add9","2,2,3","D#"),
    NEG_ADD_NINTH("-add9","2,1,4","E"),
    SEVENTH_SHARP_FIFTH("7#5","4,4,2","F#"),
    TRIAD_SEVENTH_SHARP_FIFTH("Δ7#5","4,4,3","C"),
    SEVENTH_SUSTAIN("7sus","5,2,3","D"),
    NEG_TRIAD_SEVENTH_SHARP_FIFTH("-Δ7#5","3,5,3","B");

    private final String label, nextNote, distances;

    public String getRelatedDistance(){
        return distances;
    }

    public String getNextNote(){
        return nextNote;
    }

    @Override
    public String getLabel() {
        return label;
    }

    Quality(String label, String distances, String note){
        this.label = label;
        nextNote = note;
        this.distances = distances;
    }
}
