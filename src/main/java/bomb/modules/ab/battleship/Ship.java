package bomb.modules.ab.battleship;

import static java.util.Arrays.stream;

public enum Ship {
    BATTLESHIP(4), DESTROYER(3), CRUISER(2), U_BOAT(1);

    /** Minimum number of ships on a given board */
    private static final int MINIMUM_NUMBER_OF_SHIPS = 4;

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

    public static int getNumberOfShipSpaces() {
        return stream(values())
                .mapToInt(ship -> ship.shipSize * ship.currentQuantity)
                .sum();
    }

    public static boolean areQuantitiesSet() {
        return stream(values())
                .mapToInt(Ship::getCurrentQuantity)
                .sum() >= MINIMUM_NUMBER_OF_SHIPS;
    }

    public static void clearAllQuantities() {
        for (Ship ship : values())
            ship.setCurrentQuantity((byte) 0);
    }
}
