package bomb.modules.c.colored.switches;

import org.javatuples.Pair;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ColoredSwitchNode {
    private static final int SIZE_LIMIT = 3;

    private final byte state;
    private final Map<Byte, Pair<EnumSet<SwitchColor>, Byte>> outgoingConnections;

    public ColoredSwitchNode(byte state) {
        this.state = state;
        outgoingConnections = new LinkedHashMap<>(SIZE_LIMIT);
    }

    public byte getState() {
        return state;
    }

    public void addConnection(byte outgoingState, EnumSet<SwitchColor> colorRestrictions, byte switchToFlip) {
        outgoingConnections.put(outgoingState, new Pair<>(colorRestrictions, switchToFlip));
    }

    public Set<Byte> getOutgoingConnections() {
        return outgoingConnections.keySet();
    }

    public Pair<EnumSet<SwitchColor>, Byte> getEdgeData(byte targetState) {
        return outgoingConnections.get(targetState);
    }

    @Override
    public int hashCode() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ColoredSwitchNode node)) return false;
        return this.state == node.state;
    }

    @Override
    public String toString() {
        return String.valueOf(state);
    }
}