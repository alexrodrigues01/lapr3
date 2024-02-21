package lapr.project.ui;

import lapr.project.controller.AdicionarDroneController;
import lapr.project.data.*;
import lapr.project.model.Drone;
import lapr.project.model.LugarDrone;
import lapr.project.utils.Pair;

import java.io.File;
import java.util.Scanner;

public class AdicionarDroneUI {

    private AdicionarDroneUI(){}

    public static void run(int numeroFicheiro) {

        AdicionarDroneController controller = new AdicionarDroneController(new DroneBD(), new FarmaciaBD());

        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\AdicionarDrone" + numeroFicheiro + ".txt"))) {
            String palavraChave;

            String[] linha;
            Pair<Drone, LugarDrone> drone = null;

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "DRONE":
                        linha = ler.nextLine().split(",");
                        drone = controller.novoDrone(Integer.parseInt(linha[0]), Double.parseDouble(linha[1]), Integer.parseInt(linha[2]), Double.parseDouble(linha[3]), Double.parseDouble(linha[4]), Double.parseDouble(linha[5]), Double.parseDouble(linha[6]), Double.parseDouble(linha[7]), Double.parseDouble(linha[8]), Double.parseDouble(linha[9]), Double.parseDouble(linha[10]), Double.parseDouble(linha[11]));
                        System.out.println("drone:" + drone.getKey());
                        System.out.println("Lugar Estacionamento:" + drone.getValue());
                        break;

                    case "CONFIRMA":
                        controller.updateDrone(drone.getValue(), drone.getKey());
                        break;

                    case "PROXIMO REGISTO":
                        controller =  new AdicionarDroneController(new DroneBD(), new FarmaciaBD());
                        break;

                    default:
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
