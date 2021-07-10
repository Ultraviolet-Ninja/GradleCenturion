package bomb.modules.t.translated;

import bomb.enumerations.TheButton;
import bomb.abstractions.Resettable;
import bomb.tools.facade.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.io.IOException;

import static bomb.tools.Mechanics.ultimateFilter;

public class TranslationController implements Resettable {
    private static final String RED_STYLE = "-fx-text-fill: #EB190E", YELLOW_STYLE = "-fx-text-fill: #FBE118",
            BLUE_STYLE = "-fx-text-fill: #3043AC",
            WHITE_STYLE = "-fx-text-fill: #FFF9ED; -fx-background-color: black";

    private boolean firstTime = true;
    private Label current = new Label();
    private TheButton[] traits = new TheButton[2];

    @FXML
    private ImageView brazil, czech, danish, dutch, english, esperanto, estonian,
            finnish, french, german, italian, norwegian, polish, spanish, swedish;

    @FXML
    private Label brazilLabel, czechLabel, danishLabel, dutchLabel, englishLabel, esperantoLabel, estonianLabel,
            finnishLabel, frenchLabel, germanLabel, italianLabel, norwegianLabel, polishLabel,
            spanishLabel, swedishLabel,
            yesLabel, noLabel;

    @FXML
    private RadioButton red, blue, yellow, white, press, hold, detonate, abort;

    @FXML
    private Tab button, onFirst, morse, password, ventGas;

    @FXML
    private TextArea morseOutput;

    @FXML
    private TextField
            morseInput, morseLetters, morseManual, morseManOut;

    public void initialize(){
        red.setStyle(RED_STYLE);
        yellow.setStyle(YELLOW_STYLE);
        blue.setStyle(BLUE_STYLE);
        white.setStyle(WHITE_STYLE);
        try {
            Morse.init();
        } catch (IOException ioException){
            FacadeFX.setAlert(Alert.AlertType.WARNING, "Hey Chief, the file has a problem");
        }
    }

    //Translation Center Method
    @FXML
    private void getFlag(){
        if (brazil.isHover()){
            TranslationCenter.setLanguage("Brazil");
            setNextLabel(brazilLabel);
        } else if (czech.isHover()){
            TranslationCenter.setLanguage("Czech");
            setNextLabel(czechLabel);
        } else if (danish.isHover()){
            TranslationCenter.setLanguage("Denmark");
            setNextLabel(danishLabel);
        } else if (dutch.isHover()){
            TranslationCenter.setLanguage("Netherlands");
            setNextLabel(dutchLabel);
        } else if (english.isHover()){
            TranslationCenter.setLanguage("US");
            setNextLabel(englishLabel);
        } else if (esperanto.isHover()){
            TranslationCenter.setLanguage("Esperanto");
            setNextLabel(esperantoLabel);
        } else if (estonian.isHover()){
            TranslationCenter.setLanguage("Estonia");
            setNextLabel(estonianLabel);
        } else if (finnish.isHover()){
            TranslationCenter.setLanguage("Finnish");
            setNextLabel(finnishLabel);
        } else if (french.isHover()){
            setNextLabel(frenchLabel);
        } else if (german.isHover()){
            TranslationCenter.setLanguage("");
            setNextLabel(germanLabel);
        } else if (italian.isHover()){
            setNextLabel(italianLabel);
        } else if (norwegian.isHover()){
            setNextLabel(norwegianLabel);
        } else if (polish.isHover()){
            setNextLabel(polishLabel);
        } else if (spanish.isHover()){
            setNextLabel(spanishLabel);
        } else if (swedish.isHover()){
            setNextLabel(swedishLabel);
        }
        clearButtons();
    }

    private void setNextLabel(Label next){
        current.setStyle("-fx-text-fill: black");
        current = next;
        current.setStyle("-fx-text-fill: crimson");
        tabRename();
        buttonRename();
        if (firstTime){
            firstTime = false;
            button.setDisable(false);
            ventGas.setDisable(false);
            morse.setDisable(false);
            password.setDisable(false);
            onFirst.setDisable(false);
        }
    }

    private void tabRename(){
        button.setText(TranslationCenter.moduleNames[0] + "(Button)");
        onFirst.setText(TranslationCenter.moduleNames[1] + "(Who's On First)");
        morse.setText(TranslationCenter.moduleNames[2] + "(Morse Code)");
        password.setText(TranslationCenter.moduleNames[3] + "(Password)");
        ventGas.setText(TranslationCenter.moduleNames[4] + "(Vent Gas)");
    }

    private void buttonRename(){
        red.setText(TranslationCenter.buttonNames[0]);
        blue.setText(TranslationCenter.buttonNames[1]);
        yellow.setText(TranslationCenter.buttonNames[2]);
        white.setText(TranslationCenter.buttonNames[3]);
        press.setText(TranslationCenter.buttonNames[4]);
        hold.setText(TranslationCenter.buttonNames[5]);
        detonate.setText(TranslationCenter.buttonNames[6]);
        abort.setText(TranslationCenter.buttonNames[7]);
    }

    private void clearButtons(){
        traits = new TheButton[2];
        FacadeFX.setToggleButtonsUnselected(red, white, blue, yellow, abort, press, detonate, hold);
    }

    //Button Methods
    @FXML
    private void scanButtons(){
        if (blue.isSelected()){
            traits[0] = TheButton.BLUE;
        } else if (red.isSelected()){
            traits[0] = TheButton.RED;
        } else if (yellow.isSelected()){
            traits[0] = TheButton.YELLOW;
        } else if (white.isSelected()){
            traits[0] = TheButton.WHITE;
        }

        if (abort.isSelected()){
            traits[1] = TheButton.ABORT;
        } else if (press.isSelected()){
            traits[1] = TheButton.PRESS;
        } else if (detonate.isSelected()){
            traits[1] = TheButton.DETONATE;
        } else if (hold.isSelected()){
            traits[1] = TheButton.HOLD;
        }

        if (traits[0] != null && traits[1] != null){
            String goingIn = Button.evaluate(traits);

//            isHold(goingIn);
//            buttonLabel.setText(goingIn + " the Button");
        }
    }

    private void isHold(String text){
//        if (text.equals("Hold")){
//            stripRed.setOpacity(0.5);
//            stripBlue.setOpacity(0.5);
//            stripYellow.setOpacity(0.5);
//            stripWhite.setOpacity(0.5);
//            stripNumTop.setOpacity(1);
//            stripNumBottom.setOpacity(1);
//        } else {
//            stripRed.setOpacity(0);
//            stripBlue.setOpacity(0);
//            stripYellow.setOpacity(0);
//            stripWhite.setOpacity(0);
//            stripNumTop.setOpacity(0);
//            stripNumBottom.setOpacity(0);
//        }
    }

    //Morse Code Methods
    @FXML
    private void typeTrigger() {
        String input = ultimateFilter(morseInput.getText(),
                "-", ".", " ");

        if (!input.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            String[] outputs = Morse.translate(input),
                    results = outputs[0].split("/");
            for (int i = 0; i < results.length; i++){
                builder.append(results[i]);
                if (i < results.length -1){
                    builder.append("\n");
                }
            }
            morseOutput.setText(builder.toString());
            if (!outputs[1].contains("null")) {
                morseLetters.setText(outputs[1]);
            } else {
                morseLetters.setText("");
            }
        } else {
            morseOutput.setText("");
            morseInput.setText(input);
            morseLetters.setText("");
        }
    }

    @FXML
    private void searchWord(){
//        String takeIn = ultimateFilter(morseManual.getText().toLowerCase(), lowercaseRegex);
//        if (!takeIn.isEmpty()){
//            takeIn = Morse.predict(takeIn);
//            MorseCodeFrequencies[] freqs = MorseCodeFrequencies.values();
//
//            for (int i = 0; i < freqs.length; i++){
//                if (takeIn.equals(freqs[i].getLabel())){
//                    morseManOut.setText(freqs[i].getLabel() + " - " + freqs[i].frequency() + "MHz");
//                    i = MorseCodeFrequencies.values().length;
//                } else {
//                    morseManOut.setText("");
//                }
//            }
//        } else {
//            morseManOut.setText("");
//        }
    }

    @FXML
    private void clearMorseFields(){
        morseOutput.setText("");
        morseInput.setText("");
        morseManOut.setText("");
        morseManual.setText("");
        morseLetters.setText("");
    }

    //Vent Gas method
    @FXML
    private void setGas(){
        if (ventGas.isSelected()){
            yesLabel.setText(VentGas.writeYes());
            noLabel.setText(VentGas.writeNo());
        }
    }

    @Override
    public void reset() {

    }
}
