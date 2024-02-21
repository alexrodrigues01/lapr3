package lapr.project.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lapr.project.data.MoradaBD;
import lapr.project.model.Drone;
import lapr.project.model.Morada;
import lapr.project.model.Veiculo;
import lapr.project.model.graph.Graph;
import lapr.project.model.graph.GraphAlgorithms;
import lapr.project.utils.Constantes;
import lapr.project.utils.Pair;

/**
 * classe referente à computação do caminho de uma determinada entrega
 */
public class CaminhoTask {
    /**
     * grafo escolhido
     */
    Graph<Morada, Double> g;

    /**
     * metodo principal que retorna pair com o caminho e o consumo energético, tempo e distância
     * @param estimativas variavel de controlo para gerar o caminho com base na estimativa energetica ou distancia
     * @param moradaFarmacia morada da farmacia inicial
     * @param moradasEncomendas moradas a realizar as entregas
     * @param massaEncomendas massa das encomendas
     * @param veiculo veiculo escolhido
     * @param isDrone variavel de controlo que determina se o caminho tem que ser gerado para um drone ou uma scooter
     * @param moradaBD variavel de acesso à base de dados
     * @return pair com o caminho e o consumo energético, tempo e distância
     * @throws Exception cenarios onde nao pode ser usado aquele veiculo
     */
    public Pair<List<Morada>, double[]> caminho(boolean estimativas, Morada moradaFarmacia, List<Morada> moradasEncomendas, double massaEncomendas, Veiculo veiculo, boolean isDrone, MoradaBD moradaBD) throws Exception {

        List<Morada> caminho = gerarCaminho(estimativas, moradaFarmacia, moradasEncomendas, isDrone);
        double[] array = new double[3];
        if (isDrone) {
            array[0] = new EstimarConsumoDroneTask().estimarConsumoPercurso((Drone) veiculo, massaEncomendas, caminho, false);
        } else {
            array[0] = new EstimarConsumoScooterTask().calculoEstimativaConsumo(estimativas, veiculo.getMassa(), massaEncomendas, caminho, false);
        }
        double energiaDisponivel = veiculo.getCapacidadeBateria() * veiculo.getCarga() / 100 * 3600;

        /*METODO RECURSIVO PARA ADICIONAR PARAGENS A UM CENTRO DE CARREGAMENTO*/
        Map< Integer, Morada> farmacias = moradaBD.getMoradasFarmacia();
        if (energiaDisponivel < array[0]) {
            if (energiaDisponivel > energiaMinimiaCaminho(veiculo, massaEncomendas, new ArrayList<>(caminho), farmacias, isDrone)) {
                adicionarParagensCarregamento(veiculo, massaEncomendas, caminho, new ArrayList<>(caminho.subList(0, 2)), 2, array[0], energiaDisponivel, farmacias, isDrone);
            } else {
                throw new IllegalStateException("Impossível utilizar este veículo. Não possui carga mínima necessária.");
            }
        }
        /**/

        array[2] = calcularDistanciaTotal(caminho);
        if (isDrone) {
            array[1] = array[2] / ((Drone) veiculo).getVelocidade();
        } else {
            array[1] = array[2] / Constantes.VELOCIDADE_SCOOTER;
        }

        return new Pair<>(caminho, array);
    }

    /**
     * retorne o caminho
     * @param estimativas variavel de controlo para gerar o caminho com base na estimativa energetica ou distancia
     * @param moradaFarmacia morada da farmacia inicial
     * @param moradasEncomendas moradas a realizar as entregas
     * @param isDrone variavel de controlo que determina se o caminho tem que ser gerado para um drone ou uma scooter
     * @return caminho
     * @throws Exception exceçoes caso nao seja possivel gerar
     */
    protected List<Morada> gerarCaminho(boolean estimativas, Morada moradaFarmacia, List<Morada> moradasEncomendas, boolean isDrone) throws Exception {

        if (isDrone) {
            List<Morada> moradas = new ArrayList<>();
            moradas.add(moradaFarmacia);
            moradas.addAll(moradasEncomendas);
            for (Morada m : moradas) {
                if (m.getAltitude() > 150) {
                    throw new Exception("As moradas a percorrer têm altitudes superiores a 150 metros.");
                }
            }
            if (estimativas) {
                g = AplicacaoPOT.getInstance().getMapaAereoEstimativas();
            } else {
                g = AplicacaoPOT.getInstance().getMapaAereoDistancias();
            }
        } else if (estimativas) {
            g = AplicacaoPOT.getInstance().getMapaTerrestreEstimativas();
        } else {
            g = AplicacaoPOT.getInstance().getMapaTerrestreDistancias();
        }
        if (!g.validVertex(moradaFarmacia)) {
            throw new Exception("A morada da farmacia nao esta no mapa.");
        }
        List<Morada> moradasAcessiveis = GraphAlgorithms.breadthFirstSearch(g, moradaFarmacia);
        for (Morada intermedia : moradasEncomendas) {
            if (!g.validVertex(intermedia)) {
                throw new Exception("Uma das moradas de entrega nao se encontra no mapa.");
            }
        }
        if (!moradasAcessiveis.containsAll(moradasEncomendas)) {
            throw new Exception("Nao e possivel aceder a uma das moradas desde a farmacia.");
        }

        moradasEncomendas.add(moradaFarmacia);
        List<Morada> caminho = GraphAlgorithms.shortestPathMultipleNodes(g, moradasEncomendas, moradaFarmacia, moradaFarmacia);
        if (!caminho.containsAll(moradasEncomendas)) {
            throw new Exception("Nao e possivel este caminho.");
        }
        return caminho;
    }

    /**
     * calcula a distancia com base num percurso
     * @param moradas percurso
     * @return distancia
     */
    private double calcularDistanciaTotal(List<Morada> moradas) {
        double somaDistancias = 0;
        for (int i = 0; i < moradas.size() - 1; i++) {
            somaDistancias += moradas.get(i).distanceFrom(moradas.get(i + 1));
        }
        return somaDistancias;
    }

    /**
     * metodo recursivo  para adicionar paragens de carregamento ao percurso
     * @param veiculo veiculo selecionado
     * @param massaEncomendas massa das encomendas selecionadas
     * @param caminho caminho base
     * @param listaSimulacao caminho auxiliar
     * @param indexCaminho index da morada no caminho
     * @param energiaEstimada energia estimada do percurso
     * @param energiaDisponivel energia disponivel do drone
     * @param farmacias mapa com as farmacias
     * @param isDrone variavel de controlo que determina se o caminho tem que ser gerado para um drone ou uma scooter
     * @throws Exception exceçoes caso nao consiga carregar
     */
    protected void adicionarParagensCarregamento(Veiculo veiculo, double massaEncomendas, List<Morada> caminho, List<Morada> listaSimulacao, int indexCaminho, double energiaEstimada, double energiaDisponivel, Map<Integer, Morada> farmacias, boolean isDrone) throws Exception {
        if (indexCaminho >= caminho.size()) {
            throw new Exception("Index invalido. Index 2 e o recomendado.");
        }
        if (farmacias.containsValue(caminho.get(indexCaminho-1)) && indexCaminho != 1) {
            energiaDisponivel += veiculo.getCapacidadeBateria() * 3600;
        }
        if (energiaEstimada <= energiaDisponivel) {
            return;
        }
        double energiaSimulacao;
        listaSimulacao.add(caminho.get(indexCaminho));
        if (isDrone) {
            energiaSimulacao = new EstimarConsumoDroneTask().estimarConsumoPercurso((Drone) veiculo, massaEncomendas, listaSimulacao, false);
        } else {
            energiaSimulacao = new EstimarConsumoScooterTask().calculoEstimativaConsumo(true, veiculo.getMassa(), massaEncomendas, listaSimulacao, false);
        }
        if (energiaSimulacao > energiaDisponivel) {
            Morada moradaAnterior = caminho.get(indexCaminho - 1);
            Morada farmMorada = getFarmaciaMaisPertoConsumo(moradaAnterior, farmacias, isDrone);
            boolean encontrouCarga = false;
            for (int i = 0; i < indexCaminho; i++) {
                moradaAnterior = caminho.get(indexCaminho - 1);
                farmMorada = getFarmaciaMaisPertoConsumo(moradaAnterior, farmacias, isDrone);
                if (!temEnergiaParaIrFarmacia(veiculo, massaEncomendas, caminho, indexCaminho, farmMorada, energiaDisponivel, isDrone)) {
                    indexCaminho--;
                } else {
                    encontrouCarga = true;
                    break;
                }
            }
            if (!encontrouCarga) {
                throw new Exception("Não encontrou um posto para carregar.");
            }
            listaSimulacao = new ArrayList<>(caminho.subList(0, indexCaminho));
            LinkedList<Morada> caminhoAux = new LinkedList<>();
            GraphAlgorithms.shortestPath(g, moradaAnterior, farmMorada, caminhoAux);
            LinkedList<Morada> caminhoAux2 = new LinkedList<>();
            GraphAlgorithms.shortestPath(g, farmMorada, caminho.get(indexCaminho), caminhoAux2);
            caminho.remove(indexCaminho - 1);
            caminho.remove(indexCaminho - 1);
            caminho.addAll(indexCaminho - 1, caminhoAux);
            caminhoAux2.remove(0);
            caminho.addAll(indexCaminho + caminhoAux.size() - 1, caminhoAux2);
            adicionarParagensCarregamento(veiculo, massaEncomendas, caminho, listaSimulacao, indexCaminho, energiaEstimada, energiaDisponivel, farmacias, isDrone);
        } else {
            adicionarParagensCarregamento(veiculo, massaEncomendas, caminho, listaSimulacao, indexCaminho + 1, energiaEstimada, energiaDisponivel, farmacias, isDrone);
        }
    }

    /**
     * energia do caminho direto
     * @param veiculo veiculo selecionado
     * @param massaEncomendas massa das encomendas selecionadas
     * @param caminho caminho base
     * @param farmacias mapa com as farmacias
     * @param isDrone variavel de controlo que determina se o caminho tem que ser gerado para um drone ou uma scooter
     * @return energia do caminho direto
     */
    protected double energiaMinimiaCaminho(Veiculo veiculo, double massaEncomendas, List<Morada> caminho, Map<Integer, Morada> farmacias, boolean isDrone) {
        List<Morada> caminhoMinimo = caminho.subList(0, 1);
        Morada farmaciaMaisPerto = getFarmaciaMaisPertoConsumo(caminho.get(1), farmacias, isDrone);
        LinkedList<Morada> caminhoAux = new LinkedList<>();
        if (isDrone) {
            GraphAlgorithms.shortestPath(AplicacaoPOT.getInstance().getMapaAereoEstimativas(), caminho.get(1), farmaciaMaisPerto, caminhoAux);
            caminhoMinimo.addAll(caminhoAux);
            return new EstimarConsumoDroneTask().estimarConsumoPercurso((Drone) veiculo, massaEncomendas, caminhoMinimo, false);
        }
        GraphAlgorithms.shortestPath(AplicacaoPOT.getInstance().getMapaTerrestreEstimativas(), caminho.get(1), farmaciaMaisPerto, caminhoAux);
        caminhoMinimo.addAll(caminhoAux);
        return new EstimarConsumoScooterTask().calculoEstimativaConsumo(true, veiculo.getMassa(), massaEncomendas, caminhoMinimo, false);
    }

    /**
     * farmacia mais proxima de uma determinada morada
     * @param morada morada selecionada
     * @param farmacias mapa de farmacias
     * @param isDrone variavel de controlo que determina se o caminho tem que ser gerado para um drone ou uma scooter
     * @return farmacia mais proxima de uma determinada morada
     */
    protected Morada getFarmaciaMaisPertoConsumo(Morada morada, Map<Integer, Morada> farmacias, boolean isDrone) {
        Graph<Morada, Double> g2;
        if (isDrone) {
            g2 = AplicacaoPOT.getInstance().getMapaAereoEstimativas();
        } else {
            g2 = AplicacaoPOT.getInstance().getMapaTerrestreEstimativas();
        }
        double menor = Double.POSITIVE_INFINITY;
        Morada maisPerto = null;
        for (Morada value : farmacias.values()) {
            double menorAux = GraphAlgorithms.shortestPath(g2, morada, value, new LinkedList<>());
            if (menor > menorAux) {
                menor = menorAux;
                maisPerto = value;
            }
        }
        return maisPerto;
    }

    /**
     * determina se tem energia suficiente para ir à farmacia
     * @param veiculo veiculo selecionado
     * @param massaEntrega massa das encomendas selecionadas
     * @param caminho caminho base
     * @param indexCaminho index que representa a posição onde se encontra o veiculo no percurso
     * @param farmMorada farmacia da morada
     * @param energiaDisponivel energia disponivel
     * @param isDrone variavel de controlo que determina se o caminho tem que ser gerado para um drone ou uma scooter
     * @return boolean informativo se o veiculo tem energia para ir à farmacia
     */
    protected boolean temEnergiaParaIrFarmacia(Veiculo veiculo, double massaEntrega, List<Morada> caminho, int indexCaminho, Morada farmMorada, double energiaDisponivel, boolean isDrone) {
        double estimativa;
        List<Morada> caminhoList = new ArrayList<>(caminho.subList(0, indexCaminho - 1));
        LinkedList<Morada> aux = new LinkedList<>();
        GraphAlgorithms.shortestPath(g, caminho.get(indexCaminho - 1), farmMorada, aux);
        caminhoList.addAll(aux);
        if (isDrone) {
            estimativa = new EstimarConsumoDroneTask().estimarConsumoPercurso((Drone) veiculo, massaEntrega, caminhoList, false);
        } else {
            estimativa = new EstimarConsumoScooterTask().calculoEstimativaConsumo(true, veiculo.getMassa(), massaEntrega, caminhoList, false);
        }
        return energiaDisponivel > estimativa;
    }
}
