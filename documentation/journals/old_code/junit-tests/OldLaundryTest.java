package bomb.modules.il.laundry;

import bomb.Widget;
import bomb.WidgetSimulations;
import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.enumerations.TriState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OldLaundryTest {
    private static final int EXPECTED_SIZE_FOR_BOB = 6;

    @BeforeEach
    void resetProperties(){
        Widget.resetProperties();
    }

    @Test
    void exceptionTest(){
        assertThrows(IllegalArgumentException.class, () -> Laundry.clean("1", "1"));
        Widget.setSerialCode("ajwf45");
        assertThrows(IllegalArgumentException.class, () -> Laundry.clean("", "1"));
        assertThrows(IllegalArgumentException.class, () -> Laundry.clean("1", ""));
        assertThrows(IllegalArgumentException.class, () -> Laundry.clean("1", "1"));
        Widget.setNumModules(10);
        assertDoesNotThrow(() -> Laundry.clean("1", "1"));
    }

    @Test
    void videoTestOne(){
        setupOne();
        assertContains(new String[]{"80F", "Low Heat", "300", "Non-Chlorine", "LEATHER - PEARL - CORSET"},
                "0", "0");
    }

    private void setupOne(){
        Widget.setDBatteries(1);
        Widget.setDoubleAs(2);
        Widget.addPort(Ports.SERIAL);
        Widget.setIndicator(TriState.ON, Indicators.NSA);
        Widget.setNumHolders(2);
        Widget.setSerialCode("g64dv1");
        Widget.setNumModules(11);
    }

    @Test
    void videoTestTwo(){
        setupTwo();
        assertContains(new String[]{"120F", "Tumble", "No Steam", "No Tetrachlorethylene",
                        "POLYESTER - SAPPHIRE - SHIRT"}, "0", "0");
    }

    @Test
    void theGreatBerate(){
        WidgetSimulations.theGreatBerate();

        WidgetSimulations.theGreatBerateTwo();
        assertContains(new String[]{"105F", "Medium Heat", "110", "Wet Cleaning",
                "CORDUROY - MALINITE - SCARF"}, "0", "1");
        WidgetSimulations.partTwoTakeTwo();
        assertContains(new String[]{"80F", "Medium Heat", "300", "No Tetrachlorethylene",
                "LEATHER - MALINITE - CORSET"}, "0", "1");
        WidgetSimulations.partTwoTakeThree();
    }

    @Test
    void thanksBobTest(){
        WidgetSimulations.thanksBobCenturion();
        String[] actual = Laundry.clean("0", "0");
        assertEquals(EXPECTED_SIZE_FOR_BOB, actual.length);
        assertTrue(actual[0].contains("105F"));
        assertTrue(actual[1].contains("Don't Tumble Dry"));
        assertTrue(actual[2].contains("300"));
        assertTrue(actual[3].contains("Bleach"));
        assertTrue(actual[4].contains("CORDUROY - JADE - CORSET"));
        assertEquals(Laundry.THANKS_BOB, actual[5]);
    }

    private void setupTwo(){
        Widget.setNumModules(11);
        Widget.setSerialCode("7h1iv1");
        Widget.setDBatteries(1);
        Widget.setNumHolders(1);
        Widget.setIndicator(TriState.ON, Indicators.NSA);
        Widget.setIndicator(TriState.OFF, Indicators.FRQ);
        Widget.setPlates(1);
        Widget.addPort(Ports.PARALLEL);
    }

    private void assertContains(String[] expected, String solved, String needy){
        String[] actual = Laundry.clean(solved, needy);
        for (int i = 0; i < expected.length; i++)
            assertTrue(actual[i].contains(expected[i]));
    }
}
