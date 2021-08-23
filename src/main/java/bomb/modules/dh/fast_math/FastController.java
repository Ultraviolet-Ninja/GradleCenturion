package bomb.modules.dh.fast_math;

import bomb.abstractions.Resettable;
import bomb.tools.event.HoverHandler;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.util.function.Consumer;

public class FastController implements Resettable {
    private final StringBuilder buttonPressTracker, outputMathTextTracker;

    @FXML
    private JFXButton alfa, bravo, charlie, delta, echo, golf, kilo, november, papa, sierra, tango, xRay, zulu;

    @FXML
    private JFXTextField outputMath;

    public FastController() {
        buttonPressTracker = new StringBuilder();
        outputMathTextTracker = new StringBuilder();
    }

    public void initialize() {
        FacadeFX.bindHandlerToButtons(new HoverHandler<>(initializeAction()), alfa, bravo, charlie, delta, echo,
                golf, kilo, november, papa, sierra, tango, xRay, zulu);
    }

    private Consumer<ActionEvent> initializeAction() {
        return event -> {
            String temp = ((Button) event.getSource()).getText();
            buttonPressTracker.append(temp);
            outputMathTextTracker.append(temp);
            try {
                if (buttonPressTracker.length() == 2) {
                    outputMathTextTracker.append(" - ").append(FastMath.solve(buttonPressTracker.toString()));
                    buttonPressTracker.setLength(0);
                }
                outputMath.setText(outputMathTextTracker.toString());
                if (outputMathTextTracker.length() > 2) outputMathTextTracker.setLength(0);
            } catch (IllegalArgumentException illegal) {
                FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getLocalizedMessage());
            }
        };
    }

    @FXML
    private void clear() {
        FacadeFX.clearText(outputMath);
        outputMathTextTracker.setLength(0);
        buttonPressTracker.setLength(0);
    }

    @Override
    public void reset() {
        clear();
    }
}
