package bomb.tools.pattern.observer;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.BLIND_ALLEY_PANE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.SOUVENIR_TOGGLE;

public final class ObserverHub {
    public enum ObserverIndex {
        FORGET_ME_NOT_TOGGLE, SOUVENIR_TOGGLE, BLIND_ALLEY_PANE, SOUVENIR_PANE, RESET
    }

    private static final EnumMap<ObserverIndex, Observer> OBSERVER_MAP = new EnumMap<>(ObserverIndex.class);
    private static final Map<String, ObserverIndex> BUTTON_NAME_MAP = Map.of(
            "Blind Alley", BLIND_ALLEY_PANE,
            "Souvenir", SOUVENIR_TOGGLE
    );

    private ObserverHub() {
    }

    public static void addObserver(ObserverIndex index, Observer observer) {
        OBSERVER_MAP.put(index, observer);
    }

    public static void updateAtIndex(@NotNull ObserverIndex index) {
        OBSERVER_MAP.get(index).update();
    }

    public static void scanButtonName(@NotNull String buttonName) {
        if (BUTTON_NAME_MAP.containsKey(buttonName)) {
            updateAtIndex(BUTTON_NAME_MAP.get(buttonName));
        }
    }
}
