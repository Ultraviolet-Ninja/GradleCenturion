package bomb.tools.pattern.observer;

import bomb.abstractions.marker.PostLeftKey;
import bomb.abstractions.marker.PostRightKey;
import bomb.annotation.DisplayComponent;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static bomb.tools.pattern.facade.FacadeFX.GET_TOGGLE_NAME;

public final class TurnTheKeysStrictModeObserver implements Observer {
    private static final Set<String> POST_KEY_MODULE_NAMES = findPostKeyModules();

    private final TextField searchBar;
    private final List<Node> radioButtonList;
    private final List<RadioButton> allRadioButtons;

    private boolean strictModeToggle;

    public TurnTheKeysStrictModeObserver(TextField searchBar, List<Node> radioButtonList, List<RadioButton> allRadioButtons) {
        this.searchBar = searchBar;
        this.radioButtonList = radioButtonList;
        this.allRadioButtons = Collections.unmodifiableList(allRadioButtons);
        strictModeToggle = false;
    }

    @Override
    public void update() {
        if (strictModeToggle) {
            radioButtonList.removeIf(node ->
                    POST_KEY_MODULE_NAMES.contains(GET_TOGGLE_NAME.apply((RadioButton) node)));
        } else {
            radioButtonList.clear();
            radioButtonList.addAll(allRadioButtons);
        }

        strictModeToggle = !strictModeToggle;
    }

    private static Set<String> findPostKeyModules() {
        return Stream.of(PostLeftKey.class, PostRightKey.class)
                .map(Class::getPermittedSubclasses)
                .flatMap(Arrays::stream)
                .filter(clazz -> clazz.isAnnotationPresent(DisplayComponent.class))
                .map(clazz -> clazz.getAnnotation(DisplayComponent.class))
                .map(DisplayComponent::buttonLinkerName)
                .collect(Collectors.toUnmodifiableSet());
    }
}
