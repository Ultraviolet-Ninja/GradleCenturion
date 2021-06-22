package bomb.tools.observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverHub {
    public enum ObserverIndex {
        FORGET_ME_NOT, SOUVENIR, BLIND_ALLEY, RESET
    }

    private static final List<Observer> OBSERVER_LIST = new ArrayList<>();
    
    private ObserverHub(){}
    
    public static void addObserver(Observer observer){
        OBSERVER_LIST.add(observer);
    }

    public static void updateAtIndex(ObserverIndex index){
        OBSERVER_LIST.get(index.ordinal()).update();
    }
}
