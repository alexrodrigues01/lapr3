package lapr.project.controller;

import com.google.zxing.WriterException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lapr.project.data.MoradaBD;
import lapr.project.model.Drone;
import lapr.project.model.Morada;
import lapr.project.model.Scooter;
import lapr.project.model.Veiculo;
import lapr.project.model.graph.Graph;
import lapr.project.utils.Constantes;
import lapr.project.utils.Pair;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CaminhoTaskTest {

    static Morada morada = new Morada("Farmacia", 0, 0, 10);
    static Morada morada1 = new Morada("1.0", 1, 20, 10);
    static Morada morada2 = new Morada("2.0", 0, 10, 10);
    static Morada morada3 = new Morada("3.0", 7, 0, 10);
    static Morada morada4 = new Morada("4.0", 0.5, 0.5, 10);
    static Morada moradaFarmacia1 = new Morada("Farm 1", 0.5, 0.55, 10);
    static Morada moradaFarmacia2 = new Morada("Farm 2", 0, 1, 10000);

    @Mock
    private static MoradaBD moradaBD = mock(MoradaBD.class);

    public CaminhoTaskTest() {
    }

    @BeforeEach
    public void setUpClass() {
        Constantes.setVentoxDrone(1);
        Constantes.setVentoyDrone(2);
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);
    }

    /**
     * Test of caminho method, of class CaminhoTask.
     */
    @Test
    public void testCaminho() throws Exception {
        System.out.println("caminho");

        Graph<Morada, Double> g = new Graph<>(true);
        g.insertVertex(morada);
        g.insertVertex(morada1);
        g.insertVertex(morada2);
        g.insertVertex(morada3);
        g.insertVertex(moradaFarmacia1);
        g.insertVertex(moradaFarmacia2);
        g.insertEdge(morada, morada3, 0.0, 1);
        g.insertEdge(morada3, moradaFarmacia1, 0.0, 1);
        g.insertEdge(moradaFarmacia1, morada2, 0.0, 1);
        g.insertEdge(morada2, morada1, 0.0, 1);
        g.insertEdge(morada1, morada, 0.0, 1);

        g.insertEdge(morada, morada1, 0.0, 3);
        g.insertEdge(morada, morada2, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada1, morada2, 0.0, 3);
        g.insertEdge(morada1, morada3, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada3, morada, 0.0, 3);
        g.insertEdge(morada3, morada1, 0.0, 3);
        g.insertEdge(morada3, morada2, 0.0, 3);
        g.insertEdge(morada3, morada3, 0.0, 3);
        g.insertEdge(morada3, moradaFarmacia2, 0.0, 3);

        AplicacaoPOT.getInstance().setMapaAereoEstimativas(g);
        boolean estimativas = true;
        Morada moradaFarmacia = morada;
        List<Morada> moradasEncomendas = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));
        double massaEncomendas = 3.0;
        Veiculo veiculo = new Drone(1, 100, 1000000, 3, 3, 3, 3, 3, 20, 0.2, 3, 0.5, 3);
        boolean isDrone = true;
        CaminhoTask instance = new CaminhoTask();
        double[] arrayDouble = {1.2891976337643588E8, 294997.54407336126, 5899950.881467225};
        Pair<List<Morada>, double[]> expResult = new Pair<>(new ArrayList<>(Arrays.asList(morada, morada3, moradaFarmacia1, morada2, morada1, morada)), arrayDouble);

        Map<Integer, Morada> farmacias = new HashMap<>();

        farmacias.put(1, moradaFarmacia1);
        farmacias.put(2, moradaFarmacia2);
        when(moradaBD.getMoradasFarmacia()).thenReturn(farmacias);
        Pair<List<Morada>, double[]> result = instance.caminho(estimativas, moradaFarmacia, moradasEncomendas, massaEncomendas, veiculo, isDrone, moradaBD);
        assertEquals(expResult.getKey(), result.getKey());
        assertArrayEquals(expResult.getValue(), result.getValue());
    }

    /**
     * Test of caminho method, of class CaminhoTask.
     */
    @Test
    public void testCaminho1() throws Exception {
        System.out.println("caminho");

        Graph<Morada, Double> g = new Graph<>(true);
        g.insertVertex(morada);
        g.insertVertex(morada1);
        g.insertVertex(morada2);
        g.insertVertex(morada3);
        g.insertVertex(moradaFarmacia1);
        g.insertVertex(moradaFarmacia2);
        g.insertEdge(morada, morada3, 0.0, 1);
        g.insertEdge(morada3, moradaFarmacia1, 0.0, 1);
        g.insertEdge(moradaFarmacia1, morada2, 0.0, 1);
        g.insertEdge(morada2, morada1, 0.0, 1);
        g.insertEdge(morada1, morada, 0.0, 1);

        g.insertEdge(morada, morada1, 0.0, 3);
        g.insertEdge(morada, morada2, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada1, morada2, 0.0, 3);
        g.insertEdge(morada1, morada3, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada3, morada, 0.0, 3);
        g.insertEdge(morada3, morada1, 0.0, 3);
        g.insertEdge(morada3, morada2, 0.0, 3);
        g.insertEdge(morada3, morada3, 0.0, 3);
        g.insertEdge(morada3, moradaFarmacia2, 0.0, 3);

        AplicacaoPOT.getInstance().setMapaTerrestreEstimativas(g);
        boolean estimativas = true;
        Morada moradaFarmacia = morada;
        List<Morada> moradasEncomendas = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));
        double massaEncomendas = 3.0;
        Veiculo veiculo = new Scooter(1, 20, 100000, 3, 0, 0, 20, 0);
        boolean isDrone = false;
        CaminhoTask instance = new CaminhoTask();
        double[] arrayDouble = {8.786200357095098E7, 196665.02938224084, 5899950.881467225};
        Pair<List<Morada>, double[]> expResult = new Pair<>(new ArrayList<>(Arrays.asList(morada, morada3, moradaFarmacia1, morada2, morada1, morada)), arrayDouble);

        Map<Integer, Morada> farmacias = new HashMap<>();

        farmacias.put(1, moradaFarmacia1);
        farmacias.put(2, moradaFarmacia2);
        when(moradaBD.getMoradasFarmacia()).thenReturn(farmacias);
        Pair<List<Morada>, double[]> result = instance.caminho(estimativas, moradaFarmacia, moradasEncomendas, massaEncomendas, veiculo, isDrone, moradaBD);
        assertEquals(expResult.getKey(), result.getKey());
        assertArrayEquals(expResult.getValue(), result.getValue());
    }

    /**
     * Test of caminho method, of class CaminhoTask.
     */
    @Test
    public void testCaminhoException1() throws Exception {
        System.out.println("caminhoException1");

        Graph<Morada, Double> g = new Graph<>(true);
        g.insertVertex(morada);
        g.insertVertex(morada1);
        g.insertVertex(morada2);
        g.insertVertex(morada3);
        g.insertVertex(moradaFarmacia1);
        g.insertVertex(moradaFarmacia2);
        g.insertEdge(morada, morada3, 0.0, 1);
        g.insertEdge(morada3, moradaFarmacia1, 0.0, 1);
        g.insertEdge(moradaFarmacia1, morada2, 0.0, 1);
        g.insertEdge(morada2, morada1, 0.0, 1);
        g.insertEdge(morada1, morada, 0.0, 1);

        g.insertEdge(morada, morada1, 0.0, 3);
        g.insertEdge(morada, morada2, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada1, morada2, 0.0, 3);
        g.insertEdge(morada1, morada3, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada3, morada, 0.0, 3);
        g.insertEdge(morada3, morada1, 0.0, 3);
        g.insertEdge(morada3, morada2, 0.0, 3);
        g.insertEdge(morada3, morada3, 0.0, 3);
        g.insertEdge(morada3, moradaFarmacia2, 0.0, 3);

        AplicacaoPOT.getInstance().setMapaAereoEstimativas(g);
        boolean estimativas = true;
        Morada moradaFarmacia = morada;
        List<Morada> moradasEncomendas = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));
        double massaEncomendas = 3.0;
        Veiculo veiculo = new Drone(1, 100, 10, 3, 3, 3, 3, 3, 20, 0.2, 3, 0.5, 3);
        boolean isDrone = true;
        CaminhoTask instance = new CaminhoTask();

        Map<Integer, Morada> farmacias = new HashMap<>();

        farmacias.put(1, moradaFarmacia1);
        farmacias.put(2, moradaFarmacia2);
        when(moradaBD.getMoradasFarmacia()).thenReturn(farmacias);
        try {
            instance.caminho(estimativas, moradaFarmacia, moradasEncomendas, massaEncomendas, veiculo, isDrone, moradaBD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("Impossível utilizar este veículo. Não possui carga mínima necessária.", e.getMessage());
        }
    }

    /**
     * Test of gerarCaminho method, of class CaminhoTask.
     */
    @Test
    public void testGerarCaminho() throws Exception { //true-true
        System.out.println("gerarCaminho");
        boolean estimativas = true;
        boolean isDrone = true;
        Graph<Morada, Double> g = new Graph<>(true);
        g.insertVertex(morada);
        g.insertVertex(morada1);
        g.insertVertex(morada2);
        g.insertVertex(morada3);
        g.insertVertex(moradaFarmacia1);
        g.insertVertex(moradaFarmacia2);
        g.insertEdge(morada, morada3, 0.0, 1);
        g.insertEdge(morada3, morada4, 0.0, 0.8);
        g.insertEdge(morada3, moradaFarmacia1, 0.0, 1);
        g.insertEdge(morada4, morada2, 0.0, 1);
        g.insertEdge(moradaFarmacia1, morada2, 0.0, 1);
        g.insertEdge(morada2, morada1, 0.0, 1);
        g.insertEdge(morada1, morada, 0.0, 1);

        g.insertEdge(morada, morada1, 0.0, 3);
        g.insertEdge(morada, morada2, 0.0, 3);
        g.insertEdge(morada, morada4, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada1, morada2, 0.0, 3);
        g.insertEdge(morada1, morada3, 0.0, 3);
        g.insertEdge(morada1, morada4, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada3, morada, 0.0, 3);
        g.insertEdge(morada3, morada1, 0.0, 3);
        g.insertEdge(morada3, morada2, 0.0, 3);
        g.insertEdge(morada3, moradaFarmacia2, 0.0, 3);

        AplicacaoPOT.getInstance().setMapaAereoEstimativas(g);
        Morada moradaFarmacia = morada;
        List<Morada> moradasEncomendas = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));
        CaminhoTask instance = new CaminhoTask();
        List<Morada> expResult = new ArrayList<>(Arrays.asList(morada, morada3, morada4, morada2, morada1, morada));
        List<Morada> result = instance.gerarCaminho(estimativas, moradaFarmacia, moradasEncomendas, isDrone);
        assertEquals(expResult, result);

        System.out.println("teste gerar caminho fail altitude");

        moradasEncomendas = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));

        List<Morada> moradasEncomendas2 = new ArrayList<>();
        moradasEncomendas2.addAll(moradasEncomendas);
        moradasEncomendas2.add(new Morada("teste", 10, 10, 160));

        Exception exception = new Exception("As moradas a percorrer têm altitudes superiores a 150 metros.");
        try {
            instance.gerarCaminho(estimativas, moradaFarmacia, moradasEncomendas2, isDrone);
        } catch (Exception e) {
            assertEquals(exception.getMessage(), e.getMessage());
        }

        System.out.println("teste farmacia nao esta no grafo");

        exception = new Exception("A morada da farmacia nao esta no mapa.");
        try {
            instance.gerarCaminho(estimativas, new Morada("teste", 40, 40, 40), moradasEncomendas, isDrone);
        } catch (Exception e) {
            assertEquals(exception.getMessage(), e.getMessage());
        }

        System.out.println("teste morada entrega nao esta no grafo");

        moradasEncomendas2 = new ArrayList<>();
        moradasEncomendas2.addAll(moradasEncomendas);
        moradasEncomendas2.add(new Morada("teste", 10, 10, 150));

        exception = new Exception("Uma das moradas de entrega nao se encontra no mapa.");
        try {
            instance.gerarCaminho(estimativas, moradaFarmacia, moradasEncomendas2, isDrone);
        } catch (Exception e) {
            assertEquals(exception.getMessage(), e.getMessage());
        }

        System.out.println("teste morada entrega nao da para alcançar");

        Morada m = new Morada("teste", 10, 10, 150);

        Graph<Morada, Double> testeG = g.clone();
        testeG.insertVertex(m);

        AplicacaoPOT.getInstance().setMapaAereoEstimativas(testeG);

        moradasEncomendas2 = new ArrayList<>();
        moradasEncomendas2.addAll(moradasEncomendas);
        moradasEncomendas2.add(m);

        exception = new Exception("Nao e possivel aceder a uma das moradas desde a farmacia.");
        try {
            instance.gerarCaminho(estimativas, moradaFarmacia, moradasEncomendas2, isDrone);
        } catch (Exception e) {
            assertEquals(exception.getMessage(), e.getMessage());
        }

        //reset
        AplicacaoPOT.getInstance().setMapaAereoEstimativas(g);

        AplicacaoPOT.getInstance().setMapaAereoDistancias(g);

        estimativas = false;
        moradasEncomendas = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));

        result = instance.gerarCaminho(estimativas, moradaFarmacia, new ArrayList<>(moradasEncomendas), isDrone);
        assertEquals(expResult, result);

        AplicacaoPOT.getInstance().setMapaTerrestreDistancias(g);

        moradasEncomendas = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));
        isDrone = false;

        result = instance.gerarCaminho(estimativas, moradaFarmacia, new ArrayList<>(moradasEncomendas), isDrone);
        assertEquals(expResult, result);

        AplicacaoPOT.getInstance().setMapaTerrestreEstimativas(g);

        moradasEncomendas = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));
        estimativas = true;

        result = instance.gerarCaminho(estimativas, moradaFarmacia, new ArrayList<>(moradasEncomendas), isDrone);
        assertEquals(expResult, result);

        Graph<Morada, Double> g2 = new Graph<>(true);
        g2.insertVertex(morada);
        g2.insertVertex(morada1);
        g2.insertVertex(morada2);
        g2.insertVertex(morada3);
        g2.insertVertex(moradaFarmacia1);
        g2.insertVertex(moradaFarmacia2);
        g2.insertEdge(morada, morada3, 0.0, 1);
        g2.insertEdge(moradaFarmacia1, morada2, 0.0, 1);
        g2.insertEdge(morada, morada1, 0.0, 3);
        g2.insertEdge(morada, morada2, 0.0, 3);
        g2.insertEdge(morada, morada4, 0.0, 3);
        g2.insertEdge(morada, moradaFarmacia1, 0.0, 3);
        g2.insertEdge(morada, moradaFarmacia2, 0.0, 3);

        AplicacaoPOT.getInstance().setMapaTerrestreEstimativas(g2);

        moradasEncomendas = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));

        exception = new Exception("Nao e possivel este caminho.");
        try {
            instance.gerarCaminho(estimativas, moradaFarmacia, new ArrayList<>(moradasEncomendas), isDrone);
        } catch (Exception e) {
            assertEquals(exception.getMessage(), e.getMessage());
        }
    }

    /**
     * Test of adicionarParagensCarregamento method, of class CaminhoTask.
     */
    @Test
    public void testAdicionarParagensCarregamento() throws Exception {
        System.out.println("adicionarParagensCarregamento");

        Graph<Morada, Double> g = new Graph<>(true);
        g.insertVertex(morada);
        g.insertVertex(morada1);
        g.insertVertex(morada2);
        g.insertVertex(morada3);
        g.insertVertex(moradaFarmacia1);
        g.insertVertex(moradaFarmacia2);
        Morada recarregar = new Morada("farmacia Recarregar", 0, 10.0001, 10);
        g.insertVertex(recarregar);
        g.insertEdge(morada, morada3, 0.0, 1);
        g.insertEdge(morada3, morada4, 0.0, 0.8);
        g.insertEdge(morada3, moradaFarmacia1, 0.0, 1);
        g.insertEdge(morada4, morada2, 0.0, 1);
        g.insertEdge(moradaFarmacia1, morada2, 0.0, 1);
        g.insertEdge(morada2, morada1, 0.0, 1);
        g.insertEdge(morada2, morada3, 0.0, 1);
        g.insertEdge(morada1, morada, 0.0, 1);

        g.insertEdge(morada, morada1, 0.0, 3);
        g.insertEdge(morada, morada2, 0.0, 3);
        g.insertEdge(morada, morada4, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada1, morada2, 0.0, 3);
        g.insertEdge(morada1, morada3, 0.0, 3);
        g.insertEdge(morada1, morada4, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada3, morada, 0.0, 3);
        g.insertEdge(morada3, morada1, 0.0, 3);
        g.insertEdge(morada3, morada2, 0.0, 3);
        g.insertEdge(morada3, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada2, recarregar, 0.0, 1);
        g.insertEdge(recarregar, morada3, 0.0, 3);

        Map<Integer, Morada> farmacias = new HashMap<>();

        farmacias.put(1, moradaFarmacia1);
        farmacias.put(2, moradaFarmacia2);
        farmacias.put(3, recarregar);

        AplicacaoPOT.getInstance().setMapaAereoEstimativas(g);

        Veiculo veiculo = new Drone(1, 20, 100000, 3, 3, 3, 3, 3, 20, 0.2, 3, 0.5, 3);
        double massaEncomenda = 5;
        List<Morada> caminho = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));
        List<Morada> caminhoBackUp = new ArrayList<>(caminho);
        List<Morada> simulacao = caminho.subList(0, 2);
        List<Morada> simulacaoBackUp = new ArrayList<>(simulacao);
        int index = 2;
        double energiaEstimada = 100000;
        double energiaDisponivel = 90000;
        boolean flag = false;
        boolean isDrone = true;

        CaminhoTask instance = new CaminhoTask();

        instance.gerarCaminho(
                true, morada, new ArrayList<>(caminho), isDrone);

        Exception exception = new Exception("Não encontrou um posto para carregar.");

        try {
            instance.adicionarParagensCarregamento(veiculo, massaEncomenda, caminho, simulacao, index, energiaEstimada, energiaDisponivel, farmacias, isDrone);
        } catch (Exception e) {
            assertEquals(exception.getMessage(), e.getMessage());
        }
        caminho = new ArrayList<>(caminhoBackUp);
        simulacao = new ArrayList<>(simulacaoBackUp);

        energiaEstimada = new EstimarConsumoDroneTask().estimarConsumoPercurso((Drone) veiculo, 5, caminho, false);
        energiaDisponivel = veiculo.getCapacidadeBateria() * veiculo.getCarga() / 100 * 3600;

        System.out.println(energiaEstimada);

        System.out.println(energiaDisponivel);

        instance.adicionarParagensCarregamento(veiculo, massaEncomenda, caminho, simulacao, index, energiaEstimada, energiaDisponivel, farmacias, isDrone);
        List<Morada> expResultList = new ArrayList<>(Arrays.asList(morada1, morada2, recarregar, morada3));

        assertEquals(expResultList, new ArrayList<>(caminho));
    }

    /**
     * Test of energiaMinimiaCaminho method, of class CaminhoTask.
     */
    @Test
    public void testEnergiaMinimiaCaminho() throws IOException, WriterException {
        System.out.println("energiaMinimiaCaminho");
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);
        Veiculo veiculo = new Scooter(1, 100, 10000, 3, 10, 10, 60, 30);
        double massaEncomendas = 20.0;

        Graph<Morada, Double> g = new Graph<>(true);

        g.insertVertex(morada);
        g.insertVertex(morada1);
        g.insertVertex(morada2);
        g.insertVertex(morada3);
        g.insertVertex(moradaFarmacia1);
        g.insertVertex(moradaFarmacia2);
        g.insertEdge(morada, morada1, 0.004, 1);
        g.insertEdge(morada, morada2, 0.004, 1);
        g.insertEdge(morada, morada3, 0.004, 1);
        g.insertEdge(morada3, morada1, 0.004, 1);
        g.insertEdge(morada3, morada2, 0.004, 2);
        g.insertEdge(morada3, moradaFarmacia1, 0.004, 1);
        g.insertEdge(morada1, moradaFarmacia1, 0.004, 1);
        g.insertEdge(moradaFarmacia1, moradaFarmacia2, 0.004, 1);

        AplicacaoPOT.getInstance().setMapaTerrestreEstimativas(g);
        List<Morada> caminho = new ArrayList<>(Arrays.asList(morada, morada3, morada4, morada2, morada));
        boolean isDrone = false;
        CaminhoTask instance = new CaminhoTask();
        double expResult = new EstimarConsumoScooterTask().calculoEstimativaConsumo(true, 60, 20, new ArrayList<>(Arrays.asList(morada, morada3, moradaFarmacia1)), false);

        Map<Integer, Morada> farmacias = new HashMap<>();
        farmacias.put(1, moradaFarmacia1);
        farmacias.put(2, moradaFarmacia2);

        double result = instance.energiaMinimiaCaminho(veiculo, massaEncomendas, caminho, farmacias, isDrone);
        assertEquals(expResult, result, 0.0);

        veiculo = new Drone(1, 100, 10000, 3, 3, 3, 3, 3, 20, 0.2, 3, 0.5, 3);
        isDrone = true;
        AplicacaoPOT.getInstance().setMapaAereoEstimativas(g);

        massaEncomendas = 3;
        expResult = new EstimarConsumoDroneTask().estimarConsumoPercurso((Drone) veiculo, massaEncomendas, new ArrayList<>(Arrays.asList(morada, morada3, moradaFarmacia1)), false);
        result = instance.energiaMinimiaCaminho(veiculo, massaEncomendas, caminho, farmacias, isDrone);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getFarmaciaMaisPertoConsumo method, of class CaminhoTask.
     */
    @Test
    public void testGetFarmaciaMaisPertoConsumo() {
        System.out.println("getFarmaciaMaisPertoConsumo");

        Graph<Morada, Double> g = new Graph<>(true);

        g.insertVertex(morada);
        g.insertVertex(morada1);
        g.insertVertex(morada2);
        g.insertVertex(morada3);
        g.insertVertex(moradaFarmacia1);
        g.insertVertex(moradaFarmacia2);
        g.insertEdge(morada, morada1, 0.004, 0.9);
        g.insertEdge(morada, morada2, 0.004, 2);
        g.insertEdge(morada, morada3, 0.004, 1.2);
        g.insertEdge(morada, moradaFarmacia1, 0.004, 1);
        g.insertEdge(morada, moradaFarmacia2, 0.004, 7);

        AplicacaoPOT.getInstance().setMapaTerrestreEstimativas(g);

        Map<Integer, Morada> farmacias = new HashMap<>();
        farmacias.put(1, moradaFarmacia1);
        farmacias.put(2, moradaFarmacia2);

        farmacias.put(3, morada3);
        boolean isDrone = false;
        CaminhoTask instance = new CaminhoTask();
        Morada expResult = moradaFarmacia1;
        Morada result = instance.getFarmaciaMaisPertoConsumo(morada, farmacias, isDrone);
        assertEquals(expResult, result);
        farmacias.replace(1, morada1);
        farmacias.replace(2, morada2);
        expResult = morada1;
        result = instance.getFarmaciaMaisPertoConsumo(morada, farmacias, isDrone);
        assertEquals(expResult, result);
    }

    /**
     * Test of caminho method, of class CaminhoTask.
     */
    @Test
    public void testCaminho2() throws Exception {
        System.out.println("caminho");

        Graph<Morada, Double> g = new Graph<>(true);

        g.insertVertex(morada);
        g.insertVertex(morada1);
        g.insertVertex(morada2);
        g.insertVertex(morada3);
        g.insertVertex(morada4);
        g.insertVertex(moradaFarmacia1);
        g.insertVertex(moradaFarmacia2);
        g.insertEdge(morada, morada3, 0.0, 1);
        g.insertEdge(morada3, morada4, 0.0, 1);
        g.insertEdge(morada3, moradaFarmacia1, 0.0, 2);
        g.insertEdge(morada4, morada2, 0.0, 1);
        g.insertEdge(moradaFarmacia1, morada2, 0.0, 1);
        g.insertEdge(morada2, morada1, 0.0, 1);
        g.insertEdge(morada1, morada, 0.0, 1);

        g.insertEdge(morada, morada1, 0.0, 3);
        g.insertEdge(morada, morada2, 0.0, 3);
        g.insertEdge(morada, morada4, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada1, morada2, 0.0, 3);
        g.insertEdge(morada1, morada3, 0.0, 3);
        g.insertEdge(morada1, morada4, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada4, moradaFarmacia1, 0.0, 1);
        g.insertEdge(morada1, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada3, morada, 0.0, 3);
        g.insertEdge(morada3, morada1, 0.0, 3);
        g.insertEdge(morada3, morada2, 0.0, 3);
        g.insertEdge(morada3, morada3, 0.0, 3);
        g.insertEdge(morada3, moradaFarmacia2, 0.0, 3);

        AplicacaoPOT.getInstance().setMapaTerrestreEstimativas(g);
        boolean estimativas = true;
        Morada moradaFarmacia = morada;
        List<Morada> moradasEncomendas = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));
        double massaEncomendas = 3.0;
        Veiculo veiculo = new Scooter(1, 20, 1.7814198812417465E4 * 4, 3, 10, 10, 50, 10);
        boolean isDrone = false;
        CaminhoTask instance = new CaminhoTask();
        double[] arrayDouble = new double[3];
        Map<Integer, Morada> farmacias = new HashMap<>();

        farmacias.put(1, moradaFarmacia1);
        farmacias.put(2, moradaFarmacia2);
        when(moradaBD.getMoradasFarmacia()).thenReturn(farmacias);
        Pair<List<Morada>, double[]> expResult = new Pair<>(new ArrayList<>(Arrays.asList(morada, morada3, morada4, moradaFarmacia1, morada2, morada1, morada)), arrayDouble);
        Pair<List<Morada>, double[]> result = instance.caminho(estimativas, moradaFarmacia, moradasEncomendas, massaEncomendas, veiculo, isDrone, moradaBD);
        System.out.println("expResult" + expResult.getKey());
        System.out.println("result" + result.getKey());

        double output = instance.energiaMinimiaCaminho(veiculo, massaEncomendas, new ArrayList<>(Arrays.asList(morada, morada3, morada4, morada2, morada1, morada)), farmacias, isDrone);
        assertEquals(expResult.getKey(), result.getKey());
    }

    /**
     * Test of caminho method, of class CaminhoTask.
     */
    @Test
    public void test() throws Exception {
        System.out.println("caminho");

        System.out.println("vento-drone");
        AplicacaoPOT.getInstance();
        System.out.println(Constantes.ventoxDrone);
        System.out.println(Constantes.ventoyDrone);
        System.out.println("vento-scooter");
        System.out.println(Constantes.ventoxScooter);
        System.out.println(Constantes.ventoyScooter);
        System.out.println(AplicacaoPOT.getInstance().getMapaTerrestreDistancias());
    }

    /**
     * Test of caminho method, of class CaminhoTask.
     */
    @Test
    public void testCaminho3() throws Exception {
        System.out.println("caminho");

        Graph<Morada, Double> g = new Graph<>(true);
        g.insertVertex(morada);
        g.insertVertex(morada1);
        g.insertVertex(morada2);
        g.insertVertex(morada3);
        g.insertVertex(moradaFarmacia1);
        g.insertVertex(moradaFarmacia2);
        g.insertEdge(morada, morada3, 0.0, 1);
        g.insertEdge(morada3, moradaFarmacia1, 0.0, 1);
        g.insertEdge(moradaFarmacia1, morada2, 0.0, 1);
        g.insertEdge(morada2, morada1, 0.0, 1);
        g.insertEdge(morada1, morada, 0.0, 1);

        g.insertEdge(morada, morada1, 0.0, 3);
        g.insertEdge(morada, morada2, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada1, morada2, 0.0, 3);
        g.insertEdge(morada1, morada3, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada3, morada, 0.0, 3);
        g.insertEdge(morada3, morada1, 0.0, 3);
        g.insertEdge(morada3, morada2, 0.0, 3);
        g.insertEdge(morada3, morada3, 0.0, 3);
        g.insertEdge(morada3, moradaFarmacia2, 0.0, 3);

        AplicacaoPOT.getInstance().setMapaTerrestreEstimativas(g);
        boolean estimativas = true;
        Morada moradaFarmacia = morada;
        List<Morada> moradasEncomendas = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));
        double massaEncomendas = 3.0;
        Veiculo veiculo = new Scooter(1, 100, 1000000, 3, 3, 3, 20, 3);
        boolean isDrone = false;
        CaminhoTask instance = new CaminhoTask();
        double[] arrayDouble = {8.786200357095098E7, 196665.02938224084, 5899950.881467225};
        Pair<List<Morada>, double[]> expResult = new Pair<>(new ArrayList<>(Arrays.asList(morada, morada3, moradaFarmacia1, morada2, morada1, morada)), arrayDouble);

        Map<Integer, Morada> farmacias = new HashMap<>();

        farmacias.put(1, moradaFarmacia1);
        farmacias.put(2, moradaFarmacia2);
        when(moradaBD.getMoradasFarmacia()).thenReturn(farmacias);
        Pair<List<Morada>, double[]> result = instance.caminho(estimativas, moradaFarmacia, moradasEncomendas, massaEncomendas, veiculo, isDrone, moradaBD);
        assertEquals(expResult.getKey(), result.getKey());
        assertArrayEquals(expResult.getValue(), result.getValue());
    }

    /**
     * Test of adicionarParagensCarregamento method, of class CaminhoTask.
     */
    @Test
    public void testAdicionarParagensCarregamento1() throws Exception {
        System.out.println("adicionarParagensCarregamento");

        Veiculo veiculo = new Drone(1, 20, 100000, 3, 3, 3, 3, 3, 20, 0.2, 3, 0.5, 3);
        double massaEncomenda = 5;
        List<Morada> caminho = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));
        int index = 2;
        double energiaEstimada = 100000;
        double energiaDisponivel = 100000;
        boolean isDrone = true;

        CaminhoTask instance = new CaminhoTask();

        instance.adicionarParagensCarregamento(veiculo, massaEncomenda, caminho, new ArrayList<>(), index, energiaEstimada, energiaDisponivel, new HashMap<>(), isDrone);
        List<Morada> expResultList = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));

        assertEquals(expResultList, new ArrayList<>(caminho));
    }

    /**
     * Test of adicionarParagensCarregamento method, of class CaminhoTask.
     */
    @Test
    public void testAdicionarParagensCarregamento2() throws Exception {
        System.out.println("adicionarParagensCaregamento");

        Veiculo veiculo = new Drone(1, 20, 100000, 3, 3, 3, 3, 3, 20, 0.2, 3, 0.5, 3);
        double massaEncomenda = 5;
        List<Morada> caminho = new ArrayList<>(Arrays.asList(morada1, morada2, morada3));
        int index = 3;
        double energiaEstimada = 100000;
        double energiaDisponivel = 100000;
        boolean isDrone = true;

        CaminhoTask instance = new CaminhoTask();

        String exceptionMessage = "Index invalido. Index 2 e o recomendado.";
        try {
            instance.adicionarParagensCarregamento(veiculo, massaEncomenda, caminho, new ArrayList<>(), index, energiaEstimada, energiaDisponivel, new HashMap<>(), isDrone);
        } catch (Exception e) {
            assertEquals(exceptionMessage, e.getMessage());
        }
        index = 4;
        try {
            instance.adicionarParagensCarregamento(veiculo, massaEncomenda, caminho, new ArrayList<>(), index, energiaEstimada, energiaDisponivel, new HashMap<>(), isDrone);
        } catch (Exception e) {
            assertEquals(exceptionMessage, e.getMessage());
        }
    }

    /**
     * Test of adicionarParagensCarregamento method, of class CaminhoTask.
     */
    @Test
    public void testAdicionarParagensCarregamento3() throws Exception {
        System.out.println("adicionarParagensCarregamento");

        Graph<Morada, Double> g = new Graph<>(true);
        g.insertVertex(morada);
        g.insertVertex(morada1);
        g.insertVertex(morada2);
        g.insertVertex(morada3);
        g.insertVertex(moradaFarmacia1);
        g.insertVertex(moradaFarmacia2);
        Morada recarregar = new Morada("farmacia Recarregar", 0, 10.0001, 10);
        g.insertVertex(recarregar);
        g.insertEdge(morada, morada3, 0.0, 1);
        g.insertEdge(morada3, morada4, 0.0, 0.8);
        g.insertEdge(morada3, moradaFarmacia1, 0.0, 1);
        g.insertEdge(morada4, morada2, 0.0, 1);
        g.insertEdge(moradaFarmacia1, morada2, 0.0, 1);
        g.insertEdge(morada2, morada1, 0.0, 1);
        g.insertEdge(morada2, morada3, 0.0, 1);
        g.insertEdge(morada1, morada, 0.0, 1);

        g.insertEdge(morada, morada1, 0.0, 3);
        g.insertEdge(morada, morada2, 0.0, 3);
        g.insertEdge(morada, morada4, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada1, morada2, 0.0, 3);
        g.insertEdge(morada1, morada3, 0.0, 3);
        g.insertEdge(morada1, morada4, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada3, morada, 0.0, 3);
        g.insertEdge(morada3, morada1, 0.0, 3);
        g.insertEdge(morada3, morada2, 0.0, 3);
        g.insertEdge(morada3, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada2, recarregar, 0.0, 1);
        g.insertEdge(recarregar, morada3, 0.0, 3);

        Map<Integer, Morada> farmacias = new HashMap<>();

        farmacias.put(1, moradaFarmacia1);
        farmacias.put(2, moradaFarmacia2);
        farmacias.put(3, recarregar);

        AplicacaoPOT.getInstance().setMapaAereoEstimativas(g);

        Veiculo veiculo = new Drone(1, 20, 100000, 3, 3, 3, 3, 3, 20, 0.2, 3, 0.5, 3);
        double massaEncomenda = 5;
        List<Morada> caminho = new ArrayList<>(Arrays.asList(morada1, morada2, morada3, morada4));
        List<Morada> simulacao = caminho.subList(0, 2);
        int index = 2;
        double energiaEstimada = 100000;
        double energiaDisponivel = 90000;
        boolean isDrone = true;

        CaminhoTask instance = new CaminhoTask();
        instance.gerarCaminho(true, morada, caminho, isDrone);

        energiaEstimada = new EstimarConsumoDroneTask().estimarConsumoPercurso((Drone) veiculo, 5, caminho, false);
        energiaDisponivel = veiculo.getCapacidadeBateria() * veiculo.getCarga() / 100 * 3600;

        System.out.println(energiaEstimada);

        System.out.println(energiaDisponivel);

        instance.adicionarParagensCarregamento(veiculo, massaEncomenda, caminho, simulacao, index, energiaEstimada, energiaDisponivel, farmacias, isDrone);
        List<Morada> expResultList = new ArrayList<>(Arrays.asList(morada1, morada2, morada3, morada4));
        caminho = new ArrayList<>(Arrays.asList(morada1, morada2, morada3, morada4));
        System.out.println(caminho);
        assertEquals(expResultList, new ArrayList<>(caminho));
    }
    
    @Test
    public void testTemEnergiaParaIrFarmacia() throws Exception{
        CaminhoTask instance = new CaminhoTask();
        
                Graph<Morada, Double> g = new Graph<>(true);
        g.insertVertex(morada);
        g.insertVertex(morada1);
        g.insertVertex(morada2);
        g.insertVertex(morada3);
        g.insertVertex(moradaFarmacia1);
        g.insertVertex(moradaFarmacia2);
        Morada recarregar = new Morada("farmacia Recarregar", 0, 10.0001, 10);
        g.insertVertex(recarregar);
        g.insertEdge(morada, morada3, 0.0, 1);
        g.insertEdge(morada3, morada4, 0.0, 0.8);
        g.insertEdge(morada3, moradaFarmacia1, 0.0, 1);
        g.insertEdge(morada4, morada2, 0.0, 1);
        g.insertEdge(moradaFarmacia1, morada2, 0.0, 1);
        g.insertEdge(morada2, morada1, 0.0, 1);
        g.insertEdge(morada2, morada3, 0.0, 1);
        g.insertEdge(morada1, morada, 0.0, 1);

        g.insertEdge(morada, morada1, 0.0, 3);
        g.insertEdge(morada, morada2, 0.0, 3);
        g.insertEdge(morada, morada4, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada1, morada2, 0.0, 3);
        g.insertEdge(morada1, morada3, 0.0, 3);
        g.insertEdge(morada1, morada4, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia1, 0.0, 3);
        g.insertEdge(morada1, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada3, morada, 0.0, 3);
        g.insertEdge(morada3, morada1, 0.0, 3);
        g.insertEdge(morada3, morada2, 0.0, 3);
        g.insertEdge(morada3, moradaFarmacia2, 0.0, 3);
        g.insertEdge(morada2, recarregar, 0.0, 1);
        g.insertEdge(recarregar, morada3, 0.0, 3);

        Map<Integer, Morada> farmacias = new HashMap<>();
        AplicacaoPOT.getInstance().setMapaTerrestreEstimativas(g);
        
        Veiculo veiculo = new Scooter(1, 20, 10000, 3, 10, 10, 20, 10);
        List<Morada> caminho = new ArrayList<>(Arrays.asList(morada1,morada2,morada3));
        instance.gerarCaminho(true, morada, caminho, false);
        boolean result = instance.temEnergiaParaIrFarmacia(veiculo, 10, caminho, 2, morada, 0, false);
        assertFalse(result);
    }
}
