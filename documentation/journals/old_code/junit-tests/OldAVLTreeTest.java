package bomb.tools.data.structures.avl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OldAVLTreeTest {
    private AVLTree<Double> tree;

    @BeforeEach
    void setup(){
        tree = new AVLTree<>();
    }

    @Test
    void exceptionTesting(){
        assertThrows(UnbalancedEntriesException.class,
                () -> tree = new AVLTree<>(setupList(10), setupStringArray(15)));
        assertThrows(UnbalancedEntriesException.class,
                () -> tree = new AVLTree<>(setupArray(10), setupStringArray(15)));
        assertDoesNotThrow(() -> tree = new AVLTree<>(setupList(5), setupStringArray(5)));
        assertDoesNotThrow(() -> tree = new AVLTree<>(setupArray(5), setupStringArray(5)));
    }

    private Double[] setupArray(int max){
        Double[] output = new Double[max];
        for (int i = 0; i < max; i++)
            output[i] = (double) i;
        return output;
    }

    private ArrayList<Double> setupList(int max){
        ArrayList<Double> output = new ArrayList<>();
        for (int i = 0; i < max; i++)
            output.add((double) i);
        return output;
    }

    private String[] setupStringArray(int max){
        String[] output = new String[max];
        for (int i = 0; i < max; i++)
            output[i] = String.valueOf(i);
        return output;
    }

    @Test
    void nullTest(){
        tree.addNode(824.4, "");
        assertNull(tree.dig(504.2));
        assertNull(tree.dig(824.3));
        assertEquals("", tree.dig(824.4));
    }

    @Test
    void heightTest(){
        for (int i = 0; i < 1000; i++){
            tree.addNode((double) i, String.valueOf(i));
            System.out.println(tree.getTotalHeight());
        }
        //tree.traverse();
    }

    private int logBaseTwo(int in){
        double result = Math.log(in) / Math.log(2);
        return result % 1 == 0 ? -1 : (int) result;
    }
}

