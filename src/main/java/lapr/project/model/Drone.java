package lapr.project.model;

/**
 * The type Drone.
 */
public class Drone extends Veiculo{

    private int estadoDroneID;
    private double velocidade;                  // km/h
    private double avionics;
    private double drag;
    private double rotors;
    private double cargaMaxima;

    /**
     * construtor objeto Drone
     * @param id id do drone
     * @param carga carga do drone
     * @param capacidadeBateria capacidadeBateria do drone
     * @param estadoDroneID estadoDroneID do drone
     * @param consumoHoraBateria consumoHoraBateria do drone
     * @param tensaoBateria tensaoBateria do drone
     * @param massa massa do drone
     * @param potencia potencia do drone
     * @param velocidade velocidade do drone
     * @param avionics avionics do drone
     * @param drag drag do drone
     * @param rotors rotors do drone
     * @param cargaMaxima cargaMaxima do drone
     */
    public Drone(int id, int carga, double capacidadeBateria, int estadoDroneID, double consumoHoraBateria, double tensaoBateria, double massa, double potencia, double velocidade, double avionics, double drag, double rotors, double cargaMaxima) {
        super(id, carga, capacidadeBateria, consumoHoraBateria, tensaoBateria, massa, potencia);
        setEstadoDroneID(estadoDroneID);
        setVelocidade(velocidade);
        setAvionics(avionics);
        setDrag(drag);
        setRotors(rotors);
        setCargaMaxima(cargaMaxima);
    }

    /**
     * getter estadoDroneID
     * @return estadoDroneID
     */
    public int getEstadoDroneID() {
        return estadoDroneID;
    }

    /**
     * getter velocidade
     * @return velocidade
     */
    public double getVelocidade() {
        return velocidade;
    }

    /**
     * getter avionics
     * @return avionics
     */
    public double getAvionics() {
        return avionics;
    }

    /**
     * getter drag
     * @return drag
     */
    public double getDrag() {
        return drag;
    }

    /**
     * getter rotors
     * @return rotors
     */
    public double getRotors() {
        return rotors;
    }

    /**
     * getter cargaMaxima
     * @return cargaMaxima
     */
    public double getCargaMaxima() {
        return cargaMaxima;
    }

    /**
     * setter estadoDroneID
     * @param estadoDroneID estadoDroneID
     */
    public void setEstadoDroneID(int estadoDroneID) {
        if(estadoDroneID < 1 || estadoDroneID >3){
            throw new IllegalArgumentException("Id do estado do drone inválido");
        }
        this.estadoDroneID = estadoDroneID;
    }

    /**
     * setter velocidade
     * @param velocidade velocidade
     */
    public void setVelocidade(double velocidade) {
        if(Double.compare(velocidade,0) < 1){
            throw new IllegalArgumentException("Velocidade inválida");
        }
        this.velocidade = velocidade;
    }

    /**
     * setter avionics
     * @param avionics avionics
     */
    public void setAvionics(double avionics) {
        if(Double.compare(avionics,0) < 1 ){
            throw new IllegalArgumentException("Valor de avionics inválido");
        }
        this.avionics = avionics;
    }

    /**
     * setter drag
     * @param drag drag
     */
    public void setDrag(double drag) {
        if(Double.compare(drag,0) < 1){
            throw new IllegalArgumentException("Valor de drag inválido");
        }
        this.drag = drag;
    }

    /**
     * setter rotors
     * @param rotors rotors
     */
    public void setRotors(double rotors) {
        if(Double.compare(rotors,0) < 1 || Double.compare(1,rotors) < 1){
            throw new IllegalArgumentException("Valor dos rotors inválido");
        }
        this.rotors = rotors;
    }

    /**
     * setter cargaMaxima
     * @param cargaMaxima cargaMaxima
     */
    public void setCargaMaxima(double cargaMaxima) {
        if(Double.compare(cargaMaxima,0) < 1 ){
            throw new IllegalArgumentException("Carga maxima inválida");
        }
        this.cargaMaxima = cargaMaxima;
    }

    /**
     * equals Drone
     * @param obj objeto drone comparação
     * @return boolean se é igual ou nao
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Drone other = (Drone) obj;
        if (this.estadoDroneID != other.estadoDroneID) {
            return false;
        }
        if (Double.doubleToLongBits(this.velocidade) != Double.doubleToLongBits(other.velocidade)) {
            return false;
        }
        if (Double.doubleToLongBits(this.avionics) != Double.doubleToLongBits(other.avionics)) {
            return false;
        }
        if (Double.doubleToLongBits(this.drag) != Double.doubleToLongBits(other.drag)) {
            return false;
        }
        if (Double.doubleToLongBits(this.rotors) != Double.doubleToLongBits(other.rotors)) {
            return false;
        }
        if (Double.doubleToLongBits(this.cargaMaxima) != Double.doubleToLongBits(other.cargaMaxima)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * hash code dron e
     * @return hashCode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.estadoDroneID;
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.velocidade) ^ (Double.doubleToLongBits(this.velocidade) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.avionics) ^ (Double.doubleToLongBits(this.avionics) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.drag) ^ (Double.doubleToLongBits(this.drag) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.rotors) ^ (Double.doubleToLongBits(this.rotors) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.cargaMaxima) ^ (Double.doubleToLongBits(this.cargaMaxima) >>> 32));
        return hash;
    }

    /**
     * to string drone
     * @return to string drone
     */
    @Override
    public String toString() {
        return super.toString() +
                "estadoDroneID: " + estadoDroneID +
                "\nvelocidade: " + velocidade +
                "\navionics: " + avionics +
                "\ndrag: " + drag +
                "\nrotors: " + rotors +
                "\ncargaMaxima: " + cargaMaxima +"\n";
    }
}
