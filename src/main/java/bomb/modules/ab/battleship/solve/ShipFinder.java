package bomb.modules.ab.battleship.solve;

import bomb.modules.ab.battleship.Ocean;
import bomb.modules.ab.battleship.Ship;
import bomb.tools.Coordinates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static bomb.modules.ab.battleship.Ocean.BOARD_LENGTH;
import static bomb.modules.ab.battleship.Tile.SHIP;
import static bomb.modules.ab.battleship.Tile.UNKNOWN;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ShipFinder {
    private ShipFinder(){}

    public static void updateFoundShips(Ocean ocean) {
        Set<Set<Coordinates>> possibleShips = findShips(ocean);
        eliminateIncompleteShips(ocean, possibleShips);

        possibleShips.stream()
                .mapToInt(Set::size)
                .mapToObj(Ship::matchShipToSize)
                .collect(groupingBy(identity(), counting()))
                .forEach((ship, count) -> ship.setFoundShips(count.intValue()));
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

    private static void eliminateIncompleteShips(Ocean ocean, Set<Set<Coordinates>> ships) {
        List<Set<Coordinates>> toRemove = new ArrayList<>();

        for (Set<Coordinates> ship : ships) {
            if (!isCompleteShip(ocean, ship))
                toRemove.add(ship);
        }

        toRemove.forEach(ships::remove);
    }

    private static boolean isCompleteShip(Ocean ocean, Set<Coordinates> ship) {
        for (Coordinates location : ship) {
            int x;
            int y;

            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    x = location.x() + xOffset;
                    y = location.y() + yOffset;

                    if (!isOutOfBounds(x, y) && ocean.getTileState(x, y) == UNKNOWN)
                        return false;
                }
            }
        }
        return true;
    }

    private static boolean isOutOfBounds(int x, int y) {
        return x < 0 || y < 0 ||
                x >= BOARD_LENGTH || y >= BOARD_LENGTH;
    }
}
