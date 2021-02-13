package bomb.modules.s.shape_shift;

import bomb.interfaces.Index;

public enum ShiftShape implements Index {
    ROUND(0), POINT(1), FLAT(2), TICKET(3);

    private final int idx;

    ShiftShape(int idx){
        this.idx = idx;
    }

    @Override
    public int getIdx() {
        return idx;
    }
}
