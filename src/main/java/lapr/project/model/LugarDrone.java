package lapr.project.model;

import java.util.Objects;

/**
 * classe Lugar Drone
 */
public class LugarDrone {

    private int id;
    private  boolean carregamento;
    private int estadoID;
    private int droneID;

    /**
     * construtor lugar drone
     * @param carregamento determina se o lugar consegue carregar drone
     * @param estadoID estadoID lugar drone
     * @param droneID droneID lugar drone
     */
    public LugarDrone(boolean carregamento, int estadoID, int droneID) {
        this.carregamento = carregamento;
        this.estadoID=estadoID;
        this.droneID =droneID;
    }

    /**
     * construtor lugar drone 2
     * @param id id lugar drone
     * @param carregamento determina se o lugar consegue carregar drone
     * @param estadoID estadoID lugar drone
     * @param droneID droneID lugar drone
     */
    public LugarDrone(int id, boolean carregamento, int estadoID, int droneID) {
        this.id = id;
        this.carregamento = carregamento;
        this.estadoID = estadoID;
        this.droneID = droneID;
    }

    /**
     * construtor lugar drone 3
     * @param id id lugar drone
     */
    public LugarDrone(int id) {
        this.id = id;
    }

    /**
     * setter droneID
     * @param droneID droneID
     */
    public void setDrone(int droneID) {
        this.droneID = droneID;
    }

    /**
     * getter id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * getter droneID
     * @return droneID
     */
    public int getDroneID() {
        return droneID;
    }

    /**
     * equals drone
     * @param o objeto drone comparacao
     * @return boolean equals drone
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LugarDrone that = (LugarDrone) o;
        return id == that.id && carregamento == that.carregamento && estadoID == that.estadoID && droneID == that.droneID;
    }

    /**
     * hash code lugar drone
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, carregamento, estadoID, droneID);
    }
}
