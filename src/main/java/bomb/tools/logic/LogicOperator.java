package bomb.tools.logic;

public enum LogicOperator implements BooleanOperation {
    AND {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return bitOne && bitTwo;
        }
    },
    OR {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return bitOne || bitTwo;
        }
    },
    XOR {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return ((bitOne && !bitTwo) || (!bitOne && bitTwo));
        }
    },
    NOT {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return !bitOne;
        }
    },
    NOR {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return !OR.test(bitOne, bitTwo);
        }
    },
    NAND {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return !AND.test(bitOne, bitTwo);
        }
    },
    XNOR {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return !XOR.test(bitOne, bitTwo);
        }
    },
    IMPLIES {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return !(bitOne && !bitTwo);
        }
    },
    IMPLIED_BY {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return !(!bitOne && bitTwo);
        }
    }
}
