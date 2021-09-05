package bomb.modules.c.colored_switches;

public enum ColoredSwitchProperty {
    RED, ORANGE, GREEN, CYAN, BLUE, PURPLE, BLACK;

    public static ColoredSwitchProperty getByIndex(int index) {
        return values()[index];
    }
}
