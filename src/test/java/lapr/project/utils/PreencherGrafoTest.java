package lapr.project.utils;

import java.util.ArrayList;
import java.util.List;
import lapr.project.controller.EstimarConsumoDroneTask;
import lapr.project.controller.EstimarConsumoScooterTask;
import lapr.project.data.MoradaBD;
import lapr.project.model.Morada;
import lapr.project.model.graph.Graph;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PreencherGrafoTest {

    @Mock
    private MoradaBD moradaBD;

    @Test
    void lerMoradas() {
        moradaBD = mock(MoradaBD.class);
        PreencherGrafo.setMoradaBD(moradaBD);
        Graph<Morada, Double> expResult = new Graph<>(true);
        List<Morada> moradas = new ArrayList<>();
        expResult.insertVertex(new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513));
        moradas.add(new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513));
        expResult.insertVertex(new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567));
        moradas.add(new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567));
        expResult.insertVertex(new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349));
        moradas.add(new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349));
        expResult.insertVertex(new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895));
        moradas.add(new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895));
        when(moradaBD.addMoradas(moradas)).thenReturn(true);
        Graph<Morada, Double> result = PreencherGrafo.lerMoradas("src/test/resources/moradasTeste.txt");

        assertEquals(expResult, result);

    }

    @Test
    void lerLigacoes() {
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);

        Graph<Morada, Double> auxiliarEstimativas = new Graph<>(true);
        auxiliarEstimativas.insertVertex(new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513));
        auxiliarEstimativas.insertVertex(new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567));
        auxiliarEstimativas.insertVertex(new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349));
        auxiliarEstimativas.insertVertex(new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895));

        Graph<Morada, Double> auxiliarDistancias = new Graph<>(true);
        auxiliarDistancias.insertVertex(new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513));
        auxiliarDistancias.insertVertex(new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567));
        auxiliarDistancias.insertVertex(new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349));
        auxiliarDistancias.insertVertex(new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895));

        Pair<Graph<Morada, Double>, Graph<Morada, Double>> pair = PreencherGrafo.lerLigacoes("src/test/resources/ligacoes_terrestreTeste.txt", auxiliarEstimativas, auxiliarDistancias, false);

        Graph<Morada, Double> mapaEstimativas = pair.getKey();
        Graph<Morada, Double> mapaDistancias = pair.getValue();

        Graph<Morada, Double> expResult = new Graph<>(true);
        Morada morada1 = new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513);
        Morada morada2 = new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567);
        Morada morada3 = new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349);
        Morada morada4 = new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895);

        expResult.insertVertex(morada1);
        expResult.insertVertex(morada2);
        expResult.insertVertex(morada3);
        expResult.insertVertex(morada4);

        expResult.insertEdge(morada1, morada2, 0.004, morada1.distanceFrom(morada2));
        expResult.insertEdge(morada3, morada4, 0.004, morada3.distanceFrom(morada4));

        assertEquals(expResult, mapaDistancias);
        assertEquals(expResult.edges().toString(), mapaDistancias.edges().toString());

        expResult.removeEdge(morada1, morada2);
        expResult.removeEdge(morada3, morada4);
        expResult.insertEdge(morada1, morada2, 0.004, new EstimarConsumoScooterTask().estimativaSimples(morada1, morada2, 0.004));
        expResult.insertEdge(morada3, morada4, 0.004, new EstimarConsumoScooterTask().estimativaSimples(morada3, morada4, 0.004));

        assertEquals(expResult, mapaEstimativas);
        assertEquals(expResult.edges().toString(), mapaEstimativas.edges().toString());
    }

    @Test
    void lerLigacoesDrone() {
        Constantes.setVentoxDrone(1);
        Constantes.setVentoyDrone(2);

        Graph<Morada, Double> auxiliarEstimativas = new Graph<>(true);
        auxiliarEstimativas.insertVertex(new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513));
        auxiliarEstimativas.insertVertex(new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567));
        auxiliarEstimativas.insertVertex(new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349));
        auxiliarEstimativas.insertVertex(new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895));

        Graph<Morada, Double> auxiliarDistancias = new Graph<>(true);
        auxiliarDistancias.insertVertex(new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513));
        auxiliarDistancias.insertVertex(new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567));
        auxiliarDistancias.insertVertex(new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349));
        auxiliarDistancias.insertVertex(new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895));

        Pair<Graph<Morada, Double>, Graph<Morada, Double>> pair = PreencherGrafo.lerLigacoes("src/test/resources/ligacoes_aereoTeste.txt", auxiliarEstimativas, auxiliarDistancias, true);

        Graph<Morada, Double> mapaEstimativas = pair.getKey();
        Graph<Morada, Double> mapaDistancias = pair.getValue();

        Graph<Morada, Double> expResult = new Graph<>(true);
        Morada morada1 = new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513);
        Morada morada2 = new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567);
        Morada morada3 = new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349);
        Morada morada4 = new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895);

        expResult.insertVertex(morada1);
        expResult.insertVertex(morada2);
        expResult.insertVertex(morada3);
        expResult.insertVertex(morada4);

        expResult.insertEdge(morada1, morada2, 0.0, morada1.distanceFrom(morada2));
        expResult.insertEdge(morada3, morada4, 0.0, morada3.distanceFrom(morada4));

        assertEquals(expResult, mapaDistancias);
        assertEquals(expResult.edges().toString(), mapaDistancias.edges().toString());

        expResult.removeEdge(morada1, morada2);
        expResult.removeEdge(morada3, morada4);
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);
        expResult.insertEdge(morada1, morada2, 0.0, new EstimarConsumoDroneTask().estimarConsumoSimples(morada1, morada2));
        expResult.insertEdge(morada3, morada4, 0.0, new EstimarConsumoDroneTask().estimarConsumoSimples(morada3, morada4));

        assertEquals(expResult, mapaEstimativas);
        assertEquals(expResult.edges().toString(), mapaEstimativas.edges().toString());
    }

    @Test
    void lerLigacoes2() {
        Constantes.setVentoxDrone(1);
        Constantes.setVentoyDrone(2);

        Graph<Morada, Double> auxiliar = new Graph<>(true);
        auxiliar.insertVertex(new Morada("Rua44,BuenosAires", -34.6131500, -58.3772300, 43.513));
        auxiliar.insertVertex(new Morada("Rua23,Lapaz", -16.5000000, -68.1500000, 456.567));
        auxiliar.insertVertex(new Morada("Rua14,Brasilia", -15.7797200, -47.9297200, 1320.349));
        auxiliar.insertVertex(new Morada("Rua9,Santiago", -33.4569400, -70.6482700, 27.895));

        Pair<Graph<Morada, Double>, Graph<Morada, Double>> result = PreencherGrafo.lerLigacoes("src/test/resources/ligacoes_aereoTeste.txt", auxiliar, auxiliar.clone(), true);

        assertNull(result);
    }

    @Test
    void lerLigacoesFail() {
        Constantes.setVentoxDrone(1);
        Constantes.setVentoyDrone(2);

        Graph<Morada, Double> auxiliar = new Graph<>(true);
        auxiliar.insertVertex(new Morada("Rua44,BuenosAires", -34.6131500, -58.3772300, 43.513));
        auxiliar.insertVertex(new Morada("Rua23,Lapaz", -16.5000000, -68.1500000, 456.567));
        auxiliar.insertVertex(new Morada("Rua14,Brasilia", -15.7797200, -47.9297200, 1320.349));
        auxiliar.insertVertex(new Morada("Rua9,Santiago", -33.4569400, -70.6482700, 27.895));

        assertNull(PreencherGrafo.lerLigacoes("src/main/resources/pessego.txt", auxiliar, auxiliar.clone(), true));

    }

    @Test
    void lerMoradasFail() {
        moradaBD = mock(MoradaBD.class);
        PreencherGrafo.setMoradaBD(moradaBD);
        Graph<Morada, String> expResult = new Graph<>(true);
        List<Morada> moradas = new ArrayList<>();
        expResult.insertVertex(new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513));
        moradas.add(new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513));
        expResult.insertVertex(new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567));
        moradas.add(new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567));
        expResult.insertVertex(new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349));
        moradas.add(new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349));
        expResult.insertVertex(new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895));
        moradas.add(new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895));
        when(moradaBD.addMoradas(moradas)).thenReturn(true);
        assertNull(PreencherGrafo.lerMoradas("src/main/resources/pessego.txt"));

    }

    @Test
    void lerLigacoes3() {
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);

        Graph<Morada, Double> auxiliarEstimativas = new Graph<>(true);
        auxiliarEstimativas.insertVertex(new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513));
        auxiliarEstimativas.insertVertex(new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567));
        auxiliarEstimativas.insertVertex(new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349));
        auxiliarEstimativas.insertVertex(new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895));

        Graph<Morada, Double> auxiliarDistancias = new Graph<>(true);
        auxiliarDistancias.insertVertex(new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513));
        auxiliarDistancias.insertVertex(new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567));
        auxiliarDistancias.insertVertex(new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349));
        auxiliarDistancias.insertVertex(new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895));

        Pair<Graph<Morada, Double>, Graph<Morada, Double>> pair = PreencherGrafo.lerLigacoes("src/test/resources/ligacoes_terrestreTeste.txt", auxiliarEstimativas, auxiliarDistancias, false);

        Graph<Morada, Double> mapaEstimativas = pair.getKey();
        Graph<Morada, Double> mapaDistancias = pair.getValue();

        Graph<Morada, Double> expResult = new Graph<>(true);
        Morada morada1 = new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513);
        Morada morada2 = new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567);
        Morada morada3 = new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349);
        Morada morada4 = new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895);

        expResult.insertVertex(morada1);
        expResult.insertVertex(morada2);
        expResult.insertVertex(morada3);
        expResult.insertVertex(morada4);

        expResult.insertEdge(morada1, morada2, 0.004, morada1.distanceFrom(morada2));
        expResult.insertEdge(morada3, morada4, 0.004, morada3.distanceFrom(morada4));

        assertEquals(expResult, mapaDistancias);
        assertEquals(expResult.edges().toString(), mapaDistancias.edges().toString());

        expResult.removeEdge(morada1, morada2);
        expResult.removeEdge(morada3, morada4);
        expResult.insertEdge(morada1, morada2, 0.004, new EstimarConsumoScooterTask().estimativaSimples(morada1, morada2, 0.004));
        expResult.insertEdge(morada3, morada4, 0.004, new EstimarConsumoScooterTask().estimativaSimples(morada3, morada4, 0.004));

        assertEquals(expResult, mapaEstimativas);
        assertEquals(expResult.edges().toString(), mapaEstimativas.edges().toString());
    }

    @Test
    void lerLigacoes4() {
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);

        Graph<Morada, Double> auxiliar = new Graph<>(true);
        auxiliar.insertVertex(new Morada("Rua44,BuenosAires", -34.6131500, -58.3772300, 43.513));
        auxiliar.insertVertex(new Morada("Rua23,Lapaz", -16.5000000, -68.1500000, 456.567));
        auxiliar.insertVertex(new Morada("Rua14,Brasilia", -15.7797200, -47.9297200, 1320.349));
        auxiliar.insertVertex(new Morada("Rua9,Santiago", -33.4569400, -70.6482700, 27.895));

        Pair<Graph<Morada, Double>, Graph<Morada, Double>> result = PreencherGrafo.lerLigacoes("src/test/resources/ligacoes_terrestreTeste.txt", auxiliar, auxiliar.clone(), false);

        assertNull(result);
    }

    @Test
    void lerLigacoesFail2() {
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);

        Graph<Morada, Double> auxiliar = new Graph<>(true);
        auxiliar.insertVertex(new Morada("Rua44,BuenosAires", -34.6131500, -58.3772300, 43.513));
        auxiliar.insertVertex(new Morada("Rua23,Lapaz", -16.5000000, -68.1500000, 456.567));
        auxiliar.insertVertex(new Morada("Rua14,Brasilia", -15.7797200, -47.9297200, 1320.349));
        auxiliar.insertVertex(new Morada("Rua9,Santiago", -33.4569400, -70.6482700, 27.895));

        assertNull(PreencherGrafo.lerLigacoes("src/main/resources/pessego.txt", auxiliar, auxiliar.clone(), false));

    }

    /**
     * Test of getVento method, of class PreencherGrafo.
     */
    @Test
    public void testGetVento() {
        System.out.println("getVento");
        String filePath = "src/main/resources/pessego.txt";
        boolean expResult = false;
        boolean result = PreencherGrafo.getVento(filePath);
        assertEquals(expResult, result);
    }

    /**
     * Test of getVento method, of class PreencherGrafo.
     */
    @Test
    public void testGetVento1() {
        System.out.println("getVento");
        String filePath = Constantes.FICHEIRO_VENTO_TESTE;
        boolean expResult = true;
        boolean result = PreencherGrafo.getVento(filePath);
        assertEquals(expResult, result);
        assertEquals(57, Constantes.ventoxScooter);
        assertEquals(32, Constantes.ventoyScooter);
        assertEquals(3, Constantes.ventoxDrone);
        assertEquals(4, Constantes.ventoyDrone);
    }
}
