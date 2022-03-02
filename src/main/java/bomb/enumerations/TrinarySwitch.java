package bomb.enumerations;

/**
 * TrinarySwitch deals with anything that needs to have 3 states of existence: On, Off or Unknown.
 * This is most prevalent with the Indicators because we need to be able to track ones on the bomb
 * that are lit and unlit.
 */
public enum TrinarySwitch {
    OFF, ON, UNKNOWN
}
