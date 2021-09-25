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
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static bomb.tools.filter.Mechanics.NORMAL_CHAR_REGEX;
import static bomb.tools.filter.Mechanics.ultimateFilter;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.BLIND_ALLEY_PANE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.SOUVENIR_PANE;

public class ManualController {
    private Map<String, Region> regionMap;
    private List<Node> allRadioButtons;

    @FXML private GridPane base;

    @FXML private JFXRadioButton forgetMeNot, souvenir;

    @FXML private TextField searchBar;

    @FXML private ToggleGroup options;

    @FXML private VBox menuVBox, radioButtonHouse;

    public void initialize() {
        allRadioButtons = new ArrayList<>(radioButtonHouse.getChildren());
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

        int i = 0;
        while (i < allRadioButtons.size()) {
            Node node = allRadioButtons.get(i);
            searchPattern.loadText(((RadioButton) node).getText());
            if (searchPattern.hasMatch())
                radioButtonHouse.getChildren().add(node);
            i++;
        }
    }

    private void setupMap() throws IllegalArgumentException {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\bomb\\fxml";
        List<Toggle> radioButtonList = new ArrayList<>(options.getToggles());
        List<String> filePathList = getFilesFromDirectory(new File(path));
        filePathList.removeIf(location -> location.contains("solutions") || location.contains("new") || location.contains("old"));

        List<String> formattedRadioButtonNameList = formatWords(radioButtonList.iterator()),
                filteredLocationNames = filterPathNames(filePathList);

        List<Region> regionList = createRegionList(filePathList);
        setPairs(radioButtonList, formattedRadioButtonNameList, regionList, filteredLocationNames);
    }

    private List<String> formatWords(Iterator<Toggle> nameIterator) {
        List<String> list = new ArrayList<>();
        while(nameIterator.hasNext()){
            String line = ((ToggleButton)nameIterator.next()).getText().replace(" ", "_")
                    .replace("-", "_");
            list.add(ultimateFilter(line, NORMAL_CHAR_REGEX, "_"));
        }
        return list;
    }

    private List<Region> createRegionList(List<String> fileLocations) {
        List<Region> paneList = new ArrayList<>();
        ResetObserver resetObserver = new ResetObserver();
        try {
            int i = 0;
            while (i < fileLocations.size()) {
                String fxmlFile = fileLocations.get(i);
                FXMLLoader loader = new FXMLLoader(Paths.get(fxmlFile).toUri().toURL());
                paneList.add(loader.load());
                if (!fxmlFile.contains("widget")) resetObserver.addController(loader);
                if (fxmlFile.contains("souvenir")) extractSouvenirController(loader);
                if (fxmlFile.contains("blind_alley")) extractBlindAlleyController(loader);
                i++;
            }
            ObserverHub.addObserver(resetObserver);
        } catch (IOException e){
            e.printStackTrace();
        }
        return paneList;
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
        List<String> outputList = new ArrayList<>();
        Regex filenamePattern = new Regex("\\w+\\.");
        filenamePattern.loadCollection(originalLocations);
        for (String result : filenamePattern) outputList.add(result.substring(0, result.length() - 1));
        return outputList;
    }

    private void setPairs(List<Toggle> toggleList, List<String> toggleListFormatted,
                          List<Region> paneList, List<String> paneLocationsFormatted){
        regionMap = new HashMap<>();
        for (int i = 0; i < toggleList.size(); i++){
            String keyText = ((ToggleButton)toggleList.get(i)).getText();
            Region valuePane = paneList.get(paneLocationsFormatted.indexOf(toggleListFormatted.get(i)));
            regionMap.put(keyText, valuePane);
        }
    }
}

