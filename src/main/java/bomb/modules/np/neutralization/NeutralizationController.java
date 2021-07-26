package bomb.modules.np.neutralization;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import static bomb.tools.filter.Filter.NUMBER_PATTERN;
import static bomb.tools.filter.Filter.ultimateFilter;

public class NeutralizationController implements Resettable {
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
    private void titrate(){
        //TODO - For GUI Overhaul, TextFields get cyan text with black background
        try {
            String[] answers = Neutralization.titrate((int) volume, solutionColor).split("-");
            chemName.setText(answers[0]);
            chemForm.setText(answers[1]);
            dropCount.setText(answers[2]);
            setFilter(answers[3]);
        } catch (IllegalArgumentException illegal){
            FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
        }
    }

    private void setFilter(String fil){
        boolean cond = fil.contains("No");
        filterInstr.setStyle(cond ? NO_FILTER_STYLE : FILTER_STYLE);
        filterInstr.setText("Filter: " + (cond ? "Off" : "On"));
    }

    @FXML
    private void volume() {
        String sample = ultimateFilter(acidVolume.getText(), NUMBER_PATTERN);
        if (!sample.isEmpty()) {
            volume = Double.parseDouble(sample);
            testTube.setProgress(volume / MAX_VOLUME);
        } else testTube.setProgress(0);
        volTyped = !sample.isEmpty();
        toggleLock();
    }

    @FXML
    private void changeColor(){
        switch(FacadeFX.getToggleName(acidColors)){
            case "Red":{
                testTube.setStyle(RED_STYLE);
                solutionColor = Color.RED;
                break;
            }
            case "Yellow":{
                testTube.setStyle(YELLOW_STYLE);
                solutionColor = Color.YELLOW;
                break;
            }
            case "Green":{
                testTube.setStyle(GREEN_STYLE);
                solutionColor = Color.GREEN;
                break;
            }
            default:{
                testTube.setStyle(BLUE_STYLE);
                solutionColor = Color.BLUE;
            }
        }
        toggleLock();
    }

    private void toggleLock(){
        solve.setDisable(!(solutionColor != null && volTyped));
    }

    @Override
    public void reset() {

    }
}
