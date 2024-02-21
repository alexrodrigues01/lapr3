
package lapr.project.model;

/**
 * class Veiculo
 */
public class Veiculo {
    private final int id;
    private int cargaVeiculo;
    private double capacidadeBateria; //wh
    private double consumoHoraBateria;
    private double tensaoBateria;
    private double massa;
    private double potencia;

    /**
     * construtor veiculo
     * @param id id do veiculo
     * @param cargaVeiculo cargaVeiculo do veiculo
     * @param capacidadeBateria capacidadeBateria do veiculo
     * @param consumoHoraBateria consumoHoraBateria do veiculo
     * @param tensaoBateria tensaoBateria do veiculo
     * @param massa massa do veiculo
     * @param potencia potencia do veiculo
     */
    public Veiculo(int id, int cargaVeiculo, double capacidadeBateria, double consumoHoraBateria, double tensaoBateria, double massa, double potencia) {
        this.id = id;
        setCarga(cargaVeiculo);
        setCapacidadeBateria(capacidadeBateria);
        setConsumoHoraBateria(consumoHoraBateria);
        setTensaoBateria(tensaoBateria);
        setMassa(massa);
        setPotenciaMotor(potencia);
    }

    /**
     * construtor veiculo 2
     * @param id id do veiculo
     * @param capacidadeBateria capacidadeBateria do veiculo
     * @param potencia potencia do veiculo
     */
    public Veiculo(int id, double capacidadeBateria, double potencia) {
        this.id = id;
        this.capacidadeBateria = capacidadeBateria;
        this.potencia = potencia;
    }

    /**
     * construtor veiculo 2
     * @param id id do veiculo
     * @param cargaVeiculo cargaVeiculo do veiculo
     */
    public Veiculo(int id, int cargaVeiculo) {
        this.id = id;
        this.cargaVeiculo = cargaVeiculo;
    }

    /**
     * Sets carga.
     *
     * @param carga the carga
     */
    public void setCarga(int carga) {
        if (carga < 0 || carga > 100) {
            throw new IllegalArgumentException("Carga inválida");
        }
        this.cargaVeiculo = carga;
    }

    /**
     * Sets capacidade bateria.
     *
     * @param capacidadeBateria the capacidade bateria
     */
    public void setCapacidadeBateria(double capacidadeBateria) {
        if (capacidadeBateria < 0) {
            throw new IllegalArgumentException("Capacidade da bateria inválida");
        }
        this.capacidadeBateria = capacidadeBateria;
    }
    /**
     * Sets consumoHoraBateria
     *
     * @param consumoHoraBateria the consumoHoraBateria
     */
    public void setConsumoHoraBateria(double consumoHoraBateria) {
        if (consumoHoraBateria < 0) {
            throw new IllegalArgumentException("Consumo por hora da bateria inválido");
        }
        this.consumoHoraBateria = consumoHoraBateria;
    }
    /**
     * Sets tensaoBateria
     *
     * @param tensaoBateria the tensaoBateria
     */
    public void setTensaoBateria(double tensaoBateria) {
        if (tensaoBateria < 0) {
            throw new IllegalArgumentException("Tensão da bateria inválida");
        }
        this.tensaoBateria = tensaoBateria;
    }
    /**
     * Sets massa
     *
     * @param massa the massa
     */
    public void setMassa(double massa) {
        if (massa < 0) {
            throw new IllegalArgumentException("Massa do veiculo inválida");
        }
        this.massa = massa;
    }
    /**
     * Sets potencia
     *
     * @param potencia the potencia
     */
    public void setPotenciaMotor(double potencia) {
        if (potencia < 0) {
            throw new IllegalArgumentException("Potência do motor do veiculo inválida");
        }
        this.potencia = potencia;
    }

    /**
     * getter id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * getter cargaVeiculo
     * @return cargaVeiculo
     */
    public int getCarga() {
        return cargaVeiculo;
    }

    /**
     * getter capacidadeBateria
     * @return capacidadeBateria
     */
    public double getCapacidadeBateria() {
        return capacidadeBateria;
    }

    /**
     * getter consumoHoraBateria
     * @return consumoHoraBateria
     */
    public double getConsumoHoraBateria() {
        return consumoHoraBateria;
    }

    /**
     * getter tensaoBateria
     * @return tensaoBateria
     */
    public double getTensaoBateria() {
        return tensaoBateria;
    }

    /**
     * getter massa
     * @return massa
     */
    public double getMassa() {
        return massa;
    }

    /**
     * getter potencia
     * @return potencia
     */
    public double getPotenciaMotor() {
        return potencia;
    }

    /**
     * hash code veiculo
     * @return hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.id;
        hash = 19 * hash + this.cargaVeiculo;
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.capacidadeBateria) ^ (Double.doubleToLongBits(this.capacidadeBateria) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.consumoHoraBateria) ^ (Double.doubleToLongBits(this.consumoHoraBateria) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.tensaoBateria) ^ (Double.doubleToLongBits(this.tensaoBateria) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.massa) ^ (Double.doubleToLongBits(this.massa) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.potencia) ^ (Double.doubleToLongBits(this.potencia) >>> 32));
        return hash;
    }

    /**
     * equals veiculo
     * @param obj objeto veiculo comparacao
     * @return boolean equals
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
        final Veiculo other = (Veiculo) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.cargaVeiculo != other.cargaVeiculo) {
            return false;
        }
        if (Double.doubleToLongBits(this.capacidadeBateria) != Double.doubleToLongBits(other.capacidadeBateria)) {
            return false;
        }
        if (Double.doubleToLongBits(this.consumoHoraBateria) != Double.doubleToLongBits(other.consumoHoraBateria)) {
            return false;
        }
        if (Double.doubleToLongBits(this.tensaoBateria) != Double.doubleToLongBits(other.tensaoBateria)) {
            return false;
        }
        if (Double.doubleToLongBits(this.massa) != Double.doubleToLongBits(other.massa)) {
            return false;
        }
        return Double.doubleToLongBits(this.potencia) == Double.doubleToLongBits(other.potencia);
    }

    /**
     * to string veiculo
     * @return to string
     */
    @Override
    public String toString() {
        return String.format("ID: %d%nCarga: %d%nCapacidade da bateria: %f%nConsumo Hora da bateria: %f%nTensao da bateria: %f%nMassa: %f%nPotencia: %f%n", id, cargaVeiculo, capacidadeBateria, consumoHoraBateria, tensaoBateria, massa, potencia);
    }
}
