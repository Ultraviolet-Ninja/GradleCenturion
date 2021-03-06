package bomb.modules.dh.hexamaze;

import bomb.components.hex.HexMazePanel;
import bomb.abstractions.Resettable;
import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class HexamazeController implements Resettable {
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

    public void initialize(){
        Hexamaze.setupVariables(transportPanels());
    }


    @FXML
    private void setShape(){
        toggleColorControl();
        if (!FacadeFX.getToggleName(hexGroup).equals("Peg"))
            Hexamaze.setShapeSelector(FacadeFX.getToggleName(hexGroup));
    }

    private void toggleColorControl(){
        boolean toggle = !FacadeFX.getToggleName(hexGroup).equals("Peg");
        FacadeFX.toggleNodes(toggle, redButton, yellowButton, greenButton, cyanButton, blueButton, pinkButton);
    }

    @FXML
    private void setPegFill(){
        Hexamaze.setPegFillSelector(colorPicker(FacadeFX.getToggleName(hexColorGroup)));
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
        if (FacadeFX.getToggleName(hexGroup).equals("Peg")) {
            if (FacadeFX.getToggleName(hexColorGroup)!= null)
                Hexamaze.setPegFillSelector();
        }
        else
            Hexamaze.setShapeFill();
    }

    @FXML
    private void mazeCompare(){
        try {
            Hexamaze.compareToFullMaze();
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

    @Override
    public void reset() {

    }
}
