package bomb.modules.s.simon.states;

import bomb.abstractions.Index;
import bomb.abstractions.State;

public enum StageState implements State<StageState>, Index {
    FIRST(1) {
        @Override
        public StageState nextState() {
            return SECOND;
        }
    },
    SECOND(2) {
        @Override
        public StageState nextState() {
            return THIRD;
        }
    },
    THIRD(3) {
        @Override
        public StageState nextState() {
            return FOURTH;
        }
    },
    FOURTH(4) {
        @Override
        public StageState nextState() {
            return FIRST;
        }
    };

    private final byte index;

    StageState(int index) {
        this.index = (byte) index;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
