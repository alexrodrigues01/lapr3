package lapr.project.controller;


import lapr.project.data.ClienteBD;
import lapr.project.data.MoradaBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.utils.Constantes;
import lapr.project.utils.ValidarParametros;

public class RegistoClienteController {

    private final ClienteBD clienteBD;
    private final UtilizadorBD utilizadorBD;
    private final MoradaBD moradaBD;

    /**
     * construtor registo cliente controller
     * @param clienteBD clienteBD
     * @param utilizadorBD utilizadorBD
     * @param moradaBD moradaBD
     */
    public RegistoClienteController(ClienteBD clienteBD, UtilizadorBD utilizadorBD, MoradaBD moradaBD) {
        this.clienteBD = clienteBD;
        this.utilizadorBD = utilizadorBD;
        this.moradaBD = moradaBD;
    }

    /**
     * valida cliente
     * @param email
     * @param password
     * @param nome
     * @param nif
     * @param telefone
     * @param morada
     * @param latitude
     * @param longitude
     * @param nCartCred
     * @param valCartCred
     * @param cvv
     */
    public void novoCliente(String email, String password, String nome, int nif, int telefone, String morada, double latitude, double longitude, double altitude, String nCartCred, String valCartCred, int cvv) {
        ValidarParametros.validarParametrosUtilizador(email, password, nome, nif, telefone);
        ValidarParametros.validaParametrosMorada(morada, longitude, latitude);
        ValidarParametros.validarParametrosCliente(nCartCred, valCartCred, cvv);
        if(!moradaBD.validaMorada(morada,latitude,longitude,altitude))
            throw new IllegalArgumentException("Morada inválida");
        if(!clienteBD.validaCliente(email))
            throw new IllegalArgumentException("Cliente inválido");
    }

    /**
     * regista cliente na base de dados como cliente e como utilizador
     * @param email
     * @param password
     * @param nome
     * @param nif
     * @param telefone
     * @param morada
     * @param latitude
     * @param longitude
     * @param nCartCred
     * @param valCartCred
     * @param cvv
     */
    public boolean registarCliente(String email, String password, String nome, int nif, int telefone, String morada, double latitude, double longitude, double altitude, String nCartCred, String valCartCred, int cvv) {
        return utilizadorBD.novoUtilizador(email,password,nome,nif,telefone,Constantes.PAPEL_CLIENTE,morada,latitude,longitude, altitude) && clienteBD.registaCliente(email,nCartCred,valCartCred,cvv);
    }


}
