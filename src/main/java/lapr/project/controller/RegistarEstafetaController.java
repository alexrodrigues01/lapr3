package lapr.project.controller;

import lapr.project.data.FarmaciaBD;
import lapr.project.data.MoradaBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.utils.Constantes;
import lapr.project.model.Farmacia;
import lapr.project.data.EstafetaBD;
import lapr.project.model.autorizacao.model.SessaoUtilizador;
import lapr.project.utils.ValidarParametros;

import java.sql.SQLException;

/**
 * classe referente ao registo do estafeta
 */
public class RegistarEstafetaController {

    private final FarmaciaBD farmaciaBD;
    private final UtilizadorBD utilizadorBD;
    private final EstafetaBD estafetaBD;
    private final MoradaBD moradaBD;

    /**
     * construtor registar estafeta controller
     * @param farmaciaBD variavel farmaciaBD
     * @param utilizadorBD variavel utilizadorBD
     * @param estafetaBD variavel estafetaBD
     * @param moradaBD variavel moradaBD
     */
    public RegistarEstafetaController(FarmaciaBD farmaciaBD, UtilizadorBD utilizadorBD, EstafetaBD estafetaBD, MoradaBD moradaBD) {
        this.farmaciaBD = farmaciaBD;
        this.utilizadorBD = utilizadorBD;
        this.estafetaBD = estafetaBD;
        this.moradaBD = moradaBD;
    }

    /**
     * validar estafeta
     * @param email email do estafeta
     * @param password password do estafeta
     * @param nome nome do estafeta
     * @param nif nif do estafeta
     * @param telefone telefone do estafeta
     * @param numSegSocial numSegSocial do estafeta
     * @param cargaMaxima cargaMaxima do estafeta
     * @param morada morada do estafeta
     * @param latitude latitude da morada do estafeta
     * @param longitude longitude da morada do estafeta
     * @param altitude altitude da morada do estafeta
     */
    public void validarEstafeta(String email, String password, String nome, int nif, int telefone, String numSegSocial,double cargaMaxima, String morada, double latitude, double longitude, double altitude){

        ValidarParametros.validarParametrosUtilizador(email, password, nome, nif, telefone);
        ValidarParametros.validarParametrosEstafeta(numSegSocial, cargaMaxima);
        ValidarParametros.validaParametrosMorada(morada, longitude, latitude);
        if(!moradaBD.validaMorada(morada,latitude,longitude,altitude))
            throw new IllegalArgumentException("Morada inválida");
        if(!estafetaBD.validaEstafeta(email,nif,numSegSocial))
            throw new IllegalArgumentException("Estafeta inválido");
    }

    /**
     * regista o estafeta na base de dados
     * @param email email do estafeta
     * @param password password do estafeta
     * @param nome nome do estafeta
     * @param nif nif do estafeta
     * @param telefone telefone do estafeta
     * @param numSegSocial numSegSocial do estafeta
     * @param cargaMaxima cargaMaxima do estafeta
     * @param morada morada do estafeta
     * @param latitude latitude da morada do estafeta
     * @param longitude longitude da morada do estafeta
     * @param altitude altitude da morada do estafeta
     * @return true se conseguiu
     * @throws SQLException erro no registo na base de dados
     */
    public boolean registarEstafeta(String email, String password, String nome, int nif, int telefone, String numSegSocial, double cargaMaxima, String morada, double latitude, double longitude, double altitude) throws SQLException {
        utilizadorBD.novoUtilizador(email, password,nome,nif,telefone, Constantes.PAPEL_ESTAFETA,morada,latitude,longitude,altitude);
        AplicacaoPOT app = AplicacaoPOT.getInstance();
        SessaoUtilizador sessao = app.getSessaoAtual();
        String emailGestor = sessao.getEmailUtilizador();
        Farmacia farmacia = farmaciaBD.getFarmaciabyGestor(emailGestor);
        int id = farmacia.getId();
        estafetaBD.registaEstafeta(email, id, numSegSocial, cargaMaxima);
        return true;
    }
}
