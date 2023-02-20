package bomb.modules.dh.hexamaze;

import bomb.abstractions.Resettable;
import bomb.components.hex.HexMazePanel;
import bomb.modules.dh.hexamaze.hexalgorithm.pathfinding.MazeRunner;
import bomb.tools.pattern.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public final class HexamazeController implements Resettable {
    @FXML
    private ToggleGroup hexGroup, hexColorGroup;

    @FXML
    private HexMazePanel oneOne, oneTwo, oneThree, oneFour,
            twoOne, twoTwo, twoThree, twoFour, twoFive,
            threeOne, threeTwo, threeThree, threeFour, threeFive, threeSix,
            fourOne, fourTwo, fourThree, fourFour, fourFive, fourSix, fourSeven,
            fiveOne, fiveTwo, fiveThree, fiveFour, fiveFive, fiveSix,
            sixOne, sixTwo, sixThree, sixFour, sixFive,
            sevenOne, sevenTwo, sevenThree, sevenFour;

    @FXML
    private RadioButton redButton, yellowButton, greenButton, cyanButton, blueButton, pinkButton;

    @FXML
    private Label exitDirectionLabel;

    public void initialize() {
        Hexamaze.setupVariables(transportPanels());
    }

    @FXML
    private void setShape() {
        toggleColorControl();
        if (!FacadeFX.getToggleName(hexGroup).equals("Peg"))
            Hexamaze.setShapeSelector(FacadeFX.getToggleName(hexGroup));
    }

    private void toggleColorControl() {
        boolean toggle = !FacadeFX.getToggleName(hexGroup).equals("Peg");
        if (toggle)
            FacadeFX.disableMultiple(redButton, yellowButton, greenButton, cyanButton, blueButton, pinkButton);
        else
            FacadeFX.enableMultiple(redButton, yellowButton, greenButton, cyanButton, blueButton, pinkButton);
    }

    @FXML
    private void setPegFill() {
        Hexamaze.setPegFillSelector(colorPicker(FacadeFX.getToggleName(hexColorGroup)));
    }

    private Color colorPicker(String strColor) {
        return switch (strColor) {
            case "Red" -> Color.RED;
            case "Yellow" -> Color.YELLOW;
            case "Green" -> Color.GREEN;
            case "Cyan" -> Color.CYAN;
            case "Blue" -> Color.BLUE;
            case "Pink" -> Color.PINK;
            default -> HexMazePanel.DEFAULT_PEG_COLOR;
        };
    }

    @FXML
    private void plotShape() {
        if (FacadeFX.getToggleName(hexGroup).equals("Peg") && FacadeFX.getToggleName(hexColorGroup) != null)
            Hexamaze.setPegFillSelector();
        else
            Hexamaze.setShapeFill();
    }

    @FXML
    private void mazeCompare() {
        try {
            Hexamaze.compareToFullMaze();
            String sideToExit = MazeRunner.getExitDirection();
            if (sideToExit != null)
                exitDirectionLabel.setText("Exit out of the " + sideToExit + " side");
            else
                FacadeFX.clearText(exitDirectionLabel);
        } catch (IllegalArgumentException ex) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, ex.getMessage());
        }
    }

    private List<HexMazePanel> transportPanels() {
        List<HexMazePanel> output = new ArrayList<>();
        fillOne(output);
        fillTwo(output);
        fillThree(output);
        fillFour(output);
        fillFive(output);
        fillSix(output);
        fillSeven(output);
        return output;
    }

    private void fillOne(List<HexMazePanel> out) {
        out.add(oneOne);
        out.add(oneTwo);
        out.add(oneThree);
        out.add(oneFour);
    }

    private void fillTwo(List<HexMazePanel> out) {
        out.add(twoOne);
        out.add(twoTwo);
        out.add(twoThree);
        out.add(twoFour);
        out.add(twoFive);
    }

    private void fillThree(List<HexMazePanel> out) {
        out.add(threeOne);
        out.add(threeTwo);
        out.add(threeThree);
        out.add(threeFour);
        out.add(threeFive);
        out.add(threeSix);
    }

    private void fillFour(List<HexMazePanel> out) {
        out.add(fourOne);
        out.add(fourTwo);
        out.add(fourThree);
        out.add(fourFour);
        out.add(fourFive);
        out.add(fourSix);
        out.add(fourSeven);
    }

    private void fillFive(List<HexMazePanel> out) {
        out.add(fiveOne);
        out.add(fiveTwo);
        out.add(fiveThree);
        out.add(fiveFour);
        out.add(fiveFive);
        out.add(fiveSix);
    }

    private void fillSix(List<HexMazePanel> out) {
        out.add(sixOne);
        out.add(sixTwo);
        out.add(sixThree);
        out.add(sixFour);
        out.add(sixFive);
    }

    private void fillSeven(List<HexMazePanel> out) {
        out.add(sevenOne);
        out.add(sevenTwo);
        out.add(sevenThree);
        out.add(sevenFour);
    }

    @Override
    public void reset() {
        FacadeFX.clearText(exitDirectionLabel);
        Hexamaze.resetHexPanelList();
        FacadeFX.resetToggleGroups(hexGroup, hexColorGroup);
        FacadeFX.disableMultiple(redButton, yellowButton, greenButton, cyanButton, blueButton, pinkButton);
    }
}
