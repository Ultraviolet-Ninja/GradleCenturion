package bomb.modules.s.simon.states;

import bomb.abstractions.State;

public enum StageState implements State<StageState> {
    FIRST {
        @Override
        public StageState toNextState() {
            return SECOND;
        }
    },
    SECOND {
        @Override
        public StageState toNextState() {
            return THIRD;
        }
    },
    THIRD {
        @Override
        public StageState toNextState() {
            return FOURTH;
        }
    },
    FOURTH {
        @Override
        public StageState toNextState() {
            return FIRST;
        }
    };

    public int getStageNum() {
        return ordinal() + 1;
    }
}
