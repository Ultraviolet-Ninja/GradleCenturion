package bomb.modules.dh.hexamaze;

import bomb.components.hex.HexMazePanel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class HexamazeController {
    private final ToggleGroup hexGroup = new ToggleGroup(),
            hexColorGroup = new ToggleGroup();

    @FXML
    private HexMazePanel oneOne, oneTwo, oneThree, oneFour,
            twoOne, twoTwo, twoThree, twoFour, twoFive,
            threeOne, threeTwo, threeThree, threeFour, threeFive, threeSix,
            fourOne, fourTwo, fourThree, fourFour, fourFive, fourSix, fourSeven,
            fiveOne, fiveTwo, fiveThree, fiveFour, fiveFive, fiveSix,
            sixOne, sixTwo, sixThree, sixFour, sixFive,
            sevenOne, sevenTwo, sevenThree, sevenFour;

    @FXML
    private RadioButton nullButton, circleButton, hexButton, lTriangleButton,
            rTriangleButton, uTriangleButton, dTriangleButton, pegButton,
            redButton, yellowButton, greenButton, cyanButton, blueButton, pinkButton;

    public void initialize(){
        setHexGroup();
        setHexColorGroup();
        Hexamaze.setupVariables();
    }

    private void setHexGroup(){
        nullButton.setToggleGroup(hexGroup);
        circleButton.setToggleGroup(hexGroup);
        hexButton.setToggleGroup(hexGroup);
        pegButton.setToggleGroup(hexGroup);
        lTriangleButton.setToggleGroup(hexGroup);
        rTriangleButton .setToggleGroup(hexGroup);
        uTriangleButton .setToggleGroup(hexGroup);
        dTriangleButton.setToggleGroup(hexGroup);
        pegButton.setToggleGroup(hexGroup);
    }

    private void setHexColorGroup(){
        redButton.setToggleGroup(hexColorGroup);
        yellowButton.setToggleGroup(hexColorGroup);
        greenButton.setToggleGroup(hexColorGroup);
        cyanButton.setToggleGroup(hexColorGroup);
        blueButton.setToggleGroup(hexColorGroup);
        pinkButton.setToggleGroup(hexColorGroup);
    }


    @FXML
    private void setShape(){
        toggleColorControl();
        if (!((RadioButton) hexGroup.getSelectedToggle()).getText().equals("Peg"))
            Hexamaze.setShapeSelector(((RadioButton) hexGroup.getSelectedToggle()).getText());
    }

    private void toggleColorControl(){
        toggleColor(!((RadioButton)hexGroup.getSelectedToggle()).getText().equals("Peg"));

//        if (toggleFlag){
//            toggleColor(!toggleFlag);
//            hexColorToggleSwitch = toggleFlag;
//        } else if (hexColorToggleSwitch){
//            toggleColor(toggleFlag);
//            hexColorToggleSwitch = !toggleFlag;
//        }
    }

    private void toggleColor(boolean toggle){
        redButton.setDisable(toggle);
        yellowButton.setDisable(toggle);
        greenButton.setDisable(toggle);
        cyanButton.setDisable(toggle);
        blueButton.setDisable(toggle);
        pinkButton.setDisable(toggle);
    }

    @FXML
    private void setPegFill(){
        Hexamaze.setPegFillSelector(colorPicker(((RadioButton)hexColorGroup.getSelectedToggle()).getText()));
    }

    private Color colorPicker(String strColor){
        switch(strColor){
            case "Red": return Color.RED;
            case "Yellow": return Color.YELLOW;
            case "Green": return Color.GREEN;
            case "Cyan": return Color.CYAN;
            case "Blue": return Color.BLUE;
            case "Pink": return Color.PINK;
            default: return new Color(0.776, 0.766, 0.776, 1.0);
        }
    }

    @FXML
    private void plotShape(){
        if (((RadioButton) hexGroup.getSelectedToggle()).getText().equals("Peg")) {
            if (hexColorGroup.getSelectedToggle() != null)
                Hexamaze.setPegFillSelector(transportPanels());
        }
        else
            Hexamaze.setShapeFill(transportPanels());
    }

    @FXML
    private void mazeCompare(){
        try {
            Hexamaze.compareToFullMaze(transportPanels());
        } catch (IllegalArgumentException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private ArrayList<HexMazePanel> transportPanels(){
        ArrayList<HexMazePanel> output = new ArrayList<>();
        fillOne(output);
        fillTwo(output);
        fillThree(output);
        fillFour(output);
        fillFive(output);
        fillSix(output);
        fillSeven(output);
        return output;
    }

    private void fillOne(ArrayList<HexMazePanel> out){
        out.add(oneOne);
        out.add(oneTwo);
        out.add(oneThree);
        out.add(oneFour);
    }

    private void fillTwo(ArrayList<HexMazePanel> out){
        out.add(twoOne);
        out.add(twoTwo);
        out.add(twoThree);
        out.add(twoFour);
        out.add(twoFive);
    }

    private void fillThree(ArrayList<HexMazePanel> out){
        out.add(threeOne);
        out.add(threeTwo);
        out.add(threeThree);
        out.add(threeFour);
        out.add(threeFive);
        out.add(threeSix);
    }

    private void fillFour(ArrayList<HexMazePanel> out){
        out.add(fourOne);
        out.add(fourTwo);
        out.add(fourThree);
        out.add(fourFour);
        out.add(fourFive);
        out.add(fourSix);
        out.add(fourSeven);
    }

    private void fillFive(ArrayList<HexMazePanel> out){
        out.add(fiveOne);
        out.add(fiveTwo);
        out.add(fiveThree);
        out.add(fiveFour);
        out.add(fiveFive);
        out.add(fiveSix);
    }

    private void fillSix(ArrayList<HexMazePanel> out){
        out.add(sixOne);
        out.add(sixTwo);
        out.add(sixThree);
        out.add(sixFour);
        out.add(sixFive);
    }

    private void fillSeven(ArrayList<HexMazePanel> out){
        out.add(sevenOne);
        out.add(sevenTwo);
        out.add(sevenThree);
        out.add(sevenFour);
    }
}
