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
        Object controller = loader.getController();
        if (controller == null) return;
        controllerList.add((Resettable) controller);
    }

    @Override
    public void update() {
        controllerList.parallelStream()
                .forEach(Resettable::reset);
    }
}
