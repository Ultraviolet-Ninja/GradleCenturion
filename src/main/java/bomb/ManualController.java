package bomb;

import bomb.tools.filter.Regex;
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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import static bomb.tools.filter.RegexFilter.ALL_CHAR_FILTER;
import static bomb.tools.filter.RegexFilter.filter;
import static bomb.tools.pattern.facade.FacadeFX.GET_TOGGLE_NAME;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.BLIND_ALLEY_PANE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.SOUVENIR_PANE;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class ManualController {
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

    public void initialize() throws URISyntaxException, ExecutionException, InterruptedException {
        allRadioButtons.addAll(radioButtonHouse.getChildren());
        ObserverHub.addObserver(new ForgetMeNotToggleObserver(forgetMeNot));
        ObserverHub.addObserver(new SouvenirToggleObserver(souvenir));
        regionMap = setupRegionMap().get();
    }

    @FXML
    public void buttonPress() {
        long start = System.nanoTime();
        Toggle selected = options.getSelectedToggle();
        String selectedName = GET_TOGGLE_NAME.apply(selected);
        if (selectedName.equals("Blind Alley")) ObserverHub.updateAtIndex(BLIND_ALLEY_PANE);
        else if (selectedName.equals("Souvenir")) ObserverHub.updateAtIndex(SOUVENIR_PANE);
        paneSwitch(regionMap.get(selected));
        long stop = System.nanoTime();

        System.out.printf("Time: %,d%n", stop - start);
    }

    private void paneSwitch(final Region pane) {
        if (base.getChildren().size() != 1) base.getChildren().retainAll(menuVBox);
        base.add(pane, 0, 0);
    }

    @FXML
    public void search() {
        String searchTerm = searchBar.getText();
        radioButtonHouse.getChildren().clear();

        if (searchTerm.isEmpty()) {
            radioButtonHouse.getChildren().addAll(allRadioButtons);
            return;
        }

        Regex searchPattern = new Regex(searchTerm, Pattern.CASE_INSENSITIVE);

        List<Node> resultingButtons = allRadioButtons.stream()
                .filter(radioButton -> {
                    String name = ((RadioButton) radioButton).getText();
                    searchPattern.loadText(name);
                    return searchPattern.hasMatch();
                })
                .collect(toList());
        radioButtonHouse.getChildren().addAll(resultingButtons);
    }

    private CompletableFuture<Map<Toggle, Region>> setupRegionMap() {
        CompletableFuture<Map<String, Region>> filePathFuture = createFilePathFuture();
        CompletableFuture<Map<String, Toggle>> radioButtonNameFuture =
                createRadioButtonNameFuture(options.getToggles());

        return filePathFuture.thenCombine(radioButtonNameFuture,
                (filePathMap, radioButtonMap) -> createRegionMap(radioButtonMap, filePathMap));
    }

    private CompletableFuture<Map<String, Region>> createFilePathFuture() {
        Regex filenamePattern = new Regex("\\w+\\.");
        ResetObserver resetObserver = new ResetObserver();

        CompletableFuture<Map<String, Region>> future = CompletableFuture.supplyAsync(
                () -> ManualController.class.getResource("fxml"))
                .thenApply(this::toURI)
                .handle((path, ex) -> {
                    if (ex != null){
                        ex.printStackTrace();
                        System.exit(-1);
                    }
                    return path;
                })
                .thenApply(File::new)
                .thenApply(this::getFilesFromDirectory)
                .thenApply(Collection::stream)
                .thenApply(stream -> stream.filter(location -> !location.contains("solutions")))
                .thenApply(stream -> stream.filter(location -> !location.contains("old")))
                .thenApply(stream -> stream.filter(location -> !location.contains("new")))
                .thenApply(stream -> stream.collect(toMap(
                        location -> filter(location, filenamePattern)
                                .replace(".", ""),
                        location -> createSingleRegion(location, resetObserver)
                )));

        ObserverHub.addObserver(resetObserver);
        return future;
    }

    private CompletableFuture<Map<String, Toggle>> createRadioButtonNameFuture(List<Toggle> radioButtonList) {
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

    private String formatRadioButtonName(Toggle toggle, Regex regex) {
        String buttonName = GET_TOGGLE_NAME.apply(toggle)
                .replaceAll("[ -]", "_")
                .toLowerCase();

        return filter(buttonName, regex);
    }

    private Region createSingleRegion(String fileLocation, ResetObserver resetObserver) {
        URI path = Paths.get(fileLocation).toUri();
        FXMLLoader loader = new FXMLLoader(toURL(path));
        return loadToObserver(loader, resetObserver);
    }

    private Region loadToObserver(FXMLLoader loader, ResetObserver resetObserver) {
        Region output;
        try {
            output = loader.load();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        String location = loader.getLocation().toString();
        if (!location.contains("widget")) resetObserver.addController(loader);

        if (location.contains("souvenir")) loadSouvenirController(loader);
        else if (location.contains("blind_alley")) loadBlindAlleyController(loader);
        return output;
    }

    private void loadBlindAlleyController(FXMLLoader loader) {
        ObserverHub.addObserver(new BlindAlleyPaneObserver(loader.getController()));
    }

    private void loadSouvenirController(FXMLLoader loader) {
        ObserverHub.addObserver(new SouvenirPaneObserver(loader.getController()));
    }

    private List<String> getFilesFromDirectory(final File topLevelDirectory) throws NullPointerException {
        List<String> list = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(topLevelDirectory.listFiles())) {
            if (fileEntry.isDirectory())
                list.addAll(getFilesFromDirectory(fileEntry));
            else
                list.add(fileEntry.getPath());
        }
        return list;
    }

    private Map<Toggle, Region> createRegionMap(Map<String, Toggle> radioButtonMap,
                                                Map<String, Region> filePathMap) {
        Map<Toggle, Region> regionMap = new IdentityHashMap<>();
        radioButtonMap.keySet()
                .forEach(key -> regionMap.put(
                        radioButtonMap.get(key),
                        filePathMap.get(key)
                        ));
        return regionMap;
    }

    private URI toURI(URL url) throws IllegalArgumentException {
        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private URL toURL(URI uri) throws IllegalArgumentException {
        try {
            return uri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
