package bomb.modules.dh.emoji;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.event.HoverHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Objects;
import java.util.function.Consumer;

import static bomb.modules.dh.emoji.EmojiControllerState.END;
import static bomb.modules.dh.emoji.EmojiControllerState.FIRST_EMOJI_PRESS;
import static bomb.modules.dh.emoji.EmojiControllerState.RESET;
import static bomb.modules.dh.emoji.EmojiControllerState.MATH_SYMBOL_PRESS;

public class EmojiController implements Resettable {
    private final StringBuilder internalEquation;

    private EmojiControllerState currentState;

    @FXML private JFXButton first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth,
            add, minus, equal, clear;

    @FXML private JFXTextField equationBar;

    public EmojiController(){
        currentState = FIRST_EMOJI_PRESS;
        internalEquation = new StringBuilder();
    }

    public void initialize() {
        HoverHandler<ActionEvent> emojiButtonHandler = new HoverHandler<>(createEmojiButtonAction());
        HoverHandler<ActionEvent> mathButtonHandler = new HoverHandler<>(createMathButtonAction());

        FacadeFX.bindHandlerToButtons(emojiButtonHandler, first, second, third, forth, fifth, sixth,
                seventh, eighth, ninth, tenth);
        FacadeFX.bindHandlerToButtons(mathButtonHandler, add, minus);
    }

    private Consumer<ActionEvent> createEmojiButtonAction() {
        return event -> {
            Emojis current = Emojis.getEmojiFromText(((JFXButton) event.getSource()).getText());
            internalEquation.append(Objects.requireNonNull(current).getLabel());
            exportToUI();
            handleState();
        };
    }

    private Consumer<ActionEvent> createMathButtonAction() {
        return event -> {
            if (currentState != MATH_SYMBOL_PRESS) currentState = MATH_SYMBOL_PRESS;
            String symbol = ((JFXButton) event.getSource()).getText();
            internalEquation.append(symbol);
            exportToUI();
            handleState();
        };
    }

    private void handleState() {
        switch (currentState) {
            case FIRST_EMOJI_PRESS:
                FacadeFX.enableMultiple(add, minus, clear);
                break;
            case END:
                FacadeFX.disable(equal);
            case SECOND_EMOJI_PRESS:
            case FOURTH_EMOJI_PRESS:
                FacadeFX.disableMultiple(first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth);
                break;
            case RESET:
                FacadeFX.disableMultiple(equal, clear);
            case MATH_SYMBOL_PRESS:
                FacadeFX.disableMultiple(add, minus);
                FacadeFX.enableMultiple(first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth);
                break;
            case THIRD_EMOJI_PRESS:
                FacadeFX.enable(equal);
                break;
        }
        currentState = currentState.nextState();
    }

    @FXML
    private void submitEquation() {
        if (currentState != END) currentState = END;
        internalEquation.append("\t = ").append(EmojiMath.calculate(equationBar.getText()));
        exportToUI();
        handleState();
    }

    @FXML
    private void resetModule() {
        if (currentState != RESET) currentState = RESET;
        internalEquation.setLength(0);
        exportToUI();
        handleState();
    }

    private void exportToUI() {
        equationBar.setText(internalEquation.toString());
    }

    @Override
    public void reset() {
        resetModule();
    }
}
