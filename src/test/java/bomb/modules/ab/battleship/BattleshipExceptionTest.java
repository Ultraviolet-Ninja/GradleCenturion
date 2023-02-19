package bomb.modules.ab.battleship;

import bomb.ConditionSetter;
import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.ab.battleship.Tile.CLEAR;
import static bomb.modules.ab.battleship.Tile.SHIP;

public class BattleshipExceptionTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
        Battleship.reset();
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Serial Code is required\nPlease check formatting on Widget page")
    public void calculateRadarPositionsExceptionTest() {
        Battleship.calculateRadarPositions();
    }

    @DataProvider
    public Object[][] confirmRadarSpotsExceptionTestProvider() {
        ConditionSetter empty = () -> {
        };
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

    /**
     * Impractical set up one data provider for all 6 six branches
     */

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Please use the Radar first")
    public void solveOceanFirstBranchExceptionTest() {
        Battleship.solveOcean();
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Board still contains Radar spots")
    public void solveOceanSecondBranchExceptionTest() {
        BattleshipConditionSetter.setVideoEdgeworkVersionOne();
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
        BattleshipConditionSetter.setVideoEdgeworkVersionOne();
        Battleship.calculateRadarPositions();
        Battleship.confirmRadarSpots(confirmedRadarSpots);

        Battleship.solveOcean();
    }

    @DataProvider
    public Object[][] solveOceanFourthBranchExceptionTestProvider() {
        return new Object[][]{
                {new int[]{1, 2, 3, 0, 3}, new Tile[]{CLEAR, CLEAR, SHIP}}
        };
    }

    @Test(dataProvider = "solveOceanFourthBranchExceptionTestProvider",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "The number of Ships has not been set")
    public void solveOceanFourthBranchExceptionTest(int[] validDummyCounter,
                                                    Tile[] confirmedRadarSpots) {
        BattleshipConditionSetter.setVideoEdgeworkVersionOne();
        Battleship.calculateRadarPositions();
        Battleship.setColumnCounters(validDummyCounter);
        Battleship.setRowCounters(validDummyCounter);
        Battleship.confirmRadarSpots(confirmedRadarSpots);

        Battleship.solveOcean();
    }

    @DataProvider
    public Object[][] solveOceanFifthBranchExceptionTestProvider() {
        return new Object[][]{
                {
                    new int[]{1, 2, 3, 0, 3}, new int[]{3, 1, 2, 1, 3},
                        new int[]{1, 1, 1, 1}, new Tile[]{CLEAR, CLEAR, SHIP}
                }
        };
    }

    @Test(dataProvider = "solveOceanFifthBranchExceptionTestProvider",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Values don't match\\..*")
    public void solveOceanFifthBranchExceptionTest(int[] rowCounters, int[] columnCounters,
                                                   int[] shipQuantities, Tile[] confirmedRadarSpots) {
        BattleshipConditionSetter.setVideoEdgeworkVersionOne();
        setShipQuantities(shipQuantities);
        Battleship.calculateRadarPositions();
        Battleship.setColumnCounters(rowCounters);
        Battleship.setRowCounters(columnCounters);
        Battleship.confirmRadarSpots(confirmedRadarSpots);

        Battleship.solveOcean();
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
        Battleship.reset();
    }

    private static void setShipQuantities(int[] shipQuantities) {
        Ship[] ships = Ship.SHIPS;
        for (int i = 0; i < shipQuantities.length; i++) {
            ships[i].setCurrentQuantity((byte) shipQuantities[i]);
        }
    }
}
