package bomb.modules.ab.bitwise;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Ports;
import bomb.enumerations.TriState;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.modules.ab.bitwise.BitwiseOps.AND;
import static bomb.modules.ab.bitwise.BitwiseOps.NOT;
import static bomb.modules.ab.bitwise.BitwiseOps.OR;
import static bomb.modules.ab.bitwise.BitwiseOps.XOR;
import static org.testng.Assert.assertEquals;

public class BitwiseTest {
    private static final int DEFAULT_START_TIME = 5;
    private static final String MIN_AND = "00000001", MIN_OR = "00000011", MIN_XOR = "00000010", MIN_NOT = "11111100",
            MAX_AND = "11111110", MAX_OR = "11111111", MAX_XOR = "00000001", MAX_NOT = "00000000",
            LAST_DIGIT_EVEN = "ask412", LAST_DIGIT_ODD = "wo24l5";

    @BeforeMethod
    public void setUp(){
        Widget.resetProperties();
    }

    @DataProvider
    public Object[] exceptionProvider(){
        return new ConditionSetter[]{
                () -> {},
                () -> Widget.setNumModules(1),
                () -> {
                    Widget.setStartTime(4);
                    Widget.setNumModules(1);
                }
        };
    }

    @Test(dataProvider = "exceptionProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(ConditionSetter setter){
        setter.setCondition();
        Bitwise.getByte(NOT);
    }

    @DataProvider
    public Object[][] minimumConditionProvider(){
        return new Object[][] {
                {MIN_AND, AND}, {MIN_OR, OR}, {MIN_XOR, XOR}, {MIN_NOT, NOT}
        };
    }

    @Test(dataProvider = "minimumConditionProvider")
    public void minimumConditionTest(String expected, BitwiseOps operation){
        setEssentialFalseConditions();
        Widget.setDoubleAs(2);

        assertEquals(Bitwise.getByte(operation), expected);
    }

    @DataProvider
    public Object[][] maximumConditionProvider(){
        return new Object[][] {
                {MAX_AND, AND}, {MAX_OR, OR}, {MAX_XOR, XOR}, {MAX_NOT, NOT}
        };
    }

    @Test(dataProvider = "maximumConditionProvider")
    public void maximumConditionTest(String expected, BitwiseOps operation){
        setEssentialTrueConditions();
        maximizeConditions();

        assertEquals(Bitwise.getByte(operation), expected);
    }

    private void setEssentialTrueConditions(){
        Widget.setSerialCode(LAST_DIGIT_ODD);
        Widget.setStartTime(DEFAULT_START_TIME);
        Widget.setNumModules(6);
    }

    private void setEssentialFalseConditions(){
        Widget.setSerialCode(LAST_DIGIT_EVEN);
        Widget.setStartTime(DEFAULT_START_TIME);
        Widget.setNumModules(1);
    }

    private void maximizeConditions(){
        for (int i = 2; i >= 0; i--) Widget.addPort(Ports.PARALLEL);
        Widget.setIndicator(TriState.ON, Indicator.BOB);
        Widget.setIndicator(TriState.ON, Indicator.NSA);
        Widget.setIndicator(TriState.OFF, Indicator.CLR);
        Widget.setIndicator(TriState.OFF, Indicator.CAR);
        Widget.setDBatteries(1);
        Widget.setNumHolders(2);
    }

    @AfterClass
    public void tearDown(){
        Widget.resetProperties();
    }
}
