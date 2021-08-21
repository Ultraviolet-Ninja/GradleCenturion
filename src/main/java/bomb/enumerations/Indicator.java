package bomb.enumerations;

public enum Indicator {
    BOB, CAR, CLR, FRK, FRQ, IND, MSA, NSA, SIG, SND, TRN;

    private TrinaryState state;

    public TrinaryState getState() {
        return state;
    }

    public void setState(TrinaryState in){
        state = in;
    }

    Indicator(){
        state = TrinaryState.UNKNOWN;
    }
}