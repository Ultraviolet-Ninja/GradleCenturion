package bomb;

import bomb.tools.FacadeFX;
import bomb.tools.Filter;
import com.jfoenix.controls.JFXTextArea;
import javafx.scene.control.Alert;

import java.util.function.Consumer;

public class WidgetEventFactory {
    public static Consumer<JFXTextArea> createNumbersOnlyTextArea(){
        return jfxTextArea -> {
            String number = jfxTextArea.getText();
            Filter.NUMBER_PATTERN.loadText(number);
            if (!Filter.NUMBER_PATTERN.matchesRegex()){
                FacadeFX.setAlert(Alert.AlertType.ERROR, "This is not a number");
                FacadeFX.clearText(jfxTextArea);
            } else {
                if (jfxTextArea.getPromptText().contains("Minutes")) Widget.setStartTime(Integer.parseInt(number));
                else Widget.setNumModules(Integer.parseInt(number));
            }
        };
    }
}
