package bomb.modules.ab.battleship;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinarySwitch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static bomb.modules.ab.battleship.Tile.CLEAR;
import static bomb.modules.ab.battleship.Tile.RADAR;
import static bomb.modules.ab.battleship.Tile.SHIP;
import static bomb.modules.ab.battleship.Tile.UNKNOWN;
import static org.testng.Assert.assertEquals;

public class BattleshipTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
        Battleship.reset();
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Serial Code is required")
    public void calculateRadarPositionsExceptionTest() {
        Battleship.calculateRadarPositions();
    }

    @DataProvider
    public Object[][] confirmRadarSpotsExceptionTestProvider() {
        ConditionSetter empty = () -> {};
        ConditionSetter setSerialCode = () -> Widget.setSerialCode("e60xa6");

        return new Object[][]{
                {empty, new Tile[]{CLEAR}},
                {setSerialCode, new Tile[]{CLEAR}},
                {setSerialCode, new Tile[]{CLEAR, CLEAR, CLEAR}}
        };
    }

    @Test(dataProvider = "confirmRadarSpotsExceptionTestProvider",
            expectedExceptions = IllegalArgumentException.class)
    public void confirmRadarSpotsExceptionTest(ConditionSetter setter, Tile[] inputTiles) {
        setter.setCondition();

        Battleship.confirmRadarSpots(inputTiles);
    }

    /** Impractical set up one data provider for all 6 six branches */

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Please use the Radar first")
    public void solveOceanFirstBranchExceptionTest() {
        Battleship.solveOcean();
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Board still contains Radar spots")
    public void solveOceanSecondBranchExceptionTest() {
        setVideoEdgework();
        Battleship.calculateRadarPositions();

        Battleship.solveOcean();
    }

    @DataProvider
    public Object[][] solveOceanThirdBranchExceptionTestProvider() {
        return new Object[][]{
                {new Tile[]{CLEAR, CLEAR, SHIP}}
        };
    }

    @Test(dataProvider = "solveOceanThirdBranchExceptionTestProvider",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Initial rows and columns need to be set")
    public void solveOceanThirdBranchExceptionTest(Tile[] confirmedRadarSpots) {
        setVideoEdgework();
        Battleship.calculateRadarPositions();
        Battleship.confirmRadarSpots(confirmedRadarSpots);

        Battleship.solveOcean();
    }

    @DataProvider
    public Object[][] solveOceanFourthBranchExceptionTestProvider() {
        return new Object[][]{
                {new int[] {1,2,3,0,3}, new Tile[]{CLEAR, CLEAR, SHIP}}
        };
    }

    @Test(dataProvider = "solveOceanFourthBranchExceptionTestProvider",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "The number of Ships has not been set")
    public void solveOceanFourthBranchExceptionTest(int[] validDummyCounter, Tile[] confirmedRadarSpots) {
        setVideoEdgework();
        Battleship.calculateRadarPositions();
        Battleship.setColumnCounters(validDummyCounter);
        Battleship.setRowCounters(validDummyCounter);
        Battleship.confirmRadarSpots(confirmedRadarSpots);

        Battleship.solveOcean();
    }

    @DataProvider
    public Object[][] solveOceanFifthBranchExceptionTestProvider() {
        return new Object[][]{
                {new int[] {1,2,3,0,3}, new int[] {3,1,2,1,3}, new int[]{1,1,1,1}, new Tile[]{CLEAR, CLEAR, SHIP}}
        };
    }

    @Test(dataProvider = "solveOceanFifthBranchExceptionTestProvider",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Values don't match\\..*")
    public void solveOceanFifthBranchExceptionTest(int[] rowCounters, int[] columnCounters, int[] shipQuantities,
                                                   Tile[] confirmedRadarSpots) {
        setShipQuantities(shipQuantities);
        setVideoEdgework();
        Battleship.calculateRadarPositions();
        Battleship.setColumnCounters(rowCounters);
        Battleship.setRowCounters(columnCounters);
        Battleship.confirmRadarSpots(confirmedRadarSpots);

        Battleship.solveOcean();
    }

    @Test
    public void videoTestCalculateValidRadarPositionsTest() {
        setVideoEdgework();
        Tile[][] expectedOcean = new Tile[][]{
                {RADAR, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN},
                {UNKNOWN, RADAR, UNKNOWN, UNKNOWN, UNKNOWN},
                {UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN},
                {UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN},
                {UNKNOWN, RADAR, UNKNOWN, UNKNOWN, UNKNOWN}
        };

        Set<String> radarPositions = Battleship.calculateRadarPositions();

        assertEquals(Battleship.getOcean(), new Ocean(expectedOcean));
        assertEquals(radarPositions, new TreeSet<>(Arrays.asList("a1", "b2", "b5")));
    }

    @DataProvider
    public Object[][] videoOceanSolverTestProvider() {
        return new Object[][]{
                {
                    new Tile[]{CLEAR, CLEAR, SHIP}, new int[]{1,2,3,0,4}, new int[]{3,1,2,1,3},
                        new int[]{1,1,1,1},
                        new Tile[][]{
                                {CLEAR, CLEAR, CLEAR, CLEAR, SHIP},
                                {SHIP, CLEAR, CLEAR, CLEAR, SHIP},
                                {SHIP, CLEAR, SHIP, CLEAR, SHIP},
                                {CLEAR, CLEAR, CLEAR, CLEAR, CLEAR},
                                {SHIP, SHIP, SHIP, SHIP, CLEAR}
                        }
                }
        };
    }

    @Test(enabled = true, dataProvider = "videoOceanSolverTestProvider")
    public void videoOceanSolverTest(Tile[] confirmedRadarSpots, int[] rowCounters,
                                     int[] columnCounters, int[] shipQuantities, Tile[][] expected) {
        setVideoEdgework();
        setShipQuantities(shipQuantities);
        Battleship.setRowCounters(rowCounters);
        Battleship.setColumnCounters(columnCounters);

        Battleship.calculateRadarPositions();
        Battleship.confirmRadarSpots(confirmedRadarSpots);

        Ocean ocean = Battleship.solveOcean();

        assertEquals(ocean, new Ocean(expected));
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
        Battleship.reset();
    }

    private static void setVideoEdgework() {
        Widget.setSerialCode("ZB6HA2");
        Widget.setDBatteries(2);
        Widget.setDoubleAs(2);
        Widget.setNumHolders(3);
        Widget.setIndicator(TrinarySwitch.ON, Indicator.FRQ);
        Widget.setNumberOfPlates(1);
        Widget.setPortValue(Port.PARALLEL, 1);
        Widget.setPortValue(Port.SERIAL, 1);
    }

    private static void setShipQuantities(int[] shipQuantities) {
        Ship[] ships = Ship.values();
        for (int i = 0; i< shipQuantities.length; i++) {
            ships[i].setCurrentQuantity((byte) shipQuantities[i]);
        }
    }
}
