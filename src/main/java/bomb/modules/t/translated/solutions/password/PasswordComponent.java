package bomb.modules.t.translated.solutions.password;

import bomb.abstractions.Resettable;
import bomb.modules.t.translated.solutions.TranslationComponent;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.pattern.factory.TextFormatterFactory;
import com.jfoenix.controls.JFXTextArea;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

import static bomb.modules.t.translated.NewTranslationCenter.LanguageRow.PASSWORD_ROW;

public class PasswordComponent extends Pane implements Resettable, TranslationComponent {
    @FXML
    private MFXTextField firstInputField, secondInputField, thirdInputField, fourthInputField, fifthInputField;

    @FXML
    private JFXTextArea outputArea;

    public PasswordComponent() {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("password.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public void initialize() {
        for (MFXTextField inputField : getTextFields())
            inputField.setTextFormatter(TextFormatterFactory.createSixLetterTextFormatter());
    }

    @FXML
    private void submitInfo() {
        String[] columnInfo = retrieveColumnLetters();
        try{
            String results = Password.getPasswords(columnInfo).toString()
                    .replaceAll("[()]", "")
                    .replaceAll(",", "\n");
            outputArea.setText(results);
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(Alert.AlertType.ERROR, illegal.getMessage());
        }
    }

    private String[] retrieveColumnLetters() {
        String[] columnInfo = new String[5];
        MFXTextField[] textFields = getTextFields();

        for (int i = 0; i < columnInfo.length; i++) {
            columnInfo[i] = textFields[i].getText();
        }

        return columnInfo;
    }

    @Override
    public void reset() {
        Password.setPossiblePasswords(null);
        FacadeFX.clearMultipleTextFields(getTextFields());
        FacadeFX.clearText(outputArea);
    }

    @Override
    public void setContent(List<String> languageContent) {
        String[] passwords = languageContent.get(PASSWORD_ROW.getIndex()).split("\\|");
        Password.setPossiblePasswords(passwords);
    }

    private MFXTextField[] getTextFields() {
        return new MFXTextField[]{firstInputField, secondInputField, thirdInputField, fourthInputField, fifthInputField};
    }
}
