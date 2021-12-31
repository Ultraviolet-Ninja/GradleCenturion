package bomb;

import bomb.tools.filter.Regex;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

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
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@SuppressWarnings("ConstantConditions")
public class ManualController {
    private static final String FXML_DIRECTORY = "fxml";

    private Map<Toggle, Region> regionMap;
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

    public ManualController() {
        allRadioButtons = new ArrayList<>();
    }

    public void initialize() throws ExecutionException, InterruptedException {
        searchBar.setTextFormatter(createSearchBarFormatter());
        allRadioButtons.addAll(
                radioButtonHouse.getChildren().stream()
                        .map(node -> (RadioButton)node)
                        .toList()
        );
        ObserverHub.addObserver(FORGET_ME_NOT_TOGGLE, new ForgetMeNotToggleObserver(forgetMeNot));
        ObserverHub.addObserver(SOUVENIR_TOGGLE, new SouvenirToggleObserver(souvenir));
        long start = System.nanoTime();
        regionMap = setupRegionMap().get();
        long stop = System.nanoTime();
        System.out.printf("Timer: %,d%n", stop - start);
    }

    private CompletableFuture<Map<Toggle, Region>> setupRegionMap() throws ExecutionException, InterruptedException {
        CompletableFuture<Map<String, Toggle>> radioButtonNameFuture =
                createRadioButtonNameFuture(options.getToggles());

        return createFilePathFuture().thenApply(filePathFuture ->
                        filePathFuture.thenCombine(radioButtonNameFuture, (filePathMap, radioButtonMap) ->
                                createRegionMap(radioButtonMap, filePathMap)))
                .get();
    }

    private static CompletableFuture<CompletableFuture<Map<String, Region>>> createFilePathFuture() {
        Regex filenamePattern = new Regex("\\w+(?=\\.)");
        ResetObserver resetObserver = new ResetObserver();
        URI uri = toURI(ManualController.class.getResource(FXML_DIRECTORY));

        CompletableFuture<CompletableFuture<Map<String, Region>>> future = supplyAsync(() -> new File(uri))
                .thenApply(ManualController::getFilesFromDirectory)
                .thenApply(list -> convertFilesToRegions(list, resetObserver, filenamePattern));

        ObserverHub.addObserver(RESET, resetObserver);
        return future;
    }

    private static CompletableFuture<Map<String, Region>> convertFilesToRegions(List<String> fileList,
                                                                                ResetObserver resetObserver,
                                                                                Regex filenamePattern) {
        fileList.removeIf(location -> location.contains("old") || location.contains("new"));

        CompletableFuture<List<Region>> regionListFuture = supplyAsync(fileList::stream)
                .thenApply(stream -> stream.map(location -> createSingleRegion(location, resetObserver)))
                .thenApply(stream -> stream.collect(toList()));

        return supplyAsync(() -> filenamePattern.filterCollection(fileList))
                .thenCombine(regionListFuture, (fileNameList, regionList) ->
                        IntStream.range(0, fileNameList.size())
                                .boxed()
                                .collect(toMap(fileNameList::get, regionList::get)));
    }

    private static CompletableFuture<Map<String, Toggle>> createRadioButtonNameFuture(List<Toggle> radioButtonList) {
        return supplyAsync(radioButtonList::stream)
                .thenApply(stream -> stream.collect(toMap(
                        toggle -> GET_TOGGLE_NAME.apply(toggle)
                                .replaceAll("[ -]", "_")
                                .replaceAll("[()']", "")
                                .toLowerCase(),
                        identity()
                )));
    }

    private static Region createSingleRegion(String fileLocation, ResetObserver resetObserver)
            throws IllegalArgumentException {
        URI path = Paths.get(fileLocation).toUri();
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(path.toURL());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        Region output = FacadeFX.load(loader);
        String location = loader.getLocation().toString();

        if (!location.contains("widget")) resetObserver.addController(loader);

        if (location.contains("souvenir")) loadSouvenirController(loader);
        else if (location.contains("blind_alley")) loadBlindAlleyController(loader);
        return output;
    }

    private static void loadBlindAlleyController(FXMLLoader loader) {
        ObserverHub.addObserver(BLIND_ALLEY_PANE, new BlindAlleyPaneObserver(loader.getController()));
    }

    private static void loadSouvenirController(FXMLLoader loader) {
        ObserverHub.addObserver(SOUVENIR_PANE, new SouvenirPaneObserver(loader.getController()));
    }

    private static List<String> getFilesFromDirectory(final File topLevelDirectory) {
        List<String> list = new ArrayList<>();
        ArrayDeque<File> files = new ArrayDeque<>(asList(topLevelDirectory.listFiles()));
        File temp;

        while ((temp = files.poll()) != null) {
            if (!temp.isDirectory())
                list.add(temp.getPath());
            else
                files.addAll(asList(temp.listFiles()));
        }
        return list;
    }

    private static Map<Toggle, Region> createRegionMap(Map<String, Toggle> radioButtonMap,
                                                       Map<String, Region> filePathMap) {
        Map<Toggle, Region> regionMap = new IdentityHashMap<>();
        for (Map.Entry<String, Toggle> entry : radioButtonMap.entrySet())
            regionMap.put(
                    entry.getValue(),
                    filePathMap.get(entry.getKey())
            );
        return regionMap;
    }

    private static URI toURI(URL url) throws IllegalArgumentException {
        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @FXML
    public void buttonPress() {
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

        String pattern = "[\\w ]*" + searchTerm + "[\\w ]*";
        radioButtonHouse.getChildren().addAll(
                allRadioButtons.stream()
                        .filter(radioButton -> GET_TOGGLE_NAME.apply(radioButton)
                                .toLowerCase()
                                .matches(pattern)
                        ).toList()
        );
    }
}
