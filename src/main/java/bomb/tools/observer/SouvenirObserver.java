package bomb.tools.observer;

import bomb.Widget;
import javafx.scene.control.RadioButton;

public class SouvenirObserver implements Observer{
    private final RadioButton souvenir;

    public SouvenirObserver(RadioButton souvenir){
        this.souvenir = souvenir;
    }

    @Override
    public void update() {
        souvenir.setDisable(!Widget.getSouvenir());
    }
}
