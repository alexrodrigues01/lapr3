package lapr.project.model;

import java.util.Objects;

/**
 * classe referente à encomenda
 */
public class Encomenda {
    private final int id;
    private final String emailCliente;
    private final double carga;
    private int idEntrega;
    private int idFarmacia;

    /**
     * construtor encomenda
     * @param id id da encomenda
     * @param emailCliente email do cliente da encomenda
     * @param carga carga da encomenda
     * @param idEntrega id da entrega da encomenda
     */
    public Encomenda(int id, String emailCliente, double carga, int idEntrega) {
        this.id = id;
        this.emailCliente = emailCliente;
        this.carga = carga;
        this.idEntrega = idEntrega;
    }

    /**
     * construtor encomenda 2
     * @param id id da encomenda
     * @param emailCliente email do cliente da encomenda
     * @param carga carga da encomenda
     * @param idEntrega id da entrega da encomenda
     * @param idFarmacia id da farmacia da encomenda
     */
    public Encomenda(int id, String emailCliente, double carga, int idEntrega, int idFarmacia) {
        this.id = id;
        this.emailCliente = emailCliente;
        this.carga = carga;
        this.idEntrega = idEntrega;
        this.idFarmacia = idFarmacia;
    }

    /**
     *
     * construtor encomenda 3
     * @param id id da encomenda
     * @param carga carga da encomenda
     * @param emailCliente email do cliente da encomenda
     */
    public Encomenda(int id,double carga,String emailCliente){
        this.id=id;
        this.carga=carga;
        this.emailCliente=emailCliente;
    }

    /**
     *
     * construtor encomenda 4
     * @param id id da encomenda
     * @param carga carga da encomenda
     * @param emailCliente email do cliente da encomenda
     * @param idFarmacia id da farmacia da encomenda
     */
    public Encomenda(int id,double carga,String emailCliente,int idFarmacia){
        this.id=id;
        this.carga=carga;
        this.emailCliente=emailCliente;
        this.idFarmacia=idFarmacia;
    }

    /**
     * getter carga
     * @return carga
     */
    public double getCarga() {
        return carga;
    }
    /**
     * getter emailCliente
     * @return emailCliente
     */
    public String getEmailCliente() {
        return emailCliente;
    }
    /**
     * getter id
     * @return id
     */
    public int getId() {
        return id;
    }
    /**
     * getter idEntrega
     * @return idEntrega
     */
    public int getIdEntrega() {
        return idEntrega;
    }
    /**
     * getter idFarmacia
     * @return idFarmacia
     */
    public int getIdFarmacia() {
        return idFarmacia;
    }

    /**
     * equals encomenda
     * @param o objeto encomenda a comparar
     * @return boolean de comparação equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Encomenda encomenda = (Encomenda) o;
        return id == encomenda.id &&
                Double.compare(encomenda.carga, carga) == 0 &&
                idEntrega == encomenda.idEntrega &&
                idFarmacia == encomenda.idFarmacia &&
                Objects.equals(emailCliente, encomenda.emailCliente);
    }

    /**
     * hash code do objeto
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, emailCliente, carga, idEntrega, idFarmacia);
    }

    /**
     * to string encomenda
     * @return to string
     */
    @Override
    public String toString() {
        return "ID: " + id +
                "\nemailCliente: '" + emailCliente + '\'' +
                "\nCarga: " + carga +
                "\nidEntrega: " + idEntrega +
                "\nidFarmacia: " + idFarmacia +
                '\n';
    }
}
