package lapr.project.controller;

import com.google.zxing.NotFoundException;
import lapr.project.data.FarmaciaBD;
import lapr.project.data.ScooterBD;
import lapr.project.model.Farmacia;
import lapr.project.model.LugarEstacionamento;
import lapr.project.model.Scooter;
import lapr.project.model.autorizacao.model.SessaoUtilizador;
import lapr.project.utils.Pair;

/**
 * The type Adicionar scooter controller.
 */
public class AdicionarScooterController {
    private final ScooterBD scooterBD;
    private final FarmaciaBD farmaciaBD;
    public AdicionarScooterController(ScooterBD scooterBD,FarmaciaBD farmaciaBD) {
        this.scooterBD = scooterBD;
        this.farmaciaBD=farmaciaBD;
    }

    /**
     * The Farmacia.
     */
    Farmacia farmacia;
    /**
     * The Lugar.
     */
    LugarEstacionamento lugar;

    /**
     * Método responsável por criar a scooter e adicionar essa scooter ao lugar
     *
     * @param carga a carga atual da scooter
     * @param capacidadeBateria a capacidade da bateria da scooter
     * @return the scooter
     * @throws Exception the exception
     */
    public Pair<Scooter,LugarEstacionamento> novaScooter(int carga, double capacidadeBateria,int estadoScooterID,double consumoHoraBateria,double tensaoBateria,double peso,double potenciaMotor) throws Exception {
        SessaoUtilizador sessaoUtilizador= AplicacaoPOT.getInstance().getSessaoAtual();
        int id= scooterBD.lastID()+1;
        Scooter scooter= new Scooter(id,carga,capacidadeBateria,estadoScooterID,consumoHoraBateria,tensaoBateria,peso,potenciaMotor);
        String email=sessaoUtilizador.getEmailUtilizador();
        farmacia= farmaciaBD.getFarmaciabyGestor(email);
        lugar=farmaciaBD.getLugarEstacionamentobyFarm(farmacia.getId());
        lugar.setScooter(scooter.getId());
        return new Pair<>(scooter,lugar);
    }

    /**
     * Método responsável por chamar os métodos da FarmaciaBD e da ScooterBD que atualizam os dados do lugar
     * e inserem uma nova scooter na base de dados
     *
     * @param lugar   o lugar a ser atualizado
     * @param scooter a nova scooter
     */
    public boolean update(LugarEstacionamento lugar, Scooter scooter) throws NotFoundException {
        return scooterBD.updateScooter(scooter) && farmaciaBD.updateLugar(lugar);

    }
}
