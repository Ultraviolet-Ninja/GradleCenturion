package bomb.enumerations;

import bomb.interfaces.Index;

public enum Ports implements Index {
    DVI(0), PARALLEL(1), PS2(2),
    RJ45(3), SERIAL(4), RCA(5);

    private final int index;
    @Override
    public int getIdx() {
        return index;
    }

    Ports(int index){
        this.index = index;
    }

    public static Ports fromString(String string){
        for(Ports port : Ports.values()){
            if (string.equalsIgnoreCase(port.name()))return port;
        }
        return null;
    }
}
