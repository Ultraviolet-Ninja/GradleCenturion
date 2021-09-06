package bomb.modules.ab.blind_alley;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.observer.ObserverHub;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BlindAlleyController implements Resettable {
    @FXML private Label maxAlley;

    @FXML private JFXTextField topLeft, topMid, midLeft, trueMid, midRight, bottomLeft, bottomMid, bottomRight;

    public void liveUpdate() {
        int[][] buffer = BlindAlley.getAlleyCat();
        stylerSet(buffer);
        topLeft.setText(String.valueOf(buffer[0][0]));
        topMid.setText(String.valueOf(buffer[0][1]));
        midLeft.setText(String.valueOf(buffer[1][0]));
        trueMid.setText(String.valueOf(buffer[1][1]));
        midRight.setText(String.valueOf(buffer[1][2]));
        bottomLeft.setText(String.valueOf(buffer[2][0]));
        bottomMid.setText(String.valueOf(buffer[2][1]));
        bottomRight.setText(String.valueOf(buffer[2][2]));
        writeMax(buffer);
    }

    private void stylerSet(int[][] array) {
        topLeft.setStyle(style(array[0][0]));
        topMid.setStyle(style(array[0][1]));
        midLeft.setStyle(style(array[1][0]));
        trueMid.setStyle(style(array[1][1]));
        midRight.setStyle(style(array[1][2]));
        bottomLeft.setStyle(style(array[2][0]));
        bottomMid.setStyle(style(array[2][1]));
        bottomRight.setStyle(style(array[2][2]));
    }

    private String style(int number) {
        return switch (number) {
            case 0 -> "-fx-text-fill: black";
            case 1 -> "-fx-text-fill: green";
            case 2 -> "-fx-text-fill: yellow";
            case 3 -> "-fx-text-fill: orange";
            default -> "-fx-text-fill: red";
        };
    }

    private void writeMax(int[][] array) {
        int max = 0;
        for (int[] col : array) {
            for (int num : col)
                if (num > max) max = num;
        }

        maxAlley.setText("All with " + max);
    }

    @Override
    public void reset() {
        ObserverHub.updateAtIndex(ObserverHub.ObserverIndex.SOUVENIR_TOGGLE);
    }
}
