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
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class ChessController implements Resettable {
    private byte previousPosition = 0;
    private final List<String> positionList;

    @FXML
    private MFXButton firstPositionButton, secondPositionButton, thirdPositionButton, fourthPositionButton,
            fifthPositionButton, sixthPositionButton, submitButton;

    @FXML
    private MFXTextField positionTextField, outputTextField;

    public ChessController() {
        positionList = new ArrayList<>();
        setListAllEmpty();
    }

    public void initialize() {
        positionTextField.setTextFormatter(TextFormatterFactory.createChessNotationTextFormatter());
        positionTextField.setOnKeyTyped(createTextFieldEvent());
        firstPositionButton.setOnAction(createButtonAction());
        secondPositionButton.setOnAction(createButtonAction());
        thirdPositionButton.setOnAction(createButtonAction());
        fourthPositionButton.setOnAction(createButtonAction());
        fifthPositionButton.setOnAction(createButtonAction());
        sixthPositionButton.setOnAction(createButtonAction());
    }

    private EventHandler<ActionEvent> createButtonAction() {
        return event -> {
            String positionInput = positionTextField.getText();
            positionList.set(previousPosition, positionInput);

            MFXButton source = (MFXButton) event.getSource();
            byte nextPosition = (byte) (Integer.parseInt(source.getText()) - 1);
            positionTextField.setText(positionList.get(nextPosition));

            previousPosition = nextPosition;
            submitButton.setDisable(areAnyPositionsInvalid());
        };
    }

    private EventHandler<KeyEvent> createTextFieldEvent() {
        return event -> {
            String positionInput = positionTextField.getText();
            String inputToSet = positionInput.matches(Chess.VALIDITY_REGEX) ? positionInput : "";
            positionList.set(previousPosition, inputToSet);

            submitButton.setDisable(areAnyPositionsInvalid());
        };
    }

    private boolean areAnyPositionsInvalid() {
        for (String position : positionList) {
            if (position == null || !position.matches(Chess.VALIDITY_REGEX))
                return true;
        }
        return false;
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
        setListAllEmpty();
        previousPosition = 0;
        FacadeFX.disable(submitButton);
        FacadeFX.clearMultipleTextFields(positionTextField, outputTextField);
    }

    private void setListAllEmpty() {
        for (int i = 0; i < ChessBoard.BOARD_LENGTH; i++)
            positionList.add("");
    }
}
