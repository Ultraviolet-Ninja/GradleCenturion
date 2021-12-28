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
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.intellij.lang.annotations.Language;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import static bomb.tools.filter.RegexFilter.ALL_CHAR_FILTER;
import static bomb.tools.filter.RegexFilter.filter;
import static bomb.tools.pattern.facade.FacadeFX.GET_TOGGLE_NAME;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.BLIND_ALLEY_PANE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.SOUVENIR_PANE;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@SuppressWarnings("ConstantConditions")
public class ManualController {
    private static final String FXML_DIRECTORY = "fxml";

    private Map<Toggle, Region> regionMap;
    private final List<Node> allRadioButtons;

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
        allRadioButtons.addAll(radioButtonHouse.getChildren());
        ObserverHub.addObserver(new ForgetMeNotToggleObserver(forgetMeNot));
        ObserverHub.addObserver(new SouvenirToggleObserver(souvenir));
        long start = System.nanoTime();
        regionMap = setupRegionMap().get();
        long stop = System.nanoTime();
        System.out.printf("Timer: %,d", stop - start);
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
        @Language("regexp")
        String searchTerm = searchBar.getText();
        radioButtonHouse.getChildren().clear();

        if (searchTerm.isEmpty()) {
            radioButtonHouse.getChildren().addAll(allRadioButtons);
            return;
        }

        Regex searchPattern = new Regex(searchTerm, Pattern.CASE_INSENSITIVE);

        radioButtonHouse.getChildren().addAll(
                allRadioButtons.stream()
                        .filter(radioButton -> {
                            String name = ((RadioButton) radioButton).getText();
                            searchPattern.loadText(name);
                            return searchPattern.hasMatch();
                        }).toList()
        );
    }

    private CompletableFuture<Map<Toggle, Region>> setupRegionMap() {
        CompletableFuture<Map<String, Region>> filePathFuture = createFilePathFuture();
        CompletableFuture<Map<String, Toggle>> radioButtonNameFuture =
                createRadioButtonNameFuture(options.getToggles());

        return filePathFuture.thenCombine(radioButtonNameFuture,
                (filePathMap, radioButtonMap) -> createRegionMap(radioButtonMap, filePathMap));
    }

    private static CompletableFuture<Map<String, Region>> createFilePathFuture() {
        Regex filenamePattern = new Regex("\\w+\\.");
        ResetObserver resetObserver = new ResetObserver();

        CompletableFuture<Map<String, Region>> future = CompletableFuture.supplyAsync(
                () -> ManualController.class.getResource(FXML_DIRECTORY))
                .thenApply(ManualController::toURI)
                .handle((path, ex) -> {
                    if (ex != null) {
                        ex.printStackTrace();
                        System.exit(-1);
                    }
                    return path;
                })
                .thenApply(File::new)
                .thenApply(ManualController::getFilesFromDirectory)
                .thenApply(list -> convertFilesToRegions(list, resetObserver, filenamePattern));

        ObserverHub.addObserver(resetObserver);
        return future;
    }

    private static Map<String, Region> convertFilesToRegions(List<String> fileList, ResetObserver resetObserver,
                                                             Regex filenamePattern) {
        return fileList.stream()
                .filter(location -> !location.contains("solutions")
                        && !location.contains("old") && !location.contains("new"))
                .collect(toMap(
                        location -> filter(location, filenamePattern)
                                .replace(".", ""),
                        location -> createSingleRegion(location, resetObserver)
                ));
    }

    private static CompletableFuture<Map<String, Toggle>> createRadioButtonNameFuture(List<Toggle> radioButtonList) {
        @Language("regexp")
        String newRegex = ALL_CHAR_FILTER.getOriginalPattern()
                .replace("]", "_]");
        Regex regex = new Regex(newRegex);

        return CompletableFuture.supplyAsync(radioButtonList::stream)
                .thenApply(stream ->
                        stream.collect(toMap(
                                toggle -> formatRadioButtonName(toggle, regex),
                                identity()
                        )));
    }

    private static String formatRadioButtonName(Toggle toggle, Regex regex) {
        String buttonName = GET_TOGGLE_NAME.apply(toggle)
                .replaceAll("[ -]", "_")
                .toLowerCase();

        return filter(buttonName, regex);
    }

    private static Region createSingleRegion(String fileLocation, ResetObserver resetObserver) {
        URI path = Paths.get(fileLocation).toUri();
        FXMLLoader loader = new FXMLLoader(toURL(path));
        return loadToObserver(loader, resetObserver);
    }

    private static Region loadToObserver(FXMLLoader loader, ResetObserver resetObserver) throws IllegalArgumentException {
        Region output = FacadeFX.load(loader);

        String location = loader.getLocation().toString();
        if (!location.contains("widget")) resetObserver.addController(loader);

        if (location.contains("souvenir")) loadSouvenirController(loader);
        else if (location.contains("blind_alley")) loadBlindAlleyController(loader);
        return output;
    }

    private static void loadBlindAlleyController(FXMLLoader loader) {
        ObserverHub.addObserver(new BlindAlleyPaneObserver(loader.getController()));
    }

    private static void loadSouvenirController(FXMLLoader loader) {
        ObserverHub.addObserver(new SouvenirPaneObserver(loader.getController()));
    }

    private static List<String> getFilesFromDirectory(final File topLevelDirectory) throws NullPointerException {
        List<String> list = new ArrayList<>();
        for (final File fileEntry : topLevelDirectory.listFiles()) {
            if (fileEntry.isDirectory())
                list.addAll(getFilesFromDirectory(fileEntry));
            else
                list.add(fileEntry.getPath());
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

    private static URL toURL(URI uri) throws IllegalArgumentException {
        try {
            return uri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
