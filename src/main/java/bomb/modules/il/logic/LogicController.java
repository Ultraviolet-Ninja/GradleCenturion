package bomb.modules.il.logic;

import bomb.abstractions.Resettable;
import bomb.tools.logic.LogicOperator;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXComboBox;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBoxBase;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

import static bomb.tools.logic.LogicOperator.AND;
import static bomb.tools.logic.LogicOperator.IMPLIED_BY;
import static bomb.tools.logic.LogicOperator.LOGIC_SYMBOL_TO_ENUM_MAP;
import static bomb.tools.pattern.factory.TextFormatterFactory.createOneLetterFormatter;

public final class LogicController implements Resettable {
    @FXML
    private MFXButton submitButton;

    @FXML
    private JFXComboBox<String> firstSetFirstComboBox, firstSetSecondComboBox,
            secondSetFirstComboBox, secondSetSecondComboBox;

    @FXML
    private MFXTextField firstSetOne, firstSetTwo, firstSetThree,
            secondSetOne, secondSetTwo, secondSetThree;

    @FXML
    private MFXTextField firstSetResultField, secondSetResultField;

    @FXML
    private MFXToggleButton firstSetPriorityToggle, secondSetPriorityToggle;

    @FXML
    private MFXToggleButton firstSetFirstNegation, firstSetSecondNegation, firstSetThirdNegation,
            secondSetFirstNegation, secondSetSecondNegation, secondSetThirdNegation;

    public void initialize() {
        firstSetOne.setTextFormatter(createOneLetterFormatter());
        firstSetTwo.setTextFormatter(createOneLetterFormatter());
        firstSetThree.setTextFormatter(createOneLetterFormatter());
        secondSetOne.setTextFormatter(createOneLetterFormatter());
        secondSetTwo.setTextFormatter(createOneLetterFormatter());
        secondSetThree.setTextFormatter(createOneLetterFormatter());

        initializeToggleButton(firstSetPriorityToggle, firstSetOne, firstSetThree);
        initializeToggleButton(secondSetPriorityToggle, secondSetOne, secondSetThree);
        injectLogicSymbols();
    }

    private static void initializeToggleButton(MFXToggleButton toggleButton, MFXTextField first,
                                               MFXTextField third) {
        toggleButton.setOnAction(
                event -> {
                    MFXToggleButton source = (MFXToggleButton) event.getSource();
                    boolean isSelected = source.isSelected();
                    first.setId((isSelected ? "outside" : "priority") + "-text");
                    third.setId((isSelected ? "priority" : "outside") + "-text");
                }
        );
    }

    private void injectLogicSymbols() {
        List<String> options = EnumSet.range(AND, IMPLIED_BY).stream()
                .map(logicOperator ->
                        logicOperator.getSymbol() + " - " + logicOperator.name().replaceAll("_", " ")
                )
                .toList();

        firstSetFirstComboBox.getItems().addAll(options);
        firstSetSecondComboBox.getItems().addAll(options);
        secondSetFirstComboBox.getItems().addAll(options);
        secondSetSecondComboBox.getItems().addAll(options);
    }

    @FXML
    private void updateDisabledState() {
        var textFieldsStrings = Stream
                .of(firstSetOne, firstSetTwo, firstSetThree, secondSetOne, secondSetTwo, secondSetThree)
                .map(MFXTextField::getText);

        var comboBoxStrings = Stream
                .of(firstSetFirstComboBox, firstSetSecondComboBox, secondSetFirstComboBox, secondSetSecondComboBox)
                .map(ComboBoxBase::getValue)
                .map(value -> value == null ? "" : value);

        boolean areAllStringsFilled = Stream
                .concat(textFieldsStrings, comboBoxStrings)
                .map(text -> !text.isEmpty())
                .reduce((b1, b2) -> b1 && b2)
                .orElse(false);

        submitButton.setDisable(!areAllStringsFilled);
    }

    @FXML
    private void submitInfo() {
        try {
            boolean firstSetResult = getResults(firstSetPriorityToggle, firstSetOne, firstSetFirstNegation,
                    firstSetTwo, firstSetSecondNegation, firstSetThree, firstSetThirdNegation,
                    firstSetFirstComboBox, firstSetSecondComboBox);

            boolean secondSetResult = getResults(secondSetPriorityToggle, secondSetOne, secondSetFirstNegation,
                    secondSetTwo, secondSetSecondNegation, secondSetThree, secondSetThirdNegation,
                    secondSetFirstComboBox, secondSetSecondComboBox);

            firstSetResultField.setText(firstSetResult ? "T" : "F");
            secondSetResultField.setText(secondSetResult ? "T" : "F");

            firstSetResultField.setId(firstSetResult + "-label");
            secondSetResultField.setId(secondSetResult + "-label");
        } catch (IllegalArgumentException e) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    private static boolean getResults(MFXToggleButton toggleButton, MFXTextField firstTextField,
                                      MFXToggleButton firstNegation, MFXTextField secondTextField,
                                      MFXToggleButton secondNegation, MFXTextField thirdTextField,
                                      MFXToggleButton thirdNegation, JFXComboBox<String> firstComboBox,
                                      JFXComboBox<String> secondComboBox) throws IllegalArgumentException {
        LetterRecord[] letterRecords = createLetterRecords(
                firstTextField, firstNegation,
                secondTextField, secondNegation,
                thirdTextField, thirdNegation
        );

        LogicOperator[] logicOperators = Stream.of(firstComboBox, secondComboBox)
                .map(JFXComboBox::getValue)
                .map(selectedText -> selectedText.substring(0, 1))
                .map(LOGIC_SYMBOL_TO_ENUM_MAP::get)
                .toArray(LogicOperator[]::new);

        return Logic.solve(letterRecords, logicOperators, !toggleButton.isSelected());
    }

    private static LetterRecord[] createLetterRecords(MFXTextField firstTextField, MFXToggleButton firstNegation,
                                                      MFXTextField secondTextField, MFXToggleButton secondNegation,
                                                      MFXTextField thirdTextField, MFXToggleButton thirdNegation) {
        MFXToggleButton[] buttonArray = {firstNegation, secondNegation, thirdNegation};
        LetterRecord[] letters = new LetterRecord[buttonArray.length];

        LogicLetter[] logicLetters = Stream.of(firstTextField, secondTextField, thirdTextField)
                .map(MFXTextField::getText)
                .map(String::toUpperCase)
                .map(LogicLetter::valueOf)
                .toArray(LogicLetter[]::new);

        for (int i = 0; i < logicLetters.length; i++) {
            letters[i] = new LetterRecord(
                    buttonArray[i].isSelected(),
                    logicLetters[i]
            );
        }

        return letters;
    }

    @Override
    public void reset() {
        firstSetPriorityToggle.setSelected(true);
        firstSetPriorityToggle.fire();
        secondSetPriorityToggle.setSelected(true);
        secondSetPriorityToggle.fire();

        firstSetFirstNegation.setSelected(false);
        firstSetSecondNegation.setSelected(false);
        firstSetThirdNegation.setSelected(false);
        secondSetFirstNegation.setSelected(false);
        secondSetSecondNegation.setSelected(false);
        secondSetThirdNegation.setSelected(false);

        firstSetFirstComboBox.setValue("");
        firstSetSecondComboBox.setValue("");
        secondSetFirstComboBox.setValue("");
        secondSetSecondComboBox.setValue("");

        firstSetResultField.setText("F");
        secondSetResultField.setText("F");

        firstSetResultField.setId(false + "-label");
        secondSetResultField.setId(false + "-label");

        FacadeFX.clearMultipleTextFields(firstSetOne, firstSetTwo, firstSetThree,
                secondSetOne, secondSetTwo, secondSetThree);

        FacadeFX.disable(submitButton);
    }
}
