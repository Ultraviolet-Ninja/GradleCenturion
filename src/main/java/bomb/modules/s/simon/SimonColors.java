package bomb.modules.s.simon;

import bomb.abstractions.Labeled;

public final class SimonColors {
    public enum StateColor {
        RED, YELLOW, GREEN, BLUE
    }

    public enum ScreamColor implements Labeled {
        RED("#c30606"), ORANGE("#ff9513"), YELLOW("#cfdd00"),
        GREEN("#00a80e"), BLUE("#0097ff"), PURPLE("#9e64eb");

        private final String label;

        ScreamColor(String label) {
            this.label = label;
        }

        @Override
        public String getLabel() {
            return label;
        }
    }
}
