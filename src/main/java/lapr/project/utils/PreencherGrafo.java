package lapr.project.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lapr.project.controller.EstimarConsumoDroneTask;
import lapr.project.controller.EstimarConsumoScooterTask;
import lapr.project.data.MoradaBD;
import lapr.project.model.Morada;
import lapr.project.model.graph.Graph;

/**
 * Classe para ler os ficheiros relacionados aos grafos e criar os grafos
 */
public class PreencherGrafo {

    private static MoradaBD moradaBD;

    private PreencherGrafo() {
    }

    /**
     * Construtor com a moradaBD
     *
     * @param moradaBD
     */
    public static void setMoradaBD(MoradaBD moradaBD) {
        PreencherGrafo.moradaBD = moradaBD;
    }

    /**
     * Metodo para ler as moradas
     *
     * @param filePath path para o ficheiro com as moradas
     * @return grafo com as moradas (sem ligacoes)
     */
    public static Graph<Morada, Double> lerMoradas(String filePath) {
        Graph<Morada, Double> mapa = new Graph<>(true);
        List<Morada> moradas = new ArrayList<>();
        try (Scanner myReader = new Scanner(new File(filePath))) {

            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] info = data.split(";");
                Morada morada = new Morada(info[0], Double.parseDouble(info[1]), Double.parseDouble(info[2]), Double.parseDouble(info[3]));
                mapa.insertVertex(morada);
                moradas.add(morada);
            }
            moradaBD.addMoradas(moradas);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return mapa;
    }

    /**
     * Metodo para adicionar em dois grafos as ligacoes e os respetivos pesos,
     * um relativo a distancia entre eles e outro relativo ao consumo medio
     * entre eles
     *
     * @param filePath path para o ficheiro com as ligacoes
     * @param mapaEstimativas mapa relativo ao caminho mais economico
     * @param mapaDistancias mapa relativo ao caminho mais curto
     * @param isDrone boolean se e drone ou nao (scooter)
     * @return return Pair com os mapas Estimativas e Distancias, com as
     * ligacoes
     */
    public static Pair<Graph<Morada, Double>, Graph<Morada, Double>> lerLigacoes(String filePath, Graph<Morada, Double> mapaEstimativas, Graph<Morada, Double> mapaDistancias, boolean isDrone) {
        try (Scanner myReader = new Scanner(new File(filePath))) {
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] info = data.split(";");
                Morada morada1 = getMoradabyInfo(info[0], mapaEstimativas);
                Morada morada2 = getMoradabyInfo(info[1], mapaEstimativas);
                Morada morada3 = getMoradabyInfo(info[0], mapaDistancias);
                Morada morada4 = getMoradabyInfo(info[1], mapaDistancias);
                if (morada1 != null && morada2 != null) {
                    double coeficienteAtrito = 0;
                    if (isDrone) {
                        mapaEstimativas.insertEdge(morada1, morada2, coeficienteAtrito, new EstimarConsumoDroneTask().estimarConsumoSimples(morada1, morada2));
                    } else {
                        coeficienteAtrito = Double.parseDouble(info[2]);
                        mapaEstimativas.insertEdge(morada1, morada2, coeficienteAtrito, new EstimarConsumoScooterTask().estimativaSimples(morada1, morada2, coeficienteAtrito));
                    }
                    mapaDistancias.insertEdge(morada3, morada4, coeficienteAtrito, morada3.distanceFrom(morada4));
                } else {
                    return null;
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        }
        return new Pair<>(mapaEstimativas, mapaDistancias);
    }

    /**
     * Metodo para ir buscar uma morada de acordo com o seu nome
     *
     * @param s nome da morada que se pretende obter
     * @param mapa mapa que possui a morada
     * @return morada
     */
    private static Morada getMoradabyInfo(String s, Graph<Morada, Double> mapa) {
        Iterable<Morada> moradaIterable = mapa.vertices();
        List<Morada> moradas = new ArrayList<>();
        moradaIterable.forEach(moradas::add);
        for (Morada m : moradas) {
            if (m.getStringMorada().equalsIgnoreCase(s)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Metodo para ir buscar o vento a um determinado ficheiro e guarda-lo nas
     * Constantes para uso geral dos restantes metodos
     *
     * @param filePath path para o ficheiro do vento
     * @return boolean se leu com sucesso ou nao
     */
    public static boolean getVento(String filePath) {
        try {
            Scanner sc = new Scanner(new File(filePath));
            sc.nextLine();
            String[] ventoScooter = sc.nextLine().split(";");
            sc.nextLine();
            String[] ventoDrone = sc.nextLine().split(";");
            Constantes.setVentoxScooter(Double.parseDouble(ventoScooter[0]));
            Constantes.setVentoyScooter(Double.parseDouble(ventoScooter[1]));
            Constantes.setVentoxDrone(Double.parseDouble(ventoDrone[0]));
            Constantes.setVentoyDrone(Double.parseDouble(ventoDrone[1]));
            return true;
        } catch (FileNotFoundException | NumberFormatException ex) {
            return false;
        }
    }
}
