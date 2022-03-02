package bomb.tools;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE;

public class CoordinatesEqTest {
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Coordinates.class)
                .suppress(STRICT_INHERITANCE)
                .verify();
    }
}
