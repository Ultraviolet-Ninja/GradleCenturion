package bomb.enumerations;

import bomb.interfaces.Labeled;

/**
 * TriState deals with anything that needs to have 3 states of existence: On, Off or Unknown.
 * This is most prevalent with the Indicators because we need to be able to track ones on the bomb
 * that are lit and unlit.
 */
public enum TriState implements Labeled {
    /**
     * The off state
     */
    OFF("-fx-text-fill: red"),
    /**
     * The on state
     */
    ON("-fx-text-fill: blue"),
    /**
     * The unknown state
     */
    UNKNOWN("-fx-text-fill: black");

    private final String label;

    @Override
    public String getLabel() {
        return label;
    }

    /**
     * TriState constructor
     *
     * @param label The color given to the state for JavaFX purposes
     */
    TriState(String label){
        this.label = label;
    }
}
