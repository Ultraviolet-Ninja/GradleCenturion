package bomb.modules.s.shape_shift;

import bomb.abstractions.Resettable;
import bomb.tools.data.structures.ring.NewReadOnlyRing;
import javafx.fxml.FXML;

public class ShapeShiftController implements Resettable {
    private NewReadOnlyRing<ShapeEnd> leftSide, rightSide;

    public void initialize(){
        leftSide = new NewReadOnlyRing<>(ShapeEnd.values().length);
        rightSide = new NewReadOnlyRing<>(ShapeEnd.values().length);
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

    @Override
    public void reset() {

    }
}
