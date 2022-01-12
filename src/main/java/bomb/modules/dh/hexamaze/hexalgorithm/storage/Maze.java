package bomb.modules.dh.hexamaze.hexalgorithm.storage;

import bomb.modules.dh.hexamaze.hexalgorithm.factory.MazeFactory;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public class Maze extends AbstractHexagon {
    private static final int FULL_MAZE_SIDE_LENGTH = 12;

    public Maze() throws IllegalArgumentException {
        super(new HexagonalPlane(FULL_MAZE_SIDE_LENGTH));

        try {
            hexagon.readInNodeList(MazeFactory.createMaze());
        } catch (IOException | CsvException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
