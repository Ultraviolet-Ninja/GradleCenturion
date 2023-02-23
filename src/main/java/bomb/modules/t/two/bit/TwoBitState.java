package bomb.modules.t.two.bit;

import bomb.abstractions.State;

public enum TwoBitState implements State<TwoBitState> {
    SECOND_QUERY {
        @Override
        public TwoBitState toNextState() {
            return THIRD_QUERY;
        }
    },
    THIRD_QUERY {
        @Override
        public TwoBitState toNextState() {
            return SUBMIT;
        }
    },
    SUBMIT {
        @Override
        public TwoBitState toNextState() {
            return SECOND_QUERY;
        }
    }
}
