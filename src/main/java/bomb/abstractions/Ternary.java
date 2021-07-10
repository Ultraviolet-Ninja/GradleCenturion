package bomb.abstractions;

import bomb.enumerations.TrinaryState;

/**
 * This interface deals with any enum that has 3 states: On, Off or Unknown
 */
public interface Ternary {
    /**
     * Returns the known state of the object
     *
     * @return The state
     */
    TrinaryState getState();

    /**
     * Sets the state of the object
     *
     * @param in The state
     */
    void setState(TrinaryState in);
}
