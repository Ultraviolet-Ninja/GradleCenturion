package bomb.tools.logic;

import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

public enum LogicOperator implements BooleanOperation {
    NOT {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return !bitOne;
        }
    },
    AND("∧") {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return bitOne && bitTwo;
        }
    },
    OR("∨") {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return bitOne || bitTwo;
        }
    },
    XOR("⊻") {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return ((bitOne && !bitTwo) || (!bitOne && bitTwo));
        }
    },
    NOR("↓") {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return !OR.test(bitOne, bitTwo);
        }
    },
    NAND("|") {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return !AND.test(bitOne, bitTwo);
        }
    },
    XNOR("↔") {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return !XOR.test(bitOne, bitTwo);
        }
    },
    IMPLIES("→") {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return !(bitOne && !bitTwo);
        }
    },
    IMPLIED_BY("←") {
        @Override
        public boolean test(boolean bitOne, boolean bitTwo) {
            return !(!bitOne && bitTwo);
        }
    };

    private final String symbol;

    LogicOperator() {
        symbol = null;
    }

    LogicOperator(String symbol) {
        this.symbol = symbol;
    }

    public static final Map<String, LogicOperator> LOGIC_SYMBOL_TO_ENUM_MAP;

    static {
        LOGIC_SYMBOL_TO_ENUM_MAP = new TreeMap<>(EnumSet.range(AND, IMPLIED_BY)
                .stream()
                .collect(toMap(
                        logicSymbol -> logicSymbol.symbol,
                        identity()
                ))
        );
    }
}
