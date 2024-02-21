package lapr.project.ui;

import lapr.project.controller.AtualizarScooterController;
import lapr.project.data.FarmaciaBD;
import lapr.project.data.ScooterBD;
import lapr.project.model.Scooter;

import java.io.File;
import java.util.Scanner;

public class AtualizarScooterUI {

    private AtualizarScooterUI(){
    }

    public static void run(int numeroFicheiro) {

        AtualizarScooterController controller = new AtualizarScooterController(new ScooterBD(),new FarmaciaBD());
        try (Scanner ler = new Scanner(new File("src\\main\\resources\\input\\AtualizarScooter" + numeroFicheiro + ".txt"))) {
            String palavraChave;

            String[] linha;

            while (ler.hasNextLine()) {

                palavraChave = ler.nextLine();

                switch (palavraChave) {

                    case "LISTA":
                        for(Scooter s: controller.getListaScooter()){
                            System.out.println(s);
                        }
                        break;

                    case "SCOOTER":
                        linha = ler.nextLine().split(",");
                        System.out.println("ID: "+controller.getScooterByID(Integer.parseInt(linha[0])).get(0));
                        System.out.println("Carga: "+controller.getScooterByID(Integer.parseInt(linha[0])).get(1));
                        System.out.println("Capacidade Bateria: "+controller.getScooterByID(Integer.parseInt(linha[0])).get(2));
                        System.out.println("Estado Scooter ID: "+controller.getScooterByID(Integer.parseInt(linha[0])).get(3));
                        System.out.println("Consumo Hora Bateria: "+controller.getScooterByID(Integer.parseInt(linha[0])).get(4));
                        System.out.println("Tensão Bateria: "+controller.getScooterByID(Integer.parseInt(linha[0])).get(5));
                        System.out.println("Massa: "+controller.getScooterByID(Integer.parseInt(linha[0])).get(6));
                        System.out.println("Potencia: "+controller.getScooterByID(Integer.parseInt(linha[0])).get(7));
                        break;

                    case "DADOS":
                        linha = ler.nextLine().split(",");
                        controller.validaDados(linha[0], linha[1], linha[2], linha[3], linha[4], linha[5], linha[6]);
                        break;

                    case "APRESENTA OS DADOS":
                        break;

                    case "CONFIRMA":
                        controller.updateDados();
                        break;

                    case "PROXIMO REGISTO":
                        controller = new AtualizarScooterController(new ScooterBD(),new FarmaciaBD());
                        break;

                    default:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
