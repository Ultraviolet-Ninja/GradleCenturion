package bomb.enumerations;

public enum Ports {
    DVI, PARALLEL, PS2, RJ45, SERIAL, RCA;

    public static Ports fromString(String string){
        for(Ports port : Ports.values()){
            if (string.equalsIgnoreCase(port.name()))return port;
        }
        return null;
    }
}
