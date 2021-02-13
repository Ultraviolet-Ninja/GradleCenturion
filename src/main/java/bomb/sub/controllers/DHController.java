package bomb.sub.controllers;

import bomb.components.hex.HexMazePanel;
import bomb.modules.dh.emoji_math.EmojiMath;
import bomb.modules.dh.hexamaze.Hexamaze;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static bomb.enumerations.Emojis.*;

public class DHController {
    private boolean hexColorToggleSwitch = false;

    private int eqCount = 0;
    private StringBuilder equation = new StringBuilder();
    private final ToggleGroup hexGroup = new ToggleGroup(),
            hexColorGroup = new ToggleGroup();

    @FXML
    private Button first, second, third, forth, fifth,
            sixth, seventh, eighth, ninth, tenth,
            add, minus, equal, clear;

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

    @FXML
    private TextField equationBar;

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

    //Emoji Math methods
    @FXML
    private void operator(){
        eqCount = 3;
        if (add.isHover()) equation.append("+");
        else if (minus.isHover()) equation.append("-");
        equationBar.setText(equation.toString());
        disableControl();
    }

    @FXML
    private void addToEquation(){
        eqCount++;
        if (first.isHover())
            equation.append(COLON_CLOSE.getLabel());
        else if (second.isHover())
            equation.append(EQUAL_OPEN.getLabel());
        else if (third.isHover())
            equation.append(OPEN_COLON.getLabel());
        else if (forth.isHover())
            equation.append(CLOSED_EQUAL.getLabel());
        else if (fifth.isHover())
            equation.append(COLON_OPEN.getLabel());
        else if (sixth.isHover())
            equation.append(CLOSED_COLON.getLabel());
        else if (seventh.isHover())
            equation.append(EQUAL_CLOSED.getLabel());
        else if (eighth.isHover())
            equation.append(OPEN_EQUAL.getLabel());
        else if (ninth.isHover())
            equation.append(COLON_BAR.getLabel());
        else if (tenth.isHover())
            equation.append(BAR_COLON.getLabel());
        equationBar.setText(equation.toString());
        disableControl();
    }

    private void disableControl(){
        switch (eqCount){
            case 1: {
                add.setDisable(false);
                minus.setDisable(false);
                clear.setDisable(false);
            } break;
            case 2:
            case 5: toggleNumberButtons(true); break;
            case 3: {
                add.setDisable(true);
                minus.setDisable(true);
                toggleNumberButtons(false);
            } break;
            case 4: equal.setDisable(false);
        }
    }

    @FXML
    private void submitEquation(){
        equation.append("\t = ").append(EmojiMath.calculate(equationBar.getText()));
        equationBar.setText(equation.toString());
        clearParam();
    }

    @FXML
    private void clearParam(){
        eqCount = 0;
        add.setDisable(true);
        minus.setDisable(true);
        equal.setDisable(true);
        clear.setDisable(true);
        toggleNumberButtons(false);
        equation = new StringBuilder();
    }

    private void toggleNumberButtons(boolean set) {
        first.setDisable(set);
        second.setDisable(set);
        third.setDisable(set);
        forth.setDisable(set);
        fifth.setDisable(set);
        sixth.setDisable(set);
        seventh.setDisable(set);
        eighth.setDisable(set);
        ninth.setDisable(set);
        tenth.setDisable(set);
    }

    //Hexamaze methods
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
