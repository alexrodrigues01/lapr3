package lapr.project.model;

/**
 * classe morada
 */
public class Morada {

    private int id;
    private final String stringMorada;
    private final double latitude;
    private final double longitude;
    private final double altitude;

    /**
     * construtor morada
     * @param id id morada
     * @param stringMorada string morada
     * @param latitude latitude morada
     * @param longitude longitude morada
     * @param altitude altitude morada
     */
    public Morada(int id, String stringMorada, double latitude, double longitude, double altitude) {
        this.id = id;
        this.stringMorada = stringMorada;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    /**
     * construtor morada 2
     * @param stringMorada string morada
     * @param latitude latitude morada
     * @param longitude longitude morada
     * @param altitude altitude morada
     */
    public Morada(String stringMorada, double latitude, double longitude, double altitude) {
        this.stringMorada = stringMorada;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    /**
     * hash code morada
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        return hash;
    }

    /**
     * equals morada
     * @param obj objeto morada comparacao
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
        final Morada other = (Morada) obj;
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        return Double.doubleToLongBits(this.longitude) == Double.doubleToLongBits(other.longitude);
    }

    /**
     * setter string morada
     * @return stringMorada
     */
    public String getStringMorada() {
        return stringMorada;
    }

    /**
     * getter morada id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * getter morada latitude
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * getter morada longitude
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * getter morada altitude
     * @return altitude
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * calculo da distancia sem contar com a altitude entre duas moradas
     * @param m outra morada
     * @return distancia sem contar com a altitude entre duas moradas
     */
    public double distanceFromNoHeight(Morada m) {
        final double EARTH_RADIUS = 6371e3;
        double latitude1 = latitude * Math.PI / 180;
        double latitude2 = m.latitude * Math.PI / 180;
        double differenceOfLatitude = (latitude - m.latitude) * Math.PI / 180;
        double differenceOfLongitude = (longitude - m.longitude) * Math.PI / 180;

        double a = Math.sin(differenceOfLatitude / 2) * Math.sin(differenceOfLatitude / 2)
                + Math.cos(latitude1) * Math.cos(latitude2) * Math.sin(differenceOfLongitude / 2)
                * Math.sin(differenceOfLongitude / 2);
        double aux = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * aux;
    }

    /**
     * calculo da distancia entre duas moradas
     * @param m outra morada
     * @return distancia entre duas moradas
     */
    public double distanceFrom(Morada m) {
        double distanceNoHeight = this.distanceFromNoHeight(m);
        return Math.sqrt(Math.pow(distanceNoHeight, 2) + Math.pow(altitude - m.altitude, 2));
    }

    /**
     * calculo da inclinacao da rua em radianos entre duas moradas
     * @param m outra morada
     * @return inclinacao da rua em radianos entre duas moradas
     */
    public double getInclinacaoRuaRadianos(Morada m) {
        double distanceNoHeight = this.distanceFromNoHeight(m);
        double diffAltitude = m.altitude - altitude;
        double angle = Math.atan2(Math.abs(diffAltitude), distanceNoHeight);
        if (diffAltitude < 0) {
            return -angle;
        }
        return angle;
    }

    /**
     * to string morada
     * @return to string
     */
    @Override
    public String toString() {
        return stringMorada;
    }
}
