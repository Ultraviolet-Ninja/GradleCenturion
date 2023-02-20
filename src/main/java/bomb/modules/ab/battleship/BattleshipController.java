package bomb.modules.ab.battleship;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.stream.Stream;

import static bomb.tools.pattern.factory.TextFormatterFactory.createBattleshipCounterTextFormatter;

public final class BattleshipController implements Resettable {
    @FXML
    private MFXButton solveButton, radarButton;

    @FXML
    private MFXTextField rowOne, rowTwo, rowThree, rowFour, rowFive,
            columnOne, columnTwo, columnThree, columnFour, columnFive;

    @FXML
    private Rectangle oneOne, oneTwo, oneThree, oneFour, oneFive,
            twoOne, twoTwo, twoThree, twoFour, twoFive,
            threeOne, threeTwo, threeThree, threeFour, threeFive,
            fourOne, fourTwo, fourThree, fourFour, fourFive,
            fiveOne, fiveTwo, fiveThree, fiveFour, fiveFive;

    private final Rectangle[][] frontendGrid;
    private final MFXTextField[] rowGroup, columnGroup;

    public BattleshipController() {
        frontendGrid = new Rectangle[Ocean.BOARD_LENGTH][];
        rowGroup = new MFXTextField[Ocean.BOARD_LENGTH];
        columnGroup = new MFXTextField[Ocean.BOARD_LENGTH];
    }

    public void initialize() {
        frontendGrid[0] = new Rectangle[]{oneOne, oneTwo, oneThree, oneFour, oneFive};
        frontendGrid[1] = new Rectangle[]{twoOne, twoTwo, twoThree, twoFour, twoFive};
        frontendGrid[2] = new Rectangle[]{threeOne, threeTwo, threeThree, threeFour, threeFive};
        frontendGrid[3] = new Rectangle[]{fourOne, fourTwo, fourThree, fourFour, fourFive};
        frontendGrid[4] = new Rectangle[]{fiveOne, fiveTwo, fiveThree, fiveFour, fiveFive};

        int counter = 0;
        for (MFXTextField field : new MFXTextField[]{rowOne, rowTwo, rowThree, rowFour, rowFive}) {
            field.setTextFormatter(createBattleshipCounterTextFormatter());
            rowGroup[counter++] = field;
        }

        counter = 0;
        for (MFXTextField field : new MFXTextField[]{columnOne, columnTwo, columnThree, columnFour, columnFive}) {
            field.setTextFormatter(createBattleshipCounterTextFormatter());
            columnGroup[counter++] = field;
        }
    }

    @FXML
    private void enableRadarButton() {
        radarButton.setDisable(
                Stream.concat(Arrays.stream(rowGroup), Arrays.stream(columnGroup))
                .map(TextField::getText)
                .anyMatch(String::isEmpty)
        );
    }

    @FXML
    private void revealRadarSpots() {

    }

    @FXML
    private void solveBoard() {

    }

    @FXML
    private void resetPuzzle() {
        reset();
    }

    public void reset() {
        Battleship.reset();
        solveButton.setDisable(true);
        radarButton.setDisable(true);

        for (MFXTextField field : rowGroup) {
            FacadeFX.clearText(field);
        }

        for (MFXTextField field : columnGroup) {
            FacadeFX.clearText(field);
        }

    }
}
