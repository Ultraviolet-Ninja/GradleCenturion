package bomb.modules.ab.bitwise;

import bomb.abstractions.Resettable;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.event.HoverHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.function.Consumer;

public class BitwiseController implements Resettable {
    @FXML
    private Button bitAnd, bitOr, bitXor, bitNot;

    @FXML
    private TextField bitOut;

    public void initialize(){
        FacadeFX.bindHandlerToButtons(new HoverHandler<>(initAction()), bitAnd, bitNot, bitXor, bitOr);
    }

    private Consumer<ActionEvent> initAction(){
        return event -> {
            BitwiseOps source = BitwiseOps.valueOf(((Button) event.getSource()).getText().toUpperCase());
            try {
                bitOut.setText(Bitwise.getByte(source));
            } catch (IllegalArgumentException illegal){
                FacadeFX.setAlert(Alert.AlertType.INFORMATION, illegal.getMessage(),
                        "Something's empty", "Do more edge work");
            }
        };
    }

    @Override
    public void reset() {

    }
}
