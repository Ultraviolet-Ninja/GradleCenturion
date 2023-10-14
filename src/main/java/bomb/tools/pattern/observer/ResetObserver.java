package bomb.tools.pattern.observer;

import bomb.abstractions.Resettable;
import javafx.fxml.FXMLLoader;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class ResetObserver implements Observer {
    private static final Logger LOG = LoggerFactory.getLogger(ResetObserver.class);
    private final List<Resettable> controllerList;

    public ResetObserver() {
        controllerList = new ArrayList<>();
    }

    //Be on the lookout if the parallel stream in ManualController causes any overwriting to occur in the ArrayList
    public void addController(@NotNull FXMLLoader loader) {
        Object controller = loader.getController();
        if (controller == null) return;
        controllerList.add((Resettable) controller);
        LOG.debug("View of ResetObserver: {}", controllerList);
    }

    @Override
    public void update() {
        controllerList.parallelStream()
                .forEach(Resettable::reset);
    }
}
