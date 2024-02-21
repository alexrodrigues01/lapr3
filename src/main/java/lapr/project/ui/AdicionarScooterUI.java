package lapr.project.ui;

import lapr.project.controller.AdicionarScooterController;
import lapr.project.data.*;
import lapr.project.model.LugarEstacionamento;
import lapr.project.model.Scooter;
import lapr.project.utils.Pair;
import java.io.File;
import java.util.Scanner;

public class AdicionarScooterUI {

    private AdicionarScooterUI() {
    }

    public static void run(int numeroFicheiro) {

        AdicionarScooterController controller = new AdicionarScooterController(new ScooterBD(),new FarmaciaBD());
        Pair<Scooter,LugarEstacionamento> dados = null;
        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\AdicionarScooter" + numeroFicheiro + ".txt"))) {
            String palavraChave;

            String[] linha;

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "SCOOTER":
                        linha = ler.nextLine().split(",");
                        dados = controller.novaScooter(Integer.parseInt(linha[0]), Double.parseDouble(linha[1]), Integer.parseInt(linha[2]), Double.parseDouble(linha[3]), Double.parseDouble(linha[4]), Double.parseDouble(linha[5]), Double.parseDouble(linha[6]));
                        break;

                    case "APRESENTA OS DADOS":
                        break;

                    case "CONFIRMA":
                        if(dados != null)
                            controller.update(dados.getValue(), dados.getKey());
                        break;

                    case "PROXIMO REGISTO":
                        controller = new AdicionarScooterController(new ScooterBD(),new FarmaciaBD());
                        break;

                    default:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
