package bomb.modules.dh.hexamaze_redesign.hexalgorithm;

import bomb.abstractions.EquatableObject;

import java.util.EnumSet;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

public class HexNode extends EquatableObject {
    private EnumSet<HexWall> walls;
    private HexShape hexShape;

    public HexNode(HexShape hexShape, EnumSet<HexWall> constructs) {
        walls = constructs;
        this.hexShape = hexShape;
    }

    public HexNode(HexNode toCopy) {
        walls = EnumSet.copyOf(toCopy.walls);
        hexShape = toCopy.hexShape;
    }

    public boolean isPathClear(int wallTag) {
        return walls.stream()
                .mapToInt(Enum::ordinal)
                .anyMatch(ordinal -> ordinal == wallTag);
    }

    public EnumSet<HexWall> getWalls() {
        return walls;
    }

    public void setWalls(EnumSet<HexWall> walls) {
        this.walls = walls;
    }

    public HexShape getHexShape() {
        return hexShape;
    }

    public void setHexShape(HexShape hexShape) {
        this.hexShape = hexShape;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof HexNode)) return false;
        HexNode other = (HexNode) obj;
        return other.hexShape == this.hexShape && walls.equals(other.walls);
    }

    @Override
    public int hashCode() {
        return HASHING_NUMBER * (hexShape != null ? hexShape.hashCode() : 1) +
                ((walls != null) ? walls.hashCode() : 0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
                hexShape != null ? hexShape.toString() : "No Shape"
        );
        sb.append("-");
        if (walls == null) return sb.append("No walls").toString();
        sb.append(
                walls.stream()
                .map(Objects::toString)
                .collect(joining(" "))
        );
        return sb.toString();
    }

    public enum HexShape {
        CIRCLE, HEXAGON, LEFT_TRIANGLE, RIGHT_TRIANGLE, UP_TRIANGLE, DOWN_TRIANGLE
    }

    public enum HexWall {
        TOP_LEFT, TOP, TOP_RIGHT, BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT
    }
}
