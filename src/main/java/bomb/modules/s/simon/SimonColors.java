package bomb.modules.s.simon;

import bomb.abstractions.Labeled;

public class SimonColors {
    public enum States {
        RED, YELLOW, GREEN, BLUE
    }

    public enum Screams implements Labeled {
        RED("#c30606"), ORANGE("#ff9513"), YELLOW("#cfdd00"),
        GREEN("#00a80e"), BLUE("#0097ff"), PURPLE("#9e64eb");

        private final String label;

        Screams(String label) {
            this.label = label;
        }

        @Override
        public String getLabel() {
            return label;
        }
    }
}
