package bomb.modules.s.souvenir;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.pattern.factory.TextFormatterFactory;
import bomb.tools.pattern.observer.Observer;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.javatuples.Pair;

public class SouvenirController implements Observer, Resettable {
    private final ObservableList<Pair<String, String>> souvenirData;
    private final FilteredList<Pair<String, String>> filteredData;

    @FXML
    private MFXTextField searchField;

    @FXML
    private TableView<Pair<String, String>> artifactView;

    @FXML
    private TableColumn<Pair<String, String>, String> keyColumn, answerColumn;

    public SouvenirController() {
        souvenirData = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(souvenirData, b -> true);
    }

    public void initialize() {
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("Value0"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("Value1"));
        searchField.setTextFormatter(TextFormatterFactory.createNewLineRestrictionFormatter());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entry -> {
                if (newValue == null || newValue.isEmpty())
                    return true;
                String lowercaseSearch = newValue.toLowerCase();

                if (entry.getValue0().toLowerCase().contains(lowercaseSearch)) {
                    return true;
                } else return entry.getValue1().toLowerCase().contains(lowercaseSearch);
            });
            artifactView.setItems(filteredData);
        });
    }

    @Override
    public void reset() {
        Souvenir.reset();
        FacadeFX.clearText(searchField);
        FacadeFX.disable(searchField);
        souvenirData.clear();
        artifactView.setItems(filteredData);
    }

    @Override
    public void update() {
        souvenirData.clear();
        souvenirData.addAll(Souvenir.getPuzzleArtifacts());
        artifactView.setItems(souvenirData);

        if (searchField.isDisable() && !souvenirData.isEmpty())
            FacadeFX.enable(searchField);
    }
}
