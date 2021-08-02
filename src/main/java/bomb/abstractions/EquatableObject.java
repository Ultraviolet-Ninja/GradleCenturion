package bomb.abstractions;

public abstract class EquatableObject {
    protected static final short HASHING_NUMBER = 5501;

    public abstract boolean equals(Object o);
    public abstract int hashCode();
}
