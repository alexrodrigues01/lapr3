package lapr.project.controller;

import lapr.project.data.DroneBD;
import lapr.project.data.FarmaciaBD;
import lapr.project.model.Drone;
import lapr.project.model.Farmacia;
import lapr.project.model.autorizacao.model.SessaoUtilizador;

import java.util.List;

public class RemoverDroneController {

    private final DroneBD droneBD;

    private final FarmaciaBD farmaciaBD;

    /**
     * construtor RemoverDroneController
     * @param droneBD droneBD
     * @param farmaciaBD farmaciaBD
     */
    public RemoverDroneController(DroneBD droneBD,FarmaciaBD farmaciaBD) {
        this.droneBD = droneBD;
        this.farmaciaBD=farmaciaBD;
    }

    /**
     * retorna a lista de drones da farmacia do gestor a utilizar a app
     * @return lista de drones da farmacia do gestor a utilizar a app
     */
    public List<Drone> getListaDrone() {
        SessaoUtilizador sessaoUtilizador= AplicacaoPOT.getInstance().getSessaoAtual();
        String email=sessaoUtilizador.getEmailUtilizador();
        Farmacia farmacia= farmaciaBD.getFarmaciabyGestor(email);
        return droneBD.getDronesByFarmID(farmacia.getId());
    }

    /**
     * retorna drone com base no seu id
     * @param droneID id do drone
     * @return drone com o id definido
     */
    public String getDroneByID(int droneID){
        return droneBD.getDroneByID(droneID);
    }

    /**
     * remove drone com o id especifico
     * @param droneID id do drone
     * @return boolean remover
     */
    public boolean removerDrone(int droneID){
        return droneBD.removerDrone(droneID);
    }
}
