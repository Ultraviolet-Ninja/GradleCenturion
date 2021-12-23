package bomb.tools;

import static bomb.tools.number.MathUtils.HASHING_NUMBER;

public class Coordinates implements Comparable<Coordinates> {
    private final int x, y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinates immutableAdd(Coordinates vector) {
        return new Coordinates(this.x + vector.x, this.y + vector.y);
    }

    public Coordinates immutableAdd(int x, int y) {
        return new Coordinates(this.x + x, this.y + y);
    }

    @Override
    public int hashCode() {
        return x * HASHING_NUMBER + y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates other = (Coordinates) o;
        return other.x == this.x && other.y == this.y;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(Coordinates o) {
        if (o == null) return -1;

        int xComparison = Integer.compare(x, o.x);
        if (xComparison != 0) return xComparison;

        return Integer.compare(y, o.y);
    }
}
