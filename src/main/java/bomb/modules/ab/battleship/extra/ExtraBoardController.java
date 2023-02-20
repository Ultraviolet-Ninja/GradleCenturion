package bomb.modules.ab.battleship.extra;

import bomb.modules.ab.battleship.Ocean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

import static bomb.modules.ab.battleship.BattleshipUITranslator.translateToFrontendGrid;

public final class ExtraBoardController {
    @FXML
    private Rectangle oneOne, oneTwo, oneThree, oneFour, oneFive,
            twoOne, twoTwo, twoThree, twoFour, twoFive,
            threeOne, threeTwo, threeThree, threeFour, threeFive,
            fourOne, fourTwo, fourThree, fourFour, fourFive,
            fiveOne, fiveTwo, fiveThree, fiveFour, fiveFive;

    private final Rectangle[][] frontendGrid;
    private final Ocean ocean;
    private final Stage stage;

    public ExtraBoardController(Ocean ocean) {
        this.stage = new Stage();
        this.ocean = ocean;
        frontendGrid = new Rectangle[Ocean.BOARD_LENGTH][];
        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(ExtraBoardController.class.getResource("extra_board.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        loader.setController(this);

        stage.setScene(new Scene(root, 600, 400));
    }

    public void initialize() {
        frontendGrid[0] = new Rectangle[]{oneOne, oneTwo, oneThree, oneFour, oneFive};
        frontendGrid[1] = new Rectangle[]{twoOne, twoTwo, twoThree, twoFour, twoFive};
        frontendGrid[2] = new Rectangle[]{threeOne, threeTwo, threeThree, threeFour, threeFive};
        frontendGrid[3] = new Rectangle[]{fourOne, fourTwo, fourThree, fourFour, fourFive};
        frontendGrid[4] = new Rectangle[]{fiveOne, fiveTwo, fiveThree, fiveFour, fiveFive};

        translateToFrontendGrid(frontendGrid, ocean);
    }

    public void show() {
        stage.show();
    }
}
