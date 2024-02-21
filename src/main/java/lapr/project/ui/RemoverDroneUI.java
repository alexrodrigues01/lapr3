package lapr.project.ui;

import lapr.project.controller.RemoverDroneController;
import lapr.project.data.DroneBD;
import lapr.project.data.FarmaciaBD;
import lapr.project.model.Drone;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class RemoverDroneUI {

    private RemoverDroneUI(){}

    public static void run(int numeroFicheiro) {

        RemoverDroneController controller = new RemoverDroneController(new DroneBD(),new FarmaciaBD());

        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\RemoverDrone" + numeroFicheiro + ".txt"))) {

            String palavraChave;

            int droneID = 0;

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "APRESENTA DRONES":
                        List<Drone> drones = controller.getListaDrone();
                        break;

                    case "DRONE":
                        droneID = ler.nextInt();
                        String droneInfo = controller.getDroneByID(droneID);
                        break;

                    case "APRESENTA":

                        break;

                    case "CONFIRMA":
                        controller.removerDrone(droneID);
                        break;

                    case "PROXIMO DRONE":
                        droneID = 0;
                        controller = new RemoverDroneController(new DroneBD(),new FarmaciaBD());

                    default:
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
