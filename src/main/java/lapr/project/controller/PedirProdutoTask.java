package lapr.project.controller;

import java.util.*;
import lapr.project.data.MoradaBD;
import lapr.project.data.NotaBD;
import lapr.project.data.ProdutoBD;
import lapr.project.model.Morada;
import lapr.project.model.graph.Graph;
import lapr.project.model.graph.GraphAlgorithms;
import lapr.project.utils.Pair;

/**
 * classe PedirProdutoTask
 */
public class PedirProdutoTask {

    private final MoradaBD moradaBD;
    private final ProdutoBD produtoBD;
    private final NotaBD notaBD;

    private List<Pair<Double, Integer>> farmacias;

    /**
     * construtor PedirProdutoTask
     * @param moradaBD moradaBD
     * @param produtoBD produtoBD
     * @param notaBD notaBD
     */
    public PedirProdutoTask(MoradaBD moradaBD, ProdutoBD produtoBD, NotaBD notaBD) {
        this.moradaBD = moradaBD;
        this.produtoBD = produtoBD;
        this.notaBD = notaBD;

    }

    /**
     * pedir produto à farmacia mais proximo
     * @param idFarmaciaRecetora id da farmacia que receber o pedido
     * @param idProduto id do produto a pedir
     * @param quantidadeNecessaria id da quantidade de produto a pedir
     * @return boolean
     */
    public boolean pedirProduto(int idFarmaciaRecetora, int idProduto, int quantidadeNecessaria) {

        Map<Integer, Morada> moradas = moradaBD.getMoradasFarmacia();
        if (moradas == null)
            throw new IllegalArgumentException("Erro ao ir buscar todas as moradas da farmacia");

        Morada moradaRecetora = moradas.remove(idFarmaciaRecetora);

        farmacias = new ArrayList<>();

        Graph<Morada, Double> grafo = AplicacaoPOT.getInstance().getMapaTerrestreDistancias();

        for (Map.Entry<Integer, Morada> entry : moradas.entrySet()) {
            if (produtoBD.verificaStock(entry.getKey(), idProduto, quantidadeNecessaria)) {

                double distancia = GraphAlgorithms.shortestPath(grafo, moradaRecetora, entry.getValue(), new LinkedList<>());
                distancia += GraphAlgorithms.shortestPath(grafo, entry.getValue(), moradaRecetora, new LinkedList<>());

                farmacias.add(new Pair<>(distancia, entry.getKey()));
            }
        }

        if (farmacias.isEmpty())
            return false;

        ordenarFarmacias(farmacias);

        int idFarmaciaEmissora = farmacias.get(0).getValue();

        int notaID = notaBD.gerarNotas(idFarmaciaRecetora, idFarmaciaEmissora, idProduto, quantidadeNecessaria);
        if (notaID == 0)
            throw new IllegalArgumentException("Erro ao criar as notas de emissão e receção");

        return true;
    }

    /**
     * ordena farmacias por ordem crescente de distância
     * @param farmacias lista farmacias ordenas
     */
    private void ordenarFarmacias(List<Pair<Double, Integer>> farmacias) {

        Collections.sort(farmacias, (Pair<Double, Integer> o1, Pair<Double, Integer> o2) -> Double.compare(o1.getKey(), o2.getKey()));
    }

    /**
     * lista de id farmacias com distancia
     * @return lista de id farmacia e distancias
     */
    public List<Pair<Double, Integer>> getFarmacias() {
        return farmacias;
    }
}
