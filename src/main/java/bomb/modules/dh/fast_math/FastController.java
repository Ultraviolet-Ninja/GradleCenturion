package bomb.modules.dh.fast_math;

import bomb.abstractions.Resettable;
import bomb.tools.FacadeFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class FastController implements Resettable {
    private final StringBuilder appendLetters = new StringBuilder(),
            textFieldTracker = new StringBuilder();

    @FXML
    private Button alfa, bravo, charlie, delta, echo, golf, kilo, november, papa, sierra, tango, xRay, zulu;

    @FXML
    private TextField outputMath;

    public void initialize(){
//        FacadeFX.bindHandlerToButtons(new HoverHandler<>(initializeAction()), alfa, bravo, charlie, delta, echo,
//                golf, kilo, november, papa, sierra, tango, xRay, zulu);
    }

    private Consumer<ActionEvent> initializeAction(){
        return event -> {
            String temp = ((Button) event.getSource()).getText();
            appendLetters.append(temp);
            textFieldTracker.append(temp);
            try {
                if (appendLetters.length() == 2) {
                    textFieldTracker.append(" - ").append(FastMath.solve(appendLetters.toString()));
                    appendLetters.setLength(0);
                }
                outputMath.setText(textFieldTracker.toString());
                if (textFieldTracker.length() > 2) textFieldTracker.setLength(0);
            } catch (IllegalArgumentException illegal){
                FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getLocalizedMessage());
            }
        };
    }

    @FXML
    private void clear(){
        FacadeFX.clearText(outputMath);
        textFieldTracker.setLength(0);
        appendLetters.setLength(0);
    }

    @Override
    public void reset() {
        clear();
    }
}
