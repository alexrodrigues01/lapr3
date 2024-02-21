package lapr.project.utils;

/**
 * Metodo para validar parametros
 *
 */
public class ValidarParametros {

    /**
     * Construtor private para evitar a criacao de objetos deste tipo
     */
    private ValidarParametros() {
    }

    /**
     * Metodo para validar os parametros do utilizador
     *
     * @param email email utilizador
     * @param password password utilizador
     * @param nome nome utilizador
     * @param nif nif utilizador
     * @param telefone telefone utilizador
     */
    public static void validarParametrosUtilizador(String email, String password, String nome, int nif, int telefone) {
        if (!email.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b")) {
            throw new IllegalArgumentException("Endereço de email inválido.");
        }
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password inválida.");
        }
        if (nome.isEmpty() || !nome.matches("^([A-Z][a-z]+([ ]?[a-z]?['-]?[A-Z][a-z]+)*)$")) {
            throw new IllegalArgumentException("Nome inválido.");
        }
        if (String.valueOf(nif).length() != 9) {
            throw new IllegalArgumentException("NIF inválido");
        }
        if (String.valueOf(telefone).length() != 9) {
            throw new IllegalArgumentException("Número de telefone inválido");
        }
    }

    /**
     * Metodo para validar os parametros do estafeta
     *
     * @param numSegSocial numero da seguranca social do estafeta
     * @param cargaMaxima carga maxima do estafeta
     */
    public static void validarParametrosEstafeta(String numSegSocial, double cargaMaxima) {
        if (numSegSocial.length() != 11) {
            throw new IllegalArgumentException("Número de Segurança Social inválido");
        }
        if (cargaMaxima <= 0) {
            throw new IllegalArgumentException("Carga Máxima não pode ser igual ou inferior a 0");
        }
    }

    /**
     * Metodo para validar os parametros da morada
     *
     * @param morada nome da morada
     * @param longitude longitude da morada
     * @param latitude latitude da morada
     */
    public static void validaParametrosMorada(String morada, double longitude, double latitude) {
        if (!String.valueOf(latitude).matches("^(\\+|-)?(?:90(?:(?:\\.0{1,15})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,15})?))$")) {
            throw new IllegalArgumentException("Latitude inválida");
        }
        if (!String.valueOf(longitude).matches("^(\\+|-)?(?:180(?:(?:\\.0{1,15})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,15})?))$")) {
            throw new IllegalArgumentException("Longitude inválida");
        }
        if (morada.isEmpty()) {
            throw new IllegalArgumentException("Morada inválida");
        }
    }

    /**
     * Metodo para validar os parametros da farmacia
     *
     * @param nome nome da farmacia
     * @param nif nif da farmacia
     * @param telemovel telemovel da farmacia
     * @param email email da farmacia
     */
    public static void validaParametrosFarmacia(String nome, int nif, int telemovel, String email) {
        if (!email.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b")) {
            throw new IllegalArgumentException("Endereço de email inválido.");
        }
        if (nome.isEmpty()) {
            throw new IllegalArgumentException("Nome inválido.");
        }
        if (String.valueOf(nif).length() != 9) {
            throw new IllegalArgumentException("NIF inválido");
        }
        if (String.valueOf(telemovel).length() != 9) {
            throw new IllegalArgumentException("Número de telefone inválido");
        }
    }

    /**
     * Metodo para validar os parametros da scooter
     *
     * @param carga carga da scooter
     * @param capacidadeBateria capacidade da Bateria da scooter
     * @param estadoScooterID id do estado da scooter
     * @param consumoHoraBateria consumo hora da bateria da scooter
     * @param tensaoBateria tensao da bateria da scooter
     * @param peso peso da scooter
     * @param potenciaMotor potencia do motor da scooter
     */
    public static void validaParametrosScooter(String carga, String capacidadeBateria, String estadoScooterID, String consumoHoraBateria, String tensaoBateria, String peso, String potenciaMotor) {
        if (carga.isEmpty() || Integer.parseInt(carga) < 0 || Integer.parseInt(carga) > 100) {
            throw new IllegalArgumentException("Carga inválida");
        }
        if (capacidadeBateria.isEmpty() || Double.compare(Double.parseDouble(capacidadeBateria), 0) < 1) {
            throw new IllegalArgumentException("Capacidade da bateria inválida");
        }
        if (estadoScooterID.isEmpty() || Integer.parseInt(estadoScooterID) < 1) {
            throw new IllegalArgumentException("Id do estado da scooter inválido");
        }
        if (consumoHoraBateria.isEmpty() || Double.parseDouble(consumoHoraBateria) < 0) {
            throw new IllegalArgumentException("Consumo da bateria inválido");
        }
        if (tensaoBateria.isEmpty() || Double.parseDouble(tensaoBateria) < 0) {
            throw new IllegalArgumentException("Tensão da bateria inválida");
        }
        if (peso.isEmpty() || Double.parseDouble(peso) < 0) {
            throw new IllegalArgumentException("Peso da scooter inválido");
        }
        if (potenciaMotor.isEmpty() || Double.parseDouble(potenciaMotor) < 0) {
            throw new IllegalArgumentException("Potencia do motor da scooter inválida");
        }
    }

    /**
     * valida parametros do produto
     *
     * @param nome nome do produto
     * @param precoUnitario preco unitario do produto
     */
    public static void validarParametrosProduto(String nome, double precoUnitario, int pesoUnitario, int iva) {

        if (nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (Double.compare(precoUnitario, 0) < 1) {
            throw new IllegalArgumentException("O preço não pode ser menor ou igual a 0.");
        }
        if (pesoUnitario < 1) {
            throw new IllegalArgumentException("O peso não pode ser menor ou igual a 0.");
        }
        if (iva < 0 || iva > 100) {
            throw new IllegalArgumentException("Iva inválido.");
        }
    }

    /**
     * Metodo para validar os parametros do cliente
     *
     * @param nCartCred numero de cartao de credito do cliente
     * @param valCartCred validade do cartao de credito do cliente
     * @param cvv cvv do cartao do cliente
     */
    public static void validarParametrosCliente(String nCartCred, String valCartCred, int cvv) {

        if (!nCartCred.matches("^[1-9][0-9]{15}")) {
            throw new IllegalArgumentException("Número de Cartão Crédito inválido");
        }
        if (valCartCred.isEmpty() || !valCartCred.matches("^(0[1-9]|1[0-2])\\/\\d{2}$")) {
            throw new IllegalArgumentException("Validade do Cartão Crédito inválido.");
        }
        if (String.valueOf(cvv).length() != 3) {
            throw new IllegalArgumentException("CVV inválido.");
        }
    }

    /**
     * Metodo para validar os parametros do parque de estacionamento
     *
     * @param lugaresSemCarregamento lugares sem carregamento do parque de
     * estacionamento
     * @param lugaresComCarregamento lugares com carregamento do parque de
     * estacionamento
     * @param capacidadeEnergia capacidade de energia do parque de
     * estacionamento
     */
    public static void validaParametrosLugarEstacionamento(int lugaresSemCarregamento, int lugaresComCarregamento, double capacidadeEnergia) {
        if (lugaresSemCarregamento < 0) {
            throw new IllegalArgumentException("Número de lugares de estacionamento sem carregamento inválido");
        }
        if (lugaresComCarregamento < 0) {
            throw new IllegalArgumentException("Número de lugares de estacionamento com carregamento inválido");
        }
        if (capacidadeEnergia < 0) {
            throw new IllegalArgumentException("Capacidade de energia do parque inválida");
        }
    }

    /**
     * Metodo para validar os parametros do NIF
     *
     * @param nif nif para validar
     */
    public static void validaParametroNIF(int nif) {
        if (String.valueOf(nif).length() != 9) {
            throw new IllegalArgumentException("NIF inválido");
        }
    }
}
