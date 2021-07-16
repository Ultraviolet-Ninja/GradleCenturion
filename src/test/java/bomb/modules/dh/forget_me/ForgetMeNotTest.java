package bomb.modules.dh.forget_me;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.Indicator;
import bomb.enumerations.Port;
import bomb.enumerations.TrinaryState;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ForgetMeNotTest {
    private static final int NUMBER_OF_MODULES = 20;

    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
        ForgetMeNot.reset();
    }

    @DataProvider
    public Object[][] exceptionProvider(){
        ConditionSetter blank = () -> {};
        ConditionSetter activatedForgetMeNot = () -> Widget.setForgetMeNot(true);
        ConditionSetter triggerNoModule = () -> {
            Widget.setForgetMeNot(true);
            Widget.setSerialCode("12sk23");
        };
        return new Object[][]{
                {blank}, {activatedForgetMeNot}, {triggerNoModule}
        };
    }

    @Test(dataProvider = "exceptionProvider", expectedExceptions =
            {IllegalStateException.class, IllegalArgumentException.class})
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
            Widget.setNumModules(NUMBER_OF_MODULES);
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
        assertEquals(ForgetMeNot.stringifyFinalCode(), expected);
    }

    @Test(dataProvider = "trainingVideoProvider")
    public void getStageTest(ConditionSetter setup, int[] input, String expected){
        setup.setCondition();
        for (int number : input)
            ForgetMeNot.add(number);
        assertEquals(ForgetMeNot.getStage(), expected.replace("-", "").length());
    }

    @DataProvider
    public Object[][] createFirstNumberBranchProvider(){
        String serialCode = "12jaw3";
        ConditionSetter setupCarIndicatorUnlit = () -> {
            Widget.setForgetMeNot(true);
            Widget.setSerialCode(serialCode);
            Widget.setNumModules(NUMBER_OF_MODULES);
            Widget.setIndicator(TrinaryState.OFF, Indicator.CAR);
        };
        ConditionSetter setupSigIndicatorUnlit = () -> {
            Widget.setForgetMeNot(true);
            Widget.setSerialCode(serialCode);
            Widget.setNumModules(NUMBER_OF_MODULES);
            Widget.setIndicator(TrinaryState.OFF, Indicator.SIG);
        };
        ConditionSetter setupNoIndicators = () -> {
            Widget.setForgetMeNot(true);
            Widget.setSerialCode(serialCode);
            Widget.setNumModules(NUMBER_OF_MODULES);
        };

        return new Object[][]{
                {setupCarIndicatorUnlit, 0, "2"}, {setupSigIndicatorUnlit, 0, "7"}, {setupNoIndicators, 0, "0"}
        };
    }

    @Test(dataProvider = "createFirstNumberBranchProvider")
    public void createFirstNumberBranchTest(ConditionSetter setup, int input, String expected){
        setup.setCondition();
        ForgetMeNot.add(input);
        assertEquals(ForgetMeNot.stringifyFinalCode(), expected);
    }

    @DataProvider
    public Object[][] createSecondNumberBranchProvider(){
        String serialCode = "00iu00";
        ConditionSetter setupFirstBranch = () -> {
            Widget.setForgetMeNot(true);
            Widget.setSerialCode(serialCode);
            Widget.setNumModules(NUMBER_OF_MODULES);
            Widget.setPortValue(Port.SERIAL, 1);
        };
        ConditionSetter setupSecondBranch = () -> {
            Widget.setForgetMeNot(true);
            Widget.setSerialCode(serialCode);
            Widget.setNumModules(NUMBER_OF_MODULES);
        };

        return new Object[][]{
                {setupFirstBranch, new int[]{0, 0}, "00"}, {setupSecondBranch, new int[]{0, 0}, "01"}
        };
    }

    @Test(dataProvider = "createSecondNumberBranchProvider")
    public void createSecondNumberTest(ConditionSetter setup, int[] inputs, String expected){
        setup.setCondition();
        for (int input : inputs)
            ForgetMeNot.add(input);
        assertEquals(ForgetMeNot.stringifyFinalCode(), expected);
    }

    @Test
    public void undoLastStageTest(){
        Widget.setForgetMeNot(true);
        Widget.setSerialCode("12lak2");
        Widget.setNumModules(NUMBER_OF_MODULES);

        ForgetMeNot.undoLastStage();
        assertEquals(ForgetMeNot.getStage(), 0);

        ForgetMeNot.add(1);
        assertEquals(ForgetMeNot.getStage(), 1);

        ForgetMeNot.undoLastStage();
        assertEquals(ForgetMeNot.getStage(), 0);
    }

    @AfterClass
    public void tearDown(){
        Widget.resetProperties();
    }
}