package bomb.sub.controllers;

import bomb.modules.s.SimonStates;
import bomb.enumerations.Simon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Polygon;

public class SController {
    private boolean redSwitch = false, yellowSwitch = false, greenSwitch = false, blueSwitch = false;
    private int stageCounter = 1;
    private StringBuilder statesMemory = new StringBuilder();
    private final String red = "rgba(69,1,0,1)|rgb(31,0,2)", yellow = "rgba(68,67,0,1)|rgb(43,45,0)",
            green = "rgba(0,61,1,1)|rgb(0,43,1)", blue = "rgba(0,23,82,1)|rgb(0,18,65)";
    private String[] colors;

    @FXML
    private Arc topLeft, topRight, bottomLeft, bottomRight;

    @FXML
    private Button statesRed, statesYellow, statesGreen, statesBlue, statesEnter;

    @FXML
    private Polygon nWest, north, nEast, sWest, south, sEast;

    @FXML
    private TextArea statesOutput;

    //Simon Screams methods


    //Simon States methods
    @FXML
    private void setPriority(){
        setFalse();
        stageCounter = 1;
        if (statesRed.isHover())
            setPriority("Red");
        else if (statesYellow.isHover())
            setPriority("Yellow");
        else if (statesGreen.isHover())
            setPriority("Green");
        else if (statesBlue.isHover())
            setPriority("Blue");
    }

    private void setFalse(){
        redSwitch = false;
        yellowSwitch = false;
        greenSwitch = false;
        blueSwitch = false;
    }

    private void setPriority(String color){ //TL TR BL BR
        switch (color) { //Red Blue Green Yellow
            case "Red": {
                setFills(new String[]{red, blue, green, yellow});
                SimonStates.setPriority(Simon.States.RED);
            } // Yellow Green Blue Red
            case "Yellow": {
                setFills(new String[]{yellow, green, blue, red});
                SimonStates.setPriority(Simon.States.YELLOW);
            } // Green Red Yellow Blue
            case "Green": {
                setFills(new String[]{green, red, yellow, blue});
                SimonStates.setPriority(Simon.States.GREEN);
            } //Blue Yellow Red Green
            default: {
                setFills(new String[]{blue, yellow, red, green});
                SimonStates.setPriority(Simon.States.BLUE);
            }
        }
        toggleArcs(false);
    }

    private void setFills(String[] values){
        colors = values;
        setFill(topLeft, values[0]);
        setFill(topRight, values[1]);
        setFill(bottomLeft, values[2]);
        setFill(bottomRight, values[3]);
    }

    private void setFill(Arc arc, String value){
        String[] values = value.split("\\|");
        arc.setFill(Paint.valueOf(values[0]));
        arc.setStroke(Paint.valueOf(values[1]));
    }

    private void toggleArcs(boolean tog){
        topLeft.setDisable(tog);
        topRight.setDisable(tog);
        bottomLeft.setDisable(tog);
        bottomRight.setDisable(tog);
    }

    private void toggleButtons(boolean tog){
        statesRed.setDisable(tog);
        statesYellow.setDisable(tog);
        statesGreen.setDisable(tog);
        statesBlue.setDisable(tog);
    }

    @FXML
    private void colorSet(){
        if (topLeft.isHover())
            determineColor(topLeft, 0);
        else if (topRight.isHover())
            determineColor(topRight, 1);
        else if (bottomLeft.isHover())
            determineColor(bottomLeft, 2);
        else
            determineColor(bottomRight, 3);
        enterButtonDisable();
    }

    private void determineColor(Arc arc, int idx){
        String redLit = "rgb(255,18,0)|rgb(184,69,50)", yellowLit = "rgb(255,243,0)|rgb(185,158,0)",
                greenLit = "rgb(0,218,0)|rgb(18,145,26)", blueLit = "rgb(0,108,255)|rgb(39,86,177)";
        switch (colors[idx]){
            case red: {
                setFill(arc, redSwitch ? red : redLit);
                redSwitch = !redSwitch;
            }
            case yellow: {
                setFill(arc, yellowSwitch ? yellow : yellowLit);
                yellowSwitch = !yellowSwitch;
            }
            case green: {
                setFill(arc, greenSwitch ? green : greenLit);
                greenSwitch = !greenSwitch;
            }
            default: {
                setFill(arc, blueSwitch ? blue : blueLit);
                blueSwitch = !blueSwitch;
            }
        }
    }

    private void enterButtonDisable(){
        statesEnter.setDisable(!redSwitch && !yellowSwitch && !greenSwitch && !blueSwitch);
    }

    @FXML
    private void nextButtonArray(){
        Simon.States[] currentStage = deterList();
        statesMemory.append("Stage ").append(stageCounter).append(" ")
                .append(SimonStates.add(currentStage, stageCounter++)).append("\n");
        statesOutput.setText(statesMemory.toString());
        checkEnd();
    }

    private Simon.States[] deterList(){
        int counter = 0;
        Simon.States[] list = new Simon.States[deterSize()];
        if (redSwitch)
            list[counter++] = Simon.States.RED;
        if (yellowSwitch)
            list[counter++] = Simon.States.YELLOW;
        if (greenSwitch)
            list[counter++] = Simon.States.GREEN;
        if (blueSwitch)
            list[counter] = Simon.States.BLUE;
        return list;
    }

    private int deterSize(){
        int counter = 0;
        if (redSwitch)
            counter++;
        if (yellowSwitch)
            counter++;
        if (greenSwitch)
            counter++;
        if (blueSwitch)
            counter++;
        return counter;
    }

    private void checkEnd(){
        if (stageCounter == 5) {
            toggleArcs(true);
            toggleButtons(true);
            statesEnter.setDisable(true);
        }
    }

    @FXML
    private void stateModReset(){
        toggleArcs(true);
        stageCounter = 1;
        statesMemory = new StringBuilder();
        toggleButtons(false);
        SimonStates.resetMod();
    }
}