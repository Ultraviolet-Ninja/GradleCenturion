package bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage;

import bomb.tools.Coordinates;

import java.util.stream.IntStream;

public abstract class AbstractHexagon {
    protected HexagonDataStructure hexagon;

    public AbstractHexagon() {
        hexagon = null;
    }

    public AbstractHexagon(HexagonDataStructure hexagon) {
        this.hexagon = hexagon;
    }

    public HexagonDataStructure getHexagon() {
        return hexagon;
    }

    public HexNode getAtCoordinates(Coordinates coordinates) {
        return hexagon.findAtCoordinate(coordinates);
    }

    public static IntStream calculateColumnLengthStream(int sideLength) {
        int span = HexagonDataStructure.CALCULATE_SPAN.applyAsInt(sideLength);
        return IntStream.concat(
                IntStream.rangeClosed(sideLength, span),
                IntStream.rangeClosed(span - 1, sideLength)
        );
    }
}
