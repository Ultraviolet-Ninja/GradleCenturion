package bomb.modules.ab.battleship;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE;

public class OceanEqTest {
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Ocean.class)
                .withNonnullFields("gameBoard")
                .suppress(STRICT_INHERITANCE)
                .verify();
    }
}
