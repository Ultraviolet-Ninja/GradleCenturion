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
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
public final class ManualController {
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

        return radioButtonNameFuture.thenCombine(fxmlMapFuture, ManualController::joinOnStringKeys);
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

    private static Map<Toggle, Region> joinOnStringKeys(Map<String, Toggle> radioButtonMap,
                                                        Map<String, Region> filePathMap) {
        Map<Toggle, Region> regionMap = new LinkedHashMap<>();
        for (var entry : radioButtonMap.entrySet()) {
            regionMap.put(
                    entry.getValue(),
                    filePathMap.getOrDefault(entry.getKey(), EMPTY_VIEW)
            );
        }
        return regionMap;
    }

    private static Map<String, Region> createFXMLMap(ResetObserver resetObserver) {
        var displayClassStream = getAnnotatedClasses().parallelStream();

        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            displayClassStream = displayClassStream.sequential();
        }

        return displayClassStream
                .map(cls -> mapClassToRegion(cls, resetObserver))
                .collect(toMap(Pair::getValue0, Pair::getValue1));
//        var annotatedClasses = getAnnotatedClasses();
//        var virtualThreadList = new ArrayList<Future<Pair<String, Region>>>(annotatedClasses.size());
//
//        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
//            for (var annotatedClass : annotatedClasses) {
//                var future = executor.submit(() -> mapClassToRegion(annotatedClass, resetObserver));
//                virtualThreadList.add(future);
//            }
//        }
//
//        return virtualThreadList.stream()
//                .map(ManualController::extractFromFuture)
//                .collect(toMap(Pair::getValue0, Pair::getValue1));
    }

    private static Pair<String, Region> extractFromFuture(Future<Pair<String, Region>> future) {
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Class<?>> getAnnotatedClasses() {
        var annotatedClasses = new ArrayList<>(List.of(Widget.class, NoteController.class));
        var queue = new ArrayDeque<>(asList(Widget.class.getPermittedSubclasses()));

        Class<?> temp;
        while ((temp = queue.poll()) != null) {
            Class<?>[] subclasses = temp.getPermittedSubclasses();
            if (subclasses != null) {
                queue.addAll(asList(subclasses));
            }

            if (temp.isAnnotationPresent(DisplayComponent.class)) {
                annotatedClasses.add(temp);
            }
        }
        return annotatedClasses;
    }

    private static Pair<String, Region> mapClassToRegion(Class<?> clazz, ResetObserver resetObserver) {
        DisplayComponent annotation = clazz.getAnnotation(DisplayComponent.class);
        URL resource = clazz.getResource(annotation.resource());
        String buttonLinkerName = annotation.buttonLinkerName();

        return new Pair<>(
                buttonLinkerName,
                loadSingleRegion(new FXMLLoader(resource), resetObserver)
        );
    }

    private static Region loadSingleRegion(FXMLLoader loader, ResetObserver resetObserver)
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
