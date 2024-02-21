/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.data;

import java.sql.*;
import lapr.project.model.Utilizador;

/**
 * classe UtilizadorBD
 */
public class UtilizadorBD {

    /**
     * valida utilizador na base de dados
     *
     * @param email email do utilizador
     * @param password palavra chave do utilizador
     * @param nome nome do utilizador
     * @param nif nif do utilizador
     * @param telemovel telemovel do utilizador
     * @param papel papel do utilizador
     * @param morada morada do utilizador
     * @param longitude longitude do utilizador
     * @param latitude latitude do utilizador
     */
    public boolean novoUtilizador(String email, String password, String nome, int nif, int telemovel, int papel, String morada, double latitude, double longitude, double altitude) {
        try {
            Connection connection = BaseDados.getConnection();
            int moradaID;
            try(PreparedStatement ps = connection.prepareStatement("SELECT id FROM Morada WHERE morada = ? AND latitude = ? AND longitude = ? AND altitude = ?")){
                ps.setString(1, morada);
                ps.setDouble(2, latitude);
                ps.setDouble(3, longitude);
                ps.setDouble(4, altitude);

                try(ResultSet result = ps.executeQuery()){
                    if(result.next())
                        moradaID = result.getInt(1);
                    else
                        throw new IllegalArgumentException("ERRO - a morada nao existe no sistema");
                }

            }

            try(PreparedStatement ps = connection.prepareStatement("INSERT INTO Utilizador (email, password, nome, nif, telefone, id_morada, id_papel) values(?, ?, ?, ?, ?, ?, ?)")){
                ps.setString(1, email);
                ps.setString(2, password);
                ps.setString(3, nome);
                ps.setInt(4, nif);
                ps.setInt(5, telemovel);
                ps.setInt(6, moradaID);
                ps.setInt(7, papel);

                ps.executeUpdate();
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * buscar na base de dados o utilizador com o email igual ao passado por
     * parâmetro
     *
     * @param email email do utilizador
     * @return utilizador
     * @throws Exception excecao
     * @throws SQLException sql exception
     */
    public Utilizador procuraUtilizador(String email) {
        try {
            Connection connection = BaseDados.getConnection();
            try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM Utilizador WHERE email like ?")){
                ps.setString(1, email);
                try(ResultSet result = ps.executeQuery()){
                    if (result.next()) {
                        return new Utilizador(result.getString("nome"), result.getString("email"), result.getInt("nif"), result.getInt("telefone"), result.getString("password"), result.getInt("id_papel"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return null;
    }

    /**
     * verificação se o utilizador existe
     *
     * @param email email do utilizador
     * @return true em caso de sucesso, false em caso de insucesso
     */
    public boolean hasUtilizador(String email) {
        try {

            Connection connection = BaseDados.getConnection();
            try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM Utilizador WHERE email like ?")){
                ps.setString(1, email);
                try(ResultSet result = ps.executeQuery()){
                    if (result.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return false;
    }

    /**
     * obtem id da morada atraves do email do cliente
     * @param emailCliente email de cliente
     * @return id da morada
     */
    public int getIdMoradaByEmailCliente(String emailCliente) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rs = con.createStatement().executeQuery("SELECT id_morada FROM utilizador where email=" + emailCliente)){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return 0;
    }
}
