package bomb.modules.ab.bitwise;

import bomb.tools.FacadeFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class BitwiseController {
    @FXML
    private Button bitAnd, bitOr, bitXor, bitNot;

    @FXML
    private TextField bitOut;

    //FIXME
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
            FacadeFX.setAlert(Alert.AlertType.INFORMATION, illegal.getMessage(),
                    "Something's empty", "Do more edge work");
        }
    }
}
