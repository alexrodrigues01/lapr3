package lapr.project.data;

import java.sql.*;
import java.util.*;

import lapr.project.model.Morada;

/**
 * class MoradaBD
 */
public class MoradaBD {
    /**
     * retorna morada da base de dados com determinado id
     * @param id id morada
     * @return morada
     */
    public Morada getMoradaById(int id) {
        try {
            Connection con = BaseDados.getConnection();
            try (ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Morada WHERE id=" + id)) {
                if (!rs.next())
                    throw new IllegalArgumentException("ERRO - morada inexistente");
                return new Morada(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return null;
    }

    /**
     * adiciona moradas à base de dados
     * @param moradas lista de moradas
     * @return boolean
     */
    public boolean addMoradas(List<Morada> moradas) {
        try {
            Connection connection = BaseDados.getConnection();
            try (PreparedStatement cs = connection.prepareStatement("INSERT into Morada (morada,latitude,longitude,altitude) values(?,?,?,?)")) {
                for (Morada m : moradas) {
                    try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM Morada WHERE morada = ?")) {
                        ps.setString(1, m.getStringMorada());
                        try (ResultSet result = ps.executeQuery()) {
                            if (result.next() && result.getInt(1) == 0) {
                                cs.setString(1, m.getStringMorada());
                                cs.setDouble(2, m.getLatitude());
                                cs.setDouble(3, m.getLongitude());
                                cs.setDouble(4, m.getAltitude());
                                cs.executeUpdate();
                            }
                        }
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * valida a existência da morada na base de dados
     * @param morada string morada
     * @param latitude latitude morada
     * @param longitude longitude morada
     * @param altitude altitude morada
     * @return boolean
     */
    public boolean validaMorada(String morada, double latitude, double longitude, double altitude) {
        try {
            Connection connection = BaseDados.getConnection();
            try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM Morada WHERE morada = ? AND latitude = ? AND longitude = ? AND altitude = ?")) {
                ps.setString(1, morada);
                ps.setDouble(2, latitude);
                ps.setDouble(3, longitude);
                ps.setDouble(4, altitude);

                try (ResultSet result = ps.executeQuery()) {
                    if (!result.next() && result.getInt(1) != 1)
                        return false;
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * mapa onde a chave é o id da farmacia e a chave é a morada indo buscar a base de dados
     * @return mapa id farmacia e morada
     */
    public Map<Integer, Morada> getMoradasFarmacia() {
        try {
            Connection con = BaseDados.getConnection();
            try (ResultSet rs = con.createStatement().executeQuery("SELECT Farmacia.id, Morada.morada, Morada.latitude, Morada.longitude, Morada.altitude FROM Morada inner join Farmacia ON Morada.id = Farmacia.id_morada")) {
                Map<Integer, Morada> moradasFarmacia = new HashMap<>();
                while (rs.next()) {
                    moradasFarmacia.put(rs.getInt(1), new Morada(rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5)));
                }
                return moradasFarmacia;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return null;
    }

    /**
     * morada do utilizador com o email do cliente
     * @param emailCliente emal do cliente
     * @return morada do user
     */
    public Morada getMoradaUtilizador(String emailCliente) {
        try {
            Connection con = BaseDados.getConnection();

            try (PreparedStatement ps = con.prepareStatement("SELECT Morada.morada, Morada.latitude, Morada.longitude, Morada.altitude FROM Morada inner join Utilizador ON Morada.id = Utilizador.id_morada WHERE Utilizador.email = ?")) {
                ps.setString(1, emailCliente);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next())
                        throw new IllegalArgumentException("ERRO - falha na obtencao da morada do utilizador");
                    return new Morada(rs.getString(1), rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getDouble("altitude"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("ERRO - falha na obtencao da morada do utilizador");
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * valida a morada da farmacia na base de dados
     * @param moradaFarmacia morada
     * @param latitudeFarmacia latitude
     * @param longitudeFarmacia longitude
     * @param altitudeFarmacia altitude
     * @return boolean
     */
    public boolean validaMoradaFarmacia(String moradaFarmacia, double latitudeFarmacia, double longitudeFarmacia, double altitudeFarmacia) {

        try {
            Connection con = BaseDados.getConnection();

            try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM Morada INNER JOIN Farmacia ON Morada.id = Farmacia.id_morada WHERE Morada.morada = ? AND Morada.latitude = ? AND Morada.longitude = ? AND Morada.altitude = ?")) {
                ps.setString(1, moradaFarmacia);
                ps.setDouble(2, latitudeFarmacia);
                ps.setDouble(3, longitudeFarmacia);
                ps.setDouble(4, altitudeFarmacia);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next() && rs.getInt(1) == 0){
                        return false;
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("ERRO - falha na obtencao da morada da farmacia");
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * lista de moradas na base de dados
     * @return lista de moradas
     */
    public List<Morada> getMoradas() {
        try {
            Connection con = BaseDados.getConnection();
            try (ResultSet rs = con.createStatement().executeQuery("SELECT Morada.id, Morada.morada, Morada.latitude, Morada.longitude, Morada.altitude FROM Morada ")) {
                ArrayList<Morada> moradas = new ArrayList<>();
                while (rs.next()) {
                    moradas.add( new Morada(rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5)));
                }
                return moradas;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return Collections.emptyList();
    }
}
