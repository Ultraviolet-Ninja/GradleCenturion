package bomb.tools.data.structures.queue;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE;

public class BufferedQueueEqTest {
    @Test(enabled = false)
    public void equalsContract() {
        EqualsVerifier.forClass(BufferedQueue.class)
                .withIgnoredFields("capacity", "dataCache")
                .suppress(STRICT_INHERITANCE)
                .verify();
    }
}
