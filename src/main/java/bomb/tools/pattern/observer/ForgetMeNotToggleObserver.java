package bomb.tools.pattern.observer;

import bomb.Widget;
import javafx.scene.control.RadioButton;

public class ForgetMeNotToggleObserver implements Observer{
    private final RadioButton forgetMeNot;

    public ForgetMeNotToggleObserver(RadioButton forgetMeNot){
        this.forgetMeNot = forgetMeNot;
    }

    @Override
    public void update() {
        forgetMeNot.setDisable(!Widget.getIsForgetMeNotActive());
    }
}
