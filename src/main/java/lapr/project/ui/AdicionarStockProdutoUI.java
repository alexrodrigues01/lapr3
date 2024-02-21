package lapr.project.ui;

import lapr.project.controller.AdicionarStockProdutoController;
import lapr.project.data.*;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class AdicionarStockProdutoUI {

    private AdicionarStockProdutoUI(){}

    public static void run(int numeroFicheiro) {

        AdicionarStockProdutoController controller = new AdicionarStockProdutoController(new FarmaciaBD(), new ProdutoBD());

        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\AdicionarStock" + numeroFicheiro + ".txt"))) {
            String palavraChave;

            String[] linha = null;

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "APRESENTA LISTA":
                        List<String> produtos = controller.getProdutosByFarmacia();
                        for(String produto : produtos)
                            System.out.println(produto);
                        break;

                    case "PRODUTO":
                        linha = ler.nextLine().split(",");
                        controller.validaProduto(Integer.parseInt(linha[0]));
                        break;

                    case "APRESENTA INFORMACAO":
                        System.out.printf("Produto: %s%nQuantidade a acresentar: %s%n", linha[0], linha[1]);
                        break;

                    case "CONFIRMA":
                        controller.alteraStockProduto(Integer.parseInt(linha[0]), Integer.parseInt(linha[1]));
                        break;

                    case "PROXIMO PRODUTO":
                        controller = new AdicionarStockProdutoController(new FarmaciaBD(), new ProdutoBD());
                        break;

                    default:
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
