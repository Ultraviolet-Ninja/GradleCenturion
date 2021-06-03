package bomb;

import bomb.enumerations.Ports;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.Objects;
import java.util.function.Consumer;

public class WidgetSupporter {
    private final TextField[] portFields;

    public WidgetSupporter(TextField[] portFields){
        this.portFields = portFields;
    }

    public Consumer<ActionEvent> portAction(){
        return event -> {
            String title = ((Button) event.getSource()).getText();
            Ports temp;
            if (title.charAt(0) == '+'){
                title = trimTitle(title);
                temp = Objects.requireNonNull(Ports.fromString(title));
                Widget.addPort(temp);
            } else {
                title = trimTitle(title);
                temp = Objects.requireNonNull(Ports.fromString(title));
                Widget.subPort(temp);
            }
            updateField(portFields[temp.ordinal()], Widget.getPort(temp));
        };
    }

    private String trimTitle(String title){
        return title.replace("+", "").replace("-", "")
                .replace("1", "").trim();
    }

    private void updateField(TextField current, int nextNum){
        current.setText(String.valueOf(nextNum));
    }
}
