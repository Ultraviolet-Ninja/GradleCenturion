package bomb.modules.s.simon.states;

import bomb.abstractions.State;

public enum StageState implements State<StageState> {
    FIRST {
        @Override
        public StageState nextState() {
            return SECOND;
        }
    },
    SECOND {
        @Override
        public StageState nextState() {
            return THIRD;
        }
    },
    THIRD {
        @Override
        public StageState nextState() {
            return FOURTH;
        }
    },
    FOURTH {
        @Override
        public StageState nextState() {
            return FIRST;
        }
    };

    public int getStageNum() {
        return ordinal() + 1;
    }
}
