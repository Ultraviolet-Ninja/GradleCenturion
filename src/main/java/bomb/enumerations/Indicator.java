package bomb.enumerations;

import bomb.abstractions.Ternary;

public enum Indicator implements Ternary {
    BOB(TriState.UNKNOWN), CAR(TriState.UNKNOWN), CLR(TriState.UNKNOWN),
    FRK(TriState.UNKNOWN), FRQ(TriState.UNKNOWN), IND(TriState.UNKNOWN),
    MSA(TriState.UNKNOWN), NSA(TriState.UNKNOWN), SIG(TriState.UNKNOWN),
    SND(TriState.UNKNOWN), TRN(TriState.UNKNOWN);

    private TriState property;

    @Override
    public TriState getProp() {
        return property;
    }

    @Override
    public void setProp(TriState in){
        property = in;
    }

    Indicator(TriState in){
        property = in;
    }
}