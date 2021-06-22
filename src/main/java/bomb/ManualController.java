package bomb;

import bomb.tools.FacadeFX;
import bomb.tools.Regex;
import bomb.tools.observer.BlindAlleyObserver;
import bomb.tools.observer.ForgetMeNotObserver;
import bomb.tools.observer.ObserverHub;
import bomb.tools.observer.ResetObserver;
import bomb.tools.observer.SouvenirObserver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static bomb.tools.Mechanics.NORMAL_CHAR_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

public class ManualController {
    private Map<String, Region> regionMap;
    private ArrayList<Node> allRadioButtons;

    @FXML private Pane displayPane;

    @FXML private RadioButton forgetMeNot, souvenir;

    @FXML private TextField searchBar;

    @FXML private ToggleGroup group;

    @FXML private VBox radioButtonHouse;

    public void initialize(){
        allRadioButtons = new ArrayList<>(radioButtonHouse.getChildren());
        ObserverHub.addObserver(new ForgetMeNotObserver(forgetMeNot));
        ObserverHub.addObserver(new SouvenirObserver(souvenir));
        setupMap();
    }

    @FXML
    public void buttonPress() {
        String selected = FacadeFX.getToggleName(group);
        if (selected.equals("Blind Alley")) ObserverHub.updateAtIndex(ObserverHub.BLIND_ALLEY_INDEX);
        paneSwitch(regionMap.get(selected));
    }

    private void paneSwitch(final Region pane) {
        displayPane.getChildren().clear();
        displayPane.getChildren().add(pane);
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
        ArrayList<Toggle> nameList = new ArrayList<>(group.getToggles());
        ArrayList<String> formattedNameList = formatWords(nameList.iterator()),
                paneLocations = filesFromFolder(new File(path)),
                filteredLocations = filterLocations(paneLocations);
        ArrayList<Pane> paneList = panesFromFolder(paneLocations);
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

    private ArrayList<Pane> panesFromFolder(ArrayList<String> fileLocations) {
        ArrayList<Pane> paneList = new ArrayList<>();
        ResetObserver observer = new ResetObserver();
        try {
            for (String fxmlFile : fileLocations) {
                FXMLLoader loader = new FXMLLoader(Paths.get(fxmlFile).toUri().toURL());
                paneList.add(loader.load());
                if (!fxmlFile.contains("widget")) observer.addController(loader);
                if (fxmlFile.contains("blind_alley")) getBlindAlleyController(loader);
            }
            ObserverHub.addObserver(observer);
        } catch (IOException e){
            e.printStackTrace();
        }
        return paneList;
    }

    private void getBlindAlleyController(FXMLLoader loader){
        ObserverHub.addObserver(new BlindAlleyObserver(loader.getController()));
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

    private ArrayList<String> filterLocations(ArrayList<String> originalLocations){
        ArrayList<String> outputList = new ArrayList<>();
        Regex filenamePattern = new Regex("\\w+\\.");
        filenamePattern.loadCollection(originalLocations);
        for (String result : filenamePattern) outputList.add(result.substring(0, result.length() - 1));
        return outputList;
    }

    private void setPairs(ArrayList<Toggle> toggleList, ArrayList<String> toggleListFormatted,
                          ArrayList<Pane> paneList, ArrayList<String> paneLocationsFormatted){
        regionMap = new HashMap<>();
        for (int i = 0; i < toggleList.size(); i++){
            String keyText = ((ToggleButton)toggleList.get(i)).getText();
            Region valuePane = paneList.get(paneLocationsFormatted.indexOf(toggleListFormatted.get(i)));
            regionMap.put(keyText, valuePane);
        }
    }
}

