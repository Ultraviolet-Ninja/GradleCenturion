package bomb.enumerations;

import bomb.interfaces.Index;
import bomb.interfaces.Ternary;

public enum Indicators implements Index, Ternary {
    BOB(0, TriState.UNKNOWN), CAR(1, TriState.UNKNOWN), CLR(2, TriState.UNKNOWN),
    FRK(3, TriState.UNKNOWN), FRQ(4, TriState.UNKNOWN), IND(5, TriState.UNKNOWN),
    MSA(6, TriState.UNKNOWN), NSA(7, TriState.UNKNOWN), SIG(8, TriState.UNKNOWN),
    SND(9, TriState.UNKNOWN), TRN(10, TriState.UNKNOWN);

    private final int index;
    private TriState property;

    @Override
    public int getIdx() {
        return index;
    }

    @Override
    public TriState getProp() {
        return property;
    }

    @Override
    public void setProp(TriState in){
        property = in;
    }

    Indicators(int index, TriState in){
        this.index = index;
        property = in;
    }
}