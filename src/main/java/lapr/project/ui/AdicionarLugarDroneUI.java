package lapr.project.ui;

import lapr.project.controller.AdicionarLugarDroneController;
import lapr.project.data.FarmaciaBD;

import java.io.File;
import java.util.Scanner;

public class AdicionarLugarDroneUI {

    private AdicionarLugarDroneUI(){}

    public static void run(int numeroFicheiro) {


        AdicionarLugarDroneController controller = new AdicionarLugarDroneController(new FarmaciaBD());

        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\AdicionarLugarDrone" + numeroFicheiro + ".txt"))) {
            String palavraChave;

            String[] linha;

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "INFORMACOES LUGAR":
                        linha = ler.nextLine().split(",");
                        controller.validaParametros(Integer.parseInt(linha[0]),Integer.parseInt(linha[0]),Double.parseDouble(linha[2]));
                        int farmaciaID = controller.getFarmaciaByGestor();
                        System.out.printf("id da farmacia: %d%n>capacidade energetica do parque: %s%n>lugares sem carregador: %s%n>lugares com carregador: %s%n", farmaciaID, linha[2], linha[0], linha[1]);
                        break;

                    case "CONFIRMA":
                        controller.setLugaresDrone();
                        break;

                    default:
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
