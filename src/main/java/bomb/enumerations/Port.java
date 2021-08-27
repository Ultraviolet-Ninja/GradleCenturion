package bomb.enumerations;

public enum Port {
    DVI, PARALLEL, PS2, RJ45, SERIAL, RCA;

    public static Port fromString(String string) {
        for (Port port : Port.values()) {
            if (string.equalsIgnoreCase(port.name())) return port;
        }
        return null;
    }
}
