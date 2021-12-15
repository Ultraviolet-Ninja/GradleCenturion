package bomb.modules.s.shape_shift;

import bomb.abstractions.Resettable;
import bomb.tools.data.structures.ring.ArrayRing;
import javafx.fxml.FXML;

public class ShapeShiftController implements Resettable {
    private final ArrayRing<ShapeEnd> leftSide, rightSide;

    public ShapeShiftController() {
        leftSide = new ArrayRing<>(ShapeEnd.values());
        rightSide = new ArrayRing<>(ShapeEnd.values());
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
