package bomb.interfaces;

import bomb.enumerations.TriState;

/**
 * This interface deals with any enum that has 3 states: On, Off or Unknown
 */
public interface Ternary {
    /**
     * Returns the known state of the object
     *
     * @return The state
     */
    TriState getProp();

    /**
     * Sets the state of the object
     *
     * @param in The state
     */
    void setProp(TriState in);
}
