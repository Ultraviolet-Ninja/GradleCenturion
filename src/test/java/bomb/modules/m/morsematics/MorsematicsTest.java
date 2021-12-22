package bomb.modules.m.morsematics;

import bomb.ConditionSetter;
import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

import static bomb.ConditionSetter.EMPTY_SETTER;
import static bomb.enumerations.Indicator.NSA;
import static bomb.enumerations.Indicator.SIG;
import static bomb.enumerations.Port.DVI;
import static bomb.enumerations.Port.PS2;
import static bomb.enumerations.Port.RCA;
import static bomb.enumerations.Port.RJ45;
import static bomb.enumerations.Port.SERIAL;
import static bomb.enumerations.TrinarySwitch.ON;
import static org.testng.Assert.assertEquals;

public class MorsematicsTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionTestProvider() {
        ConditionSetter setSerialCode = () -> Widget.setSerialCode("ac5we0");
        Supplier<LinkedHashSet<String>> nullSet = () -> null;
        Supplier<LinkedHashSet<String>> insufficientSet = () -> {
            LinkedHashSet<String> set = new LinkedHashSet<>();
            set.add("-");
            set.add(".-");
            return set;
        };
        Supplier<LinkedHashSet<String>> invalidCharSet = () -> {
            LinkedHashSet<String> set = new LinkedHashSet<>();
            set.add("-");
            set.add(".-");
            set.add("");
            return set;
        };
        Supplier<LinkedHashSet<String>> duplicateCharSet = () -> {
            LinkedHashSet<String> set = new LinkedHashSet<>();
            set.add("-");
            set.add(".-");
            set.add("A");
            return set;
        };

        return new Object[][] {
                {EMPTY_SETTER, nullSet}, {setSerialCode, insufficientSet},
                {setSerialCode, invalidCharSet}, {setSerialCode, duplicateCharSet}
        };
    }

    @Test(dataProvider = "exceptionTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(ConditionSetter setSerialCode, Supplier<LinkedHashSet<String>> setSupplier) {
        setSerialCode.setCondition();

        Morsematics.solve(setSupplier.get());
    }

    @DataProvider
    public Object[][] videoTestProvider() {
        ConditionSetter bombOneEdgeWork = () -> {
            Widget.setSerialCode("AN8JG5");
            Widget.setDoubleAs(4);
            Widget.setNumHolders(2);
            Widget.setIndicator(ON, NSA);
            Widget.setPortValue(RJ45, 1);
            Widget.setPortValue(DVI, 1);
            Widget.setNumberOfPlates(1);
        };

        Supplier<LinkedHashSet<String>> bombOneSet = () -> {
            LinkedHashSet<String> set = new LinkedHashSet<>();
            set.add("-");
            set.add("W");
            set.add("m");
            return set;
        };

        ConditionSetter bombTwoEdgeWork = () -> {
            Widget.setSerialCode("DU5VM4");
            Widget.setDoubleAs(2);
            Widget.setDBatteries(1);
            Widget.setNumHolders(2);
            Widget.setIndicator(ON, SIG);
            Widget.setPortValue(SERIAL, 1);
            Widget.setPortValue(RJ45, 1);
            Widget.setPortValue(PS2, 1);
            Widget.setPortValue(RCA, 1);
            Widget.setNumberOfPlates(2);
        };

        Supplier<LinkedHashSet<String>> bombTwoSet = () -> {
            LinkedHashSet<String> set = new LinkedHashSet<>();
            set.add("--..");
            set.add("T");
            set.add("k");
            return set;
        };

        return new Object[][] {
                {bombOneEdgeWork, bombOneSet, ".-.."}, {bombTwoEdgeWork, bombTwoSet, "-..."}
        };
    }

    @Test(dataProvider = "videoTestProvider")
    public void videoTest(ConditionSetter bombConditions, Supplier<LinkedHashSet<String>> bombInputSet,
                          String expectedResult) {
        bombConditions.setCondition();

        assertEquals(Morsematics.solve(bombInputSet.get()), expectedResult);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
