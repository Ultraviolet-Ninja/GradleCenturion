package bomb.modules.s.souvenir;

import bomb.abstractions.Resettable;
import bomb.tools.TextFormatterFactory;
import bomb.tools.facade.FacadeFX;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import org.controlsfx.control.tableview2.TableColumn2;
import org.controlsfx.control.tableview2.TableView2;

public class SouvenirController implements Resettable {
    private final ObservableList<Pair<String, String>> souvenirData;
    private final FilteredList<Pair<String, String>> filteredData;

    @FXML private JFXTextArea searchArea;

    @FXML private TableView2<Pair<String, String>> artifactView;

    @FXML private TableColumn2<Pair<String, String>, String> keyColumn, answerColumn;

    public SouvenirController() {
        souvenirData = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(souvenirData, b -> true);
    }

    public void initialize() {
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("Key"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));
        searchArea.setTextFormatter(TextFormatterFactory.createNewLineRestrictionFormatter());
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

    public void liveUpdate() {
        souvenirData.clear();
        souvenirData.addAll(Souvenir.getPuzzleArtifacts());
        artifactView.setItems(souvenirData);

        if (searchArea.isDisable() && !souvenirData.isEmpty())
            FacadeFX.enable(searchArea);
    }

    @Override
    public void reset() {
        Souvenir.reset();
        FacadeFX.clearText(searchArea);
        FacadeFX.disable(searchArea);
        souvenirData.clear();
        artifactView.setItems(filteredData);
    }
}
