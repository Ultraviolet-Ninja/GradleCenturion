package bomb.modules.s.souvenir;

import bomb.abstractions.Resettable;
import bomb.tools.facade.FacadeFX;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.tableview2.TableColumn2;
import org.controlsfx.control.tableview2.TableView2;

import java.util.Map;

public class SouvenirController implements Resettable {
    private final ObservableList<Map.Entry<String, String>> souvenirData;
    private final FilteredList<Map.Entry<String, String>> filteredData;

    @FXML
    private JFXTextArea searchArea;

    @FXML
    private TableView2<Map.Entry<String, String>> artifactView;

    @FXML
    private TableColumn2<Map.Entry<String, String>, String> keyColumn, answerColumn;

    public SouvenirController() {
        souvenirData = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(souvenirData, b -> true);
    }

    public void initialize() {
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        searchArea.setTextFormatter(createNewLineRestrictionFormatter());
        searchArea.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entry -> {
                if (newValue == null || newValue.isEmpty())
                    return true;
                String lowercaseSearch = newValue.toLowerCase();

                if (entry.getKey().toLowerCase().contains(lowercaseSearch)) {
                    return true;
                } else return entry.getValue().toLowerCase().contains(lowercaseSearch);
            });
            artifactView.setItems(filteredData);
        });
    }

    private TextFormatter<String> createNewLineRestrictionFormatter() {
        return new TextFormatter<>(change ->
                (!change.isContentChange() || !change.getControlNewText().contains("\n")) ?
                        change :
                        null);
    }

    public void liveUpdate() {
        souvenirData.clear();
        souvenirData.addAll(Souvenir.getPuzzleArtifacts().entrySet());

        if (searchArea.isDisable() && !souvenirData.isEmpty())
            FacadeFX.enable(searchArea);
    }

    @FXML
    private void searchColumns() {
//        artifactView.setItems(filteredData);
    }

    @Override
    public void reset() {
        Souvenir.reset();
        //TODO - Reset the columns
        FacadeFX.clearText(searchArea);
        FacadeFX.disable(searchArea);
        souvenirData.clear();
        artifactView.setItems(souvenirData);
    }
}
