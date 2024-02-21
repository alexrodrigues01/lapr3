package lapr.project.model;

import java.util.Objects;

/**
 * classe LugarEstacionamento
 */
public class LugarEstacionamento {
    private int id;
    private boolean carregamento;
    private int estadoID;
    private int veiculoID;

    /**
     * construtor LugarEstacionamento
     * @param carregamento determina se o lugar dá para carregar
     * @param estado estado id do lugar
     * @param scooter id da scooter
     */
    public LugarEstacionamento(boolean carregamento, int estado, int scooter) {
        this.carregamento = carregamento;
        this.estadoID=estado;
        this.veiculoID=scooter;
    }

    /**
     * construtor LugarEstacionamento 2
     * @param id id lugar estacionamento
     * @param carregamento determina se o lugar dá para carregar
     * @param estado estado id do lugar
     * @param scooter id da scooter
     */
    public LugarEstacionamento(int id, boolean carregamento, int estado, int scooter) {
        this.id = id;
        this.carregamento = carregamento;
        this.estadoID = estado;
        this.veiculoID = scooter;
    }

    /**
     * construtor LugarEstacionamento 3
     * @param id id lugar estacionamento
     */
    public LugarEstacionamento(int id) {
        this.id = id;
    }

    /**
     * setter id scooter
     * @param scooter
     */
    public void setScooter(int scooter) {
        this.veiculoID = scooter;
    }

    /**
     * getter id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * getter id scooter
     * @return veiculoID
     */
    public int getScooterID() {
        return veiculoID;
    }

    /**
     * equals lugar estacionamento
     * @param o objeto lugar estacionamento comparacao
     * @return boolean equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LugarEstacionamento that = (LugarEstacionamento) o;
        return id == that.id &&
                carregamento == that.carregamento &&
                estadoID == that.estadoID &&
                veiculoID == that.veiculoID;
    }

    /**
     * hash code lugarestacionamento
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, carregamento, estadoID, veiculoID);
    }

}
