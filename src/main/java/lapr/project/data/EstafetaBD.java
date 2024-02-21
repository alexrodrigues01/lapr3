package lapr.project.data;

import lapr.project.model.Estafeta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Estafeta bd.
 */
public class EstafetaBD {

    /**
     * Validao o estafeta na base de dados.
     *
     * @param email        the email
     * @param nif          the nif
     * @param numSegSocial the num seg social
     * @return the boolean
     */
    public boolean validaEstafeta(String email,int nif, String numSegSocial) {
        try{
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM Estafeta WHERE emailEstafeta like ? and numSegSocial like ?")){
                ps.setString(1,email);
                ps.setString(2,numSegSocial);
                try(ResultSet rsEstafeta=ps.executeQuery()){
                    if(rsEstafeta.next() && (rsEstafeta.getString("emailEstafeta").equals(email) || rsEstafeta.getString("numSegSocial").equals(numSegSocial))){
                        return false;
                    }
                    try(PreparedStatement psd = con.prepareStatement("SELECT * FROM Utilizador WHERE email like ? and nif like ?")){
                        psd.setString(1,email);
                        psd.setInt(2,nif);
                        try(ResultSet rsUtilizador = psd.executeQuery()){
                            if(rsUtilizador.next()) {
                                if (rsUtilizador.getInt("nif") == nif) {
                                    return false;
                                }
                                if (rsUtilizador.getString("email").equals(email)) {
                                    return false;
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * Regista o estafeta na base de dados.
     *
     * @param email              the email
     * @param idFarmacia         the id farmacia
     * @param numSegurancaSocial the num seguranca social
     * @param cargaMaxima        the carga maxima
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean registaEstafeta(String email, int idFarmacia, String numSegurancaSocial, double cargaMaxima) throws SQLException{
        try{
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps = con.prepareStatement("insert into Estafeta (emailEstafeta, numSegSocial, cargaMaxima, id_farmacia, idEstadoEstafeta) values (?,?,?,?,1)")){
                ps.setString(1,email);
                ps.setString(2,numSegurancaSocial);
                ps.setDouble(3, cargaMaxima);
                ps.setInt(4,idFarmacia);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * retorna o email de um estafeta disponivel
     *
     * @param idFarm the id farm
     * @return the estafeta available
     */
    public String getEstafetaAvailable(int idFarm) {
        try {
            Connection connectio = BaseDados.getConnection();
            try(PreparedStatement pst = connectio.prepareStatement("SELECT * FROM Estafeta WHERE id_farmacia like ? AND idEstadoEstafeta like ?")){
                pst.setInt(1, idFarm);
                pst.setInt(2,1);
                try(ResultSet result = pst.executeQuery()){
                    if(!result.next())
                        throw new IllegalArgumentException("ERRO - nao existe estafeta disponivel");
                    return result.getString("emailEstafeta");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return null;
    }

    /**
     * Verifica se um email é de um estafeta registado.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean isEstafeta(String email) {
        try {
            Connection connectio = BaseDados.getConnection();
            try(PreparedStatement pst = connectio.prepareStatement("SELECT * FROM Estafeta WHERE emailEstafeta like ?")){
                pst.setString(1, email);
                try(ResultSet result = pst.executeQuery()){
                  if(result.next()){
                      return true;
                  }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return false;
    }

    /**
     * Muda o estado de um estafeta na base de dados.
     *
     * @param emailEstafeta the email estafeta
     * @param i             the
     * @return the estado estafeta
     */
    public boolean setEstadoEstafeta(String emailEstafeta,int i) {
        try {
            Connection conn = BaseDados.getConnection();
            try(PreparedStatement pst= conn.prepareStatement("UPDATE Estafeta SET idEstadoEstafeta=? WHERE emailEstafeta like ?")) {
                pst.setInt(1, i);
                pst.setString(2, emailEstafeta);
                pst.executeUpdate();
                return true;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return false;
    }

    /**
     * Retorna a lista de todos os estafetas da base de dados.
     *
     * @return the estafetas
     */
    public List<Estafeta> getEstafetas() {
        ArrayList<Estafeta> listaProdutos = new ArrayList<>();
        try {
            Connection con = BaseDados.getConnection();
            try (ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Estafeta inner join Utilizador on emailEstafeta like email")) {
                while (rs.next()) {
                    listaProdutos.add(new Estafeta(rs.getString("nome"), rs.getString("email"), rs.getInt("nif"), rs.getInt("telefone"), rs.getString("password"), rs.getInt("numSegSocial"), rs.getInt("id_farmacia"), rs.getInt("idEstadoEstafeta")));
                }
            }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                BaseDados.closeConnection();
            }
            return listaProdutos;
    }
}

