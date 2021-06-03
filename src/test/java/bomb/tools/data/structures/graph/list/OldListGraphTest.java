package bomb.tools.data.structures.graph.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OldListGraphTest {
    private final String[] testArray = {"Check check", "Again", "Thrice Cream", "The Forth",
            "The Fifth", "Incomplete", "Lucky No.", "Sideways Infinity"};
    private ListGraph<String> directedGraph, undirectedGraph;


    @BeforeEach
    void setUp() {
        directedGraph = new ListGraph<>(false);
        undirectedGraph= new ListGraph<>(true);
    }

    @Test
    void verticesAddition(){
        Arrays.stream(testArray).forEach(s -> {
            assertTrue(directedGraph.addVertex(s));
            assertTrue(undirectedGraph.addVertex(s));
        });

        Arrays.stream(testArray).forEach(s -> {
            assertFalse(directedGraph.addVertex(s));
            assertFalse(undirectedGraph.addVertex(s));
        });
    }


    private void fill(){
        Arrays.stream(testArray)
                .forEach(s -> {
                    directedGraph.addVertex(s);
                    undirectedGraph.addVertex(s);
                });
    }
}