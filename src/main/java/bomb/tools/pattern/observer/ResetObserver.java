package bomb.tools.pattern.observer;

import bomb.abstractions.Resettable;
import javafx.fxml.FXMLLoader;

import java.util.ArrayList;
import java.util.List;

public class ResetObserver implements Observer {
    private final List<Resettable> controllerList;

    public ResetObserver() {
        controllerList = new ArrayList<>();
    }

    public void addController(FXMLLoader loader) {
        if (loader.getController() == null) return;
        controllerList.add(loader.getController());
    }

    @Override
    public void update() {
        for (Resettable resettable : controllerList)
            resettable.reset();
    }
}
