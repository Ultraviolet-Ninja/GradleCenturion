package bomb.modules.t.bulb;

import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Rectangle;

import static bomb.modules.t.bulb.Bulb.THE_BULB;

public class TheBulbController {
    private static final String RED_STYLE = "-fx-text-fill: red", YELLOW_STYLE = "-fx-text-fill: yellow",
            GREEN_STYLE = "-fx-text-fill: #07c307", BLUE_STYLE = "-fx-text-fill: dodgerblue",
            MAGENTA_STYLE = "-fx-text-fill: #cc0bdd", BLACK_STYLE = "-fx-text-fill: black",
            WHITE_STYLE = "-fx-text-fill: white";

    private final boolean[] bulbConditions = new boolean[3];

    @FXML
    private ToggleGroup opacity, luminosity;

    @FXML
    private Label redLabel, yellowLabel, greenLabel, blueLabel, purpleLabel, whiteLabel;

    @FXML
    private Rectangle red, yellow, green, blue, purple, white;

    @FXML
    private TextArea bulbResults;

    //FIXME
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
        redLabel.setStyle(color == 0 ? RED_STYLE : BLACK_STYLE);
        yellowLabel.setStyle(color == 1 ? YELLOW_STYLE : BLACK_STYLE);
        greenLabel.setStyle(color == 2 ? GREEN_STYLE : BLACK_STYLE);
        blueLabel.setStyle(color == 3 ? BLUE_STYLE : BLACK_STYLE);
        purpleLabel.setStyle(color == 4 ? MAGENTA_STYLE : BLACK_STYLE);
        whiteLabel.setStyle(color == 5 ? WHITE_STYLE : BLACK_STYLE);
    }

    @FXML
    private void luminositySet(){
        bulbConditions[1] = true;
        THE_BULB.setLight(FacadeFX.getToggleName(opacity).equals("On") ?
                Bulb.Light.ON :
                Bulb.Light.OFF);
        plugInBulb();
    }

    @FXML
    private void opacitySet(){
        bulbConditions[2] = true;
        THE_BULB.setOpacity(FacadeFX.getToggleName(luminosity).equals("Opaque") ?
                Bulb.Opacity.OPAQUE :
                Bulb.Opacity.TRANSLUCENT);
        plugInBulb();
    }

    private void plugInBulb(){
        if (bulbConditions[0] && bulbConditions[1] && bulbConditions[2]){
            THE_BULB.setPosition(Bulb.Position.SCREWED);
            bulbResults.setText(TheBulb.entry(THE_BULB));
        }
    }
}
