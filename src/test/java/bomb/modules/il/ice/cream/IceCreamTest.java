package bomb.modules.il.ice.cream;

import bomb.ConditionSetter;
import bomb.Widget;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Stream;

import static bomb.enumerations.Indicator.CLR;
import static bomb.enumerations.Indicator.FRK;
import static bomb.enumerations.Indicator.FRQ;
import static bomb.enumerations.Indicator.IND;
import static bomb.enumerations.Indicator.MSA;
import static bomb.enumerations.Indicator.SIG;
import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.enumerations.TrinarySwitch.ON;
import static bomb.modules.il.ice.cream.Flavor.COOKIES_N_CREAM;
import static bomb.modules.il.ice.cream.Flavor.DOUBLE_CHOCOLATE;
import static bomb.modules.il.ice.cream.Flavor.DOUBLE_STRAWBERRY;
import static bomb.modules.il.ice.cream.Flavor.MINT_CHIP;
import static bomb.modules.il.ice.cream.Flavor.NEAPOLITAN;
import static bomb.modules.il.ice.cream.Flavor.RASPBERRY_RIPPLE;
import static bomb.modules.il.ice.cream.Flavor.ROCKY_ROAD;
import static bomb.modules.il.ice.cream.Flavor.THE_CLASSIC;
import static bomb.modules.il.ice.cream.Flavor.VANILLA;
import static bomb.modules.il.ice.cream.Person.ASHLEY;
import static bomb.modules.il.ice.cream.Person.CHERYL;
import static bomb.modules.il.ice.cream.Person.DAVE;
import static bomb.modules.il.ice.cream.Person.JESSICA;
import static bomb.modules.il.ice.cream.Person.PAT;
import static bomb.modules.il.ice.cream.Person.SAM;
import static bomb.modules.il.ice.cream.Person.SIMON;
import static bomb.modules.il.ice.cream.Person.TIM;
import static bomb.modules.il.ice.cream.Person.TOM;
import static org.testng.Assert.assertEquals;

public class IceCreamTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @DataProvider
    public Object[][] exceptionTestProvider() {
        final String serialCode = "AD4JK2";
        return new Object[][]{
                {"", EnumSet.of(COOKIES_N_CREAM, RASPBERRY_RIPPLE, DOUBLE_CHOCOLATE, THE_CLASSIC)},
                {serialCode, EnumSet.of(RASPBERRY_RIPPLE, DOUBLE_CHOCOLATE, THE_CLASSIC)},
                {serialCode, EnumSet.of(VANILLA, RASPBERRY_RIPPLE, DOUBLE_CHOCOLATE, THE_CLASSIC)}
        };
    }

    @Test(dataProvider = "exceptionTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(String serialCode, EnumSet<Flavor> possibleFlavors) {
        Widget.setSerialCode(serialCode);

        IceCream.findFlavor(TOM, possibleFlavors, false);
    }

    public Object[][] firstBombTests() {
        ConditionSetter edgeWork = () -> {
            Widget.setSerialCode("3B9FJ6");
            Widget.setDoubleAs(2);
            Widget.setNumHolders(1);
            Widget.setIndicator(ON, IND);
            Widget.setIndicator(ON, FRK);
            Widget.setIndicator(ON, SIG);
        };
        return new Object[][]{
                {
                        edgeWork, EnumSet.of(MINT_CHIP, NEAPOLITAN, ROCKY_ROAD, RASPBERRY_RIPPLE),
                        SAM, false, RASPBERRY_RIPPLE
                },
                {
                        edgeWork, EnumSet.of(ROCKY_ROAD, NEAPOLITAN, RASPBERRY_RIPPLE, MINT_CHIP),
                        SIMON, false, VANILLA
                },
                {
                        edgeWork, EnumSet.of(MINT_CHIP, DOUBLE_CHOCOLATE, COOKIES_N_CREAM, RASPBERRY_RIPPLE),
                        ASHLEY, false, COOKIES_N_CREAM
                }
        };
    }

    public Object[][] secondBombTests() {
        ConditionSetter edgeWork = () -> {
            Widget.setDBatteries(3);
            Widget.setDoubleAs(2);
            Widget.setNumHolders(3);
            Widget.setIndicator(ON, FRQ);
            Widget.setIndicator(OFF, CLR);
            Widget.setSerialCode("343JQ2");
        };

        return new Object[][]{
                {
                        edgeWork, EnumSet.of(DOUBLE_CHOCOLATE, DOUBLE_STRAWBERRY, NEAPOLITAN, ROCKY_ROAD),
                        JESSICA, false, DOUBLE_CHOCOLATE
                },
                {
                        edgeWork, EnumSet.of(NEAPOLITAN, DOUBLE_STRAWBERRY, MINT_CHIP, ROCKY_ROAD),
                        DAVE, false, VANILLA
                }
        };
    }

    public Object[][] thirdBombTests() {
        ConditionSetter edgeWork = () -> {
            Widget.setDBatteries(1);
            Widget.setNumHolders(1);
            Widget.setSerialCode("JE7XR5");
            Widget.setIndicator(ON, MSA);
            Widget.setIndicator(OFF, SIG);
        };

        return new Object[][]{
                {
                        edgeWork, EnumSet.of(ROCKY_ROAD, RASPBERRY_RIPPLE, NEAPOLITAN, COOKIES_N_CREAM),
                        TOM, true, ROCKY_ROAD
                },
                {
                        edgeWork, EnumSet.of(DOUBLE_CHOCOLATE, COOKIES_N_CREAM, DOUBLE_STRAWBERRY, MINT_CHIP),
                        PAT, true, DOUBLE_CHOCOLATE
                },
                {
                        edgeWork, EnumSet.of(NEAPOLITAN, DOUBLE_CHOCOLATE, ROCKY_ROAD, DOUBLE_STRAWBERRY),
                        TIM, true, DOUBLE_CHOCOLATE
                },
                {
                        edgeWork, EnumSet.of(MINT_CHIP, ROCKY_ROAD, DOUBLE_CHOCOLATE, RASPBERRY_RIPPLE),
                        CHERYL, true, DOUBLE_CHOCOLATE
                },
                {
                        edgeWork, EnumSet.of(DOUBLE_CHOCOLATE, DOUBLE_STRAWBERRY, NEAPOLITAN, RASPBERRY_RIPPLE),
                        SAM, true, DOUBLE_CHOCOLATE
                }
        };
    }

    @DataProvider
    public Object[][] videoTestProvider() {
        return Stream.of(firstBombTests(), secondBombTests(), thirdBombTests())
                .flatMap(Arrays::stream)
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "videoTestProvider")
    public void videoTest(ConditionSetter edgeWork, EnumSet<Flavor> possibleFlavors, Person person,
                          boolean hasEmptyPortPlate, Flavor expectedFlavor) {
        edgeWork.setCondition();

        assertEquals(IceCream.findFlavor(person, possibleFlavors, hasEmptyPortPlate), expectedFlavor);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
