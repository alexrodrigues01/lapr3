package lapr.project.ui;

import lapr.project.controller.RegistarEstafetaController;
import lapr.project.data.*;
import java.io.File;
import java.util.Scanner;

public class RegistarEstafetaUI {

    private RegistarEstafetaUI(){}

    public static void run(int numeroFicheiro) {

        RegistarEstafetaController controller = new RegistarEstafetaController(new FarmaciaBD(), new UtilizadorBD(), new EstafetaBD(), new MoradaBD());

        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\RegistarEstafeta" + numeroFicheiro + ".txt"))) {
            String palavraChave;

            String[] linha = null;

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "ESTAFETA":
                        linha = ler.nextLine().split(",");
                        controller.validarEstafeta(linha[0], linha[1], linha[2], Integer.parseInt(linha[3]), Integer.parseInt(linha[4]), linha[5],
                                Double.parseDouble(linha[6]), linha[7], Double.parseDouble(linha[8]), Double.parseDouble(linha[9]), Double.parseDouble(linha[10]));
                        System.out.printf("email: %s%npassword: %s%nnome: %s%nNIF: %s%ntelefone: %s%nNumero Seguranca Social: %s%ncarga maxima: %s%nmorada: %s%nlatitude: %s%nlongitude: %s%naltitude: %s%n",
                                linha[0], linha[1], linha[2], linha[3], linha[4], linha[5], linha[6], linha[7], linha[8], linha[9], linha[10]);
                        break;

                    case "CONFIRMA":
                        controller.registarEstafeta(linha[0], linha[1], linha[2], Integer.parseInt(linha[3]), Integer.parseInt(linha[4]), linha[5],
                                Double.parseDouble(linha[6]), linha[7], Double.parseDouble(linha[8]), Double.parseDouble(linha[9]), Double.parseDouble(linha[10]));
                        break;

                    case "PROXIMA FARMACIA":
                        controller = new RegistarEstafetaController(new FarmaciaBD(), new UtilizadorBD(), new EstafetaBD(), new MoradaBD());
                        break;

                    default:
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}