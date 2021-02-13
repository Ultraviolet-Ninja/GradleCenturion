package bomb.sub.controllers;

import bomb.modules.t.bulb.TheBulb;
import bomb.modules.t.two_bit.TwoBit;
import bomb.modules.t.bulb.Bulb;
import bomb.interfaces.Reset;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Rectangle;

import static bomb.modules.t.bulb.Bulb.THE_BULB;
import static bomb.tools.Mechanics.NUMBER_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

public class TController implements Reset {
    private final boolean[] bulbConditions = new boolean[3];

    private final ToggleGroup opacity = new ToggleGroup(),
            luminosity = new ToggleGroup();

    @FXML
    private Button next;

    @FXML
    private Label redLabel, yellowLabel, greenLabel, blueLabel, purpleLabel, whiteLabel;

    @FXML
    private Rectangle red, yellow, green, blue, purple, white;

    @FXML
    private TextArea bulbResults;

    @FXML
    private TextField query, numberCode, cmdLine;

    @FXML
    private ToggleButton onButton, offButton, opaqueButton, transparentButton;

    public void initialize(){
        onButton.setToggleGroup(luminosity);
        offButton.setToggleGroup(luminosity);
        opaqueButton.setToggleGroup(opacity);
        transparentButton.setToggleGroup(opacity);
    }

    //The Bulb methods
    @FXML
    private void colorSet(){
        bulbConditions[0] = true;
        if (red.isHover()){
            THE_BULB.setColor(Bulb.Color.RED);
            labelSet(0);
        } else if (yellow.isHover()){
            THE_BULB.setColor(Bulb.Color.YELLOW);
            labelSet(1);
        } else if (green.isHover()){
            THE_BULB.setColor(Bulb.Color.GREEN);
            labelSet(2);
        } else if (blue.isHover()){
            THE_BULB.setColor(Bulb.Color.BLUE);
            labelSet(3);
        } else if (purple.isHover()){
            THE_BULB.setColor(Bulb.Color.PURPLE);
            labelSet(4);
        } else if (white.isHover()){
            THE_BULB.setColor(Bulb.Color.WHITE);
            labelSet(5);
        }
        plugInBulb();
    }

    private void labelSet(int color){
        switch (color){
            case 0: {
                redLabel.setStyle("-fx-text-fill: red");
                yellowLabel.setStyle("-fx-text-fill: black");
                greenLabel.setStyle("-fx-text-fill: black");
                blueLabel.setStyle("-fx-text-fill: black");
                purpleLabel.setStyle("-fx-text-fill: black");
                whiteLabel.setStyle("-fx-text-fill: black");
            }
            case 1: {
                redLabel.setStyle("-fx-text-fill: black");
                yellowLabel.setStyle("-fx-text-fill: yellow");
                greenLabel.setStyle("-fx-text-fill: black");
                blueLabel.setStyle("-fx-text-fill: black");
                purpleLabel.setStyle("-fx-text-fill: black");
                whiteLabel.setStyle("-fx-text-fill: black");
            }
            case 2: {
                redLabel.setStyle("-fx-text-fill: black");
                yellowLabel.setStyle("-fx-text-fill: black");
                greenLabel.setStyle("-fx-text-fill: #07c307");
                blueLabel.setStyle("-fx-text-fill: black");
                purpleLabel.setStyle("-fx-text-fill: black");
                whiteLabel.setStyle("-fx-text-fill: black");
            }
            case 3: {
                redLabel.setStyle("-fx-text-fill: black");
                yellowLabel.setStyle("-fx-text-fill: black");
                greenLabel.setStyle("-fx-text-fill: black");
                blueLabel.setStyle("-fx-text-fill: dodgerblue");
                purpleLabel.setStyle("-fx-text-fill: black");
                whiteLabel.setStyle("-fx-text-fill: black");
            }
            case 4: {
                redLabel.setStyle("-fx-text-fill: black");
                yellowLabel.setStyle("-fx-text-fill: black");
                greenLabel.setStyle("-fx-text-fill: black");
                blueLabel.setStyle("-fx-text-fill: black");
                purpleLabel.setStyle("-fx-text-fill: #cc0bdd");
                whiteLabel.setStyle("-fx-text-fill: black");
            }
            default: {
                redLabel.setStyle("-fx-text-fill: black");
                yellowLabel.setStyle("-fx-text-fill: black");
                greenLabel.setStyle("-fx-text-fill: black");
                blueLabel.setStyle("-fx-text-fill: black");
                purpleLabel.setStyle("-fx-text-fill: black");
                whiteLabel.setStyle("-fx-text-fill: white");
            }
        }
    }

    @FXML
    private void luminositySet(){
        bulbConditions[1] = true;
        THE_BULB.setLight(onButton.isSelected()? Bulb.Light.ON: Bulb.Light.OFF);
        plugInBulb();
    }

    @FXML
    private void opacitySet(){
        bulbConditions[2] = true;
        THE_BULB.setOpacity(opaqueButton.isSelected()? Bulb.Opacity.OPAQUE: Bulb.Opacity.TRANSLUCENT);
        plugInBulb();
    }

    private void plugInBulb(){
        if (bulbConditions[0] && bulbConditions[1] && bulbConditions[2]){
            THE_BULB.setPosition(Bulb.Position.SCREWED);
            bulbResults.setText(TheBulb.entry(THE_BULB));
        }
    }

    //Two Bit methods
    @FXML
    private void initQueue(){
        try {
            query.setText(TwoBit.initialCode());
            numberCode.setDisable(false);
            cmdLine.setDisable(false);
            next.setDisable(false);
        } catch (IllegalArgumentException illegal){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You need to set the Serial Code");
            alert.showAndWait();
        } catch (StringIndexOutOfBoundsException incomplete){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The Serial Code is incomplete");
            alert.showAndWait();
        }
    }

    @FXML
    private void nextCode(){
        try {
            cmdLine.setText(TwoBit.nextCode(ultimateFilter(numberCode.getText(), NUMBER_REGEX)));
            numberCode.setText("");
        } catch (IllegalArgumentException illegal){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("That wasn't two bits");
            alert.showAndWait();
        }
    }

    @Override
    public void reset() {

    }
}
