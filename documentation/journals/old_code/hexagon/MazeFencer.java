package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.modules.dh.hexamaze.HexTraits;
import bomb.tools.data.structures.FixedArrayQueue;

@Deprecated
public class MazeFencer {
    private MazeFencer(){}

    public static HexGrid gateOffExits(HexGrid copiedGrid, int sideToExit) {
        HexTraits.HexWall[] allWalls = HexTraits.HexWall.values();
        FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction = copiedGrid.exportTo2DQueue();
        if (sideToExit != 0)
            gateOffTopLeftSide(extraction, allWalls, sideToExit);
        if (sideToExit != 1)
            gateOffTopRightSide(extraction, allWalls, sideToExit);
        if (sideToExit != 2)
            gateOffRightSide(extraction, allWalls, sideToExit);
        if (sideToExit != 3)
            gateOffBottomRightSide(extraction, allWalls, sideToExit);
        if (sideToExit != 4)
            gateOffBottomLeftSide(extraction, allWalls, sideToExit);
        if (sideToExit != 5)
            gateOffLeftSide(extraction, allWalls, sideToExit);
        return copiedGrid;
    }

    private static void gateOffTopLeftSide(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction,
                                           HexTraits.HexWall[] allWalls, int sideToExit) {
        /* (0,0) (1,0) (2,0) (3,0)
         * Walls 0 and 1
         */
        for (int i = 0; i <= extraction.cap() / 2; i++) {
            if (i != 0 || sideToExit != 5)
                extraction.get(i).get(0).addWalls(allWalls[0]);
            if (i != extraction.cap() - 1 || sideToExit != 1)
                extraction.get(i).get(0).addWalls(allWalls[1]);
        }
    }

    private static void gateOffTopRightSide(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction,
                                            HexTraits.HexWall[] allWalls, int sideToExit) {
        /* (3,0) (4,0) (5,0) (6,0)
         * Walls 1 and 2
         */
        for (int i = extraction.cap() / 2; i < extraction.cap(); i++) {
            if (i != extraction.cap() / 2 || sideToExit != 0)
                extraction.get(i).get(0).addWalls(allWalls[1]);
            if (i != extraction.cap() - 1 || sideToExit != 2)
                extraction.get(i).get(0).addWalls(allWalls[2]);
        }
    }

    private static void gateOffRightSide(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction,
                                         HexTraits.HexWall[] allWalls, int sideToExit) {
        /* (6,0) (6,1) (6,2) (6,3)
         * Walls 2 and 5
         */
        int lastIndex = extraction.cap() - 1;
        for (int i = 0; i <= extraction.cap() / 2; i++) {
            if (i != 0 || sideToExit != 1)
                extraction.get(lastIndex).get(i).addWalls(allWalls[2]);
            if (i != extraction.cap() / 2 - 1 || sideToExit != 3)
                extraction.get(lastIndex).get(i).addWalls(allWalls[5]);
        }
    }

    private static void gateOffBottomRightSide(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction,
                                               HexTraits.HexWall[] allWalls, int sideToExit) {
        /* (3,6) (4,5) (5,4) (6,3)
         * Walls 4 and 5
         */
        for (int i = extraction.cap() / 2; i < extraction.cap(); i++) {
            int lastIndex = extraction.get(i).cap() - 1;
            if (i != extraction.cap() / 2 || sideToExit != 2)
                extraction.get(i).get(lastIndex).addWalls(allWalls[4]);
            if (i != extraction.cap() - 1 || sideToExit != 4)
                extraction.get(i).get(lastIndex).addWalls(allWalls[5]);
        }
    }

    private static void gateOffBottomLeftSide(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction,
                                              HexTraits.HexWall[] allWalls, int sideToExit) {
        /* (0,3) (1,4) (2,5) (3,6)
         * Walls 3 and 4
         */
        for (int i = 0; i <= extraction.cap() / 2; i++) {
            int lastIndex = extraction.get(i).cap() - 1;
            if (i != 0 || sideToExit != 5)
                extraction.get(i).get(lastIndex).addWalls(allWalls[3]);
            if (i != extraction.cap() / 2 - 1 || sideToExit != 3)
                extraction.get(i).get(lastIndex).addWalls(allWalls[4]);
        }
    }

    private static void gateOffLeftSide(FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> extraction,
                                        HexTraits.HexWall[] allWalls, int sideToExit) {
        /* (0,0) (0,1) (0,2) (0,3)
         * Walls 0 and 3
         */
        for (int i = 0; i < extraction.get(0).cap(); i++) {
            if (i != 0 || sideToExit != 0)
                extraction.get(0).get(i).addWalls(allWalls[0]);
            if (i != extraction.get(0).cap() - 1 || sideToExit != 5)
                extraction.get(0).get(i).addWalls(allWalls[3]);
        }
    }
}
