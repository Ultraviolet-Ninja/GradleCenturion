package bomb.components.microcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;

public class TenPinController extends AbstractChip{
    public static final int PIN_COUNT = 10;

    @FXML
    private Circle pinOne, pinTwo, pinThree, pinFour, pinFive, pinSix, pinSeven, pinEight, pinNine, pinTen;

    public TenPinController(){
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ten_pin.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override
    public void setColors(ArrayList<Color> results) {
        Circle[] array = new Circle[]{pinOne, pinTwo, pinThree, pinFour, pinFive,
                pinSix, pinSeven, pinEight, pinNine, pinTen};
        if (results.size() == PIN_COUNT) {
            for (int i = 0; i < array.length; i++)
                array[i].setFill(results.get(i));
        }
    }
}
