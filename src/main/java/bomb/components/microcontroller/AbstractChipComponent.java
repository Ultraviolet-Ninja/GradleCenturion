package bomb.components.microcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;

public abstract class AbstractChipComponent extends Pane {
    @FXML
    protected Label type, serialNumbers;

    public AbstractChipComponent() {
        super();
    }

    public abstract void setColors(List<Color> results);

    public void setChipSerialNum(String numbers) {
        serialNumbers.setText(numbers);
    }

    public void setChipType(String name) {
        type.setText(name);
    }
}
