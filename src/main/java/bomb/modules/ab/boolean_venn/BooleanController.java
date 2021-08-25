package bomb.modules.ab.boolean_venn;

import bomb.abstractions.Resettable;
import bomb.tools.filter.Regex;
import bomb.tools.pattern.facade.FacadeFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import static bomb.tools.filter.Filter.LOGIC_SYMBOL_FILTER;
import static bomb.tools.filter.Filter.ultimateFilter;

public class BooleanController implements Resettable {
    private static final String PRESS_COLOR, DO_NOT_PRESS_COLOR, DEFAULT_TEXT;
    private static final Regex FIRST_SYMBOL_CAPTURE, SECOND_SYMBOL_CAPTURE;

    private boolean isFirstSymbol;
    private StringBuilder currentOperation;

    @FXML private MFXButton booleanAnd, booleanOr, booleanXor, booleanImplies, booleanNand, booleanNor, booleanXnor,
            booleanImpliedBy;

    @FXML private Circle a, b, c, ab, bc, ac, all, not;

    @FXML private MFXTextField booleanMathOperation;

    @FXML private MFXToggleButton priorityToggle;

    static {
        PRESS_COLOR = "rgba(115,208,115,1)";
        DO_NOT_PRESS_COLOR = "rgba(255,103,103,1)";
        DEFAULT_TEXT = "(AB)C";
        FIRST_SYMBOL_CAPTURE = new Regex("(\\(?A[^(B]?)");
        SECOND_SYMBOL_CAPTURE = new Regex("(B\\)?[^)C]?)");
    }

    public BooleanController() {
        isFirstSymbol = true;
        currentOperation = new StringBuilder(DEFAULT_TEXT);
    }

    public void initialize() {
        MFXButton[] buttonArray = {booleanAnd, booleanOr, booleanXor, booleanImplies, booleanNand, booleanNor,
                booleanXnor, booleanImpliedBy};
        int i = 0;
        EventHandler<ActionEvent> onActionEvent = createButtonAction();
        while (i < buttonArray.length) {
            buttonArray[i].setOnAction(onActionEvent);
            i++;
        }
    }

    private EventHandler<ActionEvent> createButtonAction(){
        return event -> {
            addSymbolToEquation((MFXButton) event.getSource());
            solveEquation();
            isFirstSymbol = !isFirstSymbol;
        };
    }

    @FXML
    private void switchEquationPriority() {
        priorityToggle.setText((!priorityToggle.isSelected() ? "AB" : "BC") + " Priority");

        if (currentOperation.toString().length() != DEFAULT_TEXT.length()) {
            currentOperation = shiftPriority(currentOperation.toString());
            solveEquation();
        } else overwriteOperationText(priorityToggle.isSelected() ? "A(BC)" : DEFAULT_TEXT);

        writeOutToTextField();
    }

    private void addSymbolToEquation(MFXButton button) {
        String tempCurrentOperation = currentOperation.toString();
        Regex letterCapture = isFirstSymbol ? FIRST_SYMBOL_CAPTURE : SECOND_SYMBOL_CAPTURE;

        letterCapture.loadText(tempCurrentOperation);
        letterCapture.hasMatch();
        StringBuilder capturedText = new StringBuilder(letterCapture.captureGroup(1));
        makeSymbolReplacement(capturedText, getMathSymbol(button));

        tempCurrentOperation = tempCurrentOperation.replaceAll(letterCapture.getOriginalPattern(), capturedText.toString());
        overwriteOperationText(tempCurrentOperation);
        writeOutToTextField();
    }

    private void makeSymbolReplacement(StringBuilder capturedText, String symbolToAdd){
        char lastLetter = capturedText.charAt(capturedText.length() - 1);

        if (isFirstSymbol && "AB)".indexOf(lastLetter) == -1)
            capturedText.deleteCharAt(capturedText.length() - 1);

        capturedText.append(symbolToAdd);
    }

    private void overwriteOperationText(String overwriteText){
        currentOperation.setLength(0);
        currentOperation.append(overwriteText);
    }

    private void solveEquation() {
        if (ultimateFilter(currentOperation.toString(), LOGIC_SYMBOL_FILTER).length() == 2){
            String code = BooleanVenn.resultCode(currentOperation.toString());
            setCircleFill(new Circle[]{not, c, b, a, bc, ac, ab, all}, code.toCharArray());
            toggleOperands(true);
        }
    }

    private void setCircleFill(Circle[] circles, char[] bits){
        for (int i = 0; i < circles.length; i++)
            circles[i].setFill(Paint.valueOf(bits[i] == '1' ? PRESS_COLOR : DO_NOT_PRESS_COLOR));
    }

    private String getMathSymbol(MFXButton button){
        return String.valueOf(button.getText().charAt(0));
    }

    private void toggleOperands(boolean toggle){
        FacadeFX.toggleNodes(toggle, booleanAnd, booleanOr, booleanXor, booleanImplies, booleanNand, booleanNor,
                booleanXnor, booleanImpliedBy);
    }

    private StringBuilder shiftPriority(String reference){
        StringBuilder temp = new StringBuilder();
        String[] referenceSplit = reference.replaceAll("[()]", "").split("B");

        if (priorityToggle.isSelected()) {
            temp.append(referenceSplit[0])
                    .append("(B")
                    .append(referenceSplit[1]).append(")");
        } else {
            temp.append("(")
                    .append(referenceSplit[0])
                    .append("B)")
                    .append(referenceSplit[1]);
        }
        return temp;
    }

    private void writeOutToTextField(){
        booleanMathOperation.setText(currentOperation.toString());
    }

    @FXML
    private void resetModule(){
        overwriteOperationText(DEFAULT_TEXT);
        writeOutToTextField();
        toggleOperands(false);
        priorityToggle.setSelected(false);
        priorityToggle.setText("AB Priority");

        String transparent = "rgba(130,130,130,0)";
        a.setFill(Paint.valueOf(transparent));
        b.setFill(Paint.valueOf(transparent));
        c.setFill(Paint.valueOf(transparent));
        ab.setFill(Paint.valueOf(transparent));
        ac.setFill(Paint.valueOf(transparent));
        bc.setFill(Paint.valueOf(transparent));
        all.setFill(Paint.valueOf(transparent));
        not.setFill(Paint.valueOf(transparent));
    }

    @Override
    public void reset() {
        resetModule();
    }
}
