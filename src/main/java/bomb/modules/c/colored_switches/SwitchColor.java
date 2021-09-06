package bomb.modules.c.colored_switches;

public enum SwitchColor {
    RED, ORANGE, GREEN, CYAN, BLUE, PURPLE, BLACK;

    public static SwitchColor getByIndex(int index) {
        return values()[index];
    }
}
