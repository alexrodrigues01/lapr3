package lapr.project.ui;

import lapr.project.controller.FinalizarCompraController;
import lapr.project.data.*;

import java.io.File;
import java.util.Scanner;

public class FinalizarCompraUI {

    private FinalizarCompraUI() {
    }

    public static void run(int numeroFicheiro) {

        FinalizarCompraController controller = new FinalizarCompraController(new ClienteBD(), new ProdutoBD(), new EncomendaBD(), new FaturaBD(), new FarmaciaBD(), new MoradaBD(), new NotaBD());

        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\FinalizarCompra" + numeroFicheiro + ".txt"))){
            String palavraChave;

                int linha;
                int numeroCreditosDescontados = 0;

                while (ler.hasNextLine()) {

                    palavraChave = ler.nextLine();

                    switch (palavraChave) {

                        case "GET CREDITOS":
                            int creditos = controller.getCreditos();
                            System.out.println(creditos);
                            break;

                        case "NUMERO CREDITOS A DESCONTAR":
                            numeroCreditosDescontados = ler.nextInt();
                            break;

                        case "CVV":
                            linha = ler.nextInt();
                            controller.validaCVV(linha);
                            break;

                        case "NIF":
                            linha = ler.nextInt();
                            controller.finalizarCompra(numeroCreditosDescontados, linha);
                            break;

                        case "PROXIMA COMPRA":
                            controller = new FinalizarCompraController(new ClienteBD(), new ProdutoBD(), new EncomendaBD(), new FaturaBD(), new FarmaciaBD(), new MoradaBD(), new NotaBD());
                            numeroCreditosDescontados = 0;
                            break;

                        default:
                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
