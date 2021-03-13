package bomb;

import bomb.modules.dh.hexamaze.hexalgorithm.Hex;
import bomb.modules.dh.hexamaze.hexalgorithm.HexGrid;
import bomb.modules.dh.hexamaze.hexalgorithm.Maze;
import bomb.modules.dh.hexamaze.hexalgorithm.ThreadedHexComparator;
import java.io.IOException;
import java.util.ArrayList;

public class TestingArea {
    public static void main(String[] args) {
        String[] shapes = "n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,h,n,n,n,n,n".split(",");
        ArrayList<Hex.HexNode> nodes = new ArrayList<>();
        for (String shape : shapes){
            nodes.add(new Hex.HexNode(Hex.decodeShape(shape), null));
        }
        HexGrid inputGrid = new HexGrid(new Hex(nodes), 0);
        try{
            System.out.println(ThreadedHexComparator.evaluate(new Maze(), inputGrid));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}