package bomb.modules.s.shape_shift;

import bomb.tools.data.structures.FixedRing;
import javafx.fxml.FXML;

public class ShapeShiftController {
    private FixedRing<ShiftShape> leftSide, rightSide;

    public void initialize(){
        leftSide = new FixedRing<>(ShiftShape.values().length);
        rightSide = new FixedRing<>(ShiftShape.values().length);
        for (ShiftShape shape : ShiftShape.values()){
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
