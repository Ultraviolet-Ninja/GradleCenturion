package bomb.components.microcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class AbstractChip extends Pane {
    @FXML
    protected Label type, serialNumbers;

    public AbstractChip(){
        super();
    }

    public abstract void setColors(ArrayList<Color> results);

    public  void setChipSerialNum(String numbers){
        serialNumbers.setText(numbers);
    }

    public void setChipType(String name){
        type.setText(name);
    }
}
