package bomb.modules.il.logic;

import bomb.abstractions.Resettable;
import bomb.tools.logic.LogicOperator;
import bomb.tools.pattern.facade.FacadeFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.fxml.FXML;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

import static bomb.tools.logic.LogicOperator.AND;
import static bomb.tools.logic.LogicOperator.IMPLIED_BY;
import static bomb.tools.logic.LogicOperator.LOGIC_SYMBOL_TO_ENUM_MAP;
import static bomb.tools.pattern.factory.TextFormatterFactory.createOneLetterFormatter;

public class LogicController implements Resettable {
    private static final String PRIORITY_TEXT = "priority", OUTSIDE_TEXT = "outside";

    @FXML
    private MFXButton submitButton;

    @FXML
    private MFXLegacyComboBox<String> firstSetFirstComboBox, firstSetSecondComboBox,
            secondSetFirstComboBox, secondSetSecondComboBox;

    @FXML
    private MFXTextField firstSetOne, firstSetTwo, firstSetThree,
            secondSetOne, secondSetTwo, secondSetThree;

    @FXML
    private MFXToggleButton firstSetToggle, secondSetToggle;

    public void initialize() {
        firstSetOne.setTextFormatter(createOneLetterFormatter());
        firstSetTwo.setTextFormatter(createOneLetterFormatter());
        firstSetThree.setTextFormatter(createOneLetterFormatter());
        secondSetOne.setTextFormatter(createOneLetterFormatter());
        secondSetTwo.setTextFormatter(createOneLetterFormatter());
        secondSetThree.setTextFormatter(createOneLetterFormatter());

        initializeToggleButton(firstSetToggle, firstSetOne, firstSetThree);
        initializeToggleButton(secondSetToggle, secondSetOne, secondSetThree);
        injectLogicSymbols();
    }

    private static void initializeToggleButton(MFXToggleButton toggleButton, MFXTextField first,
                                               MFXTextField third) {
        toggleButton.setOnAction(
                event -> {
                    MFXToggleButton source = (MFXToggleButton) event.getSource();
                    boolean isSelected = source.isSelected();
                    first.setId((isSelected ? OUTSIDE_TEXT : PRIORITY_TEXT) + "Text");
                    third.setId((isSelected ? PRIORITY_TEXT : OUTSIDE_TEXT) + "Text");
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
    private void submitInfo() {

    }

    private static boolean getResults(MFXToggleButton toggleButton, MFXTextField first, MFXTextField second,
                                      MFXTextField third, MFXLegacyComboBox<String> firstComboBox,
                                      MFXLegacyComboBox<String> secondComboBox) {
        LogicLetter[] logicLetters = Stream.of(first, second, third)
                .map(MFXTextField::getText)
                .map(String::toUpperCase)
                .map(LogicLetter::valueOf)
                .toArray(LogicLetter[]::new);

        LogicOperator[] logicOperators = Stream.of(firstComboBox, secondComboBox)
                .map(MFXLegacyComboBox::getValue)
                .map(selectedText -> selectedText.substring(0, 1))
                .map(LOGIC_SYMBOL_TO_ENUM_MAP::get)
                .toArray(LogicOperator[]::new);



        return false;
    }

    @Override
    public void reset() {
        firstSetToggle.setSelected(false);
        firstSetToggle.fire();
        secondSetToggle.setSelected(false);
        secondSetToggle.fire();

        firstSetFirstComboBox.setValue("");
        firstSetSecondComboBox.setValue("");
        secondSetFirstComboBox.setValue("");
        secondSetSecondComboBox.setValue("");

        FacadeFX.disable(submitButton);
    }
}
