package bomb.sub.controllers;

import bomb.modules.ab.Alphabet;
import bomb.modules.ab.Astrology;
import bomb.modules.ab.Bitwise;
import bomb.modules.ab.BlindAlley;
import bomb.modules.ab.BooleanVenn;
import bomb.enumerations.AstroSymbols;
import bomb.enumerations.BitwiseOps;
import bomb.interfaces.Reset;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import static bomb.tools.Mechanics.LOGIC_SYMBOL_REGEX;
import static bomb.tools.Mechanics.LOWERCASE_REGEX;
import static bomb.tools.Mechanics.ultimateFilter;

public class ABController implements Reset {
    private AstroSymbols[] set = new AstroSymbols[3];
    private boolean booleanShift = true;
    private StringBuilder currentOp = new StringBuilder();

    @FXML
    private Button astroReset, bitAnd, bitOr, bitXor, bitNot,
            boolAB, boolBC, boolAnd, boolOr, boolXor, boolImp,
            boolNand, boolNor, boolXnor, boolImpB, booleanReset;

    @FXML
    private Circle a, b, c, ab, bc, ac, all, not;

    @FXML
    private ImageView fire, water, earth, air,
            mercury, venus, mars, jupiter, saturn, uranus, neptune, pluto, sun, moon,
            aquarius, aries, cancer, capricorn, gemini, leo, libra, pisces, sag, scorpio, taurus, virgo;

    @FXML
    private Label maxAlley;

    @FXML
    private Tab blindAlley;

    @FXML
    private TextArea topLeft, topMid, midLeft, trueMid, midRight, bottomLeft, bottomMid, bottomRight;

    @FXML
    private TextField alphabetOut,
            bitOut,
            omen,
            boolOperation;

    //Alphabet methods
    @FXML
    private void getAlphaField(){
        if (!alphabetOut.getText().isEmpty()){
            String text = ultimateFilter(alphabetOut.getText(), LOWERCASE_REGEX);
            if (text.length() == 4){
                alphabetOut.setText(Alphabet.order(text));
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("That wasn't 4 valid characters there, bud");
                alert.show();
                alphabetOut.setText("");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("That field is empty there, bud");
            alert.show();
        }
    }

    //Astrology methods
    @FXML
    private void getElement(){
        if (fire.isHover()){
            set[0] = AstroSymbols.FIRE;
        } else if (earth.isHover()){
            set[0] = AstroSymbols.EARTH;
        } else if (air.isHover()){
            set[0] = AstroSymbols.AIR;
        } else if (water.isHover()){
            set[0] = AstroSymbols.WATER;
        }

        elementDisable(true);
        getOmen();
    }

    @FXML
    private void getZodiac(){
        if (aquarius.isHover()){
            set[2] = AstroSymbols.AQUARIUS;
        } else if (aries.isHover()){
            set[2] = AstroSymbols.ARIES;
        } else if (cancer.isHover()){
            set[2] = AstroSymbols.CANCER;
        } else if (capricorn.isHover()){
            set[2] = AstroSymbols.CAPRICORN;
        } else if (gemini.isHover()){
            set[2] = AstroSymbols.GEMINI;
        } else if (leo.isHover()){
            set[2] = AstroSymbols.LEO;
        } else if (libra.isHover()){
            set[2] = AstroSymbols.LIBRA;
        } else if (pisces.isHover()){
            set[2] = AstroSymbols.PISCES;
        } else if (sag.isHover()){
            set[2] = AstroSymbols.SAGITTARIUS;
        } else if (scorpio.isHover()){
            set[2] = AstroSymbols.SCORPIO;
        } else if (taurus.isHover()){
            set[2] = AstroSymbols.TAURUS;
        } else if (virgo.isHover()){
            set[2] = AstroSymbols.VIRGO;
        }
        zodiacDisable(true);
        getOmen();
    }

    @FXML
    private void getCeleste(){
        if (mercury.isHover()){
            set[1] = AstroSymbols.MERCURY;
        } else if (venus.isHover()){
            set[1] = AstroSymbols.VENUS;
        } else if (mars.isHover()){
            set[1] = AstroSymbols.MARS;
        } else if (jupiter.isHover()){
            set[1] = AstroSymbols.JUPITER;
        } else if (saturn.isHover()){
            set[1] = AstroSymbols.SATURN;
        } else if (uranus.isHover()){
            set[1] = AstroSymbols.URANUS;
        } else if (neptune.isHover()){
            set[1] = AstroSymbols.NEPTUNE;
        } else if (pluto.isHover()){
            set[1] = AstroSymbols.PLUTO;
        } else if (sun.isHover()){
            set[1] = AstroSymbols.SUN;
        } else if (moon.isHover()){
            set[1] = AstroSymbols.MOON;
        }
        celesteDisable(true);
        getOmen();
    }

    @FXML
    private void astroReset(){
        elementDisable(false);
        celesteDisable(false);
        zodiacDisable(false);
        set = new AstroSymbols[3];
        astroReset.setDisable(true);
    }

    private void elementDisable(boolean set){
        fire.setDisable(set);
        earth.setDisable(set);
        air.setDisable(set);
        water.setDisable(set);
    }

    private void celesteDisable(boolean set){
        sun.setDisable(set);
        moon.setDisable(set);
        mercury.setDisable(set);
        venus.setDisable(set);
        mars.setDisable(set);
        jupiter.setDisable(set);
        saturn.setDisable(set);
        uranus.setDisable(set);
        neptune.setDisable(set);
        pluto.setDisable(set);
    }

    private void zodiacDisable(boolean set){
        aquarius.setDisable(set);
        aries.setDisable(set);
        cancer.setDisable(set);
        capricorn.setDisable(set);
        gemini.setDisable(set);
        leo.setDisable(set);
        libra.setDisable(set);
        pisces.setDisable(set);
        sag.setDisable(set);
        scorpio.setDisable(set);
        taurus.setDisable(set);
        virgo.setDisable(set);
    }

    private void getOmen(){
        try {
            if (set[0] != null && set[1] != null && set[2] != null) {
                omen.setText(Astrology.calculate(set[0], set[1], set[2]));
                astroReset();
                astroReset.setDisable(true);
            } else {
                astroReset.setDisable(false);
            }
        } catch (IllegalArgumentException illegal){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The Serial Code wasn't set");
            alert.showAndWait();
        }
    }

    //Blind Alley methods
    @FXML
    private void liveUpdate() {
        if (blindAlley.isSelected()){
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
    }

    private void stylerSet(int[][] array){
        topLeft.setStyle(style(array[0][0]));
        topMid.setStyle(style(array[0][1]));
        midLeft.setStyle(style(array[1][0]));
        trueMid.setStyle(style(array[1][1]));
        midRight.setStyle(style(array[1][2]));
        bottomLeft.setStyle(style(array[2][0]));
        bottomMid.setStyle(style(array[2][1]));
        bottomRight.setStyle(style(array[2][2]));
    }

    private String style(int number){
        switch (number){
            case 0: return "-fx-text-fill: black";
            case 1: return "-fx-text-fill: green";
            case 2: return "-fx-text-fill: yellow";
            case 3: return "-fx-text-fill: orange";
            default: return "-fx-text-fill: red";
        }
    }

    private void writeMax(int[][] array){
        int max = 0;
        for (int[] col : array){
            for (int num : col){
                if (num > max) max = num;
            }
        }

        maxAlley.setText("All with " + max) ;
    }

    //Bitwise Op method
    @FXML
    private void bitwiseSolve(){
        BitwiseOps bit = null;
        if (bitAnd.isHover()){
            bit = BitwiseOps.AND;
        } else if (bitOr.isHover()){
            bit = BitwiseOps.OR;
        } else if (bitXor.isHover()){
            bit = BitwiseOps.XOR;
        } else if (bitNot.isHover()){
            bit = BitwiseOps.NOT;
        }
        try {
            bitOut.setText(Bitwise.getByte(bit));
        } catch (IllegalArgumentException illegal){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, illegal.getMessage());
            alert.setHeaderText("Something's empty");
            alert.setTitle("Do more edge work");
            alert.show();
        }
    }

    //Boolean Venn Diagram Methods
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
        String press = "rgb(115,208,115)", dontPress = "rgb(255,103,103)";
        if (ultimateFilter(currentOp.toString(), LOGIC_SYMBOL_REGEX).length() == 2){
            String code = BooleanVenn.resultCode(currentOp.toString());
            not.setFill(Paint.valueOf(code.charAt(0)=='1'?press:dontPress));
            c.setFill(Paint.valueOf(code.charAt(1)=='1'?press:dontPress));
            b.setFill(Paint.valueOf(code.charAt(2)=='1'?press:dontPress));
            a.setFill(Paint.valueOf(code.charAt(3)=='1'?press:dontPress));
            bc.setFill(Paint.valueOf(code.charAt(4)=='1'?press:dontPress));
            ac.setFill(Paint.valueOf(code.charAt(5)=='1'?press:dontPress));
            ab.setFill(Paint.valueOf(code.charAt(6)=='1'?press:dontPress));
            all.setFill(Paint.valueOf(code.charAt(7)=='1'?press:dontPress));
        }
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
        boolAnd.setDisable(toggle);
        boolOr.setDisable(toggle);
        boolXor .setDisable(toggle);
        boolImp.setDisable(toggle);
        boolNand.setDisable(toggle);
        boolNor.setDisable(toggle);
        boolXnor.setDisable(toggle);
        boolImpB.setDisable(toggle);
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
        boolOperation.setText("");
        toggleOps(true);
        boolAB.setDisable(false);
        boolBC.setDisable(false);
        booleanReset.setDisable(true);
        a.setFill(Paint.valueOf("rgba(130,130,130,0)"));
        b.setFill(Paint.valueOf("rgba(130,130,130,0)"));
        c.setFill(Paint.valueOf("rgba(130,130,130,0)"));
        ab.setFill(Paint.valueOf("rgba(130,130,130,0)"));
        ac.setFill(Paint.valueOf("rgba(130,130,130,0)"));
        bc.setFill(Paint.valueOf("rgba(130,130,130,0)"));
        all.setFill(Paint.valueOf("rgba(130,130,130,0)"));
        not.setFill(Paint.valueOf("rgba(130,130,130,0)"));
    }

    @Override
    public void reset() {
        bitOut.setText("");
    }
}
