package bomb.tools.pattern.observer;

import bomb.annotation.DisplayComponent;
import bomb.modules.ab.blind.alley.BlindAlley;
import bomb.modules.s.souvenir.Souvenir;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.Map;

import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.BLIND_ALLEY_PANE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.SOUVENIR_PANE;

public final class ObserverHub {
    public enum ObserverIndex {
        FORGET_ME_NOT_TOGGLE, SOUVENIR_TOGGLE, BLIND_ALLEY_PANE, SOUVENIR_PANE, RESET
    }

    private static final Logger LOG = LoggerFactory.getLogger(ObserverHub.class);
    private static final EnumMap<ObserverIndex, Observer> OBSERVER_MAP = new EnumMap<>(ObserverIndex.class);
    private static final Map<String, ObserverIndex> BUTTON_NAME_MAP = Map.of(
            extractButtonLinkerName(BlindAlley.class), BLIND_ALLEY_PANE,
            extractButtonLinkerName(Souvenir.class), SOUVENIR_PANE
    );

    private static String extractButtonLinkerName(Class<?> clazz) {
        return clazz.getAnnotation(DisplayComponent.class)
                .buttonLinkerName();
    }

    private ObserverHub() {
    }

    public static void addObserver(ObserverIndex index, Observer observer) {
        if (OBSERVER_MAP.containsKey(index)) {
            LOG.warn("{} value will be overwritten", index);
        }
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

    public static void ensureMapIsPopulated() {
        if (ObserverIndex.values().length != OBSERVER_MAP.size()) {
            LOG.warn("Observers not fully populated: {}", OBSERVER_MAP);
        }
    }
}
