package bomb.modules.il.logic;

import bomb.Widget;
import bomb.tools.logic.LogicOperator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static bomb.enumerations.Indicator.CAR;
import static bomb.enumerations.Indicator.FRK;
import static bomb.enumerations.Indicator.NSA;
import static bomb.enumerations.Port.DVI;
import static bomb.enumerations.Port.PS2;
import static bomb.enumerations.Port.RJ45;
import static bomb.enumerations.TrinarySwitch.OFF;
import static bomb.enumerations.TrinarySwitch.ON;
import static bomb.modules.il.logic.LogicLetter.A;
import static bomb.modules.il.logic.LogicLetter.B;
import static bomb.modules.il.logic.LogicLetter.C;
import static bomb.modules.il.logic.LogicLetter.D;
import static bomb.modules.il.logic.LogicLetter.E;
import static bomb.modules.il.logic.LogicLetter.G;
import static bomb.modules.il.logic.LogicLetter.H;
import static bomb.modules.il.logic.LogicLetter.I;
import static bomb.modules.il.logic.LogicLetter.L;
import static bomb.modules.il.logic.LogicLetter.N;
import static bomb.modules.il.logic.LogicLetter.O;
import static bomb.modules.il.logic.LogicLetter.P;
import static bomb.modules.il.logic.LogicLetter.T;
import static bomb.modules.il.logic.LogicLetter.U;
import static bomb.tools.logic.LogicOperator.AND;
import static bomb.tools.logic.LogicOperator.IMPLIED_BY;
import static bomb.tools.logic.LogicOperator.IMPLIES;
import static bomb.tools.logic.LogicOperator.OR;
import static bomb.tools.logic.LogicOperator.XNOR;
import static bomb.tools.logic.LogicOperator.XOR;
import static org.testng.Assert.assertEquals;

public class LogicTest {
    @BeforeMethod
    public void setUp() {
        Widget.resetProperties();
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Serial Code is required\n.*")
    public void serialCodeExceptionTest() {
        Logic.solve(new LetterRecord[]{}, new LogicOperator[]{}, false);
    }

    @DataProvider
    public Object[][] exceptionTestProvider() {
        LetterRecord a = recordOf(false, A);
        LetterRecord b = recordOf(false, B);
        LetterRecord c = recordOf(true, C);
        LetterRecord d = recordOf(true, D);

        return new Object[][] {
                {new LetterRecord[]{a},             new LogicOperator[]{XOR}},
                {new LetterRecord[]{a, b, c, d},    new LogicOperator[]{XOR}},
                {new LetterRecord[]{a, b, c},       new LogicOperator[]{XOR}},
                {new LetterRecord[]{a, b, c},       new LogicOperator[]{XOR, AND, AND}}
        };
    }

    @Test(dataProvider = "exceptionTestProvider", expectedExceptions = IllegalArgumentException.class)
    public void exceptionTest(LetterRecord[] letters, LogicOperator[] operators) {
        Widget.setSerialCode("VX1GJ6");

        Logic.solve(letters, operators, false);
    }

    @DataProvider
    public Object[][] videoTestProvider() {
        return new Object[][] {
                {new LetterRecord[]{recordOf(false, G), recordOf(true, E), recordOf(true, G)},
                        new LogicOperator[]{XNOR, XOR}, false, true},

                {new LetterRecord[]{recordOf(false, O), recordOf(true, C), recordOf(false, C)},
                        new LogicOperator[]{OR, XNOR}, true, false},

                {new LetterRecord[]{recordOf(true, I), recordOf(false, U), recordOf(false, I)},
                        new LogicOperator[]{XNOR, IMPLIES}, false, true},

                {new LetterRecord[]{recordOf(false, T), recordOf(true, L), recordOf(true, A)},
                        new LogicOperator[]{AND, AND}, false, false}
        };
    }

    @Test(dataProvider = "videoTestProvider")
    public void videoTest(LetterRecord[] letters, LogicOperator[] operators,
                          boolean priorityToggle, boolean expected) {
        firstEdgeWorkSetup();

        boolean actual = Logic.solve(letters, operators, priorityToggle);

        assertEquals(actual, expected);
    }

    private static void firstEdgeWorkSetup() {
        Widget.setDBatteries(2);
        Widget.setDoubleAs(2);
        Widget.setNumHolders(3);
        Widget.setPortValue(DVI, 3);
        Widget.setPortValue(PS2, 2);
        Widget.setPortValue(RJ45, 2);
        Widget.setNumberOfPlates(4);
        Widget.setSerialCode("VX1GJ6");
    }

    @DataProvider
    public Object[][] videoSecondTestProvider() {
        return new Object[][] {
                {new LetterRecord[]{recordOf(true, I), recordOf(false, P), recordOf(false, T)},
                        new LogicOperator[]{AND, IMPLIED_BY}, false, false},

                {new LetterRecord[]{recordOf(true, N), recordOf(false, B), recordOf(true, D)},
                        new LogicOperator[]{IMPLIED_BY, IMPLIED_BY}, false, false},
//                {new LetterRecord[]{}, new LogicOperator[]{}, , },
//                {new LetterRecord[]{}, new LogicOperator[]{}, , },
//                {new LetterRecord[]{}, new LogicOperator[]{}, , },
//                {new LetterRecord[]{}, new LogicOperator[]{}, , },
//                {new LetterRecord[]{}, new LogicOperator[]{}, , },
//                {new LetterRecord[]{}, new LogicOperator[]{}, , },
//                {new LetterRecord[]{}, new LogicOperator[]{}, , },
//                {new LetterRecord[]{}, new LogicOperator[]{}, , },
//                {new LetterRecord[]{}, new LogicOperator[]{}, , },
//                {new LetterRecord[]{}, new LogicOperator[]{}, , },
//                {new LetterRecord[]{}, new LogicOperator[]{}, , }
        };
    }

    @Test(enabled = false, dataProvider = "videoSecondTestProvider")
    public void videoSecondTest(LetterRecord[] letters, LogicOperator[] operators,
                          boolean priorityToggle, boolean expected) {
        secondEdgeWorkSetup();

        boolean actual = Logic.solve(letters, operators, priorityToggle);

        assertEquals(actual, expected);
    }

    private static void secondEdgeWorkSetup() {
        Widget.setDBatteries(3);
        Widget.setDoubleAs(2);
        Widget.setNumHolders(4);
        Widget.setSerialCode("AD4MB7");
        Widget.setIndicator(ON, FRK);
        Widget.setIndicator(OFF, CAR);
        Widget.setIndicator(OFF, NSA);
    }

    private static LetterRecord recordOf(boolean isNegated, LogicLetter letter) {
        return new LetterRecord(isNegated, letter);
    }

    @AfterClass
    public void tearDown() {
        Widget.resetProperties();
    }
}
