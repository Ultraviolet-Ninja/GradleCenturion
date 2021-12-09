package bomb;

import bomb.modules.dh.hexamaze_redesign.hexalgorithm.maze_finding.MazeSearch;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Grid;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexNode;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexagonDataStructure;
import bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.Maze;
import bomb.tools.filter.Regex;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static bomb.modules.dh.hexamaze_redesign.hexalgorithm.storage.HexagonDataStructure.NODAL_AREA;

public class TestingArea {
    public static DecimalFormat format = new DecimalFormat("###,###,###,###");

    public static void main(String[] args) throws IOException {
        Regex labelFilter = new Regex("\"([^\"]*)\",?");
        Regex frequencyFilter = new Regex("frequencies\\.put\\(\"([^\"]+)\", (\\d\\.\\d{1,3})\\);");
        Regex whoMapFilter = new Regex("stepTwoMap\\.put\\(\"([^\"]+)\", \"([^\"]+)\"\\);");

//        Widget.setSerialCode("e60xa6");
//        String[] moveArray = {"c5", "D5", "a-1", "F-3", "d1", "e4"};
//        List<String> moves = Arrays.asList(moveArray);
//        System.out.println(Chess.solve(moves));
        Maze maze = new Maze();
        Grid grid = createGrid();
        long start = System.nanoTime();
        System.out.println(MazeSearch.search(maze, grid));
        long stop = System.nanoTime();
        System.out.printf("Time: %,d", stop - start);
    }

    private static Grid createGrid() {
        List<HexNode> nodes = new ArrayList<>();
        int area = NODAL_AREA.applyAsInt(Grid.GRID_SIDE_LENGTH);
        for (int i = 0; i < area; i++) {
//            if (i == 3)
//                nodes.add(new HexNode(HexNode.HexShape.RIGHT_TRIANGLE, EnumSet.noneOf(HexNode.HexWall.class)));
//            if (i == 32)
//                nodes.add(new HexNode(HexNode.HexShape.CIRCLE, EnumSet.noneOf(HexNode.HexWall.class)));
//            else
                nodes.add(new HexNode(null, EnumSet.noneOf(HexNode.HexWall.class)));
        }

        return new Grid(new HexagonDataStructure(nodes));
    }
}