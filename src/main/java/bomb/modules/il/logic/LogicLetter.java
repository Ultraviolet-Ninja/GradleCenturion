package bomb.modules.il.logic;

import bomb.Widget;
import bomb.enumerations.Port;

import java.util.function.BooleanSupplier;

import static bomb.Widget.IndicatorFilter.ALL_PRESENT;
import static bomb.Widget.IndicatorFilter.LIT;
import static bomb.Widget.IndicatorFilter.UNLIT;
import static bomb.enumerations.Indicator.FRK;
import static bomb.enumerations.Indicator.IND;
import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Port.PARALLEL;
import static bomb.enumerations.Port.PS2;
import static bomb.tools.filter.RegexFilter.NUMBER_PATTERN;
import static bomb.tools.filter.RegexFilter.filter;

public enum LogicLetter implements BooleanSupplier {
    A {
        @Override
        public boolean getAsBoolean() {
            return Widget.getAllBatteries() == Widget.countIndicators(ALL_PRESENT);
        }
    },
    B {
        @Override
        public boolean getAsBoolean() {
            return Widget.countLettersInSerialCode() > Widget.countLettersInSerialCode();
        }
    },
    C {
        @Override
        public boolean getAsBoolean() {
            return Widget.hasIndicator(IND);
        }
    },
    D {
        @Override
        public boolean getAsBoolean() {
            return Widget.hasIndicator(FRK);
        }
    },
    E {
        @Override
        public boolean getAsBoolean() {
            return Widget.countIndicators(UNLIT) == 1;
        }
    },
    F {
        @Override
        public boolean getAsBoolean() {
            return Widget.countPortTypes() > 1;
        }
    },
    G {
        @Override
        public boolean getAsBoolean() {
            return Widget.getAllBatteries() >= 2;
        }
    },
    H {
        @Override
        public boolean getAsBoolean() {
            return !G.getAsBoolean();
        }
    },
    I {
        @Override
        public boolean getAsBoolean() {
            return Widget.getSerialCodeLastDigit() % 2 == 1;
        }
    },
    J {
        @Override
        public boolean getAsBoolean() {
            return Widget.getAllBatteries() > 4;
        }
    },
    K {
        @Override
        public boolean getAsBoolean() {
            return Widget.countIndicators(LIT) == 1;
        }
    },
    L {
        @Override
        public boolean getAsBoolean() {
            return Widget.countIndicators(ALL_PRESENT) > 2;
        }
    },
    M {
        @Override
        public boolean getAsBoolean() {
            Port[] ports = Port.values();

            for (Port port : ports) {
                int count = Widget.getPortQuantity(port);
                if (count > 1)
                    return false;
            }

            return true;
        }
    },
    N {
        @Override
        public boolean getAsBoolean() {
            return Widget.getNumHolders() > 2;
        }
    },
    O {
        @Override
        public boolean getAsBoolean() {
            return Widget.countIndicators(LIT) > 0 &&
                    Widget.countIndicators(UNLIT) > 0;
        }
    },
    P {
        @Override
        public boolean getAsBoolean() {
            return Widget.doesPortExists(PARALLEL);
        }
    },
    Q {
        @Override
        public boolean getAsBoolean() {
            return Widget.calculateTotalPorts() == 2;
        }
    },
    R {
        @Override
        public boolean getAsBoolean() {
            return Widget.doesPortExists(PS2);
        }
    },
    S {
        @Override
        public boolean getAsBoolean() {
            int count = filter(Widget.getSerialCode(), NUMBER_PATTERN)
                    .chars()
                    .mapToObj(i->(char)i)
                    .mapToInt(Character::getNumericValue)
                    .sum();
            return count > 10;
        }
    },
    T {
        @Override
        public boolean getAsBoolean() {
            return Widget.hasIndicator(MSA);
        }
    },
    U {
        @Override
        public boolean getAsBoolean() {
            return Widget.getNumHolders() == 1;
        }
    },
    V {
        @Override
        public boolean getAsBoolean() {
            return Widget.hasVowelInSerialCode();
        }
    },
    W {
        @Override
        public boolean getAsBoolean() {
            return Widget.countIndicators(ALL_PRESENT) == 1;
        }
    },
    X {
        @Override
        public boolean getAsBoolean() {
            return Widget.countIndicators(ALL_PRESENT) == 0;
        }
    },
    Y {
        @Override
        public boolean getAsBoolean() {
            return Widget.calculateTotalPorts() > 5;
        }
    },
    Z {
        @Override
        public boolean getAsBoolean() {
            return Widget.calculateTotalPorts() < 2;
        }
    }
}
