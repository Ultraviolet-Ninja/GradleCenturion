package bomb.modules.s.shape.shift;

import bomb.abstractions.Resettable;
import bomb.tools.data.structures.ring.ArrayRing;
import javafx.fxml.FXML;

import static bomb.modules.s.shape.shift.ShapeEnd.SHAPE_END_ARRAY;

public final class ShapeShiftController implements Resettable {
    private final ArrayRing<ShapeEnd> leftSide, rightSide;

    public ShapeShiftController() {
        leftSide = new ArrayRing<>(SHAPE_END_ARRAY);
        rightSide = new ArrayRing<>(SHAPE_END_ARRAY);
    }

    public void initialize() {

    }

    @FXML
    private void decrementLeft() {

    }

    @FXML
    private void decrementRight() {

    }

    @FXML
    private void incrementLeft() {

    }

    @FXML
    private void incrementRight() {

    }

    @Override
    public void reset() {

    }
}
