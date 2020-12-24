package bomb.tools.data.structures.hash;

import java.util.HashMap;
import java.util.Set;

public class HashPlane<X, Y, V> {
    private final HashMap<X, HashMap<Y, V>> plane;

    public HashPlane(){
        plane = new HashMap<>();
    }

    public boolean add(X x, Y y, V value){
        if (!plane.containsKey(x)){
            HashMap<Y, V> innerMap = new HashMap<>();
            innerMap.put(y, value);
            plane.put(x, innerMap);
            return true;
        } else return innerAdd(x, y, value);
    }

    private boolean innerAdd(X x, Y y, V value){
        if (!plane.get(x).containsKey(y)){
            plane.get(x).put(y, value);
            return true;
        }
        return false;
    }

    public Set<X> listColumns(){
        return plane.keySet();
    }

    public Set<Y> listSpecificRow(X x){
        return plane.get(x).keySet();
    }

    public V access(X x, Y y){
        return plane.get(x).get(y);
    }
}
