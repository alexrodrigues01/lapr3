package lapr.project.data;

import lapr.project.model.Fatura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * classe FaturaBD
 */
public class FaturaBD {

    /**
     * Cria uma nova fatura e a linha fatura associada.
     *
     * @param encomendaID               id da encomenda a que esta fatura e referente
     * @param numeroCreditosDescontados numero de créditos que o cliente quer descontar no valor final da sua fatura
     * @param taxa                      taxa de entrega
     * @param nif                       nif que o cliente quer que a fatura esteja associada
     * @param farmaciaID                id da farmacia que ira gerar a fatura
     * @return retorna o id da fatura que acabou de ser criada
     */
    public int novaFatura(int encomendaID, int numeroCreditosDescontados, int taxa, int nif, int farmaciaID) {
        try {
            Connection connection = BaseDados.getConnection();

            int faturaID;
            try (PreparedStatement ps = connection.prepareStatement("SELECT MAX(id) FROM Fatura")) {
                try(ResultSet result = ps.executeQuery()) {
                    if (result.next())
                        faturaID = result.getInt(1) +1;
                    else
                        faturaID = 1;
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO Fatura(id, data, desconto, taxaEntrega, nif, id_farmacia) VALUES (?, ?, ?, ?, ?, ?)")) {
                ps.setInt(1, faturaID);
                ps.setDate(2, new Date(System.currentTimeMillis()));
                ps.setInt(3, numeroCreditosDescontados);
                ps.setInt(4, taxa);
                ps.setInt(5, nif);
                ps.setInt(6, farmaciaID);

                ps.executeUpdate();
            }

            double valorTotal = 0;
            try (PreparedStatement ps = connection.prepareStatement("SELECT Produto.id, Produto.nome, Produto.preco_unitario, LinhaEncomendaProduto.quantidade, Produto.iva " +
                                                                        "FROM LinhaEncomendaProduto " +
                                                                        "INNER JOIN Produto ON Produto.id = LinhaEncomendaProduto.id_produto " +
                                                                        "WHERE LinhaEncomendaProduto.id_encomenda = ?")) {
                ps.setInt(1, encomendaID);

                try(ResultSet result = ps.executeQuery()) {

                    int linha = 0;
                    while (result.next()) {
                        double precoUnitario = result.getDouble(3);
                        int iva = result.getInt(5);
                        int quantidade = result.getInt(4);
                        double valorLinha = (precoUnitario + ((precoUnitario * (double) iva) / 100)) * quantidade;

                        valorTotal += valorLinha;

                        linha++;
                        try (PreparedStatement ps2 = connection.prepareStatement("INSERT INTO LinhaFatura(id_fatura, linha, nomeProduto, valor, quantidade, preco_unitario, iva, id_produto, id_encomenda)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                            ps2.setInt(1, faturaID);
                            ps2.setInt(2, linha);
                            ps2.setString(3, result.getString(2));
                            ps2.setDouble(4, valorLinha);
                            ps2.setInt(5, quantidade);
                            ps2.setDouble(6, precoUnitario);
                            ps2.setInt(7, iva);
                            ps2.setInt(8, result.getInt(1));
                            ps2.setInt(9, encomendaID);

                            ps2.executeUpdate();
                        }
                    }
                }
            }

            try(PreparedStatement ps = connection.prepareStatement("UPDATE Fatura SET valorProdutos = ? WHERE id = ?")){
                ps.setDouble(1, valorTotal);
                ps.setInt(2, faturaID);

                ps.executeUpdate();
            }

            return faturaID;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("ERRO - fatura nao criada");
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * @param faturaID id da fatura que vamos buscar a base de dados
     * @return objeto fatura que contem todas as informacoes referentes a fatura
     */
    public Fatura getFatura(int faturaID) {
        try {
            Connection connection = BaseDados.getConnection();

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Fatura WHERE id = ?")) {
                ps.setInt(1, faturaID);

                try (ResultSet r = ps.executeQuery()) {
                    if (!r.next())
                        throw new IllegalArgumentException("ERRO - falha na obtencao da fatura da base de dados");
                    Fatura fatura = new Fatura(r.getDate("data"), r.getDouble("valorProdutos"), r.getInt("taxaEntrega"), r.getInt("nif"));
                    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM LinhaFatura WHERE id_fatura = ?")) {
                        preparedStatement.setInt(1, faturaID);
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            while (resultSet.next()) {
                                fatura.setProdutos(resultSet.getString("nomeProduto"), resultSet.getDouble("valor"), resultSet.getInt("quantidade"), resultSet.getDouble("preco_unitario"), resultSet.getInt("iva"));
                            }
                            return fatura;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("ERRO - falha na obtencao da fatura da base de dados");
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * obtem lista de faturas existentes na base de dados
     * @return lista de faturas
     */
    public List<Fatura> getFaturas() {
        try {
            Connection c = BaseDados.getConnection();
            List<Fatura> faturas = new ArrayList<>();
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM Fatura")) {
                try (ResultSet r = ps.executeQuery()) {
                    while(r.next()){
                        int faturaID = r.getInt("id");
                        Fatura f = new Fatura(faturaID,r.getDate("data"), r.getDouble("valorProdutos"), r.getInt("taxaEntrega"), r.getInt("nif"));
                        try (PreparedStatement statement = c.prepareStatement("SELECT * FROM LinhaFatura WHERE id_fatura = ?")) {
                            statement.setInt(1, faturaID);
                            try (ResultSet query = statement.executeQuery()) {
                                while (query.next()) {
                                    f.setProdutos(query.getString("nomeProduto"), query.getDouble("valor"), query.getInt("quantidade"), query.getDouble("preco_unitario"), query.getInt("iva"));
                                }
                            }
                        }
                        faturas.add(f);
                    }
                }
            }
            return faturas;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("ERRO - falha na obtencao da fatura da base de dados");
        } finally {
            BaseDados.closeConnection();
        }
    }
}
