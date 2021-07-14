package bomb.modules.s.souvenir;

import bomb.abstractions.Resettable;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import org.controlsfx.control.tableview2.TableColumn2;
import org.controlsfx.control.tableview2.TableView2;

import java.util.List;

public class NewSouvenirController implements Resettable {
    @FXML private TableView2<Pair<String, String>> artifactView;

    @FXML private TableColumn2<Pair<String, String>, String> keyColumn, answerColumn;

    @FXML private JFXTextArea searchArea;

    public void initialize() {
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        searchArea.setTextFormatter(createNewLineRestrictionFormatter());
    }

    private TextFormatter<String> createNewLineRestrictionFormatter(){
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) return change;
            if (change.getControlNewText().contains("\n")) return null;

            return change;
        });
    }

    public void liveUpdate() {
        List<Pair<String, String>> injectionList = NewSouvenir.updateTableView();
        if (!injectionList.isEmpty()){
            for (Pair<String, String> pair : injectionList){
                artifactView.getItems().add(pair);
            }
        }
    }

    @FXML
    private void keySearch() {

    }

    @Override
    public void reset() {
        NewSouvenir.reset();
        //TODO - Reset the columns
    }
}
