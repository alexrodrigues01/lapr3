package lapr.project.ui;

import lapr.project.controller.RegistoProdutoController;
import lapr.project.data.*;

import java.io.File;
import java.util.Scanner;

public class RegistoProdutoUI {

    private RegistoProdutoUI(){}

    public static void run(int numeroFicheiro) {

        RegistoProdutoController controller = new RegistoProdutoController(new ProdutoBD());

        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\RegistoProduto" + numeroFicheiro + ".txt"))) {
            String palavraChave;

            String[] linha = null;

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "PRODUTO":
                        linha = ler.nextLine().split(",");
                        controller.novoProduto(linha[0], Double.parseDouble(linha[1]), Integer.parseInt(linha[2]), Integer.parseInt(linha[3]));
                        break;

                    case "APRESENTA INFORMACAO":
                        System.out.printf("nome : %s%npreco unitario: %s%npeso: %s%niva: %s%n",linha[0], linha[1], linha[2], linha[3]);
                        break;

                    case "CONFIRMA":
                        controller.registarProduto(linha[0], Double.parseDouble(linha[1]), Integer.parseInt(linha[2]), Integer.parseInt(linha[3]));
                        break;

                    case "PROXIMO PRODUTO":
                        controller = new RegistoProdutoController(new ProdutoBD());
                        break;

                    default:
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
