package bomb.modules.ab.battleship;

import bomb.ConditionSetter;
import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static bomb.enumerations.Indicator.BOB;
import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.FRQ;
import static bomb.enumerations.Indicator.SIG;
import static bomb.enumerations.Port.PARALLEL;
import static bomb.enumerations.Port.SERIAL;
import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.enumerations.TrinarySwitch.ON;
import static bomb.modules.ab.battleship.Tile.CLEAR;
import static bomb.modules.ab.battleship.Tile.RADAR;
import static bomb.modules.ab.battleship.Tile.SHIP;
import static bomb.modules.ab.battleship.Tile.UNKNOWN;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BattleshipValidationTest {
    private static final ConditionSetter FIRST_BOMB_CONDITIONS =
            BattleshipValidationTest::setVideoEdgeworkVersionOne;
    private static final ConditionSetter SECOND_BOMB_CONDITIONS =
            BattleshipValidationTest::setVideoEdgeworkVersionTwo;
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
        Battleship.reset();
    }

    @DataProvider
    public Object[][] videoTestCalculateValidRadarPositionsTestProvider() {
        return new Object[][] {
                {
                    FIRST_BOMB_CONDITIONS,
                        new Tile[][]{
                        {RADAR,   UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN},
                        {UNKNOWN, RADAR,   UNKNOWN, UNKNOWN, UNKNOWN},
                        {UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN},
                        {UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN},
                        {UNKNOWN, RADAR,   UNKNOWN, UNKNOWN, UNKNOWN}}
                }
        };
    }

    @Test(dataProvider = "videoTestCalculateValidRadarPositionsTestProvider")
    public void videoTestCalculateValidRadarPositionsTest(ConditionSetter setter,
                                                          Tile[][] expectedOcean) {
        setter.setCondition();

        Set<String> radarPositions = Battleship.calculateRadarPositions();

        assertEquals(Battleship.getOcean(), new Ocean(expectedOcean));
        assertEquals(radarPositions, new TreeSet<>(Arrays.asList("a1", "b2", "b5")));
    }

    @DataProvider
    public Object[][] videoOceanSolverTestProvider() {
        /*
         * Each test conforms to the following format
         * The current bomb edgework
         * The Tiles of the radar locations
         * The row counters (The vertical values)
         * The column counter (Horizontal values)
         * The ship counts on the current board starting with the largest ship
         * The expected tile arrangement of the output board
         */
        return new Object[][]{
                {
                        FIRST_BOMB_CONDITIONS,
                        new Tile[]{CLEAR, CLEAR, SHIP}, new int[]{1, 2, 3, 0, 4},
                        new int[]{3, 1, 2, 1, 3}, new int[]{1, 1, 1, 1},
                        new Tile[][]{
                                {CLEAR, CLEAR,  CLEAR,  CLEAR,  SHIP},
                                {SHIP,  CLEAR,  CLEAR,  CLEAR,  SHIP},
                                {SHIP,  CLEAR,  SHIP,   CLEAR,  SHIP},
                                {CLEAR, CLEAR,  CLEAR,  CLEAR,  CLEAR},
                                {SHIP,  SHIP,   SHIP,   SHIP,   CLEAR}
                        }
                },
                {
                        FIRST_BOMB_CONDITIONS,
                        new Tile[]{SHIP, CLEAR, SHIP}, new int[]{2, 1, 2, 1, 1},
                        new int[]{2, 2, 0, 3, 0}, new int[]{0, 1, 1, 2},
                        new Tile[][]{
                                {SHIP,  SHIP,  CLEAR, CLEAR, CLEAR},
                                {CLEAR, CLEAR, CLEAR, SHIP,  CLEAR},
                                {SHIP,  CLEAR, CLEAR, SHIP,  CLEAR},
                                {CLEAR, CLEAR, CLEAR, SHIP,  CLEAR},
                                {CLEAR, SHIP,  CLEAR, CLEAR, CLEAR}
                        }
                },
                {
                        FIRST_BOMB_CONDITIONS,
                        new Tile[]{SHIP, CLEAR, SHIP}, new int[]{4, 0, 3, 1, 2},
                        new int[]{3, 3, 1, 1, 2}, new int[]{1, 0, 3, 0},
                        new Tile[][]{
                                {SHIP,  SHIP,  SHIP,  SHIP,  CLEAR},
                                {CLEAR, CLEAR, CLEAR, CLEAR, CLEAR},
                                {SHIP,  SHIP,  CLEAR, CLEAR, SHIP},
                                {CLEAR, CLEAR, CLEAR, CLEAR, SHIP},
                                {SHIP,  SHIP,  CLEAR, CLEAR, CLEAR}
                        }
                },
                {
                        FIRST_BOMB_CONDITIONS,
                        new Tile[]{CLEAR, CLEAR, CLEAR}, new int[]{2, 1, 2, 1, 2},
                        new int[]{3, 0, 3, 2, 0}, new int[]{0, 1, 2, 1},
                        new Tile[][]{
                                {CLEAR, CLEAR, SHIP,  SHIP,  CLEAR},
                                {SHIP,  CLEAR, CLEAR, CLEAR, CLEAR},
                                {SHIP,  CLEAR, SHIP,  CLEAR, CLEAR},
                                {SHIP,  CLEAR, CLEAR, CLEAR, CLEAR},
                                {CLEAR, CLEAR, SHIP,  SHIP,  CLEAR}
                        }
                }
        };
    }

    @Test(dataProvider = "videoOceanSolverTestProvider")
    public void videoOceanSolverTest(ConditionSetter setter, Tile[] confirmedRadarSpots, int[] rowCounters,
                                     int[] columnCounters, int[] shipQuantities, Tile[][] expected) {
        setter.setCondition();
        setShipQuantities(shipQuantities);
        Battleship.setRowCounters(rowCounters);
        Battleship.setColumnCounters(columnCounters);

        Battleship.calculateRadarPositions();
        Battleship.confirmRadarSpots(confirmedRadarSpots);

        Set<Ocean> possibleOceans = Battleship.solveOcean();

        assertEquals(possibleOceans.size(), 1);
        assertTrue(possibleOceans.contains(new Ocean(expected)));
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
        Battleship.reset();
    }

    private static void setVideoEdgeworkVersionOne() {
        Widget.setSerialCode("ZB6HA2");
        Widget.setDBatteries(2);
        Widget.setDoubleAs(2);
        Widget.setNumHolders(3);
        Widget.setIndicator(ON, FRQ);
        Widget.setNumberOfPlates(1);
        Widget.setPortValue(PARALLEL, 1);
        Widget.setPortValue(SERIAL, 1);
    }

    private static void setVideoEdgeworkVersionTwo() {
        Widget.setSerialCode("9X3VS6");
        Widget.setIndicator(OFF, BOB);
        Widget.setIndicator(OFF, FRQ);
        Widget.setIndicator(OFF, CAR);
        Widget.setIndicator(ON, SIG);
        Widget.setDoubleAs(2);
        Widget.setNumHolders(1);
    }

    private static void setShipQuantities(int[] shipQuantities) {
        Ship[] ships = Ship.SHIPS;
        for (int i = 0; i < shipQuantities.length; i++) {
            ships[i].setCurrentQuantity((byte) shipQuantities[i]);
        }
    }
}
