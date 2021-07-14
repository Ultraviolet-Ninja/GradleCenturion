package bomb.modules.s.souvenir;

import bomb.abstractions.Resettable;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Pair;

import java.util.List;

public class NewSouvenirController implements Resettable {
    @FXML private JFXTreeTableView<?> artifactView;

    @FXML private TreeTableColumn<?, ?> key, answer;

    @FXML private JFXTextArea searchArea;

    public void liveUpdate() {
        List<Pair<String, String>> injectionList = NewSouvenir.updateTableView();
        if (!injectionList.isEmpty()){
            for (Pair<String, String> pair : injectionList){
                TreeItem<String> keyItem = new TreeItem<>(pair.getKey());
                TreeItem<String> answerItem = new TreeItem<>(pair.getValue());

            }
        }
    }

    @FXML
    private void keySearch() {

    }

    @Override
    public void reset() {
        NewSouvenir.reset();
    }
}
