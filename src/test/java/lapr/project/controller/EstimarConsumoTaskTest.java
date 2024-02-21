package lapr.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lapr.project.model.Morada;
import lapr.project.model.graph.Graph;
import lapr.project.utils.Constantes;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author joaoo
 */
public class EstimarConsumoTaskTest {

    /**
     * Test of calcularTempoEntrega method, of class EstimarConsumoTask.
     */
    @Test
    public void testCalcularTempoEntrega() {
        System.out.println("calcularTempoEntrega");
        double distanciaTotal = 120000; // m
        EstimarConsumoScooterTask instance = new EstimarConsumoScooterTask();
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);
        double expResult = 14400;
        double result = instance.calcularTempoEntrega(distanciaTotal);
        assertEquals(expResult, result,0.1);
    }

    /**
     * Test of calculoEstimativaConsumo method, of class EstimarConsumoTask.
     */
    @Test
    public void testCalculoEstimativaConsumoCompleto() {
        System.out.println("calculoEstimativaConsumo");
        double massaScooter = 50;   // (kg)
        double cargaTotal = 10;   // (kg)
        EstimarConsumoScooterTask instance = new EstimarConsumoScooterTask();
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);
        double expResult = 4.8252596695E7;

        Morada m1 = new Morada(1, "Rua 1", 1, 1, 0);
        Morada m2 = new Morada(2, "Rua 2", 11, 1, 150);
        Morada m3 = new Morada(3, "Rua 3", 1, 11, 0);

        List<Morada> moradas = new ArrayList<>(Arrays.asList(m1, m2, m3));

        Graph<Morada, Double> g = new Graph<>(true);
        g.insertVertex(m1);
        g.insertVertex(m2);
        g.insertVertex(m3);
        g.insertEdge(m1, m2, 0.004, 1);
        g.insertEdge(m2, m3, 0.004, 1);
        AplicacaoPOT.getInstance().setMapaTerrestreEstimativas(g);

        double result = instance.calculoEstimativaConsumo(true, massaScooter, cargaTotal, moradas, false);
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result, 0.0000000001E7);

        Graph<Morada, Double> g2 = new Graph<>(true);
        g2.insertVertex(m1);
        g2.insertVertex(m2);
        g2.insertVertex(m3);
        g2.insertEdge(m1, m2, 0.004, 1);
        g2.insertEdge(m2, m3, 0.004, 1);
        AplicacaoPOT.getInstance().setMapaTerrestreDistancias(g2);

        result = instance.calculoEstimativaConsumo(false, massaScooter, cargaTotal, moradas, false);
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result, 0.0000000001E7);
    }

    /**
     * Test of estimativaSimples method, of class EstimarConsumoTask.
     */
    @Test
    public void testEstimativaSimples() {
        System.out.println("estimativaSimples");
        Morada m1 = new Morada(1, "Rua 1", 1, 1, 0);
        Morada m2 = new Morada(2, "Rua 2", 2, 1, 1500);
        EstimarConsumoScooterTask instance = new EstimarConsumoScooterTask();
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);
        double expResult = 1978027.4331067;

        double result = instance.estimativaSimples(m1, m2, 0.004);
        assertEquals(expResult, result, 0.0000001);
    }

    /**
     * Test of estimativaSimples method, of class EstimarConsumoTask.
     */
    @Test
    public void testEstimativaSimples1() {
        System.out.println("estimativaSimples");
        Morada m1 = new Morada(1, "Rua 1", 0, 0, 10);
        Morada m2 = new Morada(2, "Rua 2", 0, 1, 10000);
        EstimarConsumoScooterTask instance = new EstimarConsumoScooterTask();
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);
        double expResult = 9154248.35436048;

        double result = instance.estimativaSimples(m1, m2, 0.004);
        assertEquals(expResult, result, 0.00000001);
    }

    /**
     * Test of estimativaSimples method, of class EstimarConsumoTask.
     */
    @Test
    public void testEstimativaSimples2() {
        System.out.println("estimativaSimples");
        Morada m1 = new Morada(1, "Rua 1", 0, 0, 10);
        Morada m2 = new Morada(2, "Rua 2", 0.5, 0.5, 10);
        EstimarConsumoScooterTask instance = new EstimarConsumoScooterTask();
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);
        double expResult = 656278.2648282;

        double result = instance.estimativaSimples(m1, m2, 0.004);
        assertEquals(expResult, result, 0.0000001);
    }

    /**
     * Test of calcularVelocidadeRelacaoVento method, of class EstimarConsumoTask.
     */
    @Test
    public void testCalcularVelocidadeRelacaoVento() {
        System.out.println("calcularVelocidadeRelacaoVento");
        Morada m1 = new Morada(1, "Rua 1", 1, 1, 0);
        Morada m2 = new Morada(2, "Rua 2", 50, 50, 1500);
        double velocidadeSemVento = Constantes.VELOCIDADE_SCOOTER * (double) 1000 / 3600;
        EstimarConsumoScooterTask instance = new EstimarConsumoScooterTask();
        Constantes.setVentoxScooter(10);
        Constantes.setVentoyScooter(2);
        double expResult = -0.15194804090523;
        double result = instance.calcularVelocidadeRelacaoVento(m1, m2, velocidadeSemVento);
        assertEquals(expResult, result, 0.000000001);
    }

    /**
     * Test of calcularVelocidadeRelacaoVento method, of class EstimarConsumoTask.
     */
    @Test
    public void testCalcularVelocidadeRelacaoVento1() {
        System.out.println("calcularVelocidadeRelacaoVento");
        Morada m1 = new Morada(1, "Rua 1", 0, 0, 0);
        Morada m2 = new Morada(2, "Rua 2", 0, 1, 0);
        double velocidadeSemVento = Constantes.VELOCIDADE_SCOOTER * (double) 1000 / 3600;
        EstimarConsumoScooterTask instance = new EstimarConsumoScooterTask();
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(0);
        double expResult = velocidadeSemVento-1;
        double result = instance.calcularVelocidadeRelacaoVento(m1, m2, velocidadeSemVento);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of calcularVelocidadeRelacaoVento method, of class EstimarConsumoTask.
     */
    @Test
    public void testCalcularVelocidadeRelacaoVento2() {
        System.out.println("calcularVelocidadeRelacaoVento");
        Morada m1 = new Morada(1, "Rua 1", 0, 0, 0);
        Morada m2 = new Morada(2, "Rua 2", 0, 1, 0);
        double velocidadeSemVento = Constantes.VELOCIDADE_SCOOTER * (double) 1000 / 3600;
        EstimarConsumoScooterTask instance = new EstimarConsumoScooterTask();
        Constantes.setVentoxScooter(0);
        Constantes.setVentoyScooter(1);
        double expResult = velocidadeSemVento;
        double result = instance.calcularVelocidadeRelacaoVento(m1, m2, velocidadeSemVento);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    @Test
    void infoScooterTest() {
        double massaScooter = 50;   // (kg)
        double cargaTotal = 10;   // (kg)
        EstimarConsumoScooterTask instance = new EstimarConsumoScooterTask();
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);

        Morada m1 = new Morada(1, "Rua 1", 1, 1, 0);
        Morada m2 = new Morada(2, "Rua 2", 11, 1, 150);
        Morada m3 = new Morada(3, "Rua 3", 1, 11, 0);

        List<Morada> moradas = new ArrayList<>(Arrays.asList(m1, m2, m3));

        Graph<Morada, Double> g = new Graph<>(true);
        g.insertVertex(m1);
        g.insertVertex(m2);
        g.insertVertex(m3);
        g.insertEdge(m1, m2, 0.004, 1);
        g.insertEdge(m2, m3, 0.004, 1);
        AplicacaoPOT.getInstance().setMapaTerrestreEstimativas(g);

        String expResult = "Troço: Rua 1 -> Rua 2\n" +
                "Massa total: 140.0 kg\n" +
                "Velocidade Scooter: 8.333333333333334 m/s\n" +
                "Velocidade em relação ao vento: 6.333333333333334 m/s\n" +
                "Inclinação da rua: 1.3489824006953778E-4 radianos\n" +
                "Aceleração gravitica: 9.80665\n" +
                "Densidade do ar: 1.2041 kg/m^3\n" +
                "Coeficiente da resistência do ar: 1.1\n" +
                "Area frontal da scooter: 0.3 m^2\n" +
                "Força da inclinação: 0.18520597507519518 N\n" +
                "Força da atrito: 5.491723950032079 N\n" +
                "Força da resistência do Ar: 7.9691351666666685 N\n" +
                "Força total: 13.646065091773943 N\n" +
                "Potência total: 86.42507891456832 W\n" +
                "Tempo estimado: 133433.91318755463 s\n" +
                "Energia Estimada: 1.1532036477114066E7 J\n" +
                "\n" +
                "Troço: Rua 2 -> Rua 3\n" +
                "Massa total: 140.0 kg\n" +
                "Velocidade Scooter: 8.333333333333334 m/s\n" +
                "Velocidade em relação ao vento: 9.040440114519882 m/s\n" +
                "Inclinação da rua: -9.571225009569346E-5 radianos\n" +
                "Aceleração gravitica: 9.80665\n" +
                "Densidade do ar: 1.2041 kg/m^3\n" +
                "Coeficiente da resistência do ar: 1.1\n" +
                "Area frontal da scooter: 0.3 m^2\n" +
                "Força da inclinação: -0.13140631503549857 N\n" +
                "Força da atrito: 5.491723974845612 N\n" +
                "Força da resistência do Ar: 16.237742423540155 N\n" +
                "Força total: 21.59806008335027 N\n" +
                "Potência total: 195.2559687733304 W\n" +
                "Tempo estimado: 188063.7014539522 s\n" +
                "Energia Estimada: 3.6720560218489826E7 J\n" +
                "\n" +
                "Caminho: Rua 1 -> Rua 2 -> Rua 3\n" +
                "Energia Estimada Total: 4.825259669560389E7 J\n";

        assertEquals(expResult, instance.info(true, massaScooter, cargaTotal, moradas).toString());
    }

    @Test
    void infoTesScooter2(){
        double massaScooter = 50;   // (kg)
        double cargaTotal = 10;   // (kg)
        EstimarConsumoScooterTask instance = new EstimarConsumoScooterTask();
        Constantes.setVentoxScooter(1);
        Constantes.setVentoyScooter(2);

        Morada m1 = new Morada(1, "Rua 1", 1, 1, 0);
        Morada m2 = new Morada(2, "Rua 2", 11, 1, 150);
        Morada m3 = new Morada(3, "Rua 3", 1, 11, 0);
        Morada m4 = new Morada(4, "Rua 4",11,11,0);

        List<Morada> moradas = new ArrayList<>(Arrays.asList(m1, m2, m3, m4));

        Graph<Morada, Double> g = new Graph<>(true);
        g.insertVertex(m1);
        g.insertVertex(m2);
        g.insertVertex(m3);
        g.insertVertex(m4);
        g.insertEdge(m1, m2, 0.004, 1);
        g.insertEdge(m2, m3, 0.004, 1);
        g.insertEdge(m3, m4, 0.004, 1);
        AplicacaoPOT.getInstance().setMapaTerrestreEstimativas(g);

        String expResult = "Troço: Rua 1 -> Rua 2\n" +
                "Massa total: 140.0 kg\n" +
                "Velocidade Scooter: 8.333333333333334 m/s\n" +
                "Velocidade em relação ao vento: 6.333333333333334 m/s\n" +
                "Inclinação da rua: 1.3489824006953778E-4 radianos\n" +
                "Aceleração gravitica: 9.80665\n" +
                "Densidade do ar: 1.2041 kg/m^3\n" +
                "Coeficiente da resistência do ar: 1.1\n" +
                "Area frontal da scooter: 0.3 m^2\n" +
                "Força da inclinação: 0.18520597507519518 N\n" +
                "Força da atrito: 5.491723950032079 N\n" +
                "Força da resistência do Ar: 7.9691351666666685 N\n" +
                "Força total: 13.646065091773943 N\n" +
                "Potência total: 86.42507891456832 W\n" +
                "Tempo estimado: 133433.91318755463 s\n" +
                "Energia Estimada: 1.1532036477114066E7 J\n" +
                "\n" +
                "Troço: Rua 2 -> Rua 3\n" +
                "Massa total: 140.0 kg\n" +
                "Velocidade Scooter: 8.333333333333334 m/s\n" +
                "Velocidade em relação ao vento: 9.040440114519882 m/s\n" +
                "Inclinação da rua: -9.571225009569346E-5 radianos\n" +
                "Aceleração gravitica: 9.80665\n" +
                "Densidade do ar: 1.2041 kg/m^3\n" +
                "Coeficiente da resistência do ar: 1.1\n" +
                "Area frontal da scooter: 0.3 m^2\n" +
                "Força da inclinação: -0.13140631503549857 N\n" +
                "Força da atrito: 5.491723974845612 N\n" +
                "Força da resistência do Ar: 16.237742423540155 N\n" +
                "Força total: 21.59806008335027 N\n" +
                "Potência total: 195.2559687733304 W\n" +
                "Tempo estimado: 188063.7014539522 s\n" +
                "Energia Estimada: 3.6720560218489826E7 J\n" +
                "\n" +
                "Troço: Rua 3 -> Rua 4\n" +
                "Massa total: 140.0 kg\n" +
                "Velocidade Scooter: 8.333333333333334 m/s\n" +
                "Velocidade em relação ao vento: 6.333333333333334 m/s\n" +
                "Inclinação da rua: 0.0 radianos\n" +
                "Aceleração gravitica: 9.80665\n" +
                "Densidade do ar: 1.2041 kg/m^3\n" +
                "Coeficiente da resistência do ar: 1.1\n" +
                "Area frontal da scooter: 0.3 m^2\n" +
                "Força da inclinação: 0.0 N\n" +
                "Força da atrito: 5.491724 N\n" +
                "Força da resistência do Ar: 7.9691351666666685 N\n" +
                "Força total: 13.460859166666669 N\n" +
                "Potência total: 85.25210805555558 W\n" +
                "Tempo estimado: 133433.91197347044 s\n" +
                "Energia Estimada: 1.1375522281837793E7 J\n" +
                "\n" +
                "Caminho: Rua 1 -> Rua 2 -> Rua 3 -> Rua 4\n" +
                "Energia Estimada Total: 5.962811897744168E7 J\n";

        assertEquals(expResult, instance.info(true, massaScooter, cargaTotal, moradas).toString());
    }

    @Test
    void setPrintableScooterTest(){
        EstimarConsumoScooterTask instance = new EstimarConsumoScooterTask();
        StringBuilder expResult = new StringBuilder();
        instance.setPrintable(expResult);
    }
}
