package bomb.modules.ab.battleship;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinarySwitch;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static bomb.modules.ab.battleship.Tile.CLEAR;
import static org.testng.Assert.assertEquals;

public class BattleshipTest {
    @BeforeTest
    public void setUp() {
        Widget.resetProperties();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
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

    @Test(dataProvider = "confirmRadarSpotsExceptionTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void confirmRadarSpotsExceptionTest(ConditionSetter setter, Tile[] inputTiles) {
        setter.setCondition();

        Battleship.confirmRadarSpots(inputTiles);
    }

    @Test
    public void videoTestCalculateValidRadarPositionsTest() {
        setVideoEdgework();

        Set<String> radarPositions = Battleship.calculateRadarPositions();

        assertEquals(radarPositions, new TreeSet<>(Arrays.asList("a1", "b2", "b5")));
    }

    @DataProvider
    public Object[][] videoOceanSolverTestProvider() {
        return new Object[][]{

        };
    }

    @Test(enabled = false)
    public void videoOceanSolverTest(Tile[] confirmedRadarSpots, int[] rowCounters,
                                     int[] columnCounters, ConditionSetter shipSetter, Tile[][] expected) {
        shipSetter.setCondition();
        setVideoEdgework();

        Battleship.calculateRadarPositions();
        Battleship.confirmRadarSpots(confirmedRadarSpots);
        Battleship.setRowCounters(rowCounters);
        Battleship.setColumnCounters(columnCounters);

        Ocean ocean = Battleship.solveOcean();
        int actualHashCode = Objects.requireNonNull(ocean).hashCode();
        int expectedHashCode = Arrays.deepHashCode(expected);
        assertEquals(actualHashCode, expectedHashCode);
    }

    private void setVideoEdgework() {
        Widget.setSerialCode("ZB6HA2");
        Widget.setDBatteries(2);
        Widget.setDoubleAs(2);
        Widget.setNumHolders(3);
        Widget.setIndicator(TrinarySwitch.ON, Indicator.FRQ);
        Widget.setNumberOfPlates(1);
        Widget.setPortValue(Port.PARALLEL, 1);
        Widget.setPortValue(Port.SERIAL, 1);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
