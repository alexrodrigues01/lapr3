package lapr.project.controller;

import lapr.project.model.Drone;
import lapr.project.model.Morada;
import lapr.project.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EstimarConsumoDroneTaskTest {

    double cargaEntrega = 3;
    Drone drone= new Drone(1,20,300,1,250,50,3,10,20,100,3,0.5,3);
    EstimarConsumoDroneTask task = new EstimarConsumoDroneTask();

    @BeforeEach
    void setup(){
        Constantes.setVentoxDrone(1);
        Constantes.setVentoyDrone(2);
    }


    @Test
    void estimarConsumoPercursoTest() {
        Morada m = new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513);
        Morada m2 = new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567);
        Morada f1 = new Morada("Rua14,Brasilia", -15.7797200, -47.9297200, 1320.349);

        List<Morada> list = new ArrayList<>();

        assertEquals(0,task.estimarConsumoPercurso(drone,cargaEntrega,list, false));

        list.add(m);
        list.add(m2);
        list.add(f1);

        assertEquals(1.7915590972491884E8, task.estimarConsumoPercurso(drone,cargaEntrega,list, false));
        assertNotEquals(55555, task.estimarConsumoPercurso(drone,cargaEntrega,list, false));
    }

    @Test
    void estimarConsumoSimplesTest() {
        Morada m = new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513);
        Morada m2 = new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567);

        assertEquals(2.5938262085664433E8, task.estimarConsumoSimples(m,m2));
        assertNotEquals(668, task.estimarConsumoSimples(m,m2));

        Constantes.setVentoxDrone(999999999);
        Constantes.setVentoyDrone(999999999);

        assertEquals(0, task.estimarConsumoSimples(m,m2));
        assertNotEquals(444, task.estimarConsumoSimples(m,m2));
    }

    @Test
    void estimarConsumoViagemTest() {
        Constantes.setVentoxDrone(1);
        Constantes.setVentoyDrone(2);

        Morada m = new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513);
        Morada m2 = new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567);

        assertEquals(1.2870722474303733E8, task.estimarConsumoViagem(drone, m, m2, cargaEntrega, false));
        assertNotEquals(356, task.estimarConsumoViagem(drone, m, m2, cargaEntrega, false));
    }

    @Test
    void distanciaComVentoTest() {
        Morada m = new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513);
        Morada m2 = new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567);

        assertEquals(2910176.7882459275, task.distanciaComVento(drone, m, m2, false));
        assertNotEquals(566, task.distanciaComVento(drone, m, m2, false));
    }

    @Test
    void produtoEscalarTest() {
        double[] vetor1 = new double[3];
        vetor1[0] = 5;
        vetor1[1] = 2;
        vetor1[2] = 2;

        double[] vetor2 = new double[3];
        vetor2[0] = 2;
        vetor2[1] = 3;
        vetor2[2] = 4;

        assertEquals(24,task.produtoEscalar(vetor1, vetor2));
        assertNotEquals(0,task.produtoEscalar(vetor1, vetor2));
    }

    @Test
    void calcularVetorTest() {
        Morada m = new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513);
        Morada m2 = new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567);

        double[] expResult = {-1.4245064633210403, 2.6402237283905663};

        assertEquals(Arrays.toString(expResult), Arrays.toString(task.calcularVetor(3, m, m2)));

        double[] expResult2 = {5, 5, 5};

        assertNotEquals(Arrays.toString(expResult2), Arrays.toString(task.calcularVetor(3, m, m2)));
    }

    @Test
    void infoDroneTest() {
        Morada m = new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513);
        Morada m2 = new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567);
        Morada f1 = new Morada("Rua14,Brasilia", -15.7797200, -47.9297200, 1320.349);
        Morada f2 = new Morada("Rua15,Teste", -15.7798200, -47.9298200, 1120.349);

        List<Morada> list = new ArrayList<>();
        list.add(m);
        list.add(m2);
        list.add(f1);
        list.add(f2);

        String expResultString = "Troço: Rua1,BuenosAires -> Rua2,Lapaz\n" +
                "Distância: 2236888.589685118 m\n" +
                "Headwind: 4.627129192152109\n" +
                "Headwind Ratio: 0.23135645960760548\n" +
                "Massa Total: 4.0 kg\n" +
                "Aceleração gravitica: 9.80665\n" +
                "Rotors: 0.5\n" +
                "Drag: 3.0\n" +
                "Avionics: 100.0\n" +
                "Velocidade do Drone: 20.0 km/h\n" +
                "Energia Estimada: 9.065511114243476E7 J\n" +
                "\n" +
                "Troço: Rua2,Lapaz -> Rua14,Brasilia\n" +
                "Distância: 2160365.633039745 m\n" +
                "Headwind: 3.854031552954525\n" +
                "Headwind Ratio: 0.19270157764772625\n" +
                "Massa Total: 3.3333333333333335 kg\n" +
                "Aceleração gravitica: 9.80665\n" +
                "Rotors: 0.5\n" +
                "Drag: 3.0\n" +
                "Avionics: 100.0\n" +
                "Velocidade do Drone: 20.0 km/h\n" +
                "Energia Estimada: 7.169804206340066E7 J\n" +
                "\n" +
                "Troço: Rua14,Brasilia -> Rua15,Teste\n" +
                "Distância: 15.431870403827654 m\n" +
                "Headwind: -7.636753236882541\n" +
                "Headwind Ratio: -0.38183766184412704\n" +
                "Massa Total: 3.111111111111111 kg\n" +
                "Aceleração gravitica: 9.80665\n" +
                "Rotors: 0.5\n" +
                "Drag: 3.0\n" +
                "Avionics: 100.0\n" +
                "Velocidade do Drone: 20.0 km/h\n" +
                "Energia Estimada: 282.9849431990363 J\n" +
                "\n" +
                "Caminho: Rua1,BuenosAires -> Rua2,Lapaz -> Rua14,Brasilia -> Rua15,Teste\n" +
                "Energia Estimada Total: 1.623534361907786E8 J\n";

        assertEquals(expResultString, task.info(drone, cargaEntrega,list).toString());
    }

    @Test
    void infoDroneTest2() {
        Morada m = new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513);
        Morada m2 = new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567);
        Morada f1 = new Morada("Rua14,Brasilia", -15.7797200, -47.9297200, 1320.349);

        List<Morada> list = new ArrayList<>();
        list.add(m);
        list.add(m2);
        list.add(f1);

        String expResultString = "Troço: Rua1,BuenosAires -> Rua2,Lapaz\n" +
                "Distância: 2236888.589685118 m\n" +
                "Headwind: 4.627129192152109\n" +
                "Headwind Ratio: 0.23135645960760548\n" +
                "Massa Total: 4.5 kg\n" +
                "Aceleração gravitica: 9.80665\n" +
                "Rotors: 0.5\n" +
                "Drag: 3.0\n" +
                "Avionics: 100.0\n" +
                "Velocidade do Drone: 20.0 km/h\n" +
                "Energia Estimada: 1.0016813954258542E8 J\n" +
                "\n" +
                "Troço: Rua2,Lapaz -> Rua14,Brasilia\n" +
                "Distância: 2160365.633039745 m\n" +
                "Headwind: 3.854031552954525\n" +
                "Headwind Ratio: 0.19270157764772625\n" +
                "Massa Total: 3.75 kg\n" +
                "Aceleração gravitica: 9.80665\n" +
                "Rotors: 0.5\n" +
                "Drag: 3.0\n" +
                "Avionics: 100.0\n" +
                "Velocidade do Drone: 20.0 km/h\n" +
                "Energia Estimada: 7.898777018233344E7 J\n" +
                "\n" +
                "Caminho: Rua1,BuenosAires -> Rua2,Lapaz -> Rua14,Brasilia\n"+
                "Energia Estimada Total: 1.7915590972491884E8 J\n";

        assertEquals(expResultString, task.info(drone, cargaEntrega,list).toString());
    }

    @Test
    void setPrintableDroneTest(){
        StringBuilder expResult = new StringBuilder();
        task.setPrintable(expResult);
    }
}