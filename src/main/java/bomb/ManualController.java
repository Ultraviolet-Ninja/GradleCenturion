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
import javafx.scene.control.ToggleButton;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static bomb.tools.filter.RegexFilter.ALL_CHAR_FILTER;
import static bomb.tools.filter.RegexFilter.filter;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.BLIND_ALLEY_PANE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.SOUVENIR_PANE;
import static java.util.stream.Collectors.toList;

public class ManualController {
    private final Map<String, Region> regionMap;
    private final List<Node> allRadioButtons;

    @FXML private GridPane base;

    @FXML private JFXRadioButton forgetMeNot, souvenir;

    @FXML private TextField searchBar;

    @FXML private ToggleGroup options;

    @FXML private VBox menuVBox, radioButtonHouse;

    public ManualController() {
        regionMap = new HashMap<>();
        allRadioButtons = new ArrayList<>();
    }

    public void initialize() throws URISyntaxException {
        allRadioButtons.addAll(radioButtonHouse.getChildren());
        ObserverHub.addObserver(new ForgetMeNotToggleObserver(forgetMeNot));
        ObserverHub.addObserver(new SouvenirToggleObserver(souvenir));
        setupMap();
    }

    @FXML
    public void buttonPress() {
        String selected = FacadeFX.getToggleName(options);
        if (selected.equals("Blind Alley")) ObserverHub.updateAtIndex(BLIND_ALLEY_PANE);
        if (selected.equals("Souvenir")) ObserverHub.updateAtIndex(SOUVENIR_PANE);
        paneSwitch(regionMap.get(selected));
    }

    private void paneSwitch(final Region pane) {
        if (base.getChildren().size() != 1) base.getChildren().retainAll(menuVBox);
        base.add(pane, 0, 0);
    }

    @FXML
    public void search() {
        String searchTerm = searchBar.getText();
        radioButtonHouse.getChildren().clear();

        if (searchTerm.isEmpty()){
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

    private void setupMap() throws IllegalArgumentException, URISyntaxException {
        URI path = Objects.requireNonNull(ManualController.class.getResource("fxml")).toURI();

        List<Toggle> radioButtonList = new ArrayList<>(options.getToggles());
        List<String> filePathList = getFilesFromDirectory(new File(path));

        filePathList.removeIf(location -> location.contains("solutions") || location.contains("new") || location.contains("old"));

        List<String> formattedRadioButtonNameList = formatWords(radioButtonList),
                filteredLocationNames = filterPathNames(filePathList);

        List<Region> regionList = createRegionList(filePathList);
        setPairs(radioButtonList, formattedRadioButtonNameList, regionList, filteredLocationNames);
    }

    private List<String> formatWords(List<Toggle> nameList) {
        String newRegex = ALL_CHAR_FILTER.getOriginalPattern()
                .replace("]", "_]");
        Regex regex = new Regex(newRegex);

        return nameList.stream()
                .map(toggle -> ((ToggleButton)toggle).getText())
                .map(name -> name.replaceAll("[ -]", "_"))
                .map(String::toLowerCase)
                .map(name -> filter(name, regex))
                .collect(toList());
    }

    private List<Region> createRegionList(List<String> fileLocations) {
        List<Region> paneList = new ArrayList<>();
        ResetObserver resetObserver = new ResetObserver();
//        try {
//            for (String singleLocation : fileLocations) {
//                FXMLLoader loader = new FXMLLoader(Paths.get(singleLocation).toUri().toURL());
//                paneList.add(loader.load());
//                if (!singleLocation.contains("widget")) resetObserver.addController(loader);
//
//                if (singleLocation.contains("souvenir")) extractSouvenirController(loader);
//                else if (singleLocation.contains("blind_alley")) extractBlindAlleyController(loader);
//            }
//        } catch (IOException e){
//            e.printStackTrace();
//        }

        paneList = fileLocations.stream()
                .parallel()
                .map(Paths::get)
                .map(Path::toUri)
                .map(this::toURL)
                .map(FXMLLoader::new)
                .map(loader -> createRegion(loader, resetObserver))
                .collect(toList());

        ObserverHub.addObserver(resetObserver);
        return paneList;
    }

    private URL toURL(URI uri) throws IllegalArgumentException {
        try {
            return uri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Region createRegion(FXMLLoader loader, ResetObserver resetObserver) {
        String location = loader.getLocation().toString();
        try {
            Region output = loader.load();

            if (!location.contains("widget")) resetObserver.addController(loader);

            if (location.contains("souvenir")) extractSouvenirController(loader);
            else if (location.contains("blind_alley")) extractBlindAlleyController(loader);
            return output;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    private void extractBlindAlleyController(FXMLLoader loader){
        ObserverHub.addObserver(new BlindAlleyPaneObserver(loader.getController()));
    }

    private void extractSouvenirController(FXMLLoader loader){
        ObserverHub.addObserver(new SouvenirPaneObserver(loader.getController()));
    }

    private List<String> getFilesFromDirectory(final File topLevelDirectory) throws IllegalArgumentException{
        List<String> list = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(topLevelDirectory.listFiles())){
            if (fileEntry.isDirectory())
                list.addAll(getFilesFromDirectory(fileEntry));
            else
                list.add(fileEntry.getPath());
        }
        return list;
    }

    private List<String> filterPathNames(List<String> originalLocations){
        Regex filenamePattern = new Regex("\\w+\\.");
        filenamePattern.loadCollection(originalLocations);
        return filenamePattern.stream()
                .map(line -> line.replace(".", ""))
                .collect(toList());
    }

    private void setPairs(List<Toggle> toggleList, List<String> toggleListFormatted,
                          List<Region> paneList, List<String> paneLocationsFormatted){
        regionMap.clear();
        for (int i = 0; i < toggleList.size(); i++){
            String keyText = ((ToggleButton)toggleList.get(i)).getText();
            Region valuePane = paneList.get(paneLocationsFormatted.indexOf(toggleListFormatted.get(i)));
            regionMap.put(keyText, valuePane);
        }
    }
}

