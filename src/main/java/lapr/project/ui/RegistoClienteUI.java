package lapr.project.ui;

import lapr.project.controller.RegistoClienteController;
import lapr.project.data.*;

import java.io.File;
import java.util.Scanner;

public class RegistoClienteUI {

    private RegistoClienteUI(){}

    public static void run(int numeroFicheiro) {

        RegistoClienteController controller = new RegistoClienteController(new ClienteBD(), new UtilizadorBD(), new MoradaBD());
        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\RegistoCliente" + numeroFicheiro + ".txt"))) {

                String palavraChave;

                String[] linha = null;

                while (ler.hasNextLine()) {

                    palavraChave = ler.nextLine();

                    switch (palavraChave) {

                        case "CLIENTE":
                            linha = ler.nextLine().split(",");
                            controller.novoCliente(linha[0], linha[1], linha[2], Integer.parseInt(linha[3]), Integer.parseInt(linha[4]), linha[5], Double.parseDouble(linha[6]), Double.parseDouble(linha[7]), Double.parseDouble(linha[8]), linha[9], linha[10], Integer.parseInt(linha[11]));
                            break;

                        case "APRESENTA OS DADOS":
                            break;

                        case "CONFIRMA":
                            controller.registarCliente(linha[0], linha[1], linha[2], Integer.parseInt(linha[3]), Integer.parseInt(linha[4]), linha[5], Double.parseDouble(linha[6]), Double.parseDouble(linha[7]), Double.parseDouble(linha[8]), linha[9], linha[10], Integer.parseInt(linha[11]));
                            break;

                        case "PROXIMO REGISTO":
                            controller = new RegistoClienteController(new ClienteBD(), new UtilizadorBD(), new MoradaBD());
                            break;

                        default:
                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
