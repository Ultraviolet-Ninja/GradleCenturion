package bomb.tools.data.structures;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * The HashStack is a HashMap with a LinkedList as a Stack that controls what elements can be accessed,
 * altered, or popped off the HashMap. This data structure is based on the following criteria:
 * • Having (key, value) pairs
 * • Being able to access the values easily
 * • Being able to pop newer pairs off the stack when they're not needed
 *
 * This is designed to work as a back-tracking data structure with 2 generic classes that need to be tracked
 *
 * @param <K> The key
 * @param <V> The value
 */
public class MapStack<K, V> {
    private final HashMap<K, V> map;
    private final LinkedList<K> controlStack;

    public MapStack(){
        map = new HashMap<>();
        controlStack = new LinkedList<>();
    }

    public boolean containsKey(K key){
        return controlStack.contains(key);
    }

    public boolean containsValue(V value){
        return map.containsValue(value);
    }

    public LinkedList<K> exportKeys(){
        return controlStack;
    }

    public LinkedList<V> exportValues(){
        return new LinkedList<>(map.values());
    }

    public K getBottomKey(){
        return controlStack.getLast();
    }

    public K getTopKey(){
        return controlStack.getFirst();
    }

    public K getKey(int index){
        return index < controlStack.size() ? controlStack.get(index) : null;
    }

    public V getBottomValue(){
        return controlStack.peekLast() == null ? null : map.get(controlStack.peekLast());
    }

    public V getTopValue(){
        return controlStack.peekFirst() == null ? null : map.get(controlStack.peekFirst());
    }

    public V getValue(K key){
        return map.getOrDefault(key, null);
    }

    public V getValueByIndex(int index){
        K tempKey = getKey(index);
        return tempKey == null ? null : map.get(tempKey);
    }

    public boolean push(K key, V value){
        if (map.containsKey(key)) return false;
        map.put(key, value);
        controlStack.push(key);
        return true;
    }

    public Map.Entry<K, V> peek(){
        K tempKey = controlStack.peek();
        V tempValue = getValue(tempKey);
        return new AbstractMap.SimpleEntry<>(tempKey, tempValue);
    }

    public Map.Entry<K, V> pop(){
        K tempKey = controlStack.pop();
        V tempValue = map.remove(tempKey);
        return new AbstractMap.SimpleEntry<>(tempKey, tempValue);
    }

    public V setTopValue(V newValue){
        K tempKey = controlStack.peek();
        return tempKey == null ? null : map.put(tempKey, newValue);
    }

    public int size(){
        return map.size();
    }
}
