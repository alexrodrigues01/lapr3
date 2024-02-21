package lapr.project.controller;

import lapr.project.data.DroneBD;
import lapr.project.data.FarmaciaBD;
import lapr.project.model.*;
import lapr.project.model.autorizacao.model.SessaoUtilizador;
import lapr.project.utils.Pair;

public class AdicionarDroneController {

    private final DroneBD droneBD;
    private final FarmaciaBD farmaciaBD;

    /**
     * The Farmacia.
     */
    Farmacia farmacia;
    /**
     * The Lugar.
     */
    LugarDrone lugar;

    /**
     * construtor AdicionarDroneController
     * @param droneBD droneBD
     * @param farmaciaBD farmaciaBD
     */
    public AdicionarDroneController(DroneBD droneBD, FarmaciaBD farmaciaBD) {
        this.droneBD = droneBD;
        this.farmaciaBD = farmaciaBD;
    }

    /**
     * registo de um drone novo
     * @param carga carga do drone
     * @param capacidadeBateria capacidadeBateria do drone
     * @param estadoDroneID estadoDroneID do drone
     * @param consumoHoraBateria consumoHoraBateria do drone
     * @param tensaoBateria tensaoBateria do drone
     * @param massa massa do drone
     * @param potencia potencia do drone
     * @param velocidade velocidade do drone
     * @param avionics avionics do drone
     * @param drag drag do drone
     * @param rotors rotors do drone
     * @param cargaMaxima cargaMaxima do drone
     * @return pair drone e lugar drone
     */
    public Pair<Drone, LugarDrone> novoDrone(int carga, double capacidadeBateria, int estadoDroneID, double consumoHoraBateria, double tensaoBateria, double massa, double potencia, double velocidade, double avionics, double drag, double rotors, double cargaMaxima){
        SessaoUtilizador sessaoUtilizador = AplicacaoPOT.getInstance().getSessaoAtual();
        int id = droneBD.lastIDdrone()+1;
        Drone drone = new Drone(id,carga,capacidadeBateria,estadoDroneID,consumoHoraBateria,tensaoBateria,massa,potencia,velocidade,avionics,drag,rotors,cargaMaxima);
        String email = sessaoUtilizador.getEmailUtilizador();
        farmacia = farmaciaBD.getFarmaciabyGestor(email);
        lugar = farmaciaBD.getLugarDronebyFarm(farmacia.getId());
        lugar.setDrone(drone.getId());
        return new Pair<>(drone,lugar);
    }

    /**
     * update drone e lugar drone
     * @param lugar lugar drone
     * @param drone drone
     * @return boolean update
     */
    public boolean updateDrone(LugarDrone lugar, Drone drone){
        return droneBD.updateDrone(drone) && farmaciaBD.updateLugarDrone(lugar);
    }
}
