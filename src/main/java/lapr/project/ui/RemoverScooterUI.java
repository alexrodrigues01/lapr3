package lapr.project.ui;

import lapr.project.controller.RemoverScooterController;
import lapr.project.data.FarmaciaBD;
import lapr.project.data.ScooterBD;
import lapr.project.model.Scooter;

import java.io.File;
import java.util.Scanner;

public class RemoverScooterUI {

    private RemoverScooterUI() {
    }

    public static void run(int numeroFicheiro) {

        RemoverScooterController controller = new RemoverScooterController(new ScooterBD(),new FarmaciaBD());
        try (Scanner scanner = new Scanner(new File("src\\main\\resources\\input\\RemoverScooter" + numeroFicheiro + ".txt"))) {
            String chave;

            String[] linha;

            while (scanner.hasNextLine()) {

                chave = scanner.nextLine();

                switch (chave) {

                    case "LISTA DRONE":
                        for(Scooter scooter: controller.getListaScooter()){
                            System.out.println(scooter);
                        }
                        break;

                    case "ESOLHE SCOOTER":
                        linha = scanner.nextLine().split(",");
                        System.out.println(controller.getScooterByID(Integer.parseInt(linha[0])));
                        break;

                    case "CONFIRMA":
                        linha = scanner.nextLine().split(",");
                        controller.removerScooter(Integer.parseInt(linha[0]));
                        break;

                    case "PROXIMO REGISTO":
                        controller = new RemoverScooterController(new ScooterBD(),new FarmaciaBD());
                        break;

                    default:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
