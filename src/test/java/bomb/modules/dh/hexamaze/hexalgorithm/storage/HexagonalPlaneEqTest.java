package bomb.modules.dh.hexamaze.hexalgorithm.storage;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE;

public class HexagonalPlaneEqTest {
    @Test(enabled = false)
    public void equalsContract() {
        EqualsVerifier.forClass(HexagonalPlane.class)
                .withNonnullFields("hexagon")
                .suppress(STRICT_INHERITANCE)
                .verify();
    }
}
