package bomb;

import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;

public class Manual2Controller {
    private Map<String, Region> regionMap;
    private ArrayList<Node> allRadioButtons;

    @FXML private Pane displayPane;

    @FXML private JFXRadioButton forgetMeNot, souvenir;

    @FXML private TextField searchBar;

    @FXML private ToggleGroup options;

    @FXML private VBox radioButtonHouse;
}
