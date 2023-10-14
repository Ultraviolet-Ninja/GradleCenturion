package bomb;

import bomb.tools.boot.FxmlBootDrive;
import bomb.tools.pattern.observer.ForgetMeNotToggleObserver;
import bomb.tools.pattern.observer.ObserverHub;
import bomb.tools.pattern.observer.ResetObserver;
import bomb.tools.pattern.observer.SouvenirToggleObserver;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static bomb.tools.number.MathUtils.negativeSafeModulo;
import static bomb.tools.pattern.facade.FacadeFX.GET_TOGGLE_NAME;
import static bomb.tools.pattern.factory.TextFormatterFactory.createSearchBarFormatter;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.FORGET_ME_NOT_TOGGLE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.RESET;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.SOUVENIR_TOGGLE;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@SuppressWarnings("ConstantConditions")
public final class ManualController {
    //TODO - Remove when every FXML file is being used
    private static final Region EMPTY_VIEW;
    private static final Logger LOG = LoggerFactory.getLogger(ManualController.class);

    private Map<Toggle, Region> regionMap;
    private List<Node> observableRadioList;
    private final List<RadioButton> allRadioButtons;

    @FXML
    private GridPane base;

    @FXML
    private JFXRadioButton forgetMeNot, souvenir;

    @FXML
    private TextField searchBar;

    @FXML
    private ToggleGroup options;

    @FXML
    private VBox menuVBox, radioButtonHouse;

    static {
        var emptyViewLocation = ManualController.class.getResource("empty_view.fxml");
        try {
            EMPTY_VIEW = FXMLLoader.load(emptyViewLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ManualController() {
        allRadioButtons = new ArrayList<>();
    }

    public void initialize() throws ExecutionException, InterruptedException {
        searchBar.setTextFormatter(createSearchBarFormatter());
        observableRadioList = radioButtonHouse.getChildren();
        allRadioButtons.addAll(
                observableRadioList.stream()
                        .map(node -> (RadioButton) node)
                        .toList()
        );
        ObserverHub.addObserver(FORGET_ME_NOT_TOGGLE, new ForgetMeNotToggleObserver(forgetMeNot));
        ObserverHub.addObserver(SOUVENIR_TOGGLE, new SouvenirToggleObserver(souvenir));

        long start = System.nanoTime();
        regionMap = setupRegionMap().get();
        long stop = System.nanoTime();
        LOG.info("Boot Time: {}", convertTime(stop - start));
    }

    private static String convertTime(long nanos) {
        var duration = Duration.ofNanos(nanos);
        long seconds = duration.getSeconds() % 60;
        long millis = duration.toMillis() % 1000;
        return String.format("%01d.%03d sec", seconds, millis);
    }

    private CompletableFuture<Map<Toggle, Region>> setupRegionMap() {
        var resetObserver = new ResetObserver();
        ObserverHub.addObserver(RESET, resetObserver);
        var drive = FxmlBootDrive.createStandardDrive();
        var fxmlMapFuture = supplyAsync(() -> drive.createFXMLMap(resetObserver));
        var radioButtonNameFuture = createButtonNameFuture(options.getToggles());

        return radioButtonNameFuture.thenCombine(fxmlMapFuture, ManualController::joinOnStringKeys);
    }

    private static CompletableFuture<SequencedMap<String, Toggle>> createButtonNameFuture(List<Toggle> radioButtonList) {
        return supplyAsync(radioButtonList::stream)
                .thenApply(stream -> stream.collect(toMap(
                        GET_TOGGLE_NAME,
                        identity(),
                        (x, y) -> y,
                        LinkedHashMap::new
                )));
    }

    private static Map<Toggle, Region> joinOnStringKeys(SequencedMap<String, Toggle> radioButtonMap,
                                                        SequencedMap<String, Region> filePathMap) {
        Map<Toggle, Region> regionMap = LinkedHashMap.newLinkedHashMap(radioButtonMap.size());
        for (var entry : radioButtonMap.sequencedEntrySet()) {
            regionMap.put(
                    entry.getValue(),
                    filePathMap.getOrDefault(entry.getKey(), EMPTY_VIEW)
            );
        }
        return regionMap;
    }

    @FXML
    public void switchPaneByButtonPress() {
        Toggle selected = options.getSelectedToggle();
        ObserverHub.scanButtonName(GET_TOGGLE_NAME.apply(selected));
        paneSwitch(regionMap.get(selected));
    }

    private void paneSwitch(final Region pane) {
        if (base.getChildren().size() != 1) base.getChildren().retainAll(menuVBox);
        base.add(pane, 0, 0);
    }

    @FXML
    public void search() {
        String searchTerm = searchBar.getText().toLowerCase();
        radioButtonHouse.getChildren().clear();
        if (searchTerm.isEmpty()) {
            radioButtonHouse.getChildren().addAll(allRadioButtons);
            return;
        }

        String pattern = "[a-z3 ]*" + searchTerm + "[a-z3 ]*";
        radioButtonHouse.getChildren().addAll(
                allRadioButtons.stream()
                        .filter(radioButton -> GET_TOGGLE_NAME.apply(radioButton)
                                .toLowerCase()
                                .matches(pattern)
                        ).toList()
        );
    }

    void switchPaneByIndex(final int index) {
        ((RadioButton)observableRadioList.get(index)).fire();
    }

    void switchPaneByUpArrow() {
        int size = allRadioButtons.size();
        if (size != observableRadioList.size()) return;

        RadioButton selected = (RadioButton) options.getSelectedToggle();
        if (selected == null) return;

        int index = negativeSafeModulo(allRadioButtons.indexOf(selected) - 1, size);
        switchPaneByIndex(index);
    }

    void switchPaneByDownArrow() {
        int size = allRadioButtons.size();
        if (size != observableRadioList.size()) return;

        RadioButton selected = (RadioButton) options.getSelectedToggle();
        if (selected == null) return;

        int index = allRadioButtons.indexOf(selected) + 1;
        index %= size;
        switchPaneByIndex(index);
    }
}
