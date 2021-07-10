package bomb;

import bomb.tools.facade.FacadeFX;
import bomb.tools.Filter;
import com.jfoenix.controls.JFXTextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WidgetEventFactory {
    public static Consumer<JFXTextArea> createNumbersOnlyTextArea(){
        BiConsumer<JFXTextArea, Integer> decideSetter = (jfxTextArea, number) -> {
            if (jfxTextArea.getPromptText().contains("Minutes")) Widget.setStartTime(number);
            else Widget.setNumModules(number);
        };
        return jfxTextArea -> {
            String number = jfxTextArea.getText();
            Filter.NUMBER_PATTERN.loadText(number);
            if (!Filter.NUMBER_PATTERN.matchesRegex()){
                FacadeFX.setAlert(Alert.AlertType.ERROR, "This is not a number");
                jfxTextArea.setText("0");
                decideSetter.accept(jfxTextArea, 0);
            } else decideSetter.accept(jfxTextArea, Integer.parseInt(number));
        };
    }

    public static TextFormatter<String> createSerialCodeFormatter(){
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) return change;

            String text = change.getControlNewText();
            if (text.contains("\n") || text.length() > 6) return null;

            return change;
        });
    }

    public static TextFormatter<String> createNumbersOnlyFormatter(){
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) return change;

            String text = change.getControlNewText();
            Filter.NUMBER_PATTERN.loadText(text);
            if (text.equals("")) return change;
            return Filter.NUMBER_PATTERN.matchesRegex() ? change : null;
        });
    }
}
