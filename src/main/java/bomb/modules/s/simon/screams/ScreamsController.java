package bomb.modules.s.simon.screams;

import bomb.components.simon.screams.CustomStar;
import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;

public class ScreamsController {
    @FXML
    private CustomStar star;

    @FXML
    private ToggleButton selectorToggle;

    @FXML
    private void setSelector(){
        if (!selectorToggle.isSelected() && star.confirmDifferentColors())
            FacadeFX.setAlert(Alert.AlertType.ERROR, "2+ edges have the same color");
        else star.setSelectorMode(selectorToggle.isSelected());
    }

    @FXML
    private void collectClicks(){

    }

    @FXML
    private void resetStar(){

    }
}
