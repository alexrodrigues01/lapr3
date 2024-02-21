package lapr.project.controller;

import lapr.project.data.ProdutoBD;
import lapr.project.model.CarrinhoCompras;
import lapr.project.model.Produto;

import javax.naming.InvalidNameException;

/**
 * The type Adicionar produto carrinho controller.
 */
public class AdicionarProdutoCarrinhoController {
    private final ProdutoBD produtoBD;
    private int idProduto;

    /**
     * Instantiates a new Adicionar produto carrinho controller.
     *
     * @param produtoBD the produto bd
     */
    public AdicionarProdutoCarrinhoController(ProdutoBD produtoBD) {
        this.produtoBD = produtoBD;
    }

    /**
     * Valida o produto escolhido.
     *
     * @param idProduto the id produto
     * @return the boolean
     * @throws InvalidNameException the invalid name exception
     */
    public boolean validarProdutoId(int idProduto) throws InvalidNameException {
        this.idProduto=idProduto;
        return produtoBD.validarProdutoId(idProduto);
    }

    /**
     * Adiciona ao carrinho o produto e a quantidade.
     *
     * @param quantidade the quantidade
     */
    public void adicionarAoCarrinho(int quantidade){
        Produto produto = produtoBD.getProdutoById(idProduto);
        CarrinhoCompras.adicionarProduto(produto, quantidade);
    }

    /**
     * Sets id produto.
     *
     * @param idProduto the id produto
     */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }
}
