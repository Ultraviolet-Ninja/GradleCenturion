package bomb.modules.s.simon.screams;

import bomb.abstractions.Resettable;
import bomb.components.simon.screams.CustomStar;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

import static bomb.tools.string.StringFormat.BULLET_POINT;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public final class ScreamsController implements Resettable {
    @FXML
    private Button solve, resetLastStage;

    @FXML
    private CustomStar star;

    @FXML
    private Label stageCounter;

    @FXML
    private TextArea resultArea;

    @FXML
    private ToggleButton colorSelectorToggle;

    @FXML
    private void setSelector() {
        if (!colorSelectorToggle.isSelected() && !star.confirmDifferentColors()) {
            FacadeFX.setAlert("2+ edges have the same color\n\t\tOR\nThere's a white edge");
            colorSelectorToggle.setSelected(true);
            return;
        }
        star.setSelectorMode(colorSelectorToggle.isSelected());
        if (!colorSelectorToggle.isSelected()) {
            try {
                SimonScreams.initialize(star.collectOrder());
                FacadeFX.disable(colorSelectorToggle);
                FacadeFX.enable(solve);
            } catch (IllegalArgumentException ex) {
                FacadeFX.setAlert(ex.getMessage());
                colorSelectorToggle.setSelected(true);
            }
        }
    }

    @FXML
    private void collectClicks() {
        try {
            String output = SimonScreams.nextSolve(star.collectFlashOrder());

            String outputText = stream(output.split(","))
                    .map(sample -> BULLET_POINT + sample)
                    .collect(joining("\n"));

            resultArea.setText(outputText);
            star.resetClicks();
            updateStageNumber();
            resetLastStage.setDisable(false);
        } catch (IllegalArgumentException ex) {
            FacadeFX.setAlert(ex.getMessage());
        }
    }

    @FXML
    public void resetLastStagePress() {
        SimonScreams.resetLastStage();
        updateStageNumber();
        resetLastStage.setDisable(SimonScreams.getStage() == 1);
    }

    private void updateStageNumber() {
        stageCounter.setText("Stage " + (SimonScreams.getStage() + 1));
    }

    @FXML
    private void resetStar() {
        star.reset();
        FacadeFX.setToggleButtonsUnselected(colorSelectorToggle);
        FacadeFX.enable(colorSelectorToggle);
        FacadeFX.disable(solve);
        SimonScreams.reset();
        updateStageNumber();
    }

    @Override
    public void reset() {
        resetStar();
    }
}
