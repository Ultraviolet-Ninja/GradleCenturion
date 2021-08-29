package bomb;

import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.HexagonDataStructure;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TestingArea {
    public static DecimalFormat format = new DecimalFormat("###,###,###,###");

    public static void main(String[] args) {
        System.out.println(Math.floorDiv(1, 7));
        System.out.println(Math.floorDiv(8, 7));
        System.out.println(Math.floorDiv(20, 7));
    }

    private static HexGrid fromLine(String line){
        ArrayList<HexagonDataStructure.HexNode> list = new ArrayList<>();
        for (String shape : line.split(","))
            list.add(new HexagonDataStructure.HexNode(HexagonDataStructure.decodeShape(shape), null));
        return new HexGrid(new HexagonDataStructure(list));
    }

//    private static void testComparators(Maze fullMaze, HexGrid testGrid){
//        long linearStart = System.nanoTime();
//        OldHexComparator.evaluate(fullMaze, testGrid);
//        long linearStop = System.nanoTime();
//
//        long threadedStart = System.nanoTime();
//        HexComparator.findSubsection(fullMaze, testGrid);
//        long threadedStop = System.nanoTime();
//
//        long hashStart = System.nanoTime();
//        HexHashLibrary.find(testGrid);
//        long hashStop = System.nanoTime();
//
//        System.out.println("Old Linear Time: " + format.format(linearStop - linearStart));
//        System.out.println("New Linear time: " + format.format(threadedStop - threadedStart));
//        System.out.println("Hash time: " + format.format(hashStop - hashStart));
//    }
}