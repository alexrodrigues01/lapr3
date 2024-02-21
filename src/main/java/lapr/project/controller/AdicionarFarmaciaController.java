package lapr.project.controller;

import lapr.project.data.FarmaciaBD;
import lapr.project.data.GestorFarmaciaBD;
import lapr.project.data.MoradaBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.utils.Constantes;
import lapr.project.utils.ValidarParametros;

public class AdicionarFarmaciaController {

    private final FarmaciaBD farmaciaBD;
    private final GestorFarmaciaBD gestorBD;
    private final UtilizadorBD utilizadorBD;
    private final MoradaBD moradaBD;

    private String nomeFarmacia;
    private int nifFarmacia;
    private int telemovelFarmacia;
    private String emailFarmacia;

    private int lugaresComSuporte;
    private int lugaresSemSuporte;
    private double capacidadeEnergia;

    private String nomeGestor;
    private String emailGestor;
    private int nifGestor;
    private int telemovelGestor;
    private String passwordGestor;

    private String moradaFarmacia;
    private double longitudeFarmacia;
    private double latitudeFarmacia;
    private double altitudeFarmacia;

    private String moradaGestor;
    private double longitudeGestor;
    private double latitudeGestor;
    private double altitudeGestor;

    /**
     * construtor adicionar farmacia controller
     * @param farmaciaBD farmaciaBD
     * @param gestorBD gestorBD
     * @param utilizadorBD utilizadorBD
     * @param moradaBD moradaBD
     */
    public AdicionarFarmaciaController(FarmaciaBD farmaciaBD, GestorFarmaciaBD gestorBD, UtilizadorBD utilizadorBD, MoradaBD moradaBD) {
        this.farmaciaBD = farmaciaBD;
        this.gestorBD = gestorBD;
        this.utilizadorBD = utilizadorBD;
        this.moradaBD = moradaBD;

    }

    /**
     * valida os atributos da farmacia guarda os atributos na classe
     *
     * @param nome      nome da farmacia
     * @param nif       nif da farmacia
     * @param telemovel telemovel da farmacia
     * @param email     email da farmacia
     */
    public void validaFarmacia(String nome, int nif, int telemovel, String email) {

        ValidarParametros.validaParametrosFarmacia(nome, nif, telemovel, email);
        if (!farmaciaBD.validaFarmacia(email, nif))
            throw new IllegalArgumentException("Farmacia invalida");

        this.nomeFarmacia = nome;
        this.nifFarmacia = nif;
        this.emailFarmacia = email;
        this.telemovelFarmacia = telemovel;
    }

    /**
     * Valida se a quantidade de lugares de estacionamento com e sem suporte para carregamento esta correta
     * guarda os dados na classe
     *
     * @param lugaresComSuporte lugares de estacionamento com suporte para carregamento
     * @param lugaresSemSuporte lugares de estacionamento sem suporte para carregamento
     * @return
     */
    public void validaLugaresEstacionamento(int lugaresComSuporte, int lugaresSemSuporte, double capacidadeEnergia) {

        ValidarParametros.validaParametrosLugarEstacionamento(lugaresSemSuporte, lugaresComSuporte, capacidadeEnergia);

        this.lugaresComSuporte = lugaresComSuporte;
        this.lugaresSemSuporte = lugaresSemSuporte;
        this.capacidadeEnergia = capacidadeEnergia;

    }

    /**
     * valida o gestor da farmacia
     * guarda dos dados na classe
     *
     * @param nome      nome do gestor
     * @param email     email do gestor
     * @param nif       nif do gestor
     * @param telemovel telemovel do estor
     * @param password  password do gestor
     */
    public void validaGestorFarmacia(String nome, String email, int nif, int telemovel, String password) {

        ValidarParametros.validarParametrosUtilizador(email, password, nome, nif, telemovel);
        if (!gestorBD.validaGestor(email, nif))
            throw new IllegalArgumentException("Existe um utilizador com o mesmo email");

        this.nomeGestor = nome;
        this.emailGestor = email;
        this.nifGestor = nif;
        this.telemovelGestor = telemovel;
        this.passwordGestor = password;

    }

    /**
     * valida as moradas do gestor e da farmavicia
     * guarda dos dados na classe
     *
     * @param moradaFarmacia
     * @param longitudeFarmacia
     * @param latitudeFarmacia
     * @param altitudeFarmacia
     * @param longitudeGestor
     * @param latitudeGestor
     * @param altitudeGestor
     * @return
     */
    public void validaMoradas(String moradaFarmacia, double latitudeFarmacia, double longitudeFarmacia, double altitudeFarmacia,
                              String moradaGestor, double latitudeGestor, double longitudeGestor, double altitudeGestor) {

        ValidarParametros.validaParametrosMorada(moradaFarmacia, longitudeFarmacia, latitudeFarmacia);
        if (!moradaBD.validaMorada(moradaFarmacia, latitudeFarmacia, longitudeFarmacia, altitudeFarmacia))
            throw new IllegalArgumentException("Morada da Farmacia inexistente na base dados");

        if(!moradaBD.validaMoradaFarmacia(moradaFarmacia, latitudeFarmacia, longitudeFarmacia, altitudeFarmacia))
            throw new IllegalArgumentException("Ja existe uma farmacia com essa morada");

        ValidarParametros.validaParametrosMorada(moradaGestor, longitudeGestor, latitudeGestor);
        if (!moradaBD.validaMorada(moradaGestor, latitudeGestor, longitudeGestor, altitudeGestor))
            throw new IllegalArgumentException("Morada da Gestor de Farmacia inexistente na base dados");

        this.moradaFarmacia = moradaFarmacia;
        this.longitudeFarmacia = longitudeFarmacia;
        this.latitudeFarmacia = latitudeFarmacia;
        this.altitudeFarmacia = altitudeFarmacia;

        this.moradaGestor = moradaGestor;
        this.longitudeGestor = longitudeGestor;
        this.latitudeGestor = latitudeGestor;
        this.altitudeGestor = altitudeGestor;

    }

    /**
     * Guarda os dados todos ligados a farmacia (farmacia, gestor de farmacia, lugares de estacionamento e moradas) na base de dados
     *
     * @return true se foi adicionado corretamente à base de dados ou false caso contrario
     */
    public void addDados() {

        if (!utilizadorBD.novoUtilizador(emailGestor, passwordGestor, nomeGestor, nifGestor, telemovelGestor, Constantes.PAPEL_GESTOR_FARMACIA, moradaGestor, latitudeGestor, longitudeGestor, altitudeGestor))
            throw new IllegalArgumentException("Erro ao criar o novo utilizador!");
        if (!gestorBD.novoGestorFarmacia(emailGestor))
            throw new IllegalArgumentException("Erro ao criar o novo gestor!");
        int farmaciaID = farmaciaBD.novaFarmacia(nomeFarmacia, emailFarmacia, telemovelFarmacia, nifFarmacia, moradaFarmacia, latitudeFarmacia, longitudeFarmacia, altitudeFarmacia, emailGestor);
        if (farmaciaID == 0)
            throw new IllegalArgumentException("Erro ao criar a nova farmacia!");
        if (!farmaciaBD.setLugaresScooter(farmaciaID, lugaresSemSuporte, lugaresComSuporte, capacidadeEnergia))
            throw new IllegalArgumentException("Erro ao criar o parque de estacionamento");
        if (!farmaciaBD.associaProdutos(farmaciaID))
            throw new IllegalArgumentException("Erro ao associar os produtos com a farmacia");

    }
}
