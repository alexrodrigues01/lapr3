/*
 * A collection of graph algorithms.
 */
package lapr.project.model.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import lapr.project.utils.Pair;

/**
 * @author DEI-ESINF
 */

public class GraphAlgorithms {

    /**
     * Performs breadth-first search of a Graph starting in a Vertex
     *
     * @param g    Graph instance
     * @param vert information of the Vertex that will be the source of the search
     * @return qbfs a queue with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> breadthFirstSearch(Graph<V, E> g, V vert) {
        LinkedList<V> qbfs = new LinkedList<>();
        Queue<V> qaux = new LinkedList<>();
        qbfs.add(vert);
        qaux.add(vert);

        while (!qaux.isEmpty()) {
            vert = qaux.poll();
            if (g.validVertex(vert)) {
                for (V vadj : g.adjVertices(vert)) {
                    if (!qbfs.contains(vadj)) {
                        qbfs.add(vadj);
                        qaux.add(vadj);
                    }
                }
            } else {
                return null;
            }
        }
        return qbfs;
    }

    /**
     * Performs depth-first search starting in a Vertex
     *
     * @param g     Graph instance
     * @param vOrig Vertex of graph g that will be the source of the search
     *              //     * @param visited set of discovered vertices
     * @param qdfs  queue with vertices of depth-first search
     */
    private static <V, E> void depthFirstSearch(Graph<V, E> g, V vOrig, LinkedList<V> qdfs) {
        qdfs.add(vOrig);
        for (V vadj : g.adjVertices(vOrig))
            if (!qdfs.contains(vadj))
                depthFirstSearch(g, vadj, qdfs);
    }

    /**
     * @param g    Graph instance
     * @param vert information of the Vertex that will be the source of the search
     * @return qdfs a queue with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> depthFirstSearch(Graph<V, E> g, V vert) {
        if (g.validVertex(vert)) {
            LinkedList<V> qdfs = new LinkedList<>();
            depthFirstSearch(g, vert, qdfs);
            return qdfs;
        } else {
            return null;
        }

    }


    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with nonnegative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param visited set of discovered vertices
     *                //* @param pathkeys minimum path vertices keys
     * @param dist    minimum distances
     */
    protected static <V, E> void shortestPathLength(Graph<V, E> g, V vOrig, boolean[] visited, V[] pathKeys, double[] dist) {

        V[] path = pathKeys.clone();

        int vOrigKey = g.getKey(vOrig);
        dist[vOrigKey] = 0;
        while (vOrigKey != -1) {
            vOrig = path[vOrigKey];
            visited[vOrigKey] = true;
            for (V vAdj : g.adjVertices(vOrig)) {
                int vkeyAdj = g.getKey(vAdj);
                Edge<V, E> edge = g.getEdge(vOrig, vAdj);
                if (!visited[vkeyAdj] && dist[vkeyAdj] > dist[vOrigKey] + edge.getWeight()) {
                    dist[vkeyAdj] = dist[vOrigKey] + edge.getWeight();
                    pathKeys[vkeyAdj] = vOrig;
                }
            }

            vOrigKey = getMinDist(g, visited, dist);
        }
    }

    /**
     * distancia minima
     * @param g grafo
     * @param visited array boolean de nodes visitados
     * @param dist distancias
     * @param <V> classe generica
     * @param <E> classe generica
     * @return distancia minima
     */
    private static <V, E> int getMinDist(Graph<V, E> g, boolean[] visited, double[] dist) {
        double minDist = Double.MAX_VALUE;
        int vkey = -1;
        for (int i = 0; i < g.numVertices(); i++) {
            if (!visited[i] && dist[i] < minDist) {
                minDist = dist[i];
                vkey = i;
            }
        }
        return vkey;
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param g    Graph instance
     *             //* @param voInf    information of the Vertex origin
     *             //* @param vdInf    information of the Vertex destination
     *             //* @param pathkeys minimum path vertices keys
     * @param path stack with the minimum path (correct order)
     */
    protected static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest, V[] pathKeys, LinkedList<V> path) {
        if (!vOrig.equals(vDest)) {
            path.push(vDest);
            int vKey = g.getKey(vDest);
            V prevVKey = pathKeys[vKey];


            getPath(g, vOrig, prevVKey, pathKeys, path);
        } else {
            path.push(vOrig);
        }
    }

    /**
     * shortest-path between vOrig and vDest
     * @param g grafo
     * @param vOrig node original
     * @param vDest node destino
     * @param shortPath caminho mais curto
     * @param <V> classe generica
     * @param <E> classe generica
     * @return soma dos edges
     */
    public static <V, E> double shortestPath(Graph<V, E> g, V vOrig, V vDest, LinkedList<V> shortPath) {

        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return 0;
        }

        int numVertices = g.numVertices();
        boolean[] visited = new boolean[numVertices];

        V[] pathKeys = (V[]) new Object[numVertices];

        double[] dist = new double[numVertices];

        for (V vert : g.vertices()) {
            int key = g.getKey(vert);
            dist[key] = Double.MAX_VALUE;
            pathKeys[key] = vert;
            visited[key] = false;
        }

        shortestPathLength(g, vOrig, visited, pathKeys, dist);

        double lengthPath = dist[g.getKey(vDest)];

        if (lengthPath != Double.MAX_VALUE) {
            getPath(g, vOrig, vDest, pathKeys, shortPath);
            return lengthPath;
        }

        return 0;
    }

    /**
     * shortest-path between voInf and all other
     * @param g grafi
     * @param vOrig node origem
     * @param paths caminhos
     * @param dists distancias
     * @param <V> classe generica
     * @param <E> classe generica
     * @return boolean
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig, ArrayList<LinkedList<V>> paths, ArrayList<Double> dists) {

        if (!g.validVertex(vOrig)) return false;
        paths.clear();
        dists.clear();
        for (V vert : g.vertices()) {
            LinkedList<V> shortPath = new LinkedList<>();
            double dist = shortestPath(g, vOrig, vert, shortPath);
            paths.add(shortPath);

            if (dist != 0 || vOrig == vert) {
                dists.add(dist);
            } else {
                dists.add(Double.MAX_VALUE);
            }
        }

        return true;
    }

    /**
     * algoritmo de Floyd Warshall adaptado
     * @param network grafo
     * @param list lista nodes intermedios
     * @param <V> classe generica
     * @param <E> classe generico
     * @return
     */
        public static <V, E> Pair<List<V>, Double>[][] allShortPathsRanged(Graph<V, E> network, List<V> list) {
        int size = list.size();
        Pair<List<V>, Double> record[][] = new Pair[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                record[i][j] = new Pair<>(new ArrayList<>(), Double.POSITIVE_INFINITY);
            }
        }
        for (V vert : list) {
            for (Edge<V, E> edge : network.outgoingEdges(vert)) {
                if (list.contains(edge.getVDest())) {
                    Pair<List<V>, Double> aux = record[list.indexOf(vert)][list.indexOf(edge.getVDest())];
                    aux.getKey().add(edge.getVDest());
                    aux.setValue(edge.getWeight());
                }
            }
        }
        for (int i = 0; i < size; i++) {
            record[i][i] = new Pair<>(new ArrayList<>(), (double) 0);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                LinkedList<V> auxPath = new LinkedList<>();
                double auxValue = shortestPath(network, list.get(i), list.get(j), auxPath);
                if (!auxPath.isEmpty() && auxValue < record[i][j].getValue()) {
                    record[i][j].setKey(auxPath.subList(1, auxPath.size()));
                    record[i][j].setValue(auxValue);
                }
            }
        }
        return record;
    }

    /**
     * metodo que retorna o menor caminho entre dois vertices passando por uma lista de vertices
     * @param network grafo
     * @param nodes nodes intermedios
     * @param vOrig node de origem
     * @param vDest node destino
     * @param <V> objeto generico
     * @param <E> objeto generico
     * @return enor caminho entre dois vertices passando por uma lista de vertices
     */
    public static <V, E> List<V> shortestPathMultipleNodes(Graph<V, E> network, List<V> nodes, V vOrig, V vDest) {
        Pair<List<V>, Double>[][] allShortestPaths = allShortPathsRanged(network, nodes);

        List<V> pathMenor = new ArrayList<>();
        List<V> pathAux;
        double somaMenor = Double.POSITIVE_INFINITY;
        double somaAux;

        List<List<Integer>> combs = new ArrayList<>();
        List<Integer> auxCombs = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            auxCombs.add(i);
        }
        int vOrigIndex = nodes.indexOf(vOrig);
        int vDestIndex = nodes.indexOf(vDest);
        
        if(vOrigIndex<vDestIndex){
            auxCombs.remove(vDestIndex);
            auxCombs.remove(vOrigIndex);
        }else if(vOrigIndex>vDestIndex){
            auxCombs.remove(vOrigIndex);
            auxCombs.remove(vDestIndex);
        }else{
            auxCombs.remove(vOrigIndex);
        }
        
        combinacoes(auxCombs.size(), auxCombs, combs);
        for (List<Integer> comb : combs) {
            comb.add(vDestIndex);
            int k = vOrigIndex;
            somaAux = 0;
            pathAux = new ArrayList<>();
            for (int i : comb) {
                somaAux += allShortestPaths[k][i].getValue();
                pathAux.addAll(allShortestPaths[k][i].getKey());
                k = i;
            }
            if (somaAux < somaMenor) {
                somaMenor = somaAux;
                pathMenor = new ArrayList<>(pathAux);
            }
        }

        pathMenor.add(0, vOrig);
        return pathMenor;
    }

    /**
     * combinações de uma lista
     * @param n tamanho lista
     * @param elements lista elementos
     * @param combinacoes lista de combinaçoes
     * @param <V> objeto generico
     */
    protected static <V> void combinacoes(int n, List<V> elements, List<List<V>> combinacoes) {
        if (n <= 1) {
            combinacoes.add(new ArrayList(elements));
        } else {
            for (int i = 0; i < n - 1; i++) {
                combinacoes(n - 1, elements, combinacoes);
                if (n % 2 == 0) {
                    swap(elements, i, n - 1);
                } else {
                    swap(elements, 0, n - 1);
                }
            }
            combinacoes(n - 1, elements, combinacoes);
        }
    }

    /**
     * troca de elementos numa combinacao
     * @param input lista de input
     * @param a primeiro index
     * @param b segundo index
     * @param <V> objeto generico
     */
    private static <V> void swap(List<V> input, int a, int b) {
        V tmp = input.get(a);
        V bValue = input.get(b);
        input.remove(a);
        input.add(a, bValue);
        input.remove(b);
        input.add(b, tmp);
    }
}