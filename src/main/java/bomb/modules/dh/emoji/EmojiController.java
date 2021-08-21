package bomb.modules.dh.emoji;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.event.HoverHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.Objects;
import java.util.function.Consumer;

public class EmojiController implements Resettable {
    private int eqCount = 0;
    private StringBuilder equation = new StringBuilder();

    @FXML private Button first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth,
            add, minus, equal, clear;

    @FXML private TextField equationBar;

    public void initialize() {
        HoverHandler<ActionEvent> handler = new HoverHandler<>(createButtonAction());
        FacadeFX.bindHandlerToButtons(handler, first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth);
    }

    private Consumer<ActionEvent> createButtonAction() {
        return event -> {
            eqCount++;
            Emojis current = Emojis.getEmojiFromText(((Button) event.getSource()).getText());
            equation.append(Objects.requireNonNull(current).getLabel());
            equationBar.setText(equation.toString());
            disableControl();
        };
    }

    @FXML
    private void operator() {
        eqCount = 3;
        if (add.isHover()) equation.append("+");
        else if (minus.isHover()) equation.append("-");
        equationBar.setText(equation.toString());
        disableControl();
    }

    private void disableControl() {
        switch (eqCount) {
            case 1: {
                add.setDisable(false);
                minus.setDisable(false);
                clear.setDisable(false);
            }
            break;
            case 2:
            case 5:
                toggleNumberButtons(true);
                break;
            case 3: {
                add.setDisable(true);
                minus.setDisable(true);
                toggleNumberButtons(false);
            }
            break;
            case 4:
                equal.setDisable(false);
        }
    }

    //TODO There's a problem with the equation submission
    @FXML
    private void submitEquation() {
        equation.append("\t = ").append(EmojiMath.calculate(equationBar.getText()));
        equationBar.setText(equation.toString());
        clearParam();
    }

    @FXML
    private void clearParam() {
        eqCount = 0;
        FacadeFX.toggleNodes(true, add, minus, equal, clear);
        toggleNumberButtons(false);
        equation = new StringBuilder();
    }

    private void toggleNumberButtons(boolean set) {
        FacadeFX.toggleNodes(set, first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth);
    }

    @Override
    public void reset() {
        clearParam();
    }
}
