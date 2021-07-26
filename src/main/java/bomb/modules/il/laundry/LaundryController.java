package bomb.modules.il.laundry;

import bomb.abstractions.Resettable;
import bomb.tools.filter.Regex;
import bomb.tools.pattern.factory.TextFormatterFactory;
import bomb.tools.pattern.facade.FacadeFX;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LaundryController implements Resettable {
    private static final String WASH_INSTRUCTIONS = "Wash Instructions: ", DRY_INSTRUCTIONS = "Dry Instructions: ",
            IRONING_INSTRUCTIONS = "Iron Instructions: ", SPECIAL_INSTRUCTIONS = "Special Instructions: ";
    private static final String MATERIAL_RESET_TEXT = "Material - Color - Item";

    @FXML private ImageView washImage, dryImage;

    @FXML private Label washText, dryText, ironText, specialText, article, bob;

    @FXML private JFXTextField solvedModuleNumberField, needyModuleNumberField;

    public void initialize(){
        solvedModuleNumberField.setTextFormatter(TextFormatterFactory.createNumbersOnlyFormatter());
        needyModuleNumberField.setTextFormatter(TextFormatterFactory.createNumbersOnlyFormatter());
    }

    @FXML
    private void coinInsert(){
        try{
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
            article.setText(restructure(outputs[4]));
            if (outputs.length == 6) bob.setText(outputs[5]);
        } catch (IllegalArgumentException illegal){
            FacadeFX.setAlert(Alert.AlertType.INFORMATION, illegal.getMessage());
        }
    }

    private String separateText(String filename){
        Regex filenamePattern = new Regex("\\w+(?: \\w+)?\\.", filename);
        return filenamePattern.toNewString()
                .replace(".", "")
                .replace("F", "°F");
    }

    private String restructure(String in){
        String[] buffer = in.split(" - ");
        StringBuilder builder = new StringBuilder();

        for (String s : buffer) {
            builder.append(s, 0, 1).append(s.substring(1).toLowerCase()).append(" - ");
        }

        return builder.substring(0, builder.length()-3);
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
