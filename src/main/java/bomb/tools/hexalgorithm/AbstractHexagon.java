package bomb.tools.hexalgorithm;

import bomb.tools.data.structures.FixedArrayQueue;

public abstract class AbstractHexagon {
    protected final Hex hexagon;

    public AbstractHexagon(Hex hexagon){
        this.hexagon = hexagon;
    }

    public int sideLength() {
        return hexagon.sideLength();
    }

    public int span(){
        return hexagon.getSpan();
    }

    public Hex hexport(){
        return hexagon;
    }

    public FixedArrayQueue<FixedArrayQueue<Hex.HexNode>> exportTo2DQueue(){
        return hexagon.exportTo2DQueue();
    }
}
