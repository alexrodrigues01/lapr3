package lapr.project.controller;

import java.util.*;

import lapr.project.data.MoradaBD;
import lapr.project.model.Morada;
import lapr.project.model.graph.Graph;
import lapr.project.model.graph.GraphAlgorithms;
import lapr.project.utils.Pair;

public class AtribuirFarmaciaProximaTask {

    private final MoradaBD moradaBD;

    private List<Pair<Double, Integer>> distanciaFarmacias;

    /**
     * Construtor da classe AtribuirFarmaciaProximaTask
     * @param moradaBD moradaBD
     */
    public AtribuirFarmaciaProximaTask(MoradaBD moradaBD) {
        this.moradaBD = moradaBD;
    }

    /**
     * Procura a farmacia mais proxima do cliente
     *
     * @param emailCliente email do cliente
     * @return do id da farmacia mais proxima
     */
    public int atribuiEncomendaFarmacia(String emailCliente) {

        Map<Integer, Morada> moradas = moradaBD.getMoradasFarmacia();
        if (moradas == null)
            throw new IllegalArgumentException("Erro ao ir buscar todas as moradas da farmacia");

        Morada moradaCliente = moradaBD.getMoradaUtilizador(emailCliente);
        if (moradaCliente == null)
            throw new IllegalArgumentException("Erro ao ir buscar a morada do cliente");

        distanciaFarmacias = new ArrayList<>();

        Graph grafo = AplicacaoPOT.getInstance().getMapaTerrestreDistancias();

        for (Map.Entry<Integer, Morada> farmacia : moradas.entrySet()) {

            double distancia = GraphAlgorithms.shortestPath(grafo, farmacia.getValue(), moradaCliente, new LinkedList<>());
            if (distancia != 0)
                distanciaFarmacias.add(new Pair<>(distancia, farmacia.getKey()));
        }

        ordenarFarmacias();

        return distanciaFarmacias.get(0).getValue();
    }

    /**
     * ordena a farmacia por ordem crescente de distancia do cliente
     */
    private void ordenarFarmacias() {
        Collections.sort(distanciaFarmacias, (Pair<Double, Integer> o1, Pair<Double, Integer> o2) -> Double.compare(o1.getKey(), o2.getKey()));
    }

    /**
     * Retorna a estrutura que contem as distancias de cada farmacia ao cliente
     *
     * @return da estrutura que contem as distancias de cada farmacia ao cliente
     */
    public List<Pair<Double, Integer>> getDistanciaFarmacias() {
        return distanciaFarmacias;
    }
}
