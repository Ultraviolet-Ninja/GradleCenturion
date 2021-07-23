package bomb;

import bomb.tools.Regex;
import bomb.tools.facade.FacadeFX;
import bomb.tools.observer.BlindAlleyPaneObserver;
import bomb.tools.observer.ForgetMeNotToggleObserver;
import bomb.tools.observer.ObserverHub;
import bomb.tools.observer.ResetObserver;
import bomb.tools.observer.SouvenirPaneObserver;
import bomb.tools.observer.SouvenirToggleObserver;
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
import javafx.scene.layout.Pane;
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

import static bomb.tools.Mechanics.NORMAL_CHAR_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;
import static bomb.tools.observer.ObserverHub.ObserverIndex.BLIND_ALLEY_PANE;
import static bomb.tools.observer.ObserverHub.ObserverIndex.SOUVENIR_PANE;

public class ManualController {
    private Map<String, Region> regionMap;
    private List<Node> allRadioButtons;

    @FXML private GridPane base;

    @FXML private JFXRadioButton forgetMeNot, souvenir;

    @FXML private TextField searchBar;

    @FXML private ToggleGroup options;

    @FXML private VBox menuVBox, radioButtonHouse;

    public void initialize(){
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
    public void search(){
        String searchTerm = searchBar.getText();
        radioButtonHouse.getChildren().clear();
        if (searchTerm.isEmpty()){
            radioButtonHouse.getChildren().addAll(allRadioButtons);
            return;
        }
        Regex searchPattern = new Regex(searchTerm, Pattern.CASE_INSENSITIVE);

        for(Node node : allRadioButtons){
            searchPattern.loadText(((RadioButton)node).getText());
            if (searchPattern.hasMatch())
                radioButtonHouse.getChildren().add(node);
        }
    }

    private void setupMap() throws IllegalArgumentException{
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\bomb\\fxml";
        List<Toggle> nameList = new ArrayList<>(options.getToggles());
        List<String> formattedNameList = formatWords(nameList.iterator()),
                paneLocations = filesFromFolder(new File(path)),
                filteredLocations = filterLocations(paneLocations);
        List<Pane> paneList = panesFromFolder(paneLocations);
        setPairs(nameList, formattedNameList, paneList, filteredLocations);
    }

    private ArrayList<String> formatWords(Iterator<Toggle> nameIterator){
        ArrayList<String> list = new ArrayList<>();
        while(nameIterator.hasNext()){
            String line = ((ToggleButton)nameIterator.next()).getText().replace(" ", "_")
                    .replace("-", "_");
            list.add(ultimateFilter(line, NORMAL_CHAR_REGEX, "_"));
        }
        return list;
    }

    private ArrayList<Pane> panesFromFolder(List<String> fileLocations) {
        ArrayList<Pane> paneList = new ArrayList<>();
        ResetObserver observer = new ResetObserver();
        try {
            for (String fxmlFile : fileLocations) {
                FXMLLoader loader = new FXMLLoader(Paths.get(fxmlFile).toUri().toURL());
                paneList.add(loader.load());
                if (!fxmlFile.contains("widget")) observer.addController(loader);
                if (fxmlFile.contains("souvenir")) extractSouvenirController(loader);
                if (fxmlFile.contains("blind_alley")) extractBlindAlleyController(loader);
            }
            ObserverHub.addObserver(observer);
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

    private ArrayList<String> filesFromFolder(final File folder) throws IllegalArgumentException{
        ArrayList<String> list = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())){
            if (fileEntry.isDirectory())
                list.addAll(filesFromFolder(fileEntry));
            else
                list.add(fileEntry.getPath());
        }
        return list;
    }

    private ArrayList<String> filterLocations(List<String> originalLocations){
        ArrayList<String> outputList = new ArrayList<>();
        Regex filenamePattern = new Regex("\\w+\\.");
        filenamePattern.loadCollection(originalLocations);
        for (String result : filenamePattern) outputList.add(result.substring(0, result.length() - 1));
        return outputList;
    }

    private void setPairs(List<Toggle> toggleList, List<String> toggleListFormatted,
                          List<Pane> paneList, List<String> paneLocationsFormatted){
        regionMap = new HashMap<>();
        for (int i = 0; i < toggleList.size(); i++){
            String keyText = ((ToggleButton)toggleList.get(i)).getText();
            Region valuePane = paneList.get(paneLocationsFormatted.indexOf(toggleListFormatted.get(i)));
            regionMap.put(keyText, valuePane);
        }
    }
}

