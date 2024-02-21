package lapr.project.ui;

import lapr.project.controller.IniciarEntregaController;
import lapr.project.data.*;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class IniciarEntregaUI {

    private IniciarEntregaUI() {
    }

    public static void run(int numeroFicheiro) {

        IniciarEntregaController controller = new IniciarEntregaController();

        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\IniciarEntrega" + numeroFicheiro + ".txt"))) {
            String palavraChave;

            String[] linha;

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "APRESENTA ENTREGAS":
                     List<String> entregas= controller.getEntregas(new EstafetaBD(),new GestorFarmaciaBD());
                        for (String entrega:entregas) {
                            System.out.println(entrega);
                        }
                        break;

                    case "ESCOLHE ENTREGA":
                        linha = ler.nextLine().split(",");
                        controller.iniciarEntrega(Integer.parseInt(linha[0]));
                        break;

                    default:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
