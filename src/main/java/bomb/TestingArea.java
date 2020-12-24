package bomb;

import bomb.interfaces.Coordinate;
import bomb.tools.Coordinates;
import bomb.tools.data.structures.MapStack;
import bomb.tools.hexalgorithm.Hex;
import bomb.tools.hexalgorithm.HexPanelFiller;

import java.util.ArrayList;

public class TestingArea {
    public static void main(String[] args) {
        int[] array = new int[2];
        System.out.println(array[-1]);
//        ArrayList<HexTraits.HexShape> array = new ArrayList<>();
//        System.out.println(Hex.nodalArea(4));
//        int length = Hex.nodalArea(4);
//        for (int i = 0; i < length; i++){
//            if (i == 12)
//                array.add(HexTraits.HexShape.Circle);
////            else if (i ==14)
////                array.add(HexTraits.HexShape.Hexagon);
//            else
//                array.add(null);
//        }
//        HexGrid grid = new HexGrid();
//        grid.fill(array);
//        Maze maze;
//        try{
//            maze = new Maze();
//            System.out.println(HexComparator.evaluate(maze, grid) == null);
//        } catch (IOException io){
//            System.err.println("SHIT BRUV");
//        }

    }

    private static ArrayList<Hex.HexNode> makeHex(){
        return null;
    }
}