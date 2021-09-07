package bomb.modules.m.microcontroller;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinarySwitch;
import bomb.modules.m.microcontroller.chip.AbstractController;
import bomb.modules.m.microcontroller.chip.StrikeController;
import javafx.scene.paint.Color;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.MAGENTA;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;
import static javafx.scene.paint.Color.YELLOW;
import static org.testng.Assert.assertEquals;

public class MicroControllerTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionProvider() {
        return new Object[][]{
                {"a", "12", null}, {"akw234", "a1", null}, {"akw234", "31", null}
        };
    }

    @Test(dataProvider = "exceptionProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(String serialCode, String moduleSerialNumbers, AbstractController controller) {
        Widget.setSerialCode(serialCode);

        MicroController.getPinColors(moduleSerialNumbers, controller);
    }

    @DataProvider
    public Object[][] validBranchTestProvider() {
        ConditionSetter emptyCondition = () -> {
        };
        ConditionSetter secondBranchCondition = () -> {
            Widget.setIndicator(TrinarySwitch.ON, Indicator.SIG);
            Widget.setPortValue(Port.RJ45, 1);
        };
        ConditionSetter fourthBranchCondition = () -> Widget.setDoubleAs(2);

        String normalSerialCode = "223asd";
        String serialCodeContainsC = "c22dkf";

        String moduleSerialNumber = "22";
        String containsOne = "12";

        return new Object[][]{
                {emptyCondition, normalSerialCode, containsOne, new StrikeController(6),
                        new Color[]{MAGENTA, YELLOW, RED, GREEN, BLUE, WHITE}},
                {secondBranchCondition, normalSerialCode, moduleSerialNumber, new StrikeController(6),
                        new Color[]{RED, YELLOW, BLUE, MAGENTA, GREEN, WHITE}},
                {emptyCondition, serialCodeContainsC, moduleSerialNumber, new StrikeController(6),
                        new Color[]{MAGENTA, RED, YELLOW, GREEN, BLUE, WHITE}},
                {fourthBranchCondition, normalSerialCode, moduleSerialNumber, new StrikeController(6),
                        new Color[]{BLUE, RED, MAGENTA, YELLOW, GREEN, WHITE}},
                {emptyCondition, normalSerialCode, moduleSerialNumber, new StrikeController(6),
                        new Color[]{RED, GREEN, MAGENTA, YELLOW, BLUE, WHITE}}
        };
    }

    @Test(dataProvider = "validBranchTestProvider")
    public void validBranchTest(ConditionSetter testConditions, String serialCode, String moduleSerialNumbers,
                                AbstractController controller, Color[] expectedResults) {
        testConditions.setCondition();
        Widget.setSerialCode(serialCode);
        List<Color> converted = Arrays.asList(expectedResults);

        assertEquals(converted, MicroController.getPinColors(moduleSerialNumbers, controller));
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
