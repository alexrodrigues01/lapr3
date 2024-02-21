package lapr.project.model;

import lapr.project.controller.AdicionarProdutoCarrinhoController;
import lapr.project.data.ProdutoBD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CarrinhoComprasTest {

    @BeforeEach
    void setUp() {
        CarrinhoCompras.limparCarrinho();
    }

    @Test
    void adicionarProduto() {
        CarrinhoCompras.limparCarrinho();
        Produto produto=new Produto(1, "Produto", 1, 10, 1);
        CarrinhoCompras.adicionarProduto(produto,10);
        HashMap<Produto,Integer> expResult=new HashMap<>();
        expResult.put(produto,10);
        assertEquals(expResult,CarrinhoCompras.getCarrinho());
    }

    @Test
    void adicionarProduto1() {
        CarrinhoCompras.limparCarrinho();
        Produto produto=new Produto(1, "Produto", 1, 10, 1);
        CarrinhoCompras.adicionarProduto(produto,15);
        CarrinhoCompras.adicionarProduto(produto,10);
        HashMap<Produto,Integer> expResult=new HashMap<>();
        expResult.put(produto,10);
        assertEquals(expResult,CarrinhoCompras.getCarrinho());
        assertEquals(CarrinhoCompras.getPrecoTotal(),10);
    }


    @Test
    void retirarPreco() {
        Produto instance = new Produto(3,"Produto",2.0,2,80);
        CarrinhoCompras.adicionarProduto(instance,3);
       CarrinhoCompras.retirarPreco(instance,3);
        assertEquals(0, CarrinhoCompras.getPrecoTotal());
    }

    @Test
    void getCarrinho() {
        HashMap<Produto,Integer> carrinho = new HashMap<>();
        Produto produto1 = new Produto(3,"Produto",2.0,2,80);
        Produto produto2 = new Produto(3,"Produto",2.0,2,80);
        carrinho.put(produto1,3);
        carrinho.put(produto2,3);

        CarrinhoCompras.adicionarProduto(produto1, 3);
        CarrinhoCompras.adicionarProduto(produto2, 3);

        assertEquals(carrinho,CarrinhoCompras.getCarrinho());
    }

    @Test
    void getPrecoTotal() {
        CarrinhoCompras.adicionarProduto(new Produto(3,"Produto",2.0,2,80), 3);

        assertEquals(6.0,CarrinhoCompras.getPrecoTotal(),0.1);
    }

    @Test
    void limparCarrinho() {

        CarrinhoCompras.limparCarrinho();
        HashMap<Produto,Integer> expected = new HashMap<>();
        Map<Produto,Integer> carrinho = CarrinhoCompras.getCarrinho();

        assertEquals(expected , carrinho);
    }
}