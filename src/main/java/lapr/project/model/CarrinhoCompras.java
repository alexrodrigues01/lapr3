package lapr.project.model;

import java.util.HashMap;
import java.util.Map;

/**
 * classe referente ao carrinho de compras
 */
public class CarrinhoCompras {
    /**
     * carrinho de compra
     */
    private static Map<Produto,Integer> carrinho = new HashMap<>();
    /**
     * preco total
     */
    private static double precoTotal;

    /**
     * construtor CarrinhoCompras
     */
    private CarrinhoCompras() {
    }

    /**
     * adiciona produto e a quantidade ao carrinho
     * @param produto produto
     * @param quantidade quantidade do produto
     */
    public static void adicionarProduto(Produto produto, int quantidade){
        if(carrinho.containsKey(produto)){
            retirarPreco(produto,carrinho.get(produto));
            carrinho.replace(produto,quantidade);
        }else {
            carrinho.put(produto, quantidade);

        }
        atualizarPreco(produto,quantidade);
    }

    /**
     * atualiza o preco com base na quantidade
     * @param produto produto
     * @param quantidade quantidade do produto
     */
    private static void atualizarPreco(Produto produto,int quantidade){
        precoTotal=precoTotal+produto.getPrecoUnitario()*quantidade;
    }

    /**
     * retira o preco com base na quantidade
     * @param produto produto
     * @param quantidade quantidade do produto
     */
    public static void retirarPreco(Produto produto,int quantidade){
        precoTotal=precoTotal-produto.getPrecoUnitario()*quantidade;
    }

    /**
     * getter carrinho
     * @return carrinho
     */
    public static Map<Produto,Integer> getCarrinho(){
        return carrinho;
    }

    /**
     * getter do preco total
     * @return precoTotal
     */
    public static double getPrecoTotal() {
        return precoTotal;
    }

    /**
     * limpar carrinho de compras
     */
    public static void limparCarrinho(){
        carrinho.clear();
        precoTotal = 0;
    }
}
