package bomb.modules.il.laundry;

import bomb.abstractions.Resettable;
import bomb.tools.filter.Regex;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.pattern.factory.TextFormatterFactory;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static bomb.tools.string.StringFormat.FIRST_LETTER_CAPITAL;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public final class LaundryController implements Resettable {
    private static final String WASH_INSTRUCTIONS = "Wash Instructions: ", DRY_INSTRUCTIONS = "Dry Instructions: ",
            IRONING_INSTRUCTIONS = "Iron Instructions: ", SPECIAL_INSTRUCTIONS = "Special Instructions: ";
    private static final String MATERIAL_RESET_TEXT = "Material - Color - Item";

    @FXML
    private ImageView washImage, dryImage;

    @FXML
    private Label washText, dryText, ironText, specialText, article, bob;

    @FXML
    private JFXTextField solvedModuleNumberField, needyModuleNumberField;

    public void initialize() {
        solvedModuleNumberField.setTextFormatter(TextFormatterFactory.createNumbersOnlyFormatter());
        needyModuleNumberField.setTextFormatter(TextFormatterFactory.createNumbersOnlyFormatter());
    }

    @FXML
    private void coinInsert() {
        try {
            String[] outputs = Laundry.clean(
                    solvedModuleNumberField.getText(),
                    needyModuleNumberField.getText()
            );

            washImage.setImage(new Image(String.valueOf(getClass().getResource(outputs[0]))));
            dryImage.setImage(new Image(String.valueOf(getClass().getResource(outputs[1]))));
            washText.setText(WASH_INSTRUCTIONS + separateText(outputs[0]));
            dryText.setText(DRY_INSTRUCTIONS + separateText(outputs[1]));

            ironText.setText(IRONING_INSTRUCTIONS + outputs[2]);
            specialText.setText(SPECIAL_INSTRUCTIONS + outputs[3]);
            article.setText(reformatClothingOutput(outputs[4]));
            if (outputs.length == 6) bob.setText(outputs[5]);
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(Alert.AlertType.INFORMATION, illegal.getMessage());
        }
    }

    private static String separateText(String filename) {
        Regex filenamePattern = new Regex("\\w+(?: \\w+)?\\.", filename);
        return filenamePattern.createFilteredString()
                .replace(".", "")
                .replace("F", "°F");
    }

    private static String reformatClothingOutput(String in) {
        final String delimiter = " - ";
        return stream(in.split(delimiter))
                .map(FIRST_LETTER_CAPITAL)
                .collect(joining(delimiter));
    }

    @Override
    public void reset() {
        washText.setText(WASH_INSTRUCTIONS);
        dryText.setText(DRY_INSTRUCTIONS);
        ironText.setText(IRONING_INSTRUCTIONS);
        specialText.setText(SPECIAL_INSTRUCTIONS);
        article.setText(MATERIAL_RESET_TEXT);
        FacadeFX.clearText(bob);
        FacadeFX.clearMultipleTextFields(needyModuleNumberField, solvedModuleNumberField);
    }
}
