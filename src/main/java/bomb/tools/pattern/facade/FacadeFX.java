package bomb.tools.pattern.facade;

import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.function.Function;

import static bomb.Main.IMAGE_ICON_RESOURCE;

public final class FacadeFX {
    private static final Logger LOG = LoggerFactory.getLogger(FacadeFX.class);
    public static final Function<Toggle, String> GET_TOGGLE_NAME = toggle ->
            ((ToggleButton)toggle).getText();

    public static final Function<ActionEvent, String> BUTTON_NAME_FROM_EVENT = actionEvent -> {
        var source = actionEvent.getSource();
        return switch (source) {
            case Button button -> button.getText();
            case ToggleButton toggleButton -> GET_TOGGLE_NAME.apply(toggleButton);
            default -> "";
        };
    };

    private FacadeFX() {
    }

    public static void bindHandlerToButtons(EventHandler<ActionEvent> handler, Button... buttons) {
        for (Button button : buttons)
            button.setOnAction(handler);
    }

    public static void bindOnClickHandler(EventHandler<MouseEvent> handler, Node... nodes) {
        for (Node node : nodes)
            node.setOnMouseClicked(handler);
    }

    public static void clearText(TextInputControl text) {
        text.setText("");
    }

    public static void clearText(Label label) {
        label.setText("");
    }

    public static void clearMultipleTextFields(TextInputControl... texts) {
        for (TextInputControl text : texts)
            clearText(text);
    }

    public static void clearMultipleLabels(Label... labels) {
        for (Label label : labels)
            clearText(label);
    }

    public static void disable(Node node) {
        node.setDisable(true);
    }

    public static void disableMultiple(Node... nodes) {
        for (Node node : nodes)
            disable(node);
    }

    public static void enable(Node node) {
        node.setDisable(false);
    }

    public static void enableMultiple(Node... nodes) {
        for (Node node : nodes)
            enable(node);
    }

    public static void parallelTransition(Node node, Transition... transitions) {
        ParallelTransition parallel = new ParallelTransition(node);
        parallel.getChildren().addAll(transitions);
        parallel.play();
    }

    public static String getToggleName(ToggleGroup group) {
        return ((ToggleButton) group.getSelectedToggle()).getText();
    }

    public static boolean hasSelectedToggle(ToggleGroup group) {
        return group.getSelectedToggle() != null;
    }

    public static Region load(URL url) throws IllegalArgumentException {
        return load(new FXMLLoader(url));
    }

    public static Region load(FXMLLoader loader) throws IllegalArgumentException {
        try {
            return loader.load();
        } catch (IOException e) {
            LOG.error("Loader Exception", e);
            throw new IllegalArgumentException(e);
        }
    }

    public static void loadComponent(FXMLLoader loader) throws IllegalArgumentException {
        try {
            loader.load();
        } catch (IOException e) {
            LOG.error("Loader Exception", e);
            throw new IllegalArgumentException(e);
        }
    }

    public static void loadComponent(String componentName, FXMLLoader loader) throws IllegalArgumentException {
        try {
            loader.load();
        } catch (IOException e) {
            LOG.error(componentName + " Loader Exception", e);
            throw new IllegalArgumentException(e);
        }
    }

    public static void resetToggleGroup(ToggleGroup group) {
        group.selectToggle(null);
    }

    public static void resetToggleGroups(ToggleGroup... groups) {
        for (ToggleGroup group : groups)
            resetToggleGroup(group);
    }

    public static void resetSliderValues(Slider... sliders) {
        for (Slider slider : sliders)
            slider.setValue(0.0);
    }

    public static void setAlert(String context) {
        setAlert(Alert.AlertType.ERROR, context, "", "Error occurred");
    }

    public static void setAlert(Alert.AlertType type, String context) {
        setAlert(type, context, "", determineTitle(type));
    }

    public static void setAlert(Alert.AlertType type, String context, String header, String title) {
        LOG.debug("Context: {}", context);
        var alert = buildAlert(type, context, header, title);
        alert.showAndWait();
    }

    public static Alert buildAlert(Alert.AlertType type, String context, String header, String title) {
        var alert = new Alert(type, context);
        alert.setHeaderText(header);
        alert.setTitle(title);

        var stage = (Stage) alert.getDialogPane()
                .getScene()
                .getWindow();

        var icon = new Image(IMAGE_ICON_RESOURCE);
        stage.getIcons().add(icon);
        return alert;
    }

    private static String determineTitle(Alert.AlertType type) {
        return switch (type) {
            case ERROR -> "Error occurred";
            case WARNING -> "Warning";
            case INFORMATION -> "Info";
            case CONFIRMATION -> "Confirmation";
            default -> "";
        };
    }

    public static void setToggleButtonsUnselected(ToggleButton... toggleButtons) {
        for (ToggleButton button : toggleButtons)
            button.setSelected(false);
    }

    public static void setVisible(Node node) {
        node.setOpacity(1.0);
    }

    public static void setNodesVisible(Node ... nodes) {
        for (Node node : nodes)
            setVisible(node);
    }

    public static void setInvisible(Node node) {
        node.setOpacity(0.0);
    }

    public static void setNodesInvisible(Node ... nodes) {
        for (Node node : nodes)
            setInvisible(node);
    }
}
