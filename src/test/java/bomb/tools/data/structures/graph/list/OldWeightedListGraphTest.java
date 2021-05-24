package bomb.tools.data.structures.graph.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OldWeightedListGraphTest {
    private final String[] testArray = {"Check check", "Again", "Thrice Cream", "The Forth",
            "The Fifth", "Incomplete", "Lucky No.", "Sideways Infinity"};
    private WeightedListGraph<String> directedGraph, undirectedGraph;


    @BeforeEach
    void setUp() {
        directedGraph = new WeightedListGraph<>(false);
        undirectedGraph= new WeightedListGraph<>(true);
    }

    @Test
    void verticesAddition(){
        Arrays.stream(testArray).forEach(s -> {
            assertTrue(directedGraph.addVertex(s));
            assertTrue(undirectedGraph.addVertex(s));
        });

        Arrays.stream(testArray)
                .forEach(s -> {
                    assertFalse(directedGraph.addVertex(s));
                    assertFalse(undirectedGraph.addVertex(s));
                });
    }

    @Test
    void edgeAddition(){
//        fill();

    }

    private void fill(){
        Arrays.stream(testArray)
                .forEach(s -> {
                    directedGraph.addVertex(s);
                    undirectedGraph.addVertex(s);
                });
    }
}
