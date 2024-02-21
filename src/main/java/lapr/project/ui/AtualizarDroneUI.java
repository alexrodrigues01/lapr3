package lapr.project.ui;

import lapr.project.controller.AtualizarDroneController;
import lapr.project.data.DroneBD;
import lapr.project.data.FarmaciaBD;
import lapr.project.model.Drone;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class AtualizarDroneUI {

    private AtualizarDroneUI(){}

    public static void run(int numeroFicheiro) {

        AtualizarDroneController controller = new AtualizarDroneController(new DroneBD(),new FarmaciaBD());

        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\AtualizarDrone" + numeroFicheiro + ".txt"))) {
            String palavraChave;

            String[] linha;

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "APRESENTA LISTA DRONE":
                        List<Drone> drones = controller.getListaDrone();
                        break;

                    case "DRONE":
                        List<String> droneInfo = controller.getDroneByID(ler.nextInt());
                        break;

                    case "APRESENTA ANTIGOS E SOLICITA NOVOS":
                        linha = ler.nextLine().split(",");
                        controller.validaDados(Integer.parseInt(linha[0]), Double.parseDouble(linha[1]), Integer.parseInt(linha[2]), Double.parseDouble(linha[3]), Double.parseDouble(linha[4]), Double.parseDouble(linha[5]), Double.parseDouble(linha[6]), Double.parseDouble(linha[7]), Double.parseDouble(linha[8]), Double.parseDouble(linha[9]), Double.parseDouble(linha[10]), Double.parseDouble(linha[11]));
                        break;

                    case "APRESENTA":
                        break;

                    case "CONFIRMA":
                        controller.updateDados();
                        break;

                    case "PROXIMA SCOOTER":
                        controller = new AtualizarDroneController(new DroneBD(),new FarmaciaBD());
                        break;

                    default:
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
