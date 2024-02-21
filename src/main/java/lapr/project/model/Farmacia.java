package lapr.project.model;

/**
 * classe Farmacia
 */
public class Farmacia {
    private int id;
    private final String nome;
    private final int nif;
    private final int telefone;
    private final String email;
    private String emailGestor;
    private String moradaId;
    private LugarEstacionamento lugarEstacionamento;
    private LugarDrone lugarDrone;

    /**
     * construtor
     * @param id id da farmacia
     * @param nome nome da farmacia
     * @param nif nif da farmacia
     * @param telefone telefone da farmacia
     * @param email email da farmacia
     * @param emailGestor email do gestor da farmacia
     * @param moradaId id da morada da farmacia
     * @param lugarDrone lugar de drone
     */
    public Farmacia(int id, String nome, int nif, int telefone, String email, String emailGestor, String moradaId, LugarDrone lugarDrone) {
        this.id = id;
        this.nome = nome;
        this.nif = nif;
        this.telefone = telefone;
        this.email = email;
        this.emailGestor = emailGestor;
        this.moradaId = moradaId;
        this.lugarDrone = lugarDrone;
    }

    /**
     * construtor
     * @param id id da farmacia
     * @param nome nome da farmacia
     * @param nif nif da farmacia
     * @param telefone telefone da farmacia
     * @param email email da farmacia
     * @param emailGestor email do gestor da farmacia
     * @param moradaId id da morada da farmacia
     * @param lugarEstacionamento lugar de estacionamento
     */
    public Farmacia(int id, String nome, int nif, int telefone, String email, String emailGestor, String moradaId, LugarEstacionamento lugarEstacionamento) {
        this.id = id;
        this.nome = nome;
        this.nif = nif;
        this.telefone = telefone;
        this.email = email;
        this.emailGestor = emailGestor;
        this.moradaId = moradaId;
        this.lugarEstacionamento=lugarEstacionamento;
    }

    /**
     * construtor
     * @param id id da farmacia
     * @param nome nome da farmacia
     * @param nif nif da farmacia
     * @param telefone telefone da farmacia
     * @param email email da farmacia
     * @param emailGestor email do gestor da farmacia
     * @param moradaId id da morada da farmacia
     */
    public Farmacia(int id, String nome, int nif, int telefone, String email, String emailGestor, String moradaId) {
        this.id = id;
        this.nome = nome;
        this.nif = nif;
        this.telefone = telefone;
        this.email = email;
        this.emailGestor = emailGestor;
        this.moradaId = moradaId;
    }

    /**
     * construtor
     * @param nome nome da farmacia
     * @param nif nif da farmacia
     * @param telefone telefone da farmacia
     * @param email email da farmacia
     */
    public Farmacia(String nome, int nif, int telefone, String email) {
        this.nome = nome;
        this.nif = nif;
        this.telefone = telefone;
        this.email = email;
    }

    /**
     * getter para id da farmacia
     * @return id da farmacia
     */
    public int getId() {
        return id;
    }

    /**
     * getter para lugar de estacionamento
     * @return lugar de estacionamento
     */
    public LugarEstacionamento getLugarEstacionamento() {
        return lugarEstacionamento;
    }

    /**
     * getter para lugar de drone
     * @return lugar de drone
     */
    public LugarDrone getLugarDrone() {
        return lugarDrone;
    }

    /**
     * getter para id da morada
     * @return id da morada
     */
    public String getMoradaId() {
        return moradaId;
    }

    /**
     * farmacia to string
     * @return farmacia em string
     */
    @Override
    public String toString() {
        return String.format("ID: %d%nNome: %s%nNIF: %d%nTelefone: %d%nEmail: %s%nEmailGestor: %s%nMorada ID: %s%n",id,nome,nif,telefone,email,emailGestor,moradaId);
    }
}
