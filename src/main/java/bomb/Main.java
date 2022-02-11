package bomb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Stream;

import static javafx.scene.input.KeyCode.DIGIT0;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.DIGIT3;
import static javafx.scene.input.KeyCode.DIGIT4;
import static javafx.scene.input.KeyCode.DIGIT5;
import static javafx.scene.input.KeyCode.DIGIT6;
import static javafx.scene.input.KeyCode.DIGIT7;
import static javafx.scene.input.KeyCode.DIGIT8;
import static javafx.scene.input.KeyCode.DIGIT9;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("manual.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ManualController controller = loader.getController();

        setSceneKeyboardEvents(scene, controller);
        setSceneArrowEvents(scene, controller);

        primaryStage.setTitle("Centurion Bomb Manual");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("KTANE logo.png"))));
        primaryStage.show();
    }

    private static void setSceneKeyboardEvents(Scene scene, ManualController controller) {
        List<KeyCodeCombination> digitList = Stream.of(DIGIT1, DIGIT2, DIGIT3, DIGIT4, DIGIT5, DIGIT6, DIGIT7,
                        DIGIT8, DIGIT9, DIGIT0)
                        .map(code -> new KeyCodeCombination(code, CONTROL_DOWN))
                        .toList();

        int count = 0;
        for (KeyCodeCombination combo : digitList) {
            int index = count++;
            scene.addEventFilter(
                    KeyEvent.KEY_PRESSED,
                    event -> {
                        if (combo.match(event))
                            controller.switchPaneByIndex(index);
                    }
            );
        }
    }

    private static void setSceneArrowEvents(Scene scene, ManualController controller) {
        KeyCodeCombination upArrow = new KeyCodeCombination(UP);
        KeyCodeCombination downArrow = new KeyCodeCombination(DOWN);

        scene.addEventFilter(
                KeyEvent.KEY_PRESSED,
                event -> {
                    if (downArrow.match(event))
                        controller.switchPaneByDownArrow();
                }
        );

        scene.addEventFilter(
                KeyEvent.KEY_PRESSED,
                event -> {
                    if (upArrow.match(event))
                        controller.switchPaneByUpArrow();
                }
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
