package bomb.modules.ab.battleship;

import java.util.Arrays;

import static java.util.Arrays.stream;

public enum Ship {
    BATTLESHIP(4), CRUISER(3), DESTROYER(2), SUBMARINE(1);

    public static final Ship[] SHIPS = values();

    /** Minimum number of ships on a given board */
    private static final int MINIMUM_NUMBER_OF_SHIPS = 4;


    private final byte shipSize;

    private byte currentQuantity;

    Ship(int shipSize) {
        this.shipSize = (byte) shipSize;
        this.currentQuantity = 0;
    }

    public void setCurrentQuantity(byte currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    @Override
    public String toString() {
        return String.format("Type: %s - Required Number: %d",
                name(), currentQuantity);
    }

    public static int getNumberOfShipSpaces() {
        return stream(SHIPS)
                .mapToInt(ship -> ship.shipSize * ship.currentQuantity)
                .sum();
    }

    public static boolean areQuantitiesSet() {
        return stream(SHIPS)
                .mapToInt(Ship::getCurrentQuantity)
                .sum() >= MINIMUM_NUMBER_OF_SHIPS;
    }

    public static void clearAllQuantities() {
        for (Ship ship : SHIPS)
            ship.setCurrentQuantity((byte) 0);
    }

    public static Ship matchShipToSize(int size) {
        for (Ship ship : SHIPS) {
            if (ship.shipSize == size)
                return ship;
        }
        return null;
    }

    public static int[] getAllShipCounts() {
        return Arrays.stream(values())
                .mapToInt(ship -> ship.currentQuantity)
                .toArray();
    }
}
