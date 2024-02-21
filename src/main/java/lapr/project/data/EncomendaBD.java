package lapr.project.data;

import lapr.project.model.Encomenda;
import lapr.project.model.Entrega;
import lapr.project.model.Morada;
import lapr.project.model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * classe EncomendaBD
 */
public class EncomendaBD {
    private static final String CARGA = "carga";
    private static final String EMAIL = "emailCliente";

    /**
     * Cria a nova encomenda, as linhas com os produtos da encomenda e atualiza o stock dos produtos
     *
     * @param emailCliente email do cliente
     * @param carga        peso da encomenda
     * @param carrinho     carrinho das compras com os produtos comprados pelo cliente e as suas respetivas quantidades
     * @return retorna o id da encomenda que acabou de ser criada
     */
    public int novaEncomenda(int farmaciaID, int carga, Map<Produto, Integer> carrinho, String emailCliente) {
        try {
            Connection connection = BaseDados.getConnection();

            int encomendaID;
            try (PreparedStatement ps = connection.prepareStatement("SELECT MAX(id) FROM Encomenda")) {
                try(ResultSet result = ps.executeQuery()){
                    if (!result.next())
                        encomendaID = 1;
                    else
                        encomendaID = result.getInt(1) + 1;
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO Encomenda (id, carga, emailCliente, id_farmacia) VALUES (?, ?, ?, ?)")) {
                ps.setInt(1, encomendaID);
                ps.setDouble(2, (double) carga / 1000.0);
                ps.setString(3, emailCliente);
                ps.setInt(4, farmaciaID);
                ps.executeUpdate();
            }

            for (Map.Entry<Produto, Integer> produto : carrinho.entrySet()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO LinhaEncomendaProduto (id_encomenda, id_produto, quantidade) VALUES (?, ?, ?)")) {
                    preparedStatement.setInt(1, encomendaID);
                    preparedStatement.setInt(2, produto.getKey().getId());
                    preparedStatement.setInt(3, produto.getValue());
                    preparedStatement.executeQuery();
                    try (PreparedStatement ps2 = connection.prepareStatement("UPDATE Produto_Farmacia SET stock = stock - ? WHERE id_produto = ? AND id_farmacia = ?")) {
                        ps2.setInt(1, produto.getValue());
                        ps2.setInt(2, produto.getKey().getId());
                        ps2.setInt(3, farmaciaID);
                        ps2.executeQuery();
                    }
                }
            }
            return encomendaID;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("ERRO - encomenda não criada na base de dados");
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * lista de encomendas atraves do id de uma entrega
     * @param idEntrega id da entrega
     * @return lista de encomendas
     */
    public List<Encomenda> getEncomendasByEntregaId(int idEntrega) {
        try {
            Connection con = BaseDados.getConnection();
            ArrayList<Encomenda> arrayList = new ArrayList<>();
            try (ResultSet rs = con.createStatement().executeQuery("SELECT * FROM encomenda where id=" + idEntrega)) {
                while (rs.next()) {
                    arrayList.add(new Encomenda(rs.getInt("id"), rs.getDouble(CARGA), rs.getString(EMAIL)));
                }
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return new ArrayList<>();
    }

    /**
     * lista de moradas dos clientes atraves de uma lista de encomendas
     * @param encomendas lista de encomendas
     * @return lista de moradas dos clientes atraves de uma lista de encomendas
     */
    public List<Morada> getMoradasClientesByEncomendas(List<Encomenda> encomendas) {
        try {
            Connection con = BaseDados.getConnection();
            List<Morada> moradas = new ArrayList<>();
            try (PreparedStatement ps = con.prepareStatement("SELECT Morada.id, Morada.morada, Morada.latitude, Morada.longitude, Morada.altitude FROM Morada INNER JOIN Utilizador ON Morada.id = Utilizador.id_morada WHERE Utilizador.email = ?")) {
                for (Encomenda encomenda : encomendas) {
                    ps.setString(1, encomenda.getEmailCliente());
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String morada = rs.getString("morada");
                            double latitude = rs.getDouble("latitude");
                            double longitude = rs.getDouble("longitude");
                            double altitude = rs.getDouble("altitude");
                            Morada m = new Morada(id, morada, latitude, longitude, altitude);
                            moradas.add(m);
                        }
                    }
                }
            }
            return moradas;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return new ArrayList<>();
    }

    /**
     * lista de encomendas da farmacia atraves do id de uma farmacia
     * @param idFarm id da farmacia
     * @return lista de encomendas da farmacia
     */
    public List<Encomenda> getEncomendasByFarmID(int idFarm) {
        List<Encomenda> arrayList = new ArrayList<>();
        try {
            Connection connect = BaseDados.getConnection();
            try (ResultSet rset = connect.createStatement().executeQuery("SELECT * FROM encomenda where id_farmacia=" + idFarm+ " AND id_Entrega is null")) {
                while (rset.next()) {
                    arrayList.add(new Encomenda(rset.getInt("id"), rset.getDouble(CARGA), rset.getString(EMAIL)));
                }
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return arrayList;
    }

    /**
     * getter id da farmacia de uma encomenda usando o id dela
     * @param idEnc id encomenda
     * @return id da farmacia
     */
    public int getFarmIDByEncID(int idEnc) {
        try {
            Connection connect = BaseDados.getConnection();
            try (ResultSet rset = connect.createStatement().executeQuery("SELECT id_farmacia FROM encomenda where id=" + idEnc)) {
                rset.next();
                return rset.getInt("id_farmacia");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return 0;
    }

    /**
     * lista de encomendas atraves do id dessas encomendas
     * @param encomendas lista de id de encomendas
     * @return  lista de encomendas
     */
    public List<Encomenda> getEncomendasByID(List<Integer> encomendas) {
        List<Encomenda> arrayList = new ArrayList<>();
        try {
            Connection connect = BaseDados.getConnection();
            for (int idEnc : encomendas) {
                try (ResultSet rse = connect.createStatement().executeQuery("SELECT * FROM encomenda where id=" + idEnc)) {
                    if (rse.next()) {
                        arrayList.add(new Encomenda(rse.getInt("id"), rse.getDouble(CARGA), rse.getString(EMAIL),rse.getInt("id_farmacia")));
                    }
                }
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return arrayList;
    }

    /**
     * setter do id da entrega
     * @param listaEncomendas lista de encomendas
     * @param entrega entrega
     * @return boolean
     */
    public boolean setIDEntrega(List<Encomenda> listaEncomendas, Entrega entrega) {
        try {
            Connection con = BaseDados.getConnection();
            for (Encomenda encomenda : listaEncomendas) {
                try (ResultSet rs = con.createStatement().executeQuery("UPDATE Encomenda SET id_Entrega= " + entrega.getId() + " WHERE id=" + encomenda.getId())) {
                }
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
     * obtem lista de encomendas existentes na base de dados
     * @return  lista de encomendas
     */
    public List<Encomenda> getEncomendas() {
        List<Encomenda> arrayList = new ArrayList<>();
        try {
            Connection connect = BaseDados.getConnection();
            try (ResultSet rse = connect.createStatement().executeQuery("SELECT * FROM encomenda")) {
                while (rse.next()) {
                    arrayList.add(new Encomenda(rse.getInt("id"), rse.getString(EMAIL), rse.getDouble(CARGA), rse.getInt("id_Entrega"),rse.getInt("id_farmacia")));
                }
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return arrayList;
    }
}

