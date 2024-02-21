package lapr.project.controller;

import lapr.project.data.DroneBD;
import lapr.project.data.ScooterBD;
import lapr.project.utils.Emails;
import lapr.project.ui.FileReaderWriter;
import lapr.project.utils.Pair;


/**
 * The type Enviar email seguranca task.
 */
public class EnviarEmailSegurancaTask {
    private EnviarEmailSegurancaTask() {
    }

    /**
     * Este metodo chama o metodo de ler o ficheiro e envia email sobre o veiculo estar bem estacionado.
     *
     * @param fileName  the file name
     * @param scooterBD the scooter bd
     * @param droneBD   the drone bd
     * @return the boolean
     */
    public static boolean sendEmail(String fileName, ScooterBD scooterBD, DroneBD droneBD){
            Pair<String, Pair<String, String>> pairReturn= FileReaderWriter.lockFileReader(fileName,scooterBD,droneBD);
        if(pairReturn!=null && pairReturn.getKey()!=null && pairReturn.getValue()!=null && pairReturn.getValue().getKey()!=null && pairReturn.getValue().getValue()!=null) {
            Pair<String, String> pair = pairReturn.getValue();
            return Emails.sendEmail("LAPR3-G020", pair.getValue(), "Veiculo lockado com sucesso", "O veiculo com as seguintes informações foi lockado com sucesso\n" + pair.getKey() + "\nCumprimentos,\nGrupo20");
        }
        return false;
    }
}
