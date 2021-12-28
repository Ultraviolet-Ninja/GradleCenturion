package bomb.tools.pattern.observer;

import bomb.Widget;
import javafx.scene.control.RadioButton;

@SuppressWarnings("ClassCanBeRecord")
public class SouvenirToggleObserver implements Observer {
    private final RadioButton souvenir;

    public SouvenirToggleObserver(RadioButton souvenir) {
        this.souvenir = souvenir;
    }

    @Override
    public void update() {
        souvenir.setDisable(!Widget.getIsSouvenirActive());
    }
}
