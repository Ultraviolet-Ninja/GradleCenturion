package bomb.tools;

import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.function.Consumer;

public class HoverHandler<T extends Event> implements EventHandler<T> {
    private final Consumer<T> action;

    public HoverHandler(Consumer<T> action){
        this.action = action;
    }

    @Override
    public void handle(T event) {
        action.accept(event);
    }
}
