package bomb.modules.dh.hexamaze.hexalgorithm.storage;

import bomb.abstractions.State;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Objects;

import static bomb.tools.number.MathUtils.HASHING_NUMBER;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;

public final class HexNode implements Rotatable {
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
        StringBuilder stringBuilder = new StringBuilder(
                hexShape != null ? hexShape.toString() : "No Shape"
        );
        stringBuilder.append('-');
        if (walls == null) return stringBuilder.append("No walls").toString();
        stringBuilder.append(
                walls.stream()
                .map(Objects::toString)
                .collect(joining(" "))
        );
        return stringBuilder.toString();
    }

    @Override
    public void rotate() {
        if (!walls.isEmpty()) {
            walls = walls.stream()
                    .map(State::toNextState)
                    .collect(toCollection(() -> EnumSet.noneOf(HexWall.class)));
        }

        if (hexShape != null) hexShape = hexShape.toNextState();
    }

    public enum HexShape implements State<HexShape> {
        CIRCLE, HEXAGON, LEFT_TRIANGLE {
            @Override
            public HexShape toNextState() {
                return RIGHT_TRIANGLE;
            }
        }, RIGHT_TRIANGLE {
            @Override
            public HexShape toNextState() {
                return LEFT_TRIANGLE;
            }
        }, UP_TRIANGLE {
            @Override
            public HexShape toNextState() {
                return DOWN_TRIANGLE;
            }
        }, DOWN_TRIANGLE {
            @Override
            public HexShape toNextState() {
                return UP_TRIANGLE;
            }
        };

        @Override
        public HexShape toNextState() {
            return this;
        }
    }

    public enum HexWall implements State<HexWall> {
        TOP_LEFT {
            @Override
            public HexWall toNextState() {
                return TOP;
            }
        }, TOP {
            @Override
            public HexWall toNextState() {
                return TOP_RIGHT;
            }
        }, TOP_RIGHT {
            @Override
            public HexWall toNextState() {
                return BOTTOM_RIGHT;
            }
        }, BOTTOM_LEFT {
            @Override
            public HexWall toNextState() {
                return TOP_LEFT;
            }
        }, BOTTOM {
            @Override
            public HexWall toNextState() {
                return BOTTOM_LEFT;
            }
        }, BOTTOM_RIGHT {
            @Override
            public HexWall toNextState() {
                return BOTTOM;
            }
        }
    }
}
