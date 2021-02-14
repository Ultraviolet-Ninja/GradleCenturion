package bomb.tools.observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverHub {
    public static final int FORGET_ME_INDEX = 0, SOUVENIR_INDEX = 1, BLIND_ALLEY_INDEX = 2;

    private static final List<Observer> OBSERVER_LIST = new ArrayList<>();
    
    private ObserverHub(){}
    
    public static void addObserver(Observer observer){
        OBSERVER_LIST.add(observer);
    }

    public static void updateAtIndex(int index){
        OBSERVER_LIST.get(index).update();
    }
}
