package bomb.tools;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class FacadeFX {
    private FacadeFX(){}

    public static void bindHandlerToButtons(EventHandler<ActionEvent> handler, Button... buttons){
        for (Button button : buttons)
            button.setOnAction(handler);
    }

    public static void clearText(TextInputControl text){
        text.setText("");
    }

    public static void clearText(Label label){
        label.setText("");
    }

    public static void clearTextMultiple(TextInputControl ... texts){
        for (TextInputControl text : texts)
            clearText(text);
    }

    public static void clearTextMultiple(Label ... labels){
        for (Label label : labels)
            clearText(label);
    }

    public static void disable(Node node){
        node.setDisable(true);
    }

    public static void enable(Node node){
        node.setDisable(false);
    }

    public static String getToggleName(ToggleGroup group){
        return ((ToggleButton) group.getSelectedToggle()).getText();
    }

    public static void setAlert(Alert.AlertType type, String context, String header, String title){
        Alert alert = new Alert(type, context);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static void toggleNodes(boolean toggle, Node ... nodes){
        if (nodes.length > 0) if (nodes[0].isDisabled() == toggle) return;
        for (Node node : nodes) node.setDisable(toggle);
    }

    public static void unselectButtons(ToggleButton ... toggleButtons){
        for (ToggleButton button : toggleButtons)
            button.setSelected(false);
    }
}
