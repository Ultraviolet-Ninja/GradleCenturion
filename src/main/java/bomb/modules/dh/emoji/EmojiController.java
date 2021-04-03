package bomb.modules.dh.emoji;

import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static bomb.modules.dh.emoji.Emojis.BAR_COLON;
import static bomb.modules.dh.emoji.Emojis.CLOSED_COLON;
import static bomb.modules.dh.emoji.Emojis.CLOSED_EQUAL;
import static bomb.modules.dh.emoji.Emojis.COLON_BAR;
import static bomb.modules.dh.emoji.Emojis.COLON_CLOSE;
import static bomb.modules.dh.emoji.Emojis.COLON_OPEN;
import static bomb.modules.dh.emoji.Emojis.EQUAL_CLOSED;
import static bomb.modules.dh.emoji.Emojis.EQUAL_OPEN;
import static bomb.modules.dh.emoji.Emojis.OPEN_COLON;
import static bomb.modules.dh.emoji.Emojis.OPEN_EQUAL;

public class EmojiController {
    private int eqCount = 0;
    private StringBuilder equation = new StringBuilder();

    @FXML
    private Button first, second, third, forth, fifth,
            sixth, seventh, eighth, ninth, tenth,
            add, minus, equal, clear;

    @FXML
    private TextField equationBar;

    @FXML
    private void operator(){
        eqCount = 3;
        if (add.isHover()) equation.append("+");
        else if (minus.isHover()) equation.append("-");
        equationBar.setText(equation.toString());
        disableControl();
    }

    //FIXME
    @FXML
    private void addToEquation(){
        eqCount++;
        if (first.isHover())
            equation.append(COLON_CLOSE.getLabel());
        else if (second.isHover())
            equation.append(EQUAL_OPEN.getLabel());
        else if (third.isHover())
            equation.append(OPEN_COLON.getLabel());
        else if (forth.isHover())
            equation.append(CLOSED_EQUAL.getLabel());
        else if (fifth.isHover())
            equation.append(COLON_OPEN.getLabel());
        else if (sixth.isHover())
            equation.append(CLOSED_COLON.getLabel());
        else if (seventh.isHover())
            equation.append(EQUAL_CLOSED.getLabel());
        else if (eighth.isHover())
            equation.append(OPEN_EQUAL.getLabel());
        else if (ninth.isHover())
            equation.append(COLON_BAR.getLabel());
        else if (tenth.isHover())
            equation.append(BAR_COLON.getLabel());
        equationBar.setText(equation.toString());
        disableControl();
    }

    private void disableControl(){
        switch (eqCount){
            case 1: {
                add.setDisable(false);
                minus.setDisable(false);
                clear.setDisable(false);
            } break;
            case 2:
            case 5: toggleNumberButtons(true); break;
            case 3: {
                add.setDisable(true);
                minus.setDisable(true);
                toggleNumberButtons(false);
            } break;
            case 4: equal.setDisable(false);
        }
    }

    @FXML
    private void submitEquation(){
        equation.append("\t = ").append(EmojiMath.calculate(equationBar.getText()));
        equationBar.setText(equation.toString());
        clearParam();
    }

    @FXML
    private void clearParam(){
        eqCount = 0;
        FacadeFX.toggleNodes(true, add, minus, equal, clear);
        toggleNumberButtons(false);
        equation = new StringBuilder();
    }

    private void toggleNumberButtons(boolean set) {
        FacadeFX.toggleNodes(set, first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth);
    }
}
