package bomb.modules.dh.hexamaze.hexalgorithm.storage;

import bomb.modules.dh.hexamaze.hexalgorithm.factory.MazeFactory;

public final class Maze extends AbstractHexagon {
    private static final int FULL_MAZE_SIDE_LENGTH = 12;

    public Maze() throws IllegalArgumentException, IllegalStateException {
        super(new HexagonalPlane(FULL_MAZE_SIDE_LENGTH));
        hexagon.readInNodeList(MazeFactory.createMaze());
    }
}
