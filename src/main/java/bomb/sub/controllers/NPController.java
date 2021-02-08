package bomb.sub.controllers;

import bomb.modules.np.Neutralization;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import static bomb.tools.Mechanics.NUMBER_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

public class NPController{
    private Color solColor = null;
    private double volume;
    private final ToggleGroup acidColors = new ToggleGroup();
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
    private ToggleButton red, yellow, green, blue;

    public void initialize(){
        red.setToggleGroup(acidColors);
        yellow.setToggleGroup(acidColors);
        green.setToggleGroup(acidColors);
        blue.setToggleGroup(acidColors);
    }

    @FXML
    private void titrate(){
        //TODO - For GUI Overhaul, TextFields get cyan text with black background
        try {
            String[] answers = Neutralization.getStats((int) volume, solColor).split("-");
            chemName.setText(answers[0]);
            chemForm.setText(answers[1]);
            dropCount.setText(answers[2]);
            setFilter(answers[3]);
        } catch (IllegalArgumentException illegal){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(illegal.getMessage());
            alert.showAndWait();
        }
    }

    private void setFilter(String fil){
        if (fil.contains("No")){
            filterInstr.setText("Filter: Off");
            filterInstr.setStyle("-fx-background-color: #fa0001; -fx-text-fill: whitesmoke");
        } else {
            filterInstr.setText("Filter: On");
            filterInstr.setStyle("-fx-background-color: #00ff01; -fx-text-fill: whitesmoke");
        }
    }

    @FXML
    private void volume() {
        String sample = ultimateFilter(acidVolume.getText(), NUMBER_REGEX);
        if (!sample.isEmpty()) {
            volume = Double.parseDouble(sample);
            testTube.setProgress(volume / 20);
            volTyped = true;
            unlock();
        }
    }

    @FXML
    private void changeColor(){
        if (red.isSelected()){
            testTube.setStyle("-fx-accent: #ea0001");
            solColor = Color.RED;
        } else if (yellow.isSelected()){
            testTube.setStyle("-fx-accent: #f8db03");
            solColor = Color.YELLOW;
        } else if (green.isSelected()){
            testTube.setStyle("-fx-accent: #00fe01");
            solColor = Color.GREEN;
        } else {
            testTube.setStyle("-fx-accent: #00007e");
            solColor = Color.BLUE;
        }
        unlock();
    }

    private void unlock(){
        if (solColor != null && volTyped) solve.setDisable(false);
    }
}