package bomb.modules.dh.emoji;

import bomb.interfaces.Index;
import bomb.interfaces.Labeled;

public enum Emojis implements Index, Labeled {
    COLON_CLOSE(0, ":)"), EQUAL_OPEN(1, "=("), OPEN_COLON(2, "(:"),
    CLOSED_EQUAL(3, ")="), COLON_OPEN(4, ":("), CLOSED_COLON(5, "):"),
    EQUAL_CLOSED(6, "=)"), OPEN_EQUAL(7, "(="), COLON_BAR(8, ":|"),
    BAR_COLON(9, "|:");

    private final String label;
    private final int index;

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public int getIdx() {
        return index;
    }

    Emojis(int index, String label){
        this.index = index;
        this.label = label;
    }
}
