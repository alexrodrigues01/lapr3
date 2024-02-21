package lapr.project.data;

import lapr.project.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * classe ClienteBD
 */
public class ClienteBD {

    /**
     * verifica se o cliente nao existe na base de dados
     * @param email
     */
    public boolean validaCliente(String email) {
        try{
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM Cliente WHERE emailCliente like ?")){
                ps.setString(1,email);
                try(ResultSet rsCliente=ps.executeQuery()){
                    if(rsCliente.next()){
                        return false;
                    }
                }
            }
            try(PreparedStatement psd = con.prepareStatement("SELECT * FROM Utilizador WHERE email like ?")){
                psd.setString(1,email);
                try(ResultSet rsUtilizador = psd.executeQuery()){
                    if(rsUtilizador.next()){
                        return false;
                    }
                }
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * regista cliente na base de dados
     * @param email
     * @param nCartCred
     * @param valCartCred
     * @param cvv
     */
    public boolean registaCliente(String email, String nCartCred, String valCartCred, int cvv) {
        try{
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps = con.prepareStatement("insert into Cliente (emailCliente, nCartCred, valCartCred, cvv) values (?, ?, ?, ?)")){
                ps.setString(1,email);
                ps.setString(2,nCartCred);
                ps.setString(3,valCartCred);
                ps.setInt(4,cvv);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * verifica cvv do cliente na base de dados
     * @param emailCliente email do cliente
     * @param cvv cvv do cliente
     * @return boolean
     */
    public boolean verificaCVV(String emailCliente, int cvv){
        try{
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps = con.prepareStatement("SELECT cvv FROM Cliente WHERE emailCliente = ?")){
                ps.setString(1, emailCliente);
                try(ResultSet result = ps.executeQuery()){
                    if(result.next() && result.getInt("cvv") == cvv) {
                        return true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }finally {
            BaseDados.closeConnection();
        }
        return false;
    }

    /**
     * adicionar creditos ao cliente ba base de dados
     * @param emailCliente email do cliente
     * @param creditos creditos a adicionar
     * @return boolean
     */
    public boolean adicionaCreditos(String emailCliente, int creditos){
        try{
            Connection con = BaseDados.getConnection();

            try(PreparedStatement ps = con.prepareStatement("UPDATE Cliente SET creditos = creditos + ? WHERE emailCliente = ?")){
                ps.setInt(1, creditos);
                ps.setString(2, emailCliente);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * getter creditos do cliente na base de dados
     * @param emailCliente email do cliente
     * @return creditos do cliente
     */
    public int getCreditos(String emailCliente) {
        int creditos=0;
        try{
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps = con.prepareStatement("SELECT creditos FROM Cliente WHERE emailCliente = ?")){
                ps.setString(1, emailCliente);

                try(ResultSet result = ps.executeQuery()){
                    if(result.next())
                        creditos=result.getInt("creditos");
                    return creditos;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDados.closeConnection();
        }
        return creditos;
    }

    /**
     * descontar cfreditos nos creditos do cliente na base de dados
     * @param emailCliente email do cliente
     * @param numeroCreditosDescontados creditos a descontar
     * @return boolean
     */
    public boolean descontaCreditos(String emailCliente, int numeroCreditosDescontados) {
        try{
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps = con.prepareStatement("UPDATE Cliente SET creditos = creditos - ? WHERE emailCliente = ?")){
                ps.setInt(1, numeroCreditosDescontados);
                ps.setString(2, emailCliente);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * retorna lista de clientes da base de dados
     * @return lista de clientes
     */
    public List<Cliente> getListaClientes() {
        List<Cliente> clienteList = new ArrayList<>();
        try {
            Connection con = BaseDados.getConnection();
            String sql="SELECT * FROM Utilizador inner join Cliente on Utilizador.email like Cliente.emailCliente";
            try(Statement statement = con.createStatement()){
                try(ResultSet resultSet=statement.executeQuery(sql)){
                    while (resultSet.next()) {
                        clienteList.add(new Cliente(resultSet.getString("nome"),resultSet.getString("email"),
                                resultSet.getInt("nif"),resultSet.getInt("telefone"),resultSet.getString("password"),
                                resultSet.getString("nCartCred"),resultSet.getString("valCartCred"),resultSet.getInt("cvv")));
                    }
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            BaseDados.closeConnection();

        }
        return clienteList;
    }
}
