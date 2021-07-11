package bomb.modules.dh.forget_me;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinaryState;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ForgetMeNotTest {
    @BeforeTest
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionProvider(){
        ConditionSetter blank = () -> {};
        ConditionSetter activatedForgetMeNot = () -> Widget.setForgetMeNot(true);
        return new Object[][]{
                {blank}, {activatedForgetMeNot}
        };
    }

    @Test(dataProvider = "exceptionProvider", expectedExceptions = {IllegalStateException.class})
    public void exceptionTest(ConditionSetter setup){
        setup.setCondition();
        ForgetMeNot.add(1);
    }

    @DataProvider
    public Object[][] trainingVideoProvider(){
        ConditionSetter widgetSetup = () -> {
            Widget.setForgetMeNot(true);
            Widget.setSerialCode("QS1LN4");
            Widget.setDoubleAs(4);
            Widget.setNumHolders(2);
            Widget.setNumberOfPlates(1);
            Widget.setIndicator(TrinaryState.ON, Indicator.TRN);
            Widget.setIndicator(TrinaryState.OFF, Indicator.SIG);
            Widget.setPortValue(Port.DVI, 1);
            Widget.setNumModules(11);
            Widget.setStartTime(20);
        };
        return new Object[][]{
                {widgetSetup, new int[]{9, 7, 7, 0, 5, 3, 9, 3}, "398-148-07"}
        };
    }

    @Test(dataProvider = "trainingVideoProvider")
    public void trainingVideo(ConditionSetter setup, int[] input, String expected){
        setup.setCondition();
        for (int number : input)
            ForgetMeNot.add(number);
        assertEquals(expected, ForgetMeNot.stringifyFinalCode());
    }

    @AfterClass
    public void tearDown(){
        Widget.resetProperties();
    }
}
