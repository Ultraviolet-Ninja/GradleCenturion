package bomb.modules.ab.boolean_venn;

import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import static bomb.tools.Mechanics.LOGIC_SYMBOL_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

public class BooleanController {
    private static final String PRESS = "rgba(115,208,115,1)",
            NO_PRESS = "rgba(255,103,103,1)", CLEAR = "rgba(130,130,130,0)";

    private boolean booleanShift = true;
    private StringBuilder currentOp = new StringBuilder();

    @FXML
    private Button boolAB, boolBC, boolAnd, boolOr, boolXor, boolImp,
            boolNand, boolNor, boolXnor, boolImpB, booleanReset;

    @FXML
    private Circle a, b, c, ab, bc, ac, all, not;

    @FXML
    private TextField boolOperation;

    @FXML
    private void prioritySet(){
        booleanReset.setDisable(false);
        if (currentOp.toString().isEmpty()) {
            currentOp.append(boolAB.isHover() ? "(AB)C" : "A(BC)");
            toggleOps(false);
        }
        else
            currentOp = reFormatPriority(currentOp.toString());
        setCircles();
        writeOut(currentOp.toString());
        switchButton();
    }

    @FXML
    private void operate(){
        if (booleanShift){
            currentOp = adder(currentOp.toString(), "B");
            booleanShift = false;
        } else {
            currentOp = adder(currentOp.toString(), "C");
            toggleOps(true);
            setCircles();
            booleanShift = true;
        }
        writeOut(currentOp.toString());
    }

    private void setCircles(){
        if (ultimateFilter(currentOp.toString(), LOGIC_SYMBOL_REGEX).length() == 2){
            String code = BooleanVenn.resultCode(currentOp.toString());
            setFill(new Circle[]{not, c, b, a, bc, ac, ab, all}, code.toCharArray());
        }
    }

    private void setFill(Circle[] circles, char[] bits){
        for (int i = 0; i < circles.length; i++)
            circles[i].setFill(Paint.valueOf(bits[i] == '1' ? PRESS : NO_PRESS));
    }

    private StringBuilder adder(String text, String splitter){
        if (text.charAt(text.length()-1) == ')' && !splitter.equals("C")) splitter = "\\(";
        StringBuilder temp = new StringBuilder();
        temp.append(text.split(splitter)[0]);
        temp.append(operand());
        temp.append(splitter.replace('\\', ' ').replaceAll(" ", ""));
        if (splitter.equals("(") || splitter.equals("B") || text.charAt(text.length()-1) == ')')
            temp.append(text.split(splitter)[1]);
        return temp;
    }

    private String operand(){
        if (boolAnd.isHover()){
            return grabIt(boolAnd);
        } else if (boolOr.isHover()){
            return grabIt(boolOr);
        } else if (boolXor.isHover()){
            return grabIt(boolXor);
        } else if (boolImp.isHover()){
            return grabIt(boolImp);
        } else if (boolNand.isHover()){
            return grabIt(boolNand);
        } else if (boolNor.isHover()){
            return grabIt(boolNor);
        } else if (boolXnor.isHover()){
            return grabIt(boolXnor);
        } else {
            return grabIt(boolImpB);
        }
    }

    private String grabIt(Button button){
        return String.valueOf(button.getText().charAt(0));
    }

    private void toggleOps(boolean toggle){
        FacadeFX.toggleNodes(toggle, boolAnd, boolOr, boolXor, boolImp, boolNand, boolNor, boolXnor, boolImpB);
    }

    private StringBuilder reFormatPriority(String reference){
        StringBuilder temp = new StringBuilder();
        if (reference.charAt(0) == '(') { //When AB has Priority
            reference = reference.replaceAll("\\(", "")
                    .replaceAll("\\)", "");
            temp.append(reference.split("B")[0]).append("(B")
                    .append(reference.split("B")[1]).append(")");
        } else { // When BC has Priority
            reference = reference.replaceAll("\\(", "")
                    .replaceAll("\\)", "");
            temp.append("(").append(reference.split("B")[0]).append("B)")
                    .append(reference.split("B")[1]);
        }
        return temp;
    }

    private void writeOut(String in){
        boolOperation.setText(in);
    }

    private void switchButton(){
        boolAB.setDisable(boolAB.isHover());
        boolBC.setDisable(boolBC.isHover());
    }

    @FXML
    private void resetBool(){
        currentOp = new StringBuilder();
        booleanShift = true;
        FacadeFX.clearText(boolOperation);
        toggleOps(true);
        FacadeFX.toggleNodes(false, boolAB, boolBC);
        booleanReset.setDisable(true);
        a.setFill(Paint.valueOf(CLEAR));
        b.setFill(Paint.valueOf(CLEAR));
        c.setFill(Paint.valueOf(CLEAR));
        ab.setFill(Paint.valueOf(CLEAR));
        ac.setFill(Paint.valueOf(CLEAR));
        bc.setFill(Paint.valueOf(CLEAR));
        all.setFill(Paint.valueOf(CLEAR));
        not.setFill(Paint.valueOf(CLEAR));
    }
}
