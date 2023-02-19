package bomb.modules.ab.battleship.solve;

import bomb.modules.ab.battleship.Ocean;
import bomb.modules.ab.battleship.Ship;
import bomb.tools.Coordinates;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static bomb.modules.ab.battleship.Ocean.BOARD_LENGTH;
import static bomb.modules.ab.battleship.Tile.SHIP;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ShipFinder {
    private ShipFinder(){}

    public static boolean inspectCompletedBoard(Ocean ocean) {
        EnumMap<Ship, Long> countMap = new EnumMap<>(Ship.class);
        countMap.putAll(findShips(ocean)
                .stream()
                .mapToInt(Set::size)
                .mapToObj(Ship::matchShipToSize)
                .filter(Objects::nonNull)
                .collect(groupingBy(identity(), counting())));

        for (Ship ship : Ship.SHIPS) {
            countMap.putIfAbsent(ship, 0L);
        }

        int[] shipCounts = countMap.values()
                .stream()
                .mapToInt(Long::intValue)
                .toArray();

        return Arrays.equals(shipCounts, Ship.getAllShipCounts());
    }

    private static Set<Set<Coordinates>> findShips(Ocean ocean) {
        boolean[][] visited = new boolean[BOARD_LENGTH][BOARD_LENGTH];
        Set<Set<Coordinates>> output = new HashSet<>();

        for (int x = 0; x < BOARD_LENGTH; x++) {
            for (int y = 0; y < BOARD_LENGTH; y++) {
                if (ocean.getTileState(x, y) == SHIP && !visited[x][y]) {
                    Set<Coordinates> singleShip = new TreeSet<>();
                    depthFirstSearch(ocean, x, y, visited, singleShip);
                    output.add(singleShip);
                }
                visited[x][y] = true;
            }
        }

        return output;
    }

    private static void depthFirstSearch(Ocean ocean, int x, int y, boolean[][] visited,
                                         Set<Coordinates> singleShip) {
        if (isOutOfBounds(x, y) || visited[x][y] || ocean.getTileState(x, y) != SHIP)
            return;

        visited[x][y] = true;
        singleShip.add(new Coordinates(x, y));

        depthFirstSearch(ocean, x + 1, y, visited, singleShip);
        depthFirstSearch(ocean, x - 1, y, visited, singleShip);
        depthFirstSearch(ocean, x, y + 1, visited, singleShip);
        depthFirstSearch(ocean, x, y - 1, visited, singleShip);
    }

    private static boolean isOutOfBounds(int x, int y) {
        return x < 0 || y < 0 ||
                x >= BOARD_LENGTH || y >= BOARD_LENGTH;
    }
}
