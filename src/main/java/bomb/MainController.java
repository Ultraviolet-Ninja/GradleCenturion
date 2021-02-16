package bomb;

import bomb.tools.FacadeFX;
import bomb.tools.observer.BlindAlleyObserver;
import bomb.tools.observer.ForgetMeNotObserver;
import bomb.tools.observer.ObserverHub;
import bomb.tools.observer.SouvenirObserver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import static bomb.tools.Mechanics.NORMAL_CHAR_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

public class MainController {
    private HashMap<String, Region> regionMap;

    @FXML private Pane displayPane;

    @FXML private RadioButton forgetMeNot, souvenir;

    @FXML private ToggleGroup group;

    public void initialize(){
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

    private void setupMap() throws IllegalArgumentException{
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\bomb\\fxml";
        ArrayList<Toggle> nameList = new ArrayList<>(group.getToggles());
        ArrayList<String> formattedNameList = formatWords(nameList.iterator()),
                paneLocations = filesFromFolder(new File(path)),
                filteredLocations = filterLocations(paneLocations);
        ArrayList<Pane> paneList = panesFromFolder(paneLocations);
        setPairs(nameList, formattedNameList, paneList, filteredLocations);
        System.out.println();
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
        try {
            for (String fxmlFile : fileLocations) {
                FXMLLoader loader = new FXMLLoader(Paths.get(fxmlFile).toUri().toURL());
                paneList.add(loader.load());
                if (fxmlFile.contains("blind_alley")) getBlindAlleyController(loader);
            }
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
        for (final String location : originalLocations){
            String[] splits = location.replace("\\", "/").split("/");
            outputList.add(splits[splits.length - 1].replace(".fxml", ""));
        }
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

