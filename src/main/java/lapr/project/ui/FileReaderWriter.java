package lapr.project.ui;

import lapr.project.data.DroneBD;
import lapr.project.data.ScooterBD;
import lapr.project.utils.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import static lapr.project.utils.Constantes.PATH;
import static lapr.project.utils.Constantes.PATHC;

public class FileReaderWriter {

    private FileReaderWriter() {
    }

    /**
     * Este metodo lê o ficheiro de lock , invoca o metodo que cria o ficheiro info e retorna a informação necessária para o email.
     *
     * @param name
     * @param scooterBD
     * @param droneBD
     * @return
     */
    public static Pair<String, Pair<String, String>> lockFileReader(String name, ScooterBD scooterBD, DroneBD droneBD) {
        Scanner myReader = null;
        File myObj = null;
        try {
            File flag = new File(PATH + "\\" + name);
            boolean delete = flag.delete();
            String[] array = name.split("[.]");
            String[] date = array[0].split("[_]");
            name = array[0] + "." + array[1] + ".txt";
            myObj = new File(PATH + "\\" + name);
            myReader = new Scanner(myObj);
            if (myReader.hasNextLine()) {
                String localDate = date[1] + "_" + date[2] + "_" + date[3] + "_" + date[4] + "_" + date[5] + "_" + date[6];
                String data = myReader.nextLine();
                String[] info = data.split(";");
                String veiculo = null;
                String email = null;
                Pair<Double, Integer> capacidadeParque=null;
                if (scooterBD.isScooter(Integer.parseInt(info[0]))) {
                    if(info[2]!=null)
                        email = info[2];
                    veiculo = scooterBD.getScooterByID(Integer.parseInt(info[0]));
                    capacidadeParque = scooterBD.getCapacidadeAndParqueByIdVeiculo(Integer.parseInt(info[0]));
                }

                if (droneBD.isDrone(Integer.parseInt(info[0]))) {
                    email = droneBD.getEmailAdministradorByDrone(Integer.parseInt(info[0]));
                    veiculo = droneBD.getDroneByID(Integer.parseInt(info[0]));
                    capacidadeParque = scooterBD.getCapacidadeAndParqueByIdVeiculo(Integer.parseInt(info[0]));
                }

                if (email != null && veiculo != null && capacidadeParque.getKey() != null && capacidadeParque.getValue() != null) {
                        scooterBD.setACarregar(Integer.parseInt(info[0]),1);
                        scooterBD.atualizarCarga(Integer.parseInt(info[1]), Integer.parseInt(info[0]));
                        scooterInformationWriter(Integer.parseInt(info[0]), Integer.parseInt(info[1]), localDate, email, droneBD.getCapacidadeDroneById(Integer.parseInt(info[0])), capacidadeParque.getKey(), capacidadeParque.getValue());
                }
                    return new Pair<>(localDate, new Pair<>(veiculo, email));
                }

            } catch(FileNotFoundException | NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException e){
                System.out.println(e.getMessage());
                return null;
            } finally{
                if (myReader != null)
                    myReader.close();
                if (myObj != null) {
                    boolean delete = myObj.delete();
                }
            }

            return null;
        }

    /**
     * Este metodo cria o ficheiro info.txt com todas as informações necessárias para a determinação da estimativa de carregamento.
     *
     * @param idScooter
     * @param qtdBateria
     * @param localDate
     * @param email
     * @param capacidadeVeiculo
     * @param capacidadeCarregador
     * @param idParque
     */
    private static void scooterInformationWriter(int idScooter, int qtdBateria, String localDate, String email, int capacidadeVeiculo, double capacidadeCarregador,int idParque) {
        try (FileWriter fileWriter = new FileWriter(PATHC + "\\" + "info.txt")) {
            fileWriter.write(idScooter + ";" + qtdBateria + ";" + capacidadeVeiculo + ";" + localDate + ";" + email + ";" + capacidadeCarregador+";"+idParque);
//            fileWriter.write(idScooter+";"+qtdBateria+";"+5000+";"+localDate+";"+email);
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    /**
     * Este metodo lê o ficheiro de estimativa e envia email para o gestor/estafeta para indicar o tempo de carregamento.
     * @param name
     * @param scooterBD
     * @param droneBD
     * @return
     */
    public static Pair<String, Pair<String, Integer>> estimateFileReader(String name, ScooterBD scooterBD, DroneBD droneBD) {
        Scanner myReader = null;
        File myObj = null;
        try {
            File flag = new File(PATH + "\\" + name);
            final boolean delete = flag.delete();
            String[] array = name.split("[.]");
            name = array[0] + "." + array[1] + ".txt";
            myObj = new File(PATH + "\\" + name);
            myReader = new Scanner(myObj);
            if (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] info = data.split("[;]");
                String veiculo = null;
                if (scooterBD.isScooter(Integer.parseInt(info[0]))) {
                    veiculo = scooterBD.getScooterByID(Integer.parseInt(info[0]));
                }
                if (droneBD.isDrone(Integer.parseInt(info[0]))) {
                    veiculo = droneBD.getDroneByID(Integer.parseInt(info[0]));
                }
//                return new Pair<>(info[2],new Pair<>("ola",Integer.parseInt(info[1])));
                return new Pair<>(info[2], new Pair<>(veiculo, Integer.parseInt(info[1])));
            }

        } catch (FileNotFoundException | NumberFormatException e) {
        } finally {
            if (myReader != null)
                myReader.close();
            if (myObj != null) {
                boolean delete = myObj.delete();
            }
        }
        return null;
    }
}
