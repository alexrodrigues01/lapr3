package lapr.project.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * classe NotaBD
 */
public class NotaBD {

    /**
     * Metodo gerar notas
     *
     * @param idFarmaciaRecetora id da farmacia que pediu o produto
     * @param idFarmaciaEmissora id da farmacia que enviou o produto
     * @param idProduto id do produto
     * @param quantidadeNecessaria quantidade que a farmacia recetora precisa
     * @return retorna id da primeira nota gerada
     */
    public int gerarNotas(int idFarmaciaRecetora, int idFarmaciaEmissora, int idProduto, int quantidadeNecessaria) {
        try {
            Connection connection = BaseDados.getConnection();
            int notaID;
            try (PreparedStatement ps = connection.prepareStatement("SELECT MAX(id) FROM NOTA")) {
                try (ResultSet result = ps.executeQuery()) {
                    if (!result.next()) {
                        notaID = 1;
                    } else {
                        notaID = result.getInt(1) + 1;
                    }
                }

            }

            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO Nota (id, id_farmaciaEmissora, id_farmaciaRecetora, id_produto, quantidade, id_tipoNota) values (?, ?, ?, ?, ?, ?)")) {
                //nota de emissao
                ps.setInt(1, notaID);
                ps.setInt(2, idFarmaciaEmissora);
                ps.setInt(3, idFarmaciaRecetora);
                ps.setInt(4, idProduto);
                ps.setInt(5, quantidadeNecessaria);
                ps.setInt(6, 2);

                ps.executeUpdate();

                //nota de rececao
                ps.setInt(1, notaID + 1);
                ps.setInt(2, idFarmaciaEmissora);
                ps.setInt(3, idFarmaciaRecetora);
                ps.setInt(4, idProduto);
                ps.setInt(5, quantidadeNecessaria);
                ps.setInt(6, 1);

                ps.executeUpdate();
            }

            //reducao do stock da farmacia que forneceu o produto
            try (PreparedStatement ps = connection.prepareStatement("UPDATE Produto_Farmacia SET stock = stock - ? WHERE id_produto = ? AND id_farmacia = ?")) {
                ps.setInt(1, quantidadeNecessaria);
                ps.setInt(2, idProduto);
                ps.setInt(3, idFarmaciaEmissora);

                ps.executeUpdate();
            }

            //aumento do stock da farmacia que recebeu o produto
            try (PreparedStatement ps = connection.prepareStatement("UPDATE Produto_Farmacia SET stock = stock + ? WHERE id_produto = ? AND id_farmacia = ?")) {

                ps.setInt(1, quantidadeNecessaria);
                ps.setInt(2, idProduto);
                ps.setInt(3, idFarmaciaRecetora);

                ps.executeUpdate();
            }

            return notaID;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * obtem lista de notas existentes na base de dados
     * @return lista de notas
     */
    public List<String> getNotas(){
        try {
            Connection c = BaseDados.getConnection();
            List<String> notas = new ArrayList<>();
            try (PreparedStatement ps = c.prepareStatement("SELECT Nota.id, Nota.quantidade, Nota.id_farmaciaEmissora, Nota.id_farmaciaRecetora, Produto.nome, TipoNota.designacao FROM Nota INNER JOIN Produto ON Produto.id = Nota.id_produto INNER JOIN TipoNota ON TipoNota.id = Nota.id_tipoNota")) {
                try (ResultSet r = ps.executeQuery()) {
                    while(r.next()){
                        String s = String.format("ID: %d%nQuantidade: %d%nFarmacia Recetora: %s%nFarmacia Emissora: %s%nProduto: %s%nTipo de Nota: %s%n%n",r.getInt(1),r.getInt(2),new FarmaciaBD().getNomeFarmacia(r.getInt(3)),new FarmaciaBD().getNomeFarmacia(r.getInt(4)),r.getString(5),r.getString(6));
                        notas.add(s);
                    }
                }
            }
            return notas;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("ERRO - falha na obtencao da fatura da base de dados");
        } finally {
            BaseDados.closeConnection();
        }
    }
}
