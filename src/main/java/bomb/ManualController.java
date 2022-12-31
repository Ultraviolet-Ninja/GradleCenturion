package bomb;

import bomb.annotation.DisplayComponent;
import bomb.modules.ab.blind.alley.BlindAlleyController;
import bomb.modules.s.souvenir.SouvenirController;
import bomb.tools.note.NoteController;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.pattern.observer.BlindAlleyPaneObserver;
import bomb.tools.pattern.observer.ForgetMeNotToggleObserver;
import bomb.tools.pattern.observer.ObserverHub;
import bomb.tools.pattern.observer.ResetObserver;
import bomb.tools.pattern.observer.SouvenirPaneObserver;
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
import org.javatuples.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static bomb.tools.number.MathUtils.negativeSafeModulo;
import static bomb.tools.pattern.facade.FacadeFX.GET_TOGGLE_NAME;
import static bomb.tools.pattern.factory.TextFormatterFactory.createSearchBarFormatter;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.BLIND_ALLEY_PANE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.FORGET_ME_NOT_TOGGLE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.RESET;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.SOUVENIR_PANE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.SOUVENIR_TOGGLE;
import static java.util.Arrays.asList;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@SuppressWarnings("ConstantConditions")
public class ManualController {
    //TODO - Remove when every FXML file is being used
    private static final Region EMPTY_VIEW;

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
        System.out.printf("Timer: %,d%n", stop - start);
    }

    private CompletableFuture<Map<Toggle, Region>> setupRegionMap() {
        ResetObserver resetObserver = new ResetObserver();
        ObserverHub.addObserver(RESET, resetObserver);
        var fxmlMapFuture = supplyAsync(() -> createFXMLMap(resetObserver));
        var radioButtonNameFuture = createButtonNameFuture(options.getToggles());

        return radioButtonNameFuture.thenCombine(fxmlMapFuture, ManualController::createRegionMap);
    }

    private static CompletableFuture<Map<String, Toggle>> createButtonNameFuture(List<Toggle> radioButtonList) {
        return supplyAsync(radioButtonList::stream)
                .thenApply(stream -> stream.collect(toMap(
                        GET_TOGGLE_NAME,
                        identity(),
                        (x, y) -> y,
                        LinkedHashMap::new
                )));
    }

    private static Map<Toggle, Region> createRegionMap(Map<String, Toggle> radioButtonMap,
                                                       Map<String, Region> filePathMap) {
        Map<Toggle, Region> regionMap = new LinkedHashMap<>();
        for (Map.Entry<String, Toggle> entry : radioButtonMap.entrySet())
            regionMap.put(
                    entry.getValue(),
                    filePathMap.getOrDefault(entry.getKey(), EMPTY_VIEW)
            );
        return regionMap;
    }

    private static Map<String, Region> createFXMLMap(ResetObserver resetObserver) {
        var displayClassStream = getDisplayedClasses().parallelStream();

        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            displayClassStream = displayClassStream.sequential();
        }

        return displayClassStream
                .map(cls -> mapClassToRegion(cls, resetObserver))
                .collect(toMap(Pair::getValue0, Pair::getValue1));
    }

    private static List<Class<?>> getDisplayedClasses() {
        List<Class<?>> list = new ArrayList<>(List.of(Widget.class, NoteController.class));
        ArrayDeque<Class<?>> files = new ArrayDeque<>(asList(Widget.class.getPermittedSubclasses()));

        Class<?> temp;
        while ((temp = files.poll()) != null) {
            Class<?>[] nextSubLevel = temp.getPermittedSubclasses();
            if (nextSubLevel != null) {
                files.addAll(asList(nextSubLevel));
            }

            if (temp.isAnnotationPresent(DisplayComponent.class)) {
                list.add(temp);
            }
        }
        return list;
    }

    private static Pair<String, Region> mapClassToRegion(Class<?> clazz, ResetObserver resetObserver) {
        DisplayComponent annotation = clazz.getAnnotation(DisplayComponent.class);
        URL resource = clazz.getResource(annotation.resource());
        String buttonLinkerName = annotation.buttonLinkerName();

        return new Pair<>(
                buttonLinkerName,
                createSingleRegion(new FXMLLoader(resource), resetObserver)
        );
    }

    private static Region createSingleRegion(FXMLLoader loader, ResetObserver resetObserver)
            throws IllegalArgumentException {
        Region output = FacadeFX.load(loader);
        String location = loader.getLocation().toString();

        if (!location.endsWith("widget.fxml")) resetObserver.addController(loader);

        if (location.endsWith("souvenir.fxml")) loadSouvenirController(loader.getController());
        else if (location.endsWith("blind_alley.fxml")) loadBlindAlleyController(loader.getController());
        return output;
    }

    private static void loadBlindAlleyController(BlindAlleyController controller) {
        ObserverHub.addObserver(BLIND_ALLEY_PANE, new BlindAlleyPaneObserver(controller));
    }

    private static void loadSouvenirController(SouvenirController controller) {
        ObserverHub.addObserver(SOUVENIR_PANE, new SouvenirPaneObserver(controller));
    }

    @FXML
    public void switchPaneByButtonPress() {
        Toggle selected = options.getSelectedToggle();
        String selectedName = GET_TOGGLE_NAME.apply(selected);
        if (selectedName.equals("Blind Alley")) ObserverHub.updateAtIndex(BLIND_ALLEY_PANE);
        else if (selectedName.equals("Souvenir")) ObserverHub.updateAtIndex(SOUVENIR_PANE);
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
