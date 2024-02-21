package lapr.project.model.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import lapr.project.utils.Pair;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author DEI-ISEP
 */
public class GraphAlgorithmsTest {

    Graph<String, String> completeMap = new Graph<>(false);
    Graph<String, String> incompleteMap = new Graph<>(false);

    public GraphAlgorithmsTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {

        completeMap.insertVertex("Porto");
        completeMap.insertVertex("Braga");
        completeMap.insertVertex("Vila Real");
        completeMap.insertVertex("Aveiro");
        completeMap.insertVertex("Coimbra");
        completeMap.insertVertex("Leiria");

        completeMap.insertVertex("Viseu");
        completeMap.insertVertex("Guarda");
        completeMap.insertVertex("Castelo Branco");
        completeMap.insertVertex("Lisboa");
        completeMap.insertVertex("Faro");

        completeMap.insertEdge("Porto", "Aveiro", "A1", 75);
        completeMap.insertEdge("Porto", "Braga", "A3", 60);
        completeMap.insertEdge("Porto", "Vila Real", "A4", 100);
        completeMap.insertEdge("Viseu", "Guarda", "A25", 75);
        completeMap.insertEdge("Guarda", "Castelo Branco", "A23", 100);
        completeMap.insertEdge("Aveiro", "Coimbra", "A1", 60);
        completeMap.insertEdge("Coimbra", "Lisboa", "A1", 200);
        completeMap.insertEdge("Coimbra", "Leiria", "A34", 80);
        completeMap.insertEdge("Aveiro", "Leiria", "A17", 120);
        completeMap.insertEdge("Leiria", "Lisboa", "A8", 150);

        completeMap.insertEdge("Aveiro", "Viseu", "A25", 85);
        completeMap.insertEdge("Leiria", "Castelo Branco", "A23", 170);
        completeMap.insertEdge("Lisboa", "Faro", "A2", 280);

        incompleteMap = completeMap.clone();

        incompleteMap.removeEdge("Aveiro", "Viseu");
        incompleteMap.removeEdge("Leiria", "Castelo Branco");
        incompleteMap.removeEdge("Lisboa", "Faro");
    }

    /**
     * Test of BreadthFirstSearch method, of class GraphAlgorithms.
     */
    @org.junit.jupiter.api.Test
    public void testBreadthFirstSearch() {
        System.out.println("Test BreadthFirstSearch");

        assertTrue(GraphAlgorithms.breadthFirstSearch(completeMap, "LX") == null);

        LinkedList<String> path = GraphAlgorithms.breadthFirstSearch(incompleteMap, "Faro");

        assertTrue(path.size() == 1);

        Iterator<String> it = path.iterator();
        assertTrue(it.next().compareTo("Faro") == 0);

        path = GraphAlgorithms.breadthFirstSearch(incompleteMap, "Porto");
        assertTrue(path.size() == 7);

        path = GraphAlgorithms.breadthFirstSearch(incompleteMap, "Viseu");
        assertTrue(path.size() == 3);
    }

    /**
     * Test of DepthFirstSearch method, of class GraphAlgorithms.
     */
    @org.junit.jupiter.api.Test
    public void testDepthFirstSearch() {
        System.out.println("Test of DepthFirstSearch");

        LinkedList<String> path;

        assertTrue(GraphAlgorithms.depthFirstSearch(completeMap, "LX") == null);

        path = GraphAlgorithms.depthFirstSearch(incompleteMap, "Faro");
        assertTrue(path.size() == 1);

        Iterator<String> it = path.iterator();
        assertTrue(it.next().compareTo("Faro") == 0);

        path = GraphAlgorithms.depthFirstSearch(incompleteMap, "Porto");
        assertTrue(path.size() == 7);

        path = GraphAlgorithms.depthFirstSearch(incompleteMap, "Viseu");
        assertTrue(path.size() == 3);

        it = path.iterator();
        assertTrue(it.next().compareTo("Viseu") == 0);
        assertTrue(it.next().compareTo("Guarda") == 0);
        assertTrue(it.next().compareTo("Castelo Branco") == 0);
    }

    /**
     * Test of shortestPath method, of class GraphAlgorithms.
     */
    @org.junit.jupiter.api.Test
    public void testShortestPath() {
        System.out.println("Test of shortest path");

        LinkedList<String> shortPath = new LinkedList<String>();
        double lenpath = 0;
        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "LX", shortPath);
        assertTrue(lenpath == 0);

        lenpath = GraphAlgorithms.shortestPath(incompleteMap, "Porto", "Faro", shortPath);
        assertTrue(lenpath == 0);

        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Porto", shortPath);
        assertTrue(shortPath.size() == 1);

        lenpath = GraphAlgorithms.shortestPath(incompleteMap, "Porto", "Lisboa", shortPath);
        assertTrue(lenpath == 335);

        Iterator<String> it = shortPath.iterator();

        assertTrue(it.next().compareTo("Porto") == 0);
        assertTrue(it.next().compareTo("Aveiro") == 0);
        assertTrue(it.next().compareTo("Coimbra") == 0);
        assertTrue(it.next().compareTo("Lisboa") == 0);

        lenpath = GraphAlgorithms.shortestPath(incompleteMap, "Braga", "Leiria", shortPath);
        assertTrue(lenpath == 255);

        it = shortPath.iterator();

        assertTrue(it.next().compareTo("Braga") == 0);
        assertTrue(it.next().compareTo("Porto") == 0);
        assertTrue(it.next().compareTo("Aveiro") == 0);
        assertTrue(it.next().compareTo("Leiria") == 0);

        shortPath.clear();
        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Castelo Branco", shortPath);
        assertTrue(lenpath == 335);
        assertTrue(shortPath.size() == 5);

        it = shortPath.iterator();

        assertTrue(it.next().compareTo("Porto") == 0);
        assertTrue(it.next().compareTo("Aveiro") == 0);
        assertTrue(it.next().compareTo("Viseu") == 0);
        assertTrue(it.next().compareTo("Guarda") == 0);
        assertTrue(it.next().compareTo("Castelo Branco") == 0);

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco
        //should change shortest path between Porto and Castelo Branco
        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.insertEdge("Leiria", "Castelo Branco", "A23", 170);
        shortPath.clear();
        lenpath = GraphAlgorithms.shortestPath(completeMap, "Porto", "Castelo Branco", shortPath);
        assertTrue(lenpath == 365);
        assertTrue(shortPath.size() == 4);

        it = shortPath.iterator();

        assertTrue(it.next().compareTo("Porto") == 0);
        assertTrue(it.next().compareTo("Aveiro") == 0);
        assertTrue(it.next().compareTo("Leiria") == 0);
        assertTrue(it.next().compareTo("Castelo Branco") == 0);

    }

    /**
     * Test of shortestPaths method, of class GraphAlgorithms.
     */
    @org.junit.jupiter.api.Test
    public void testShortestPaths() {
        System.out.println("Test of shortest path");

        ArrayList<LinkedList<String>> paths = new ArrayList<>();
        ArrayList<Double> dists = new ArrayList<>();

        GraphAlgorithms.shortestPaths(completeMap, "Porto", paths, dists);

        assertEquals(paths.size(), dists.size());
        assertEquals(completeMap.numVertices(), paths.size());
        assertEquals(1, paths.get(completeMap.getKey("Porto")).size());
        assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), paths.get(completeMap.getKey("Lisboa")));
        assertEquals(Arrays.asList("Porto", "Aveiro", "Viseu", "Guarda", "Castelo Branco"), paths.get(completeMap.getKey("Castelo Branco")));
        assertEquals(335, dists.get(completeMap.getKey("Castelo Branco")), 0.01);

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco
        //should change shortest path between Porto and Castelo Branco
        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.insertEdge("Leiria", "Castelo Branco", "A23", 170);
        GraphAlgorithms.shortestPaths(completeMap, "Porto", paths, dists);
        assertEquals(365, dists.get(completeMap.getKey("Castelo Branco")), 0.01);
        assertEquals(Arrays.asList("Porto", "Aveiro", "Leiria", "Castelo Branco"), paths.get(completeMap.getKey("Castelo Branco")));

        GraphAlgorithms.shortestPaths(incompleteMap, "Porto", paths, dists);
        assertEquals(Double.MAX_VALUE, dists.get(completeMap.getKey("Faro")), 0.01);
        assertEquals(335, dists.get(completeMap.getKey("Lisboa")), 0.01);
        assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), paths.get(completeMap.getKey("Lisboa")));
        assertEquals(335, dists.get(completeMap.getKey("Lisboa")), 0.01);

        GraphAlgorithms.shortestPaths(incompleteMap, "Braga", paths, dists);
        assertEquals(255, dists.get(completeMap.getKey("Leiria")), 0.01);
        assertEquals(Arrays.asList("Braga", "Porto", "Aveiro", "Leiria"), paths.get(completeMap.getKey("Leiria")));
    }

    /**
     * Test of allShortestPathsRanged method, of class GraphAlgorithms.
     */
    @Test
    public void testAllShortestPathsRanged() {
        System.out.println("allShortestPathsRanged");
        Graph<String, String> network = new Graph(true);
        String v1 = "A";
        String v2 = "B";
        String v3 = "C";
        network.insertVertex(v1);
        network.insertVertex(v2);
        network.insertVertex(v3);

        network.insertEdge(v1, v3, "dist", 2);
        network.insertEdge(v2, v1, "dist", 4);
        network.insertEdge(v2, v3, "dist", 2);
        network.insertEdge(v3, v1, "dist", 1);
        Pair[][] expResult = {
            {new Pair(new ArrayList<String>(), 0.0), new Pair(new ArrayList<String>(), Double.POSITIVE_INFINITY), new Pair(new ArrayList<String>(Arrays.asList("C")), 2.0)},
            {new Pair(new ArrayList<String>(Arrays.asList("C", "A")), 3.0), new Pair(new ArrayList<String>(), 0.0), new Pair(new ArrayList<String>(Arrays.asList("C")), 2.0)},
            {new Pair(new ArrayList<String>(Arrays.asList("A")), 1.0), new Pair(new ArrayList<String>(), Double.POSITIVE_INFINITY), new Pair(new ArrayList<String>(), 0.0)}
        };
        Pair<List<String>, Double>[][] result = GraphAlgorithms.allShortPathsRanged(network, Arrays.asList(v1, v2, v3));
        Pair<List<String>, Double>[][] allShortestPaths = GraphAlgorithms.allShortPathsRanged(network, new ArrayList<String>(Arrays.asList(v1, v2, v3)));

        assertArrayEquals(expResult, result);
    }

    /**
     * Test of allShortestPathsRanged method, of class GraphAlgorithms.
     */
    @Test
    public void testAllShortestPathsRangedError1() {
        System.out.println("allShortestPathsRanged");
        Graph<String, String> network = new Graph(true);
        String v1 = "A";
        String v2 = "B";
        String v3 = "C";
        String v4 = "D";
        network.insertVertex(v1);
        network.insertVertex(v2);
        network.insertVertex(v3);
        network.insertVertex(v4);

        network.insertEdge(v1, v3, "dist", 2);
        network.insertEdge(v2, v1, "dist", 7);
        network.insertEdge(v2, v1, "dist", 4);
        network.insertEdge(v2, v3, "dist", 2);
        network.insertEdge(v3, v1, "dist", 1);
        network.insertEdge(v1, v4, "dist", 2);
        network.insertEdge(v2, v4, "dist", 4);
        network.insertEdge(v3, v4, "dist", 1);
        Pair[][] expResult = {
            {new Pair(new ArrayList<String>(), 0.0), new Pair(new ArrayList<String>(), Double.POSITIVE_INFINITY), new Pair(new ArrayList<String>(Arrays.asList("C")), 2.0)},
            {new Pair(new ArrayList<String>(Arrays.asList("C", "A")), 3.0), new Pair(new ArrayList<String>(), 0.0), new Pair(new ArrayList<String>(Arrays.asList("C")), 2.0)},
            {new Pair(new ArrayList<String>(Arrays.asList("A")), 1.0), new Pair(new ArrayList<String>(), Double.POSITIVE_INFINITY), new Pair(new ArrayList<String>(), 0.0)}
        };
        Pair<List<String>, Double>[][] result = GraphAlgorithms.allShortPathsRanged(network, Arrays.asList(v1, v2, v3));
        Pair<List<String>, Double>[][] allShortestPaths = GraphAlgorithms.allShortPathsRanged(network, new ArrayList<String>(Arrays.asList(v1, v2, v3)));

        assertEquals(allShortestPaths[0][0].getValue(), 0.0);
        assertEquals(allShortestPaths[0][1].getValue(), Double.POSITIVE_INFINITY);
        assertEquals(allShortestPaths[0][2].getValue(), 2.0);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of shortestPathMultipleNodes method, of class GraphAlgorithms.
     */
    @Test
    public void testShortestPathMultipleNodes() {
        System.out.println("shortestPathMultipleNodes");
        Graph<String, String> network = new Graph(true);
        String v1 = "A";
        String v2 = "B";
        String v3 = "C";
        network.insertVertex(v1);
        network.insertVertex(v2);
        network.insertVertex(v3);

        network.insertEdge(v1, v3, "dist", 2);
        network.insertEdge(v2, v1, "dist", 4);
        network.insertEdge(v2, v3, "dist", 2);
        network.insertEdge(v3, v1, "dist", 1);

        List<String> expResult = Arrays.asList("B", "C", "A");
        List<String> result = GraphAlgorithms.shortestPathMultipleNodes(network, new ArrayList<String>(Arrays.asList(v1, v2, v3)), v2, v1);
        assertEquals(expResult, result);
    }

    /**
     * Test of shortestPathMultipleNodes method, of class GraphAlgorithms.
     */
    @Test
    public void testShortestPathMultipleNodesM1() {
        System.out.println("shortestPathMultipleNodes");
        Graph<String, String> network = new Graph(true);
        String v1 = "A";
        String v2 = "B";
        String v3 = "C";
        network.insertVertex(v1);
        network.insertVertex(v2);
        network.insertVertex(v3);

        network.insertEdge(v1, v3, "dist", 2);
        network.insertEdge(v2, v1, "dist", 4);
        network.insertEdge(v2, v3, "dist", 2);
        network.insertEdge(v3, v1, "dist", 1);

        List<String> expResult = Arrays.asList("A");
        List<String> result = GraphAlgorithms.shortestPathMultipleNodes(network, new ArrayList<String>(Arrays.asList(v1, v2, v3)), v1, v1);
        assertEquals(expResult, result);
    }

    /**
     * Test of shortestPathMultipleNodes method, of class GraphAlgorithms.
     */
    @Test
    public void testShortestPathMultipleNodesM2() {
        System.out.println("shortestPathMultipleNodes");
        Graph<String, String> network = new Graph(true);
        String v1 = "A";
        String v2 = "B";
        String v3 = "C";
        network.insertVertex(v1);
        network.insertVertex(v2);
        network.insertVertex(v3);

        network.insertEdge(v1, v3, "dist", 2);
        network.insertEdge(v2, v1, "dist", 4);
        network.insertEdge(v2, v3, "dist", 2);
        network.insertEdge(v3, v1, "dist", 1);
        network.insertEdge(v3, v2, "dist", 1);

        List<String> expResult = Arrays.asList("A", "C", "B", "C", "A");
        List<String> result = GraphAlgorithms.shortestPathMultipleNodes(network, new ArrayList<String>(Arrays.asList(v1, v2, v3)), v1, v1);
        assertEquals(expResult, result);
    }

    /**
     * Test of shortestPathMultipleNodes method, of class GraphAlgorithms.
     */
    @Test
    public void testShortestPathMultipleNodesM3() {
        System.out.println("shortestPathMultipleNodes");
        Graph<String, String> network = new Graph(true);
        String v1 = "A";
        String v2 = "B";
        String v3 = "C";
        network.insertVertex(v1);
        network.insertVertex(v2);
        network.insertVertex(v3);

        network.insertEdge(v1, v3, "dist", 2);
        network.insertEdge(v2, v1, "dist", 4);
        network.insertEdge(v2, v3, "dist", 2);
        network.insertEdge(v3, v1, "dist", 1);
        network.insertEdge(v3, v2, "dist", 1);

        List<String> expResult = Arrays.asList("A", "C", "B");
        List<String> result = GraphAlgorithms.shortestPathMultipleNodes(network, new ArrayList<String>(Arrays.asList(v1, v2, v3)), v1, v2);
        assertEquals(expResult, result);
    }

    /**
     * Test of combinacoes method, of class GraphAlgorithms.
     */
    @Test
    public void testCombinacoes() {
        System.out.println("combinacoes");
        List<Integer> listaDefault = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<List<Integer>> combinacoes = new ArrayList<>();

        List<List<Integer>> expResult = new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(1, 2, 3)),
                new ArrayList<>(Arrays.asList(1, 3, 2)), new ArrayList<>(Arrays.asList(2, 1, 3)),
                new ArrayList<>(Arrays.asList(2, 3, 1)), new ArrayList<>(Arrays.asList(3, 2, 1)),
                new ArrayList<>(Arrays.asList(3, 1, 2))));

        GraphAlgorithms.combinacoes(3, listaDefault, combinacoes);

        assertTrue(expResult.containsAll(combinacoes));
        assertTrue(combinacoes.containsAll(expResult));
        assertTrue(expResult.size() == combinacoes.size());
    }

    /**
     * Test of combinacoes method, of class GraphAlgorithms.
     */
    @Test
    public void testCombinacoesM1() {
        System.out.println("combinacoes");
        List<Integer> listaDefault = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<List<Integer>> combinacoes = new ArrayList<>();

        List<List<Integer>> expResult = new ArrayList<>();

        GraphAlgorithms.combinacoes(5, listaDefault, combinacoes);

        assertTrue(combinacoes.size() == 120);
        
        for (List<Integer> comb : combinacoes) {
            assertTrue(comb.size()==5);
            assertTrue(comb.containsAll(listaDefault));
            assertFalse(expResult.contains(comb));
            expResult.add(comb);
        }
    }

    /**
     * Test of combinacoes method, of class GraphAlgorithms.
     */
    @Test
    public void testCombinacoesM2() {
        System.out.println("combinacoes");
        List<Integer> listaDefault = new ArrayList<>(Arrays.asList(1));
        List<List<Integer>> combinacoes = new ArrayList<>();

        GraphAlgorithms.combinacoes(1, listaDefault, combinacoes);

        assertEquals(combinacoes, new ArrayList<>(Arrays.asList(listaDefault)));
    }
    
    /**
     * Test of combinacoes method, of class GraphAlgorithms.
     */
    @Test
    public void testCombinacoesM3() {
        System.out.println("combinacoes");
        List<Integer> listaDefault = new ArrayList<>(Arrays.asList(777,888,555));
        List<List<Integer>> combinacoes = new ArrayList<>();

        GraphAlgorithms.combinacoes(0, listaDefault, combinacoes);

        assertEquals(combinacoes,new ArrayList<>(Arrays.asList(listaDefault)));

    }
}
