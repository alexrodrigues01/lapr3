package lapr.project.ui;

import lapr.project.controller.AdicionarFarmaciaController;
import lapr.project.data.FarmaciaBD;
import lapr.project.data.GestorFarmaciaBD;
import lapr.project.data.MoradaBD;
import lapr.project.data.UtilizadorBD;

import java.io.File;
import java.util.Scanner;

public class AdicionarFarmaciaUI {

    private AdicionarFarmaciaUI() {
    }

    public static void run(int numeroFicheiro) {

        AdicionarFarmaciaController controller = new AdicionarFarmaciaController(new FarmaciaBD(), new GestorFarmaciaBD(), new UtilizadorBD(), new MoradaBD());

        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\AdicionarFarmacia" + numeroFicheiro + ".txt"))) {
            String palavraChave;

            String[] linha;

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "FARMACIA":
                        linha = ler.nextLine().split(",");
                        controller.validaFarmacia(linha[0], Integer.parseInt(linha[1]), Integer.parseInt(linha[2]), linha[3]);
                        break;

                    case "GESTOR":
                        linha = ler.nextLine().split(",");
                        controller.validaGestorFarmacia(linha[0], linha[1], Integer.parseInt(linha[2]), Integer.parseInt(linha[3]), linha[4]);
                        break;

                    case "LUGAR ESTACIONAMENTO":
                        linha = ler.nextLine().split(",");
                        controller.validaLugaresEstacionamento(Integer.parseInt(linha[0]), Integer.parseInt(linha[1]), Double.parseDouble(linha[2]));
                        break;

                    case "MORADAS":
                        linha = ler.nextLine().split(",");
                        controller.validaMoradas(linha[0], Double.parseDouble(linha[1]), Double.parseDouble(linha[2]), Double.parseDouble(linha[3]),
                                linha[4], Double.parseDouble(linha[5]), Double.parseDouble(linha[6]), Double.parseDouble(linha[7]));
                        break;

                    case "GUARDAR DADOS":
                        controller.addDados();
                        break;

                    case "PROXIMA FARMACIA":
                        controller = new AdicionarFarmaciaController(new FarmaciaBD(), new GestorFarmaciaBD(), new UtilizadorBD(), new MoradaBD());
                        break;

                    default:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
