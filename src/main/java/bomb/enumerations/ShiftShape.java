package bomb.enumerations;

import bomb.interfaces.Index;

public enum ShiftShape implements Index {
    ROUNDED(0), TRIANGULAR(1), RECTANGULAR(2), TICKET(3);

    private final int idx;

    ShiftShape(int idx){
        this.idx = idx;
    }

    @Override
    public int getIdx() {
        return idx;
    }
}
