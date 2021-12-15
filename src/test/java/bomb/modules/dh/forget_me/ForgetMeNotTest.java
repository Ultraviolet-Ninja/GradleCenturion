package bomb.modules.dh.forget_me;

import bomb.ConditionSetter;
import bomb.Widget;
import bomb.enumerations.Port;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.ConditionSetter.EMPTY_SETTER;
import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.SIG;
import static bomb.enumerations.Indicator.TRN;
import static bomb.enumerations.Port.DVI;
import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.enumerations.TrinarySwitch.ON;
import static org.testng.Assert.assertEquals;

public class ForgetMeNotTest {
    private static final int NUMBER_OF_MODULES = 20;

    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
        ForgetMeNot.reset();
    }

    @DataProvider
    public Object[][] exceptionTestProvider() {
        ConditionSetter activatedForgetMeNot = () -> Widget.setIsForgetMeNotActive(true);
        ConditionSetter triggerNoModule = () -> {
            Widget.setIsForgetMeNotActive(true);
            Widget.setSerialCode("12sk23");
        };
        return new Object[][]{
                {EMPTY_SETTER}, {activatedForgetMeNot}, {triggerNoModule}
        };
    }

    @Test(dataProvider = "exceptionTestProvider", expectedExceptions =
            {IllegalStateException.class, IllegalArgumentException.class})
    public void exceptionTest(ConditionSetter setup) {
        setup.setCondition();
        ForgetMeNot.add(1);
    }

    @DataProvider
    public Object[][] trainingVideoTestProvider() {
        ConditionSetter widgetSetup = () -> {
            Widget.setIsForgetMeNotActive(true);
            Widget.setSerialCode("QS1LN4");
            Widget.setDoubleAs(4);
            Widget.setNumHolders(2);
            Widget.setNumberOfPlates(1);
            Widget.setIndicator(ON, TRN);
            Widget.setIndicator(OFF, SIG);
            Widget.setPortValue(DVI, 1);
            Widget.setNumModules(NUMBER_OF_MODULES);
            Widget.setStartTime(20);
        };
        return new Object[][]{
                {widgetSetup, new int[]{9, 7, 7, 0, 5, 3, 9, 3}, "398-148-07"}
        };
    }

    @Test(dataProvider = "trainingVideoTestProvider")
    public void trainingVideo(ConditionSetter setup, int[] input, String expected) {
        setup.setCondition();
        for (int number : input)
            ForgetMeNot.add(number);
        assertEquals(ForgetMeNot.stringifyFinalCode(), expected);
    }

    @Test(dataProvider = "trainingVideoTestProvider")
    public void getStageTest(ConditionSetter setup, int[] input, String expected) {
        setup.setCondition();
        for (int number : input)
            ForgetMeNot.add(number);
        assertEquals(ForgetMeNot.getStage(), expected.replace("-", "").length());
    }

    @DataProvider
    public Object[][] createFirstNumberBranchTestProvider() {
        String serialCode = "12jaw3";
        ConditionSetter setupCarIndicatorUnlit = () -> {
            Widget.setIsForgetMeNotActive(true);
            Widget.setSerialCode(serialCode);
            Widget.setNumModules(NUMBER_OF_MODULES);
            Widget.setIndicator(OFF, CAR);
        };
        ConditionSetter setupSigIndicatorUnlit = () -> {
            Widget.setIsForgetMeNotActive(true);
            Widget.setSerialCode(serialCode);
            Widget.setNumModules(NUMBER_OF_MODULES);
            Widget.setIndicator(OFF, SIG);
        };
        ConditionSetter setupNoIndicators = () -> {
            Widget.setIsForgetMeNotActive(true);
            Widget.setSerialCode(serialCode);
            Widget.setNumModules(NUMBER_OF_MODULES);
        };

        return new Object[][]{
                {setupCarIndicatorUnlit, 0, "2"}, {setupSigIndicatorUnlit, 0, "7"},
                {setupNoIndicators, 0, "0"}
        };
    }

    @Test(dataProvider = "createFirstNumberBranchTestProvider")
    public void createFirstNumberBranchTest(ConditionSetter setup, int input, String expected) {
        setup.setCondition();
        ForgetMeNot.add(input);
        assertEquals(ForgetMeNot.stringifyFinalCode(), expected);
    }

    @DataProvider
    public Object[][] createSecondNumberBranchTestProvider() {
        String serialCode = "00iu00";
        ConditionSetter setupFirstBranch = () -> {
            Widget.setIsForgetMeNotActive(true);
            Widget.setSerialCode(serialCode);
            Widget.setNumModules(NUMBER_OF_MODULES);
            Widget.setPortValue(Port.SERIAL, 1);
        };
        ConditionSetter setupSecondBranch = () -> {
            Widget.setIsForgetMeNotActive(true);
            Widget.setSerialCode(serialCode);
            Widget.setNumModules(NUMBER_OF_MODULES);
        };

        return new Object[][]{
                {setupFirstBranch, new int[]{0, 0}, "00"}, {setupSecondBranch, new int[]{0, 0}, "01"}
        };
    }

    @Test(dataProvider = "createSecondNumberBranchTestProvider")
    public void createSecondNumberTest(ConditionSetter setup, int[] inputs, String expected) {
        setup.setCondition();
        for (int input : inputs)
            ForgetMeNot.add(input);
        assertEquals(ForgetMeNot.stringifyFinalCode(), expected);
    }

    @Test
    public void undoLastStageTest() {
        Widget.setIsForgetMeNotActive(true);
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
    public void tearDown() {
        Widget.resetProperties();
    }
}
