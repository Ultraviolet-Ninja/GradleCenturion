package bomb.modules.dh.hexamaze.hexalgorithm.storage;

import bomb.abstractions.Resettable;
import bomb.abstractions.State;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static bomb.tools.number.MathUtils.HASHING_NUMBER;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toUnmodifiableMap;

public final class HexNode implements Resettable, Rotatable {
    @NotNull
    private EnumSet<HexWall> walls;
    @NotNull
    private HexShape hexShape;
    @NotNull
    private PlayerColor playerColor;

    public HexNode(@NotNull HexShape hexShape, @NotNull EnumSet<HexWall> constructs) {
        requireNonNull(constructs);
        requireNonNull(hexShape);
        walls = constructs;
        this.hexShape = hexShape;
        playerColor = PlayerColor.NO_PLAYER;
    }

    public HexNode(@NotNull HexNode toCopy) {
        walls = EnumSet.copyOf(toCopy.walls);
        hexShape = toCopy.hexShape;
        playerColor = PlayerColor.NO_PLAYER;
    }

    public HexNode() {
        walls = EnumSet.noneOf(HexWall.class);
        hexShape = HexShape.EMPTY;
        playerColor = PlayerColor.NO_PLAYER;
    }

    public boolean isPathBlocked(HexWall testWall) {
        return walls.contains(testWall);
    }

    public @NotNull EnumSet<HexWall> getWalls() {
        return walls;
    }

    public @NotNull HexShape getHexShape() {
        return hexShape;
    }

    public void setHexShape(@NotNull HexShape hexShape) {
        requireNonNull(hexShape, "Hex Shape cannot be null");
        this.hexShape = hexShape;
    }

    public @NotNull PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(@NotNull PlayerColor color) throws IllegalArgumentException {
        requireNonNull(color, "Player color cannot be null");
        this.playerColor = color;
    }

    public void clearPlayerColor() {
        playerColor = PlayerColor.NO_PLAYER;
    }

    @Override
    public void reset() {
        clearPlayerColor();
        walls.clear();
        hexShape = HexShape.EMPTY;
    }

    @Contract(" -> new")
    public @NotNull HexNode copy() {
        return new HexNode(hexShape, EnumSet.copyOf(walls));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof HexNode other)) return false;
        return other.hexShape == this.hexShape && walls.equals(other.walls);
    }

    @Override
    public int hashCode() {
        return HASHING_NUMBER * hexShape.hashCode() +
                walls.hashCode();
    }

    @Override
    public @NotNull String toString() {
        var stringBuilder = new StringBuilder().append(hexShape)
                .append('-');
        if (walls.isEmpty()) {
            stringBuilder.append("No walls");
        } else {
            stringBuilder.append(
                    walls.stream()
                            .map(Objects::toString)
                            .collect(joining(" "))
            );
        }

        return stringBuilder.append('-')
                .append("Color:")
                .append(playerColor)
                .toString();
    }

    @Override
    public void rotate() {
        if (!walls.isEmpty()) {
            walls = walls.stream()
                    .map(State::toNextState)
                    .collect(toCollection(() -> EnumSet.noneOf(HexWall.class)));
        }

        hexShape = hexShape.toNextState();
    }

    public enum HexShape implements State<HexShape> {
        EMPTY, CIRCLE, HEXAGON, LEFT_TRIANGLE {
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

    public enum PlayerColor {
        NO_PLAYER(Color.web("c6c6c6")),
        RED(Color.RED), YELLOW(Color.YELLOW), GREEN(Color.GREEN),
        CYAN(Color.CYAN), BLUE(Color.BLUE), PINK(Color.PINK);

        private final Color paintColor;

        public static final Map<Color, PlayerColor> PLAYER_COLOR_MAPPER;

        static {
            PLAYER_COLOR_MAPPER = stream(values())
                    .collect(toUnmodifiableMap(pc -> pc.paintColor, Function.identity()));
        }

        public Color getPaintColor() {
            return paintColor;
        }

        PlayerColor(Color paintColor) {
            this.paintColor = paintColor;
        }
    }
}
