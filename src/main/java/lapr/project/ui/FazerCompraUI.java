package lapr.project.ui;

import lapr.project.controller.AdicionarProdutoCarrinhoController;
import lapr.project.controller.VisualizarCatalogoController;
import lapr.project.data.*;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class FazerCompraUI {

    private FazerCompraUI(){}

    public static void run(int numeroFicheiro) {

        AdicionarProdutoCarrinhoController adicionarProdutocontroller = new AdicionarProdutoCarrinhoController(new ProdutoBD());
        VisualizarCatalogoController catalogoController = new VisualizarCatalogoController();

        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\FazerCompra" + numeroFicheiro + ".txt"))) {
            String palavraChave;

            int linha;

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "VER CATALOGO":
                        List<String> produtos = catalogoController.getTodosProdutos();
//                        for(String produto : produtos){
//                            System.out.println(produto);
//                        }
                        break;

                    case "ESCOLHER PRODUTO":
                        linha = ler.nextInt();
                        adicionarProdutocontroller.validarProdutoId(linha);
                        break;

                    case "DEFINIR QUANTIDADE":
                        linha = ler.nextInt();
                        adicionarProdutocontroller.adicionarAoCarrinho(linha);
                        break;

                    default:
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
