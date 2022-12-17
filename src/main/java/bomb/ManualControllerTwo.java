package bomb;

import bomb.tools.pattern.observer.ForgetMeNotToggleObserver;
import bomb.tools.pattern.observer.ObserverHub;
import bomb.tools.pattern.observer.SouvenirToggleObserver;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static bomb.tools.pattern.facade.FacadeFX.GET_TOGGLE_NAME;
import static bomb.tools.pattern.factory.TextFormatterFactory.createSearchBarFormatter;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.FORGET_ME_NOT_TOGGLE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.SOUVENIR_TOGGLE;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

public class ManualControllerTwo {
    private Map<Toggle, Region> regionMap;
    private List<Node> observableRadioList;
    private final List<RadioButton> allRadioButtons;

    @FXML
    private GridPane base;

    @FXML
    private JFXRadioButton forgetMeNot, souvenir;

    @FXML
    private TextField searchBar;

    @FXML
    private ToggleGroup options;

    @FXML
    private VBox menuVBox, radioButtonHouse;

    public ManualControllerTwo() {
        allRadioButtons = new ArrayList<>();
    }

    public void initialize() throws ExecutionException, InterruptedException {
        searchBar.setTextFormatter(createSearchBarFormatter());
        observableRadioList = radioButtonHouse.getChildren();
        allRadioButtons.addAll(
                observableRadioList.stream()
                        .map(node -> (RadioButton) node)
                        .toList()
        );
        ObserverHub.addObserver(FORGET_ME_NOT_TOGGLE, new ForgetMeNotToggleObserver(forgetMeNot));
        ObserverHub.addObserver(SOUVENIR_TOGGLE, new SouvenirToggleObserver(souvenir));

//        regionMap = setupRegionMap().get();

    }

    private CompletableFuture<Map<Toggle, Region>> setupRegionMap() throws ExecutionException, InterruptedException {
        return null;
    }

    private static CompletableFuture<Map<String, Toggle>> createRadioButtonNameFuture(List<Toggle> radioButtonList) {
        return supplyAsync(radioButtonList::stream)
                .thenApply(stream -> stream.collect(toMap(
                        toggle -> GET_TOGGLE_NAME.apply(toggle)
                                .replaceAll("[ -]", "_")
                                .replaceAll("[()']", "")
                                .toLowerCase(),
                        identity()
                )));
    }

    private void createResourceFuture() {
    }
}
