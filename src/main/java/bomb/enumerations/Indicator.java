package bomb.enumerations;

import bomb.abstractions.Ternary;

public enum Indicator implements Ternary {
    BOB(TrinaryState.UNKNOWN), CAR(TrinaryState.UNKNOWN), CLR(TrinaryState.UNKNOWN),
    FRK(TrinaryState.UNKNOWN), FRQ(TrinaryState.UNKNOWN), IND(TrinaryState.UNKNOWN),
    MSA(TrinaryState.UNKNOWN), NSA(TrinaryState.UNKNOWN), SIG(TrinaryState.UNKNOWN),
    SND(TrinaryState.UNKNOWN), TRN(TrinaryState.UNKNOWN);

    private TrinaryState state;

    @Override
    public TrinaryState getState() {
        return state;
    }

    @Override
    public void setState(TrinaryState in){
        state = in;
    }

    Indicator(TrinaryState in){
        state = in;
    }
}