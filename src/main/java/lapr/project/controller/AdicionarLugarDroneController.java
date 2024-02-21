package lapr.project.controller;

import lapr.project.data.FarmaciaBD;
import lapr.project.utils.ValidarParametros;

/**
 * The type Adicionar lugar drone controller.
 */
public class AdicionarLugarDroneController {

    private final FarmaciaBD farmaciaBD;
    private int lugaresSemCarregamento;
    private int lugaresComCarregamento;
    private double capacidadeEnergia;
    private int farmaciaID;

    /**
     * Instantiates a new Adicionar lugar drone controller.
     *
     * @param farmaciaBD the farmacia bd
     */
    public AdicionarLugarDroneController(FarmaciaBD farmaciaBD) {
        this.farmaciaBD = farmaciaBD;
    }

    /**
     * Valida parametros boolean.
     *
     * @param lugaresSemCarregamento the lugares sem carregamento
     * @param lugaresComCarregamento the lugares com carregamento
     * @param capacidadeEnergia      the capacidade energia
     * @return the boolean
     */
    public boolean validaParametros(int lugaresSemCarregamento, int lugaresComCarregamento, double capacidadeEnergia){
        try{
            ValidarParametros.validaParametrosLugarEstacionamento(lugaresSemCarregamento,lugaresComCarregamento,capacidadeEnergia);
            this.lugaresSemCarregamento=lugaresSemCarregamento;
            this.lugaresComCarregamento=lugaresComCarregamento;
            this.capacidadeEnergia =capacidadeEnergia;
            return true;
        }catch (IllegalArgumentException exception){
            return false;
        }

    }

    /**
     * Retorna o id da farmácia através do email do gestor.
     *
     * @return id da farmácia
     */
    public int getFarmaciaByGestor(){
        String email= AplicacaoPOT.getInstance().getSessaoAtual().getEmailUtilizador();
        farmaciaID= farmaciaBD.getFarmaciabyGestor(email).getId();
        return farmaciaID;
    }

    /**
     * Set lugares drone boolean.
     *
     * @return the boolean
     */
    public boolean setLugaresDrone(){
       return farmaciaBD.setLugaresDrone(farmaciaID,lugaresSemCarregamento,lugaresComCarregamento,capacidadeEnergia);
    }

    /**
     * Gets lugares sem carregamento.
     *
     * @return the lugares sem carregamento
     */
    public int getLugaresSemCarregamento() {
        return lugaresSemCarregamento;
    }

    /**
     * Gets lugares com carregamento.
     *
     * @return the lugares com carregamento
     */
    public int getLugaresComCarregamento() {
        return lugaresComCarregamento;
    }

    /**
     * Gets capacidade energia.
     *
     * @return the capacidade energia
     */
    public double getCapacidadeEnergia() {
        return capacidadeEnergia;
    }

    /**
     * Gets farmacia id.
     *
     * @return the farmacia id
     */
    public int getFarmaciaID() {
        return farmaciaID;
    }
}
