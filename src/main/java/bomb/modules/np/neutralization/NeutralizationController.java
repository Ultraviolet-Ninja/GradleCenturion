package bomb.modules.np.neutralization;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import static bomb.modules.np.neutralization.Neutralization.OUTPUT_SEPARATOR;
import static bomb.tools.filter.RegexFilter.NUMBER_PATTERN;
import static bomb.tools.filter.RegexFilter.filter;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.YELLOW;

public final class NeutralizationController implements Resettable {
    private static final double MAX_VOLUME = 20;
    private static final String RED_STYLE = "-fx-accent: #ea0001", YELLOW_STYLE = "-fx-accent: #f8db03",
            GREEN_STYLE = "-fx-accent: #00fe01", BLUE_STYLE = "-fx-accent: #00007e",
            NO_FILTER_STYLE = "-fx-background-color: #fa0001; -fx-text-fill: whitesmoke",
            FILTER_STYLE = "-fx-background-color: #00ff01; -fx-text-fill: whitesmoke";

    private Color solutionColor = null;
    private double volume;
    private boolean volTyped = false;

    @FXML
    private Button solve;

    @FXML
    private Label filterInstr;

    @FXML
    private ProgressBar testTube;

    @FXML
    private TextField chemName, chemForm, acidVolume, dropCount;

    @FXML
    private ToggleGroup acidColors;

    @FXML
    private void titrate() {
        //TODO - For GUI Overhaul, TextFields get cyan text with black background
        try {
            String[] answers = Neutralization.titrate((int) volume, solutionColor).split(OUTPUT_SEPARATOR);
            chemName.setText(answers[0]);
            chemForm.setText(answers[1]);
            dropCount.setText(answers[2]);
            setFilter(answers[3]);
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(illegal.getMessage());
        }
    }

    private void setFilter(String fil) {
        boolean cond = fil.contains("No");
        filterInstr.setStyle(cond ? NO_FILTER_STYLE : FILTER_STYLE);
        filterInstr.setText("Filter: " + (cond ? "Off" : "On"));
    }

    @FXML
    private void volume() {
        String sample = filter(acidVolume.getText(), NUMBER_PATTERN);
        if (!sample.isEmpty()) {
            volume = Double.parseDouble(sample);
            testTube.setProgress(volume / MAX_VOLUME);
        } else testTube.setProgress(0);
        volTyped = !sample.isEmpty();
        toggleLock();
    }

    @FXML
    private void changeColor() {
        switch (FacadeFX.getToggleName(acidColors)) {
            case "Red" -> {
                testTube.setStyle(RED_STYLE);
                solutionColor = RED;
            }
            case "Yellow" -> {
                testTube.setStyle(YELLOW_STYLE);
                solutionColor = YELLOW;
            }
            case "Green" -> {
                testTube.setStyle(GREEN_STYLE);
                solutionColor = GREEN;
            }
            default -> {
                testTube.setStyle(BLUE_STYLE);
                solutionColor = BLUE;
            }
        }
        toggleLock();
    }

    private void toggleLock() {
        solve.setDisable(!(solutionColor != null && volTyped));
    }

    @Override
    public void reset() {

    }
}
