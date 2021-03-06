package bomb.tools;

import bomb.abstractions.Coordinate;
import bomb.abstractions.EquatableObject;

public class Coordinates extends EquatableObject implements Coordinate {
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

    public Coordinates immutableAdd(Coordinates vector){
        return new Coordinates(this.x + vector.x, this.y + vector.y);
    }

    @Override
    public int hashCode() {
        return x + HASHING_NUMBER * y;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Coordinates)) return false;
        return ((Coordinates)o).x == this.x && ((Coordinates)o).y == this.y;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }
}
