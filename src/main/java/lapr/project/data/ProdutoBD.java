package lapr.project.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.InvalidNameException;
import lapr.project.model.Produto;

/**
 * classe ProdutoBD
 */
public class ProdutoBD {

    private static final String STOCK = "stock";
    private static final String PRECO = "preco_unitario";
    private static final String MASSA = "massa_unitaria";

    /**
     * obtem a lista de todos os produtos produtos
     *
     * @return lista de todos os produtos produtos
     */
    public List<Produto> getTodosProdutos() {
        ArrayList<Produto> listaProdutos = new ArrayList<>();
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Produto")){
                while (rs.next()) {
                    listaProdutos.add(new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble(PRECO),  rs.getInt("iva"),rs.getInt(MASSA)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return listaProdutos;
    }

    /**
     * obtem a lista de produtos de uma farmacia
     *
     * @param id
     * @return lista de produtos de uma farmacia
     */
    public List<Produto> getProdutoByFarmacia(int id) {
        ArrayList<Produto> listaProdutos = new ArrayList<>();
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Produto inner join Produto_Farmacia on Produto_Farmacia.id_produto=Produto.id where Produto_Farmacia.id_farmacia=" + id)){
                while (rs.next()) {
                    listaProdutos.add(new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble(PRECO), rs.getInt("iva"), rs.getInt(MASSA)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return listaProdutos;
    }

    /**
     * verifica se o produto nao existe na base de dados
     *
     * @param nome
     */
    public boolean validaProduto(String nome) {
        try {
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps = con.prepareStatement("SELECT * FROM Produto where produto.nome like ?")){
                ps.setString(1, nome);
                try(ResultSet rsProduto = ps.executeQuery()){
                    return !rsProduto.next();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * regista produto na base de dados
     * @param nome
     * @param precoUnitario
     * @param pesoUnitario
     * @param iva
     *
     * @throws SQLException
     */
    public boolean registaProduto(String nome, double precoUnitario, int pesoUnitario, int iva) throws SQLException {
        try {
            Connection con = BaseDados.getConnection();
            try(PreparedStatement ps = con.prepareStatement("insert into Produto (nome, preco_unitario,iva, massa_unitaria) values (?, ?, ?, ?)")){
                ps.setString(1, nome);
                ps.setDouble(2, precoUnitario);
                ps.setInt(3,iva);
                ps.setInt(4, pesoUnitario);
                ps.executeUpdate();

                try(PreparedStatement ps2 = con.prepareStatement("SELECT id FROM Produto where Produto.nome like ?")){
                    ps2.setString(1, nome);
                    int idProduto ;

                    try(ResultSet rsProduto = ps2.executeQuery()){
                        if(!rsProduto.next())
                            throw new IllegalArgumentException("ERRO - produto nao existe");
                        idProduto = rsProduto.getInt("id");
                    }

                    try(ResultSet rs = con.createStatement().executeQuery("SELECT id FROM Farmacia")){
                        while(rs.next()){
                            try(PreparedStatement psd = con.prepareStatement("INSERT into Produto_Farmacia(id_produto,id_farmacia,stock) values(?,?,?)")){
                                psd.setInt(1,idProduto);
                                psd.setInt(2,rs.getInt("id"));
                                psd.setInt(3,0);
                                psd.executeUpdate();
                            }
                        }
                        return true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            BaseDados.closeConnection();
        }
    }

    /**
     * valida o id do produto atravez do id da farmacia
     * @param idFarmacia id farmacia
     * @param idProduto id produto
     * @return boolean se e valido
     * @throws InvalidNameException exception se os nomes das tabelas da base de dados forem alterados
     */
    public boolean validarProdutoIdByIdFarm(int idFarmacia, int idProduto) throws InvalidNameException {
        try {
            Connection con = BaseDados.getConnection();

            try(ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Produto inner join Produto_Farmacia on Produto_Farmacia.id_produto=Produto.id where Produto_Farmacia.id_farmacia=" + idFarmacia + " AND Produto_Farmacia.id_produto=" + idProduto)){
                if (!rs.next()) {
                    throw new InvalidNameException("Produto Invalido");
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
     * valida o id do produto, se o id existe ou nao na base de dados
     * @param idProduto id do produto
     * @return boolean se e valido
     * @throws InvalidNameException exception se os nomes das tabelas da base de dados forem alterados
     */
    public boolean validarProdutoId( int idProduto) throws InvalidNameException {
        try {
            Connection con = BaseDados.getConnection();

            try(ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Produto inner join Produto_Farmacia on Produto_Farmacia.id_produto=Produto.id where Produto_Farmacia.id_produto=" + idProduto)){
                if (!rs.next()) {
                    throw new InvalidNameException("Produto Invalido");
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
     * get do produto atraves do seu id
     * @param idProduto id produto
     * @return produto com o id correspondente
     */
    public Produto getProdutoById(int idProduto) {
        try {
            Connection con = BaseDados.getConnection();

            try(ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Produto where id=" + idProduto)){
                if(rs.next())
                    return new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble(PRECO), rs.getInt("iva"),  rs.getInt(MASSA));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDados.closeConnection();
        }
        return null;
    }

    /**
     * get stock de um produto de uma farmacia
     * @param idProduto id produto
     * @param idFarmacia id farmacia
     * @return numero de produtos em stock
     */
    public int getStock(int idProduto, int idFarmacia){
        try{
            Connection connection = BaseDados.getConnection();
            try(PreparedStatement ps = connection.prepareStatement("SELECT stock FROM Produto_Farmacia WHERE id_produto = ? AND id_farmacia = ?")){
                ps.setInt(1, idProduto);
                ps.setInt(2, idFarmacia);
                try(ResultSet r = ps.executeQuery()){
                    if(r.next())
                        return r.getInt(STOCK);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * adicionar stock de um certo produto a uma certa farmacia
     * @param idProduto id produto
     * @param idFarmacia id farmacia
     * @param stockAdicionar stock a adicionar
     * @return boolean se adicionou com sucesso
     */
    public boolean setStock(int idProduto, int idFarmacia, int stockAdicionar) {
        try {
            Connection con = BaseDados.getConnection();
            try(ResultSet resultSet=con.createStatement().executeQuery("UPDATE Produto_Farmacia SET Produto_Farmacia.stock=stock+" + stockAdicionar + " WHERE Produto_Farmacia.id_produto=" + idProduto + " AND Produto_Farmacia.id_farmacia=" + idFarmacia)){
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
     * verifica se existe stock suficiente para aquela compra
     * @param carrinho map com os produtos e as respetivas quantidades
     * @return boolean se existe stock suficiente
     */
    public boolean verificaStockDisponivelProduto(Map<Produto, Integer> carrinho) {
        try {
            Connection connection = BaseDados.getConnection();

            for (Map.Entry<Produto, Integer> produto: carrinho.entrySet()) {
                try(PreparedStatement ps = connection.prepareStatement("SELECT stock FROM Produto WHERE id = ?")){
                    ps.setInt(1, produto.getKey().getId());
                    try(ResultSet r = ps.executeQuery()){
                        r.next();
                        if (r.getInt(STOCK) < produto.getValue())
                            throw new IllegalArgumentException("Não existe stock suficiente");
                    }
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
     * verifica se tem stock para um certo produto de uma certa farmacia
     * @param idFarmacia id farmacia
     * @param idProduto id produto
     * @param quantidadeNecessaria quantidade a comparar
     * @return boolean se tem mais stock do que o comparado
     */
    public boolean verificaStock(int idFarmacia, int idProduto, int quantidadeNecessaria) {
        try {
            Connection connection = BaseDados.getConnection();

            try(PreparedStatement ps = connection.prepareStatement("SELECT stock FROM Produto_Farmacia WHERE id_produto = ? AND id_farmacia = ?")){
                ps.setInt(1, idProduto);
                ps.setInt(2, idFarmacia);

                try(ResultSet result = ps.executeQuery()){
                    result.next();
                    return result.getInt(STOCK) >= quantidadeNecessaria;
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
     * get id do produto com base no nome do produto e o id da farmacia
     * @param nomeProduto nome do produto
     * @param idFarmacia id da farmacia
     * @return int id do produto
     */
    public int getIDProduto(String nomeProduto, int idFarmacia) {
        try {
            Connection connection = BaseDados.getConnection();
            try(PreparedStatement ps = connection.prepareStatement("SELECT id FROM Produto WHERE nome = ? AND id_farmacia = ?")){
                ps.setString(1, nomeProduto);
                ps.setInt(2, idFarmacia);
                try(ResultSet result = ps.executeQuery()){
                    result.next();
                    return result.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            BaseDados.closeConnection();
        }
    }
}
