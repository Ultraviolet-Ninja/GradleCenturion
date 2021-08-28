package bomb.tools.pattern.observer;

import bomb.abstractions.Resettable;
import javafx.fxml.FXMLLoader;

import java.util.ArrayList;

public class ResetObserver implements Observer {
    private final ArrayList<Resettable> controllerList;

    public ResetObserver() {
        controllerList = new ArrayList<>();
    }

    public void addController(FXMLLoader loader) {
        if (loader.getController() == null) return;
        controllerList.add(loader.getController());
    }

    @Override
    public void update() {
        int i = 0;
        while (i < controllerList.size()) {
            controllerList.get(i).reset();
            i++;
        }
    }
}
