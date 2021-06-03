package bomb.tools;

import bomb.interfaces.Coordinate;

public class Coordinates implements Coordinate {
    private int x, y;

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinates(Coordinates original){
        this.x = original.x;
        this.y = original.y;
    }

    @Override
    public int[] getCoords() {
        return new int[]{x, y};
    }

    public void alterCurrentCoords(Coordinates alterSet){
        this.x += alterSet.x;
        this.y += alterSet.y;
    }
}
