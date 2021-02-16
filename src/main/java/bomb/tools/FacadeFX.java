package bomb.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class FacadeFX {
    private FacadeFX(){}

    public static String getToggleName(ToggleGroup group){
        return group.getSelectedToggle() instanceof ToggleButton ?
                ((ToggleButton) group.getSelectedToggle()).getText() :
                ((RadioButton) group.getSelectedToggle()).getText();
    }

    public static void setAlert(Alert.AlertType type, String context, String header, String title){
        Alert alert = new Alert(type, context);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
