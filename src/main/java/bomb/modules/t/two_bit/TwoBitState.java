package bomb.modules.t.two_bit;

import bomb.abstractions.State;

public enum TwoBitState implements State<TwoBitState> {
    FIRST_QUERY {
        @Override
        public TwoBitState nextState() {
            return null;
        }
    },
    SECOND_QUERY {
        @Override
        public TwoBitState nextState() {
            return null;
        }
    },
    THIRD_QUERY {
        @Override
        public TwoBitState nextState() {
            return null;
        }
    },
    SUBMIT {
        @Override
        public TwoBitState nextState() {
            return null;
        }
    }
}
