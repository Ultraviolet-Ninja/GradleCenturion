package bomb.tools.data.structures.avl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class AVLTreeTest {
    private AVLTree<Double> tree;

    @BeforeMethod
    public void setUp() {
        tree = new AVLTree<>();
    }

    @Test(enabled = false, expectedExceptions = UnbalancedEntriesException.class)
    public void exceptionTest() throws UnbalancedEntriesException {
        tree = new AVLTree<>(setupList(10), setupStringArray(15));
        tree = new AVLTree<>(setupArray(11), setupStringArray(14));
    }

    private Double[] setupArray(int max) {
        Double[] output = new Double[max];
        for (int i = 0; i < max; i++)
            output[i] = (double) i;
        return output;
    }

    private ArrayList<Double> setupList(int max) {
        ArrayList<Double> output = new ArrayList<>();
        for (int i = 0; i < max; i++)
            output.add((double) i);
        return output;
    }

    private String[] setupStringArray(int max) {
        String[] output = new String[max];
        for (int i = 0; i < max; i++)
            output[i] = String.valueOf(i);
        return output;
    }
}
