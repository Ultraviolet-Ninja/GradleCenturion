package bomb.modules.t.translated.solutions.password;

import bomb.abstractions.Resettable;
import bomb.modules.t.translated.TranslationComponent;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.pattern.factory.TextFormatterFactory;
import com.jfoenix.controls.JFXTextArea;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static bomb.modules.t.translated.LanguageCSVReader.LanguageRow.PASSWORD_ROW;
import static bomb.modules.t.translated.solutions.password.Password.EMPTY_RESULTS;
import static bomb.tools.pattern.facade.FacadeFX.loadComponent;
import static bomb.tools.string.StringFormat.BULLET_POINT;

public final class PasswordComponent extends Pane implements Resettable, TranslationComponent {
    @FXML
    private MFXTextField firstInputField, secondInputField, thirdInputField, fourthInputField, fifthInputField;

    @FXML
    private JFXTextArea outputArea;

    public PasswordComponent() {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("password.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loadComponent("Password", loader);
    }

    public void initialize() {
        for (MFXTextField inputField : getTextFields())
            inputField.setTextFormatter(TextFormatterFactory.createSixLetterTextFormatter());
    }

    @FXML
    private void submitInfo() {
        String[] columnInfo = retrieveColumnLetters();
        try {
            var passwords = Password.getPasswords(columnInfo);

            if (passwords.getFirst().equals(EMPTY_RESULTS)) {
                outputArea.setText(passwords.getFirst());
            } else {
                var finalOutput = passwords.stream()
                        .map(password -> BULLET_POINT + password)
                        .collect(Collectors.joining("\n"));
                outputArea.setText(finalOutput);
            }
        } catch (IllegalArgumentException illegal) {
            FacadeFX.setAlert(illegal.getMessage());
        }
    }

    private String[] retrieveColumnLetters() {
        return Arrays.stream(getTextFields())
                .map(TextField::getText)
                .toArray(String[]::new);
    }

    @Override
    public void reset() {
        Password.setPossiblePasswords(null);
        FacadeFX.clearMultipleTextFields(getTextFields());
        FacadeFX.clearText(outputArea);
    }

    @Override
    public void setContent(List<String> languageContent) {
        String[] passwords = languageContent.get(PASSWORD_ROW.getRowIndex()).split("\\|");
        Password.setPossiblePasswords(passwords);
    }

    private MFXTextField[] getTextFields() {
        return new MFXTextField[]{firstInputField, secondInputField, thirdInputField,
                fourthInputField, fifthInputField};
    }
}
