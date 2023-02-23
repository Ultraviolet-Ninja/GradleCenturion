package bomb.tools.pattern.observer;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

public final class ObserverHub {
    public enum ObserverIndex {
        FORGET_ME_NOT_TOGGLE, SOUVENIR_TOGGLE, BLIND_ALLEY_PANE, SOUVENIR_PANE, RESET
    }

    private static final EnumMap<ObserverIndex, Observer> OBSERVER_MAP = new EnumMap<>(ObserverIndex.class);

    private ObserverHub() {
    }

    public static void addObserver(ObserverIndex index, Observer observer) {
        OBSERVER_MAP.put(index, observer);
    }

    public static void updateAtIndex(@NotNull ObserverIndex index) {
        OBSERVER_MAP.get(index).update();
    }
}
