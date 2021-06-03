package bomb.modules.s.shape_shift;

import bomb.tools.data.structures.ring.ReadOnlyRing;
import javafx.fxml.FXML;

public class ShapeShiftController {
    private ReadOnlyRing<ShapeEnd> leftSide, rightSide;

    public void initialize(){
        leftSide = new ReadOnlyRing<>(ShapeEnd.values().length);
        rightSide = new ReadOnlyRing<>(ShapeEnd.values().length);
        for (ShapeEnd shape : ShapeEnd.values()){
            leftSide.add(shape);
            rightSide.add(shape);
        }
    }

    @FXML
    private void decrementLeft(){

    }

    @FXML
    private void decrementRight(){

    }

    @FXML
    private void incrementLeft(){

    }

    @FXML
    private void incrementRight(){

    }
}
