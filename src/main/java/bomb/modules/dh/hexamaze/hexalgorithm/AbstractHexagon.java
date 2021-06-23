package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.tools.data.structures.FixedArrayQueue;

public abstract class AbstractHexagon {
    protected HexagonDataStructure hexagon;

    public AbstractHexagon(){hexagon = null;}

    public AbstractHexagon(HexagonDataStructure hexagon){
        this.hexagon = hexagon;
    }

    public int sideLength() {
        return hexagon.sideLength();
    }

    public int getSpan(){
        return hexagon.getSpan();
    }

    public HexagonDataStructure hexport(){
        return hexagon;
    }

    public FixedArrayQueue<FixedArrayQueue<HexagonDataStructure.HexNode>> exportTo2DQueue(){
        return hexagon.exportTo2DQueue();
    }

    public static int[] calculateColumnLengths(int sideLength){
        int counter = 0;
        int[] request = new int[2 * sideLength - 1];

        for (int i = sideLength; i < 2 * sideLength; i++)
            request[counter++] = i;

        for (int j = 2 * sideLength - 2; j >= sideLength; j--)
            request[counter++] = j;

        return request;
    }
}
