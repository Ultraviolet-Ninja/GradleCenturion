package bomb.components.microcontroller;

import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class EightPinController extends AbstractChipComponent {
    public static final int PIN_COUNT = 8;

    @FXML
    private Circle pinOne, pinTwo, pinThree, pinFour, pinFive, pinSix, pinSeven, pinEight;

    public EightPinController() {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("eight_pin.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        FacadeFX.loadComponent(loader);
    }

    @Override
    public void setColors(@NotNull List<Color> results) {
        Circle[] array = new Circle[]{pinOne, pinTwo, pinThree, pinFour, pinFive, pinSix, pinSeven, pinEight};
        if (results.size() == PIN_COUNT) {
            for (int i = 0; i < array.length; i++)
                array[i].setFill(results.get(i));
        }
    }
}
