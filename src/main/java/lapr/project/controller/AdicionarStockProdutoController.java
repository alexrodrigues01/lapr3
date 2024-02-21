package lapr.project.controller;

import lapr.project.data.FarmaciaBD;
import lapr.project.data.ProdutoBD;
import lapr.project.model.Farmacia;
import lapr.project.model.Produto;
import lapr.project.model.autorizacao.model.SessaoUtilizador;
import lapr.project.utils.StringConverter;
import javax.naming.InvalidNameException;
import java.util.List;

/**
 * class AdicionarStockProdutoController
 */
public class AdicionarStockProdutoController {

    List<Produto> listaProdutos;

    private final FarmaciaBD farmaciaBD;
    private final ProdutoBD produtoBD;

    /**
     * construtor AdicionarStockProdutoController
     * @param farmaciaBD farmaciaBD
     * @param produtoBD produtoBD
     */
    public AdicionarStockProdutoController(FarmaciaBD farmaciaBD, ProdutoBD produtoBD) {
        this.farmaciaBD = farmaciaBD;
        this.produtoBD = produtoBD;
    }

    /**
     * getter id farmacia do gestor a usar a app
     * @return id farmacia do gestor a usar a app
     */
    public int getIdFarmacia(){
        AplicacaoPOT app = AplicacaoPOT.getInstance();
        SessaoUtilizador sessao = app.getSessaoAtual();
        String emailGestor = sessao.getEmailUtilizador();
        Farmacia farmacia = farmaciaBD.getFarmaciabyGestor(emailGestor);
        return farmacia.getId();
    }

    /**
     * getter lista de ids de produtos da farmacia
     * @return lista de ids de produtos da farmacia
     */
    public List<String> getProdutosByFarmacia(){
        int idFarmacia = getIdFarmacia();
        listaProdutos=produtoBD.getProdutoByFarmacia(idFarmacia);
        return StringConverter.convertToStringList(listaProdutos);
    }

    /**
     * valida o produto
     * @param idProduto id do produto
     * @return boolean
     * @throws InvalidNameException exception
     */
    public boolean validaProduto(int idProduto) throws InvalidNameException {
        int idFarmacia = getIdFarmacia();
        return produtoBD.validarProdutoIdByIdFarm(idFarmacia,idProduto);
    }

    /**
     * altera o stock referente ao produto que tem o id de parametro
     * @param idProduto id do produto
     * @param stockAdicionar stock a adicionar
     * @return boolean
     */
    public boolean alteraStockProduto(int idProduto, int stockAdicionar){
        int idFarmacia = getIdFarmacia();
        return produtoBD.setStock(idProduto,idFarmacia,stockAdicionar);
    }
}
