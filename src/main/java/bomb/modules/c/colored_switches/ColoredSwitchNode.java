package bomb.modules.c.colored_switches;

import org.javatuples.Pair;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ColoredSwitchNode {
    private final byte state;
    private final Map<Byte, Pair<ColoredSwitchProperty[], Byte>> outgoingConnections;

    public ColoredSwitchNode(byte state) {
        this.state = state;
        outgoingConnections = new LinkedHashMap<>();
    }

    public void addConnection(byte outgoingState, ColoredSwitchProperty[] colorRestrictions, byte switchToFlip) {
        outgoingConnections.put(outgoingState, new Pair<>(colorRestrictions, switchToFlip));
    }

    public Set<Byte> getOutgoingConnections() {
        return outgoingConnections.keySet();
    }

    public int getConnectionSize() {
        return outgoingConnections.size();
    }

    @Override
    public int hashCode() {
        return Byte.hashCode(state);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ColoredSwitchNode)) return false;

        ColoredSwitchNode node = (ColoredSwitchNode) o;
        return this.state == node.state && this.outgoingConnections.equals(node.outgoingConnections);
    }

    @Override
    public String toString() {
        return String.valueOf(state);
    }
}