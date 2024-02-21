package lapr.project.controller;

import lapr.project.data.FarmaciaBD;
import lapr.project.data.ScooterBD;
import lapr.project.model.Farmacia;
import lapr.project.model.Scooter;
import lapr.project.model.autorizacao.model.SessaoUtilizador;

import java.util.List;

/**
 * The type Remover scooter controller.
 */
public class RemoverScooterController {

    private final ScooterBD scooterBD;

    private final FarmaciaBD farmaciaBD;

    /**
     * Instantiates a new Remover scooter controller.
     *
     * @param scooterBD  the scooter bd
     * @param farmaciaBD the farmacia bd
     */
    public RemoverScooterController(ScooterBD scooterBD, FarmaciaBD farmaciaBD) {
        this.scooterBD = scooterBD;
        this.farmaciaBD=farmaciaBD;
    }

    /**
     * Retorna a lista de scooters da farmácia do gestor
     *
     * @return the lista scooter
     */
    public List<Scooter> getListaScooter() {
        SessaoUtilizador sessaoUtilizador= AplicacaoPOT.getInstance().getSessaoAtual();
        String email=sessaoUtilizador.getEmailUtilizador();
        Farmacia farmacia= farmaciaBD.getFarmaciabyGestor(email);
        return scooterBD.getScootersByFarmID(farmacia.getId());
    }

    /**
     * Get scooter by id string.
     *
     * @param scooterID the scooter id
     * @return the string
     */
    public String getScooterByID(int scooterID){
        return scooterBD.getScooterByID(scooterID);
    }

    /**
     * Remove a scooter selecionada.
     *
     * @param scooterID the scooter id
     * @return the boolean
     */
    public boolean removerScooter(int scooterID){
       return scooterBD.removerScooter(scooterID);
    }

}
