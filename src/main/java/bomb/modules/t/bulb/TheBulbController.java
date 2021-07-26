package bomb.modules.t.bulb;

import bomb.abstractions.Resettable;
import bomb.tools.facade.FacadeFX;
import bomb.tools.HoverHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.function.Consumer;

import static bomb.modules.t.bulb.BulbProperties.THE_BULB;

public class TheBulbController implements Resettable {
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

    public void initialize(){
        FacadeFX.bindOnClickHandler(new HoverHandler<>(createAction()), red, yellow, green, blue, purple, white);
    }

    private Consumer<MouseEvent> createAction(){
        return event -> {
            bulbConditions[0] = true;
            Rectangle rect = (Rectangle) event.getSource();
            for (BulbProperties.Color color : BulbProperties.Color.values()) {
                if (rect.getFill().equals(color.getAssociatedColor())){
                    THE_BULB.setColor(color);
                    labelSet(color.ordinal());
                }
            }
            plugInBulb();
        };
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
                BulbProperties.Light.ON :
                BulbProperties.Light.OFF);
        plugInBulb();
    }

    @FXML
    private void opacitySet(){
        bulbConditions[2] = true;
        THE_BULB.setOpacity(FacadeFX.getToggleName(luminosity).equals("Opaque") ?
                BulbProperties.Opacity.OPAQUE :
                BulbProperties.Opacity.TRANSLUCENT);
        plugInBulb();
    }

    private void plugInBulb(){
        if (bulbConditions[0] && bulbConditions[1] && bulbConditions[2]){
            THE_BULB.setPosition(BulbProperties.Position.SCREWED);
            bulbResults.setText(TheBulb.entry(THE_BULB));
        }
    }

    @Override
    public void reset() {

    }
}