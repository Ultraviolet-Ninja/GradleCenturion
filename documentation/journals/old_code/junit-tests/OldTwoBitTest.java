package bomb.modules.t.two;

import bomb.Widget;
import bomb.WidgetSimulations;
import bomb.enumerations.Ports;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bomb.modules.t.two.TwoBit.QUERY;
import static bomb.modules.t.two.TwoBit.SUBMIT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OldTwoBitTest {
    @BeforeEach
    void resetProperties(){
        Widget.resetProperties();
        TwoBit.resetStage();
    }

    @Test
    void exceptionTest(){
        assertThrows(IllegalArgumentException.class, TwoBit::initialCode);
        assertThrows(IllegalArgumentException.class, () -> TwoBit.nextCode("1"));
        assertThrows(IllegalArgumentException.class, () -> TwoBit.nextCode("311"));
        assertThrows(IllegalArgumentException.class, () -> TwoBit.nextCode("ab"));
        assertThrows(IllegalArgumentException.class, () -> TwoBit.nextCode("3fs11"));
        Widget.setSerialCode("124sdi");
        assertDoesNotThrow(TwoBit::initialCode);
        assertDoesNotThrow(() -> TwoBit.nextCode("3fs1"));
    }

    @Test
    void initialCodeTest(){
        setupOne();
        assertEquals("dk", TwoBit.initialCode());
    }

    @Test
    void videoQuerySubmitTest(){
        setupOne();
        assertEquals(QUERY + "gv", TwoBit.nextCode("02"));
        assertEquals(QUERY + "vt", TwoBit.nextCode("07"));
        assertEquals(SUBMIT + "gz", TwoBit.nextCode("89"));
        assertEquals(QUERY + "vc", TwoBit.nextCode("77"));
        assertEquals(QUERY + "bd", TwoBit.nextCode("67"));
        assertEquals(SUBMIT + "vg", TwoBit.nextCode("93"));
    }

    private void setupOne(){
        Widget.setPlates(2);
        Widget.addPort(Ports.RJ45);
        Widget.addPort(Ports.PS2);
        Widget.addPort(Ports.PS2);
        Widget.addPort(Ports.RCA);
        Widget.setSerialCode("AI3ZC1");
    }

    @Test
    void theGreatBerate(){
        WidgetSimulations.theGreatBerate();

        WidgetSimulations.theGreatBerateTwo();
        assertEquals("kz", TwoBit.initialCode());
        assertEquals(QUERY + "dk", TwoBit.nextCode("01"));
        assertEquals(QUERY + "tk", TwoBit.nextCode("03"));
        assertEquals(SUBMIT + "ep", TwoBit.nextCode("56"));
        WidgetSimulations.partTwoTakeTwo();

        WidgetSimulations.partTwoTakeThree();
    }

    //TODO - Create a scenario that tests an "over 100" bomb
}
