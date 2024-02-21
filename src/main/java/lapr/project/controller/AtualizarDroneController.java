package lapr.project.controller;

import lapr.project.data.DroneBD;
import lapr.project.data.FarmaciaBD;
import lapr.project.model.Drone;
import lapr.project.model.Farmacia;
import lapr.project.model.autorizacao.model.SessaoUtilizador;

import java.util.List;

/**
 * classe AtualizarDroneController
 */
public class AtualizarDroneController {
    private int id;
    private int carga;
    private double capacidadeBateriaDrone;
    private int estadoDroneID;
    private double consumoHoraBateria;
    private double tensaoBateriaDrone;
    private double massa;
    private double potencia;
    private double velocidadeDrone;
    private double avionics;
    private double drag;
    private double rotorsDrone;
    private double cargaMaxima;
    private final DroneBD droneBD;
    private final FarmaciaBD farmaciaBD;

    /**
     * construtor AtualizarDroneController
     * @param droneBD droneBD
     * @param farmaciaBD farmaciaBD
     */
    public AtualizarDroneController(DroneBD droneBD,FarmaciaBD farmaciaBD) {
        this.droneBD = droneBD;
        this.farmaciaBD=farmaciaBD;
    }

    /**
     * lista de drones
     * @return lista de drones
     */
    public List<Drone> getListaDrone() {
        SessaoUtilizador sessaoUtilizador= AplicacaoPOT.getInstance().getSessaoAtual();
        String email=sessaoUtilizador.getEmailUtilizador();
        Farmacia farmacia= farmaciaBD.getFarmaciabyGestor(email);
        return droneBD.getDronesByFarmID(farmacia.getId());
    }

    /**
     * lista dados drone segundo o seu id
     * @param droneID id do drone
     * @return lista dados drone
     */
    public List<String> getDroneByID(int droneID) {
        List<String> dadosDrone = droneBD.getDadosDronebyID(droneID);

        id = Integer.parseInt(dadosDrone.get(0));
        carga = Integer.parseInt(dadosDrone.get(1));
        capacidadeBateriaDrone = Double.parseDouble(dadosDrone.get(2));
        estadoDroneID = Integer.parseInt(dadosDrone.get(3));
        consumoHoraBateria = Double.parseDouble(dadosDrone.get(4));
        tensaoBateriaDrone = Double.parseDouble(dadosDrone.get(5));
        massa = Double.parseDouble(dadosDrone.get(6));
        potencia = Double.parseDouble(dadosDrone.get(7));
        velocidadeDrone = Double.parseDouble(dadosDrone.get(8));
        avionics = Double.parseDouble(dadosDrone.get(9));
        drag = Double.parseDouble(dadosDrone.get(10));
        rotorsDrone = Double.parseDouble(dadosDrone.get(11));
        cargaMaxima = Double.parseDouble(dadosDrone.get(12));

        return dadosDrone;
    }

    /**
     * valida dados do drone
     * @param cargaDrone cargaDrone do drone
     * @param capacidadeBateriaDrone capacidadeBateriaDrone do drone
     * @param estadoDroneID estadoDroneID do drone
     * @param consumoHoraBateriaDrone consumoHoraBateriaDrone do drone
     * @param tensaoBateriaDrone tensaoBateriaDrone do drone
     * @param massa massa do drone
     * @param potencia potencia do drone
     * @param velocidade velocidade do drone
     * @param avionics avionics do drone
     * @param drag drag do drone
     * @param rotors rotors do drone
     * @param cargaMaxima cargaMaxima do drone
     */
    public void validaDados(int cargaDrone, double capacidadeBateriaDrone, int estadoDroneID, double consumoHoraBateriaDrone, double tensaoBateriaDrone, double massa, double potencia,double velocidade, double avionics, double drag, double rotors, double cargaMaxima) {
        Drone drone = new Drone(id,cargaDrone,capacidadeBateriaDrone,estadoDroneID,consumoHoraBateriaDrone,tensaoBateriaDrone,massa,potencia,velocidade,avionics,drag,rotors,cargaMaxima);
        this.carga = drone.getCarga();
        this.capacidadeBateriaDrone = drone.getCapacidadeBateria();
        this.estadoDroneID = drone.getEstadoDroneID();
        this.consumoHoraBateria = drone.getConsumoHoraBateria();
        this.tensaoBateriaDrone = drone.getTensaoBateria();
        this.massa = drone.getMassa();
        this.potencia = drone.getPotenciaMotor();
        this.velocidadeDrone = drone.getVelocidade();
        this.avionics = drone.getAvionics();
        this.drag = drone.getDrag();
        this.rotorsDrone = drone.getRotors();
        this.cargaMaxima = drone.getCargaMaxima();
    }

    /**
     * update dados drone
     * @return boolean
     */
    public boolean updateDados() {
        return droneBD.updateDadosDrone(id, carga, capacidadeBateriaDrone, estadoDroneID, consumoHoraBateria, tensaoBateriaDrone, massa, potencia, velocidadeDrone, avionics, drag, rotorsDrone, cargaMaxima);

    }
}
