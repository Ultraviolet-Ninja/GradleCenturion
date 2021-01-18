package bomb.tools.data.structures.avl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AVLTreeTest {
    private AVLTree<Double> tree;

    @BeforeEach
    void setup(){
        tree = new AVLTree<>();
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

