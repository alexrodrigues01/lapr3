package lapr.project.model;

import com.google.zxing.WriterException;
import java.awt.image.BufferedImage;
import lapr.project.utils.QRCode;

/**
 * The type Scooter.
 */
public class Scooter extends Veiculo {

    private BufferedImage qrCode;
    private int estadoScooterID;

    /**
     * construtor scoter
     * @param id id da scooter
     * @param capacidadeBateria capacidadeBateria da scooter
     * @param potenciaMotor potenciaMotor da scooter
     */
    public Scooter(int id, double capacidadeBateria, double potenciaMotor) {
        super(id, capacidadeBateria, potenciaMotor);
    }

    /**
     * construtor scoter 2
     * @param id id da scooter
     * @param carga carga da scooter
     * @param capacidadeBateria capacidadeBateria da scooter
     * @param estadoScooterID estadoScooterID da scooter
     * @param consumoHoraBateria consumoHoraBateria da scooter
     * @param tensaoBateria tensaoBateria da scooter
     * @param massa massa da scooter
     * @param potencia potencia da scooter
     * @throws WriterException excecao qrcode
     */
    public Scooter(int id, int carga, double capacidadeBateria, int estadoScooterID, double consumoHoraBateria, double tensaoBateria, double massa, double potencia) throws WriterException {
        super(id, carga,capacidadeBateria, consumoHoraBateria, tensaoBateria, massa, potencia);
        this.qrCode = QRCode.createQRCode(id + "");
        this.estadoScooterID = estadoScooterID;
    }

    /**
     * getter qrcode
     * @return qrCode
     */
    public BufferedImage getQrCode() {
        return qrCode;
    }

    /**
     * getter getEstadoScooterID
     * @return estadoScooterID
     */
    public int getEstadoScooterID() {
        return estadoScooterID;
    }

    /**
     * equals scooter
     * @param obj objeto comparacao scooter
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
        final Scooter other = (Scooter) obj;
        if (this.estadoScooterID != other.estadoScooterID) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * hash code scooter
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.estadoScooterID;
        return hash;
    }

    /**
     * to string scooter
     * @return to string
     */
    @Override
    public String toString() {
            return super.toString();
    }
}
