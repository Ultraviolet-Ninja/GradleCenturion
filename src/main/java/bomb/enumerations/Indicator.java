package bomb.enumerations;

public enum Indicator {
    BOB, CAR, CLR, FRK, FRQ, IND, MSA, NSA, SIG, SND, TRN;

    private TrinarySwitch state;

    public TrinarySwitch getState() {
        return state;
    }

    public void setState(TrinarySwitch in){
        state = in;
    }

    Indicator(){
        state = TrinarySwitch.UNKNOWN;
    }
}