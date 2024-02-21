package lapr.project.ui;

import lapr.project.controller.CriarEntregaController;
import lapr.project.data.*;
import lapr.project.model.Encomenda;
import lapr.project.ui.interacoes_ficheiro.OutputFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CriarEntregaUI {

    private CriarEntregaUI(){}

    public static void run(String numeroFicheiro) {

        CriarEntregaController controller = new CriarEntregaController(new FarmaciaBD(), new EncomendaBD(), new MoradaBD(), new DroneBD(), new ScooterBD(), new EstafetaBD(), new EntregaBD());

        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\CriarEntrega" + numeroFicheiro + ".txt"))) {
            String palavraChave;

            String[] linha;
            List<Integer> encomendasEscolhidas = new ArrayList<>();

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "APRESENTA ENCOMENDAS":
                        List<Encomenda> encomendas = controller.getEncomendas();
                        for(Encomenda encomenda : encomendas){
                            System.out.println(encomenda.toString());
                        }
                        break;

                    case "ENCOMENDAS":
                        linha = ler.nextLine().split(",");
                        encomendasEscolhidas = new ArrayList<>();
                        for(int i = 0; i < linha.length; i++){
                            encomendasEscolhidas.add(Integer.parseInt(linha[i]));
                        }
                        break;

                    case "TIPO DE CAMINHO":
                        boolean estimativa = Boolean.parseBoolean(ler.next());
                        controller.validaDados(encomendasEscolhidas, estimativa);
                        break;

                    case "PROXIMA ENTREGA":
                        controller = new CriarEntregaController(new FarmaciaBD(), new EncomendaBD(), new MoradaBD(), new DroneBD(), new ScooterBD(), new EstafetaBD(), new EntregaBD());
                        break;

                    default:
                }
            }

        } catch (Exception e) {
            OutputFileWriter.write(e.getMessage()+"\n");
        }

    }
}
