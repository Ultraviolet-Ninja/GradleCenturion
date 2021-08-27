package bomb.enumerations;

import bomb.abstractions.Labeled;
import bomb.abstractions.Listed;

public enum WhosOnFirstWords implements Labeled, Listed {
    READY("READY", "YES", "OKAY", "WHAT", "MIDDLE", "LEFT", "PRESS", "RIGHT", "BLANK", "READY"),
    FIRST("FIRST", "LEFT", "OKAY", "YES", "MIDDLE", "NO", "RIGHT", "NOTHING", "UHHH", "WAIT", "READY",
            "BLANK", "WHAT", "PRESS", "FIRST"),
    NO("NO", "BLANK", "UHHH", "WAIT", "FIRST", "WHAT", "READY", "RIGHT", "YES", "NOTHING", "LEFT",
            "PRESS", "OKAY", "NO"),
    BLANK("BLANK", "WAIT", "RIGHT", "OKAY", "MIDDLE", "BLANK"),
    NOTHING("NOTHING", "UHHH", "RIGHT", "OKAY", "MIDDLE", "YES", "BLANK", "NO", "PRESS", "LEFT", "WHAT",
            "WAIT", "FIRST", "NOTHING"),
    YES("YES", "OKAY", "RIGHT", "UHHH", "MIDDLE", "FIRST", "WHAT", "PRESS", "READY", "NOTHING", "YES"),
    WHAT("WHAT", "UHHH", "WHAT"),
    UHHH("UHHH", "READY", "NOTHING", "LEFT", "WHAT", "OKAY", "YES", "RIGHT", "NO", "PRESS", "BLANK", "UHHH"),
    LEFT("LEFT", "RIGHT", "LEFT"),
    RIGHT("RIGHT", "YES", "NOTHING", "READY", "PRESS", "NO", "WAIT", "WHAT", "RIGHT"),
    MIDDLE("MIDDLE", "BLANK", "READY", "OKAY", "WHAT", "NOTHING", "PRESS", "NO", "WAIT", "LEFT", "MIDDLE"),
    OKAY("OKAY", "MIDDLE", "NO", "FIRST", "YES", "UHHH", "NOTHING", "WAIT", "OKAY"),
    WAIT("WAIT", "UHHH", "NO", "BLANK", "OKAY", "YES", "LEFT", "FIRST", "PRESS", "WHAT", "WAIT"),
    PRESS("PRESS", "RIGHT", "MIDDLE", "YES", "READY", "PRESS"),
    YOU("YOU", "SURE", "YOU ARE", "YOUR", "YOU'RE", "NEXT", "UH HUH", "UR", "HOLD", "WHAT?", "YOU", "UH UH",
            "LIKE", "DONE", "U"),
    YOUARE("YOU ARE", "YOUR", "NEXT", "LIKE", "UH HUH", "WHAT?", "DONE", "UH UH", "HOLD", "YOU", "U", "YOU'RE",
            "SURE", "UR", "YOU ARE"),
    YOUR("YOUR", "UH UH", "YOU ARE", "UH HUH", "YOUR"),
    YOURE("YOU'RE", "YOU", "YOU'RE"),
    UR("UR", "DONE", "U", "UR"),
    U("U", "UH HUH", "SURE", "NEXT", "WHAT?", "YOU'RE", "UR", "UH UH", "DONE", "U"),
    UHHUH("UH HUH", "UH HUH"),
    UHUH("UH UH", "UR", "U", "YOU ARE", "YOU'RE", "NEXT", "UH UH"),
    WHATQ("WHAT?", "YOU", "HOLD", "YOU'RE", "YOUR", "U", "DONE", "UH UH", "LIKE", "YOU ARE", "UH HUH", "UR",
            "NEXT", "WHAT?"),
    DONE("DONE", "SURE", "UH HUH", "NEXT", "WHAT?", "YOUR", "UR", "YOU'RE", "HOLD", "LIKE", "YOU", "U",
            "YOU ARE", " UH UH", "DONE"),
    NEXT("NEXT", "WHAT?", "UH HUH", "UH UH", "YOUR", "HOLD", "SURE", "NEXT"),
    HOLD("HOLD", "YOU ARE", "U", "DONE", "UH UH", "YOU", "UR", "SURE", "WHAT?", "YOU'RE", "NEXT", "HOLD"),
    SURE("SURE", "YOU ARE", "DONE", "LIKE", "YOU'RE", "YOU", "HOLD", "UH HUH", "UR", "SURE"),
    LIKE("LIKE", "YOU'RE", "NEXT", "U", "UR", "HOLD", "DONE", "UH UH", "WHAT?", "UH HUH", "YOU", "LIKE");

    private final String label;
    private final String[] stream;

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String[] getWords() {
        return stream;
    }

    WhosOnFirstWords(String label, String... wordStream) {
        this.label = label;
        stream = wordStream;
    }
}
