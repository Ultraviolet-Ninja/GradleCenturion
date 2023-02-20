package bomb.tools.pattern.observer;

import bomb.Widget;
import javafx.scene.control.RadioButton;

public final class SouvenirToggleObserver implements Observer {
    private final RadioButton souvenir;

    public SouvenirToggleObserver(RadioButton souvenir) {
        this.souvenir = souvenir;
    }

    @Override
    public void update() {
        souvenir.setDisable(!Widget.getIsSouvenirActive());
    }
}
