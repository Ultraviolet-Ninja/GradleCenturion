package bomb.modules.c.chess;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.pattern.factory.TextFormatterFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class ChessController implements Resettable {
    private final List<String> positionList;

    @FXML
    private MFXButton firstPositionButton, secondPositionButton, thirdPositionButton, fourthPositionButton,
            fifthPositionButton, sixthPositionButton, submitButton;

    @FXML
    private MFXTextField positionTextField, outputTextField;

    public ChessController() {
        positionList = new ArrayList<>();
    }

    public void initialize() {
        positionTextField.setTextFormatter(TextFormatterFactory.createChessNotationTextFormatter());


    }

    private EventHandler<ActionEvent> createButtonAction() {
        return event -> {
            MFXButton source = (MFXButton) event.getSource();

        };
    }

    private boolean areAllPositionsValid() {
        for (String position : positionList) {
            if (position == null || position.matches(Chess.VALIDITY_REGEX))
                return false;
        }
        return true;
    }

    @FXML
    private void submitInfo() {
        try {
            String result = Chess.solve(positionList);
            outputTextField.setText(result);
        } catch (IllegalArgumentException | IllegalStateException illegal) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
        }
    }

    @Override
    public void reset() {
        positionList.clear();
        FacadeFX.disable(submitButton);
        FacadeFX.clearMultipleTextFields(positionTextField, outputTextField);
    }
}
