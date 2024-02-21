package lapr.project.controller;

import lapr.project.data.FarmaciaBD;
import lapr.project.data.ScooterBD;
import lapr.project.model.Farmacia;
import lapr.project.model.Scooter;
import lapr.project.model.autorizacao.model.SessaoUtilizador;
import lapr.project.utils.ValidarParametros;

import java.util.List;

/**
 * The type Atualizar scooter controller.
 */
public class AtualizarScooterController {
    private String id;
    private String carga;
    private String capacidadeBateria;
    private String estadoScooterID;
    private String consumoHoraBateria;
    private String tensaoBateria;
    private String peso;
    private String potenciaMotor;
    private final ScooterBD scooterBD;
    private final FarmaciaBD farmaciaBD;

    /**
     * Instantiates a new Atualizar scooter controller.
     *
     * @param scooterBD  the scooter bd
     * @param farmaciaBD the farmacia bd
     */
    public AtualizarScooterController(ScooterBD scooterBD,FarmaciaBD farmaciaBD) {
        this.scooterBD = scooterBD;
        this.farmaciaBD=farmaciaBD;
    }

    /**
     * Retorna a lista de scooters da farmácia do gestor
     *
     * @return a lista de scooters
     */
    public List<Scooter> getListaScooter() {
        SessaoUtilizador sessaoUtilizador= AplicacaoPOT.getInstance().getSessaoAtual();
        String email=sessaoUtilizador.getEmailUtilizador();
        Farmacia farmacia= farmaciaBD.getFarmaciabyGestor(email);
        return scooterBD.getScootersByFarmID(farmacia.getId());
    }

    /**
     * Gets scooter by id.
     *
     * @param scooterID the scooter id
     * @return the scooter by id
     */
    public List<String> getScooterByID(int scooterID) {
        List<String> dados = scooterBD.getDadosScooterbyID(scooterID);
        id = dados.get(0);
        carga = dados.get(1);
        capacidadeBateria = dados.get(2);
        estadoScooterID = dados.get(3);
        consumoHoraBateria = dados.get(4);
        tensaoBateria = dados.get(5);
        peso = dados.get(6);
        potenciaMotor = dados.get(7);
        return dados;
    }

    /**
     * Valida dados.
     *
     * @param carga              the carga
     * @param capacidadeBateria  the capacidade bateria
     * @param estadoScooterID    the estado scooter id
     * @param consumoHoraBateria the consumo hora bateria
     * @param tensaoBateria      the tensao bateria
     * @param peso               the peso
     * @param potenciaMotor      the potencia motor
     */
    public void validaDados(String carga, String capacidadeBateria, String estadoScooterID, String consumoHoraBateria, String tensaoBateria, String peso, String potenciaMotor) {
        ValidarParametros.validaParametrosScooter(carga, capacidadeBateria, estadoScooterID, consumoHoraBateria, tensaoBateria, peso, potenciaMotor);
        this.carga = carga;
        this.capacidadeBateria = capacidadeBateria;
        this.estadoScooterID = estadoScooterID;
        this.consumoHoraBateria = consumoHoraBateria;
        this.tensaoBateria = tensaoBateria;
        this.peso = peso;
        this.potenciaMotor = potenciaMotor;

    }

    /**
     * Atualiza os dados da scooter com a nova informação
     *
     * @return the boolean
     */
    public boolean updateDados() {
        return scooterBD.updateDados(id, carga, capacidadeBateria, estadoScooterID, consumoHoraBateria, tensaoBateria, peso, potenciaMotor);

    }


}
