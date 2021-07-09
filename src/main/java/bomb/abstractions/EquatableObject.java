package bomb.abstractions;

public abstract class EquatableObject {
    protected int HASHING_NUMBER = 1337;

    public abstract boolean equals(Object o);
    public abstract int hashCode();
}
