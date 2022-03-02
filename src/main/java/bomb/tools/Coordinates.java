package bomb.tools;

import org.jetbrains.annotations.NotNull;

import static bomb.tools.number.MathUtils.HASHING_NUMBER;

public record Coordinates(int x, int y) implements Comparable<Coordinates> {
    public Coordinates add(@NotNull Coordinates vector) {
        return new Coordinates(this.x + vector.x, this.y + vector.y);
    }

    public Coordinates add(int x, int y) {
        return new Coordinates(this.x + x, this.y + y);
    }

    @Override
    public int hashCode() {
        return x * HASHING_NUMBER + y;
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
