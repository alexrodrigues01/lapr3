package lapr.project.controller;

import lapr.project.data.DroneBD;
import lapr.project.data.ScooterBD;
import lapr.project.utils.Emails;
import lapr.project.ui.FileReaderWriter;
import lapr.project.utils.Pair;


/**
 * The type Enviar email estimativa task.
 */
public class EnviarEmailEstimativaTask {
    private EnviarEmailEstimativaTask() {
    }

    private static final String PATH ="src\\main\\resources\\ScooterInfo";

    /**
     * Este metodo chama o metodo de ler o ficheiro e envia email sobre a estimativa.
     *
     * @param name      the name
     * @param scooterBD the scooter bd
     * @param droneBD   the drone bd
     * @return the boolean
     */
    public static boolean sendEmail(String name, ScooterBD scooterBD, DroneBD droneBD) {
       Pair<String, Pair<String,Integer>> pair=FileReaderWriter.estimateFileReader(name,scooterBD,droneBD);
        if(pair!=null && pair.getKey()!=null && pair.getValue()!=null && pair.getValue().getKey()!=null && pair.getValue().getValue()!=null) {
            return Emails.sendEmail("LAPR3-G020", pair.getKey(), "Tempo estimado de carregamento", "O veiculo com as seguintes informações irá demorar " + pair.getValue().getValue() + " minutos a carregar! \n" + pair.getValue().getKey() + "\nCumprimentos,\nGrupo20");
        }
        return false;
    }

}
