package bomb.tools.observer;

import bomb.Widget;
import javafx.scene.control.RadioButton;

public class ForgetMeNotObserver implements Observer{
    private final RadioButton forgetMeNot;

    public ForgetMeNotObserver(RadioButton forgetMeNot){
        this.forgetMeNot = forgetMeNot;
    }

    @Override
    public void update() {
        forgetMeNot.setDisable(!Widget.getForgetMeNot());
    }
}
