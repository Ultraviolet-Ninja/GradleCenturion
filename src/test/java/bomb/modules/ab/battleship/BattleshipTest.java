package bomb.modules.ab.battleship;

import bomb.ConditionSetter;
import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.ab.battleship.Tile.CLEAR;

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

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
