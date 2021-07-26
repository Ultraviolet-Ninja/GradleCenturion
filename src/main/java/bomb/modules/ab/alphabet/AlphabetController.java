package bomb.modules.ab.alphabet;

import bomb.abstractions.Resettable;
import bomb.tools.facade.FacadeFX;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

import static bomb.tools.TextFormatterFactory.createOneLetterFormatter;
import static javafx.scene.control.Alert.AlertType.ERROR;

public class AlphabetController implements Resettable {
    private final Map<JFXTextField, JFXTextField> stateMap;

    @FXML
    private JFXTextField firstInput, secondInput, thirdInput, forthInput,
            firstOutput, secondOutput, thirdOutput, forthOutput;

    public AlphabetController(){
        stateMap = new HashMap<>();
    }

    public void initialize() {
        stateMap.put(firstInput, secondInput);
        stateMap.put(secondInput, thirdInput);
        stateMap.put(thirdInput, forthInput);
        stateMap.put(forthInput, null);
        for (JFXTextField input : stateMap.keySet()){
            input.setTextFormatter(createOneLetterFormatter());
            input.setOnKeyReleased(actionHandler());
        }
    }

    private EventHandler<KeyEvent> actionHandler(){
        return event -> {
            JFXTextField source = (JFXTextField) event.getSource();
            boolean canGetResults = false;
            if (!source.getText().isEmpty()) {
                canGetResults = moveToLastEmptyTextField(source);
            }
            if (canGetResults) getResults();
        };
    }

    private boolean moveToLastEmptyTextField(JFXTextField source){
        do {
            source = stateMap.get(source);
        } while (source != null && !source.getText().isEmpty());

        if (source != null) {
            source.requestFocus();
            return false;
        }
        return true;
    }

    private void getResults(){
        String inputText = firstInput.getText() + secondInput.getText() + thirdInput.getText() + forthInput.getText();
        try {
            String results = Alphabet.order(inputText);
            firstOutput.setText(String.valueOf(results.charAt(0)));
            secondOutput.setText(String.valueOf(results.charAt(1)));
            thirdOutput.setText(String.valueOf(results.charAt(2)));
            forthOutput.setText(String.valueOf(results.charAt(3)));
        } catch (IllegalArgumentException arg){
            FacadeFX.setAlert(ERROR, arg.getMessage());
        }
    }

    @FXML
    private void resetPuzzle(){
        reset();
    }

    @Override
    public void reset() {
        FacadeFX.clearMultipleTextFields(firstInput, secondInput, thirdInput, forthInput, firstOutput, secondOutput,
                thirdOutput, forthOutput);
    }
}

