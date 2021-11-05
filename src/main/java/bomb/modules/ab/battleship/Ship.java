package bomb.modules.ab.battleship;

//TODO - Subject for deletion if not needed
//Possibly could use it for validation
public enum Ship {
    BATTLESHIP(4), DESTROYER(3), CRUISER(2), U_BOAT(1);

    private static final int MINIMUM_NUMBER_OF_SHIPS = 3;

    private final byte shipSize;

    private byte currentQuantity;

    Ship(int shipSize) {
        this.shipSize = (byte) shipSize;
        this.currentQuantity = 0;
    }

    public int getShipSize() {
        return shipSize;
    }

    public void setCurrentQuantity(byte currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public static boolean areQuantitiesSet() {
        int counter = 0;
        for (Ship ship : values()) {
            counter += ship.currentQuantity;
        }
        return counter >= MINIMUM_NUMBER_OF_SHIPS;
    }

    public static void clearAllQuantities() {
        for (Ship ship : values()) {
            ship.setCurrentQuantity((byte) 0);
        }
    }
}
