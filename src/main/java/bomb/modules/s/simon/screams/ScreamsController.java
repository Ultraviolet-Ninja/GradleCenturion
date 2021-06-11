package bomb.modules.s.simon.screams;

import bomb.components.simon.screams.CustomStar;
import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

public class ScreamsController {
    @FXML
    private Button solve;

    @FXML
    private CustomStar star;

    @FXML
    private TextArea resultArea;

    @FXML
    private ToggleButton colorSelectorToggle;

    @FXML
    private void setSelector(){
        if (!colorSelectorToggle.isSelected() && !star.confirmDifferentColors()) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, "2+ edges have the same color\n\t\tOR\nThere's a white edge");
            colorSelectorToggle.setSelected(true);
        } else {
            star.setSelectorMode(colorSelectorToggle.isSelected());
            if (!colorSelectorToggle.isSelected()){
                try{
                    SimonScreams.init(star.collectOrder());
                    FacadeFX.disable(colorSelectorToggle);
                    FacadeFX.enable(solve);
                } catch(IllegalArgumentException ex){
                    FacadeFX.setAlert(Alert.AlertType.ERROR, ex.getMessage());
                    colorSelectorToggle.setSelected(true);
                }
            }
        }
    }

    @FXML
    private void collectClicks(){
        try {
            String output = SimonScreams.nextSolve(star.collectFlashOrder());
            StringBuilder sb = new StringBuilder();
            for (String point : output.split(",")) {
                sb.append("\u2022 ").append(point).append("\n");
            }
            resultArea.setText(sb.toString());
            star.resetClicks();
        } catch (IllegalArgumentException ex){
            FacadeFX.setAlert(Alert.AlertType.ERROR, ex.getMessage());
        }
    }

    @FXML
    private void resetStar(){
        star.resetStar();
        FacadeFX.unselectButtons(colorSelectorToggle);
        FacadeFX.enable(colorSelectorToggle);
        FacadeFX.disable(solve);
        SimonScreams.reset();
    }
}
