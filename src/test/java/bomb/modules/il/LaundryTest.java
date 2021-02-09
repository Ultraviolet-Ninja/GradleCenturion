package bomb.modules.il;

import bomb.Widget;
import bomb.enumerations.Ports;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LaundryTest {
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
        Widget.setNumModules(10);
        assertDoesNotThrow(() -> Laundry.clean("1", "1"));
    }

    @Test
    void videoTestOne(){
        setupOne();
//        assertContains(new String[]{}, "0", "0");
    }

    private void setupOne(){
        Widget.setDBatteries(1);
        Widget.setDoubleAs(2);
        Widget.addPort(Ports.SERIAL);
        Widget.setNumHolders(2);
        Widget.setSerialCode("g64dv1");
        Widget.setNumModules(11);
    }

    @Test
    void videoTestTwo(){
        setupTwo();

    }

    private void setupTwo(){

    }

    private void assertContains(String[] expected, String solved, String needy){
        String[] actual = Laundry.clean(solved, needy);
        for (int i = 0; i < expected.length; i++)
            assertTrue(actual[i].contains(actual[i]));
    }
}
