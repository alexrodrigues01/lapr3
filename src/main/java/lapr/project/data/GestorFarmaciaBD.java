package lapr.project.data;

import java.sql.*;

/**
 * classe GestorFarmaciaBD
 */
public class GestorFarmaciaBD {

    /**
     * Valida gestor atraves do respetivo email e nif
     *
     * @param email email do gestor
     * @param nif nif do gestor
     * @return boolean se e valido
     */
    public boolean validaGestor(String email, int nif) {
        try {
            Connection connection = BaseDados.getConnection();
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Utilizador WHERE email like ? OR nif = ?")) {
                ps.setString(1, email);
                ps.setInt(2, nif);
                try (ResultSet result = ps.executeQuery()) {
                    if (result.next()) {
                        throw new IllegalArgumentException("Já existe um utilizador com este email");
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * valida gestor atraves do email apenas
     *
     * @param email email gestor
     * @return boolean se e valido
     */
    public boolean validaGestor(String email) {
        try {
            Connection connection = BaseDados.getConnection();
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Utilizador WHERE email like ?")) {
                ps.setString(1, email);
                try (ResultSet result = ps.executeQuery()) {
                    if (result.next()) {
                        throw new IllegalArgumentException("Já existe um utilizador com este email");
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * cria um novo gestor de farmacia e insere-o na base de dados
     *
     * @param email email do gestor
     * @return boolean se cria com sucesso
     */
    public boolean novoGestorFarmacia(String email) {
        try {
            Connection connection = BaseDados.getConnection();

            try (PreparedStatement ps = connection.prepareCall("INSERT INTO GestorFarmacia VALUES (?)")) {
                ps.setString(1, email);
                ps.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }
}
