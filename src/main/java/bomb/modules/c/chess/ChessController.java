package bomb.modules.c.chess;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class ChessController {
    private final List<String> positionList;

    @FXML
    private MFXButton firstPositionButton, secondPositionButton, thirdPositionButton, fourthPositionButton,
            fifthPositionButton, sixthPositionButton, submitButton;

    @FXML
    private MFXTextField positionTextField, outputTextField;

    public ChessController() {
        positionList = new ArrayList<>(ChessBoard.BOARD_LENGTH);
    }

    public void initialize() {

    }

    private EventHandler<ActionEvent> createButtonAction() {
        return event -> {
            Button source = (MFXButton) event.getSource();

        };
    }

    private boolean areAllPositionsValid() {
        for (String position : positionList) {
            if (position == null)
                return false;
        }
        return true;
    }
}
