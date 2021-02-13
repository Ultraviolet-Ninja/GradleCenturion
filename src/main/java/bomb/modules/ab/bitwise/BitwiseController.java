package bomb.modules.ab.bitwise;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class BitwiseController {
    @FXML
    private Button bitAnd, bitOr, bitXor, bitNot;

    @FXML
    private TextField bitOut;

    @FXML
    private void bitwiseSolve(){
        BitwiseOps bit = null;
        if (bitAnd.isHover()){
            bit = BitwiseOps.AND;
        } else if (bitOr.isHover()){
            bit = BitwiseOps.OR;
        } else if (bitXor.isHover()){
            bit = BitwiseOps.XOR;
        } else if (bitNot.isHover()){
            bit = BitwiseOps.NOT;
        }
        try {
            bitOut.setText(Bitwise.getByte(bit));
        } catch (IllegalArgumentException illegal){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, illegal.getMessage());
            alert.setHeaderText("Something's empty");
            alert.setTitle("Do more edge work");
            alert.show();
        }
    }
}
