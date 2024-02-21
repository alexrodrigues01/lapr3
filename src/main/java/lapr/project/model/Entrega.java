package lapr.project.model;

/**
 * classe referente à entrega
 */
public class Entrega {
    private final int id;
    private final double cargaTotal;
    private final double distanciaTotal;
    private final double consumoEstimando;
    private String emailEstafeta;
    private final int idEstadoEntrega;
    private final int idScooter;

    /**
     * construtor Entrega
     * @param id id da Entrega
     * @param cargaTotal cargaTotal da Entrega
     * @param distanciaTotal distanciaTotal da Entrega
     * @param consumoEstimando consumoEstimando da Entrega
     * @param emailEstafeta emailEstafeta da Entrega
     * @param idEstadoEntrega idEstadoEntrega da Entrega
     * @param idScooter idScooter da Entrega
     */
    public Entrega(int id, double cargaTotal, double distanciaTotal, double consumoEstimando, String emailEstafeta, int idEstadoEntrega, int idScooter) {
        this.id = id;
        this.cargaTotal = cargaTotal;
        this.distanciaTotal = distanciaTotal;
        this.consumoEstimando = consumoEstimando;
        this.emailEstafeta = emailEstafeta;
        this.idEstadoEntrega = idEstadoEntrega;
        this.idScooter = idScooter;
    }

    /**
     * construtor Entrega 2
     * construtor Entrega
     * @param id id da Entrega
     * @param cargaTotal cargaTotal da Entrega
     * @param distanciaTotal distanciaTotal da Entrega
     * @param consumoEstimando consumoEstimando da Entrega
     * @param idEstadoEntrega idEstadoEntrega da Entrega
     * @param idScooter idScooter da Entrega
     */
    public Entrega(int id, double cargaTotal, double distanciaTotal, double consumoEstimando, int idEstadoEntrega, int idScooter) {
        this.id = id;
        this.cargaTotal = cargaTotal;
        this.distanciaTotal = distanciaTotal;
        this.consumoEstimando = consumoEstimando;
        this.idEstadoEntrega = idEstadoEntrega;
        this.idScooter = idScooter;
    }

    /**
     * getter id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * getter cargaTotal
     * @return cargaTotal
     */
    public double getCargaTotal() {
        return cargaTotal;
    }

    /**
     * getter distanciaTotal
     * @return distanciaTotal
     */
    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    /**
     * getter consumoEstimando
     * @return consumoEstimando
     */
    public double getConsumoEstimando() {
        return consumoEstimando;
    }

    /**
     * getter emailEstafeta
     * @return emailEstafeta
     */
    public String getEmailEstafeta() {
        return emailEstafeta;
    }

    /**
     * getter idEstadoEntrega
     * @return idEstadoEntrega
     */
    public int getIdEstadoEntrega() {
        return idEstadoEntrega;
    }

    /**
     * to string entrega
     * @return to string
     */
    @Override
    public String toString() {
        return String.format("ID: %d%nCarga Total: %f%nDistancia Total: %f%nConsumo Estimando: %f%nEmail estafeta: %s%nId estado da entrega: %d%nID do veiculo: %d%n", id, cargaTotal, distanciaTotal, consumoEstimando, emailEstafeta, idEstadoEntrega, idScooter);
    }
}
