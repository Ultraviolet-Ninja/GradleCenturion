package bomb.modules.dh.hexamaze.hexalgorithm.storage;

import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Objects;

import static bomb.tools.number.MathUtils.HASHING_NUMBER;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

public class HexNode {
    private EnumSet<HexWall> walls;
    private HexShape hexShape;
    private int color;

    public HexNode(HexShape hexShape, @NotNull EnumSet<HexWall> constructs) {
        requireNonNull(constructs);
        walls = constructs;
        this.hexShape = hexShape;
        color = -1;
    }

    public HexNode(@NotNull HexNode toCopy) {
        walls = EnumSet.copyOf(toCopy.walls);
        hexShape = toCopy.hexShape;
        color = -1;
    }

    public boolean isPathBlocked(HexWall testWall) {
        return walls.contains(testWall);
    }

    public EnumSet<HexWall> getWalls() {
        return walls;
    }

    public void setWalls(@NotNull EnumSet<HexWall> walls) {
        requireNonNull(walls);
        this.walls = walls;
    }

    public HexShape getHexShape() {
        return hexShape;
    }

    public void setHexShape(HexShape hexShape) {
        this.hexShape = hexShape;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void clearColor() {
        color = -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof HexNode other)) return false;
        return other.hexShape == this.hexShape && walls.equals(other.walls);
    }

    @Override
    public int hashCode() {
        return HASHING_NUMBER * (hexShape != null ? hexShape.hashCode() : 1) +
                walls.hashCode();
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
