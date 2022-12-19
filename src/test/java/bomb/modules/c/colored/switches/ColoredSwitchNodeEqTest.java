package bomb.modules.c.colored.switches;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE;

public class ColoredSwitchNodeEqTest {
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(ColoredSwitchNode.class)
                .withIgnoredFields("outgoingConnections")
                .suppress(STRICT_INHERITANCE)
                .verify();
    }
}
