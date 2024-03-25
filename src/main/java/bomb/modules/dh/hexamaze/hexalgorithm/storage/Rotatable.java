package bomb.modules.dh.hexamaze.hexalgorithm.storage;

public sealed interface Rotatable permits Grid, HexNode, HexagonalPlane {
    void rotate();
}
