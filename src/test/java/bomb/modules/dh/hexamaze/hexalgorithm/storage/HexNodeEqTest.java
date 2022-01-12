package bomb.modules.dh.hexamaze.hexalgorithm.storage;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

import static nl.jqno.equalsverifier.Warning.NONFINAL_FIELDS;
import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE;

public class HexNodeEqTest {
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(HexNode.class)
                .withNonnullFields("walls", "hexShape")
                .withIgnoredFields("color")
                .suppress(STRICT_INHERITANCE, NONFINAL_FIELDS)
                .verify();
    }
}
