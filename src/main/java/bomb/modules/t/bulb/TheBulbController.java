package bomb.modules.t.bulb;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Rectangle;

import static bomb.modules.t.bulb.Bulb.THE_BULB;

public class TheBulbController {
    private final boolean[] bulbConditions = new boolean[3];

    private final ToggleGroup opacity = new ToggleGroup(),
            luminosity = new ToggleGroup();

    @FXML
    private Label redLabel, yellowLabel, greenLabel, blueLabel, purpleLabel, whiteLabel;

    @FXML
    private Rectangle red, yellow, green, blue, purple, white;

    @FXML
    private TextArea bulbResults;

    @FXML
    private ToggleButton onButton, offButton, opaqueButton, transparentButton;

    public void initialize(){
        onButton.setToggleGroup(luminosity);
        offButton.setToggleGroup(luminosity);
        opaqueButton.setToggleGroup(opacity);
        transparentButton.setToggleGroup(opacity);
    }

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
}
