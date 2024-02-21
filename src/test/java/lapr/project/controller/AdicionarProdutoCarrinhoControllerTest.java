package lapr.project.controller;

import lapr.project.data.ProdutoBD;
import lapr.project.model.CarrinhoCompras;
import lapr.project.model.Produto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.naming.InvalidNameException;

import java.security.InvalidParameterException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdicionarProdutoCarrinhoControllerTest {
    @Mock
    private ProdutoBD produtoBD;
    @Test
    void validarProdutoId()  {
        try {
            produtoBD=mock(ProdutoBD.class);
            when(produtoBD.validarProdutoId(1)).thenReturn(true);

            AdicionarProdutoCarrinhoController adicionarProdutoCarrinhoController=new AdicionarProdutoCarrinhoController(produtoBD);

            adicionarProdutoCarrinhoController.validarProdutoId(1);

            assertTrue( adicionarProdutoCarrinhoController.validarProdutoId(1));
        } catch (InvalidNameException exception) {
            exception.printStackTrace();
        }

    }

    @Test
    void validarProdutoIdFail()  {
        try {
            produtoBD=mock(ProdutoBD.class);
            when(produtoBD.validarProdutoIdByIdFarm(1,2)).thenReturn(false);

            AdicionarProdutoCarrinhoController adicionarProdutoCarrinhoController=new AdicionarProdutoCarrinhoController(produtoBD);

            assertFalse( adicionarProdutoCarrinhoController.validarProdutoId(111));
        } catch (InvalidNameException exception) {
            exception.printStackTrace();
        }
    }


    /*@Test
    void getStock() {
        produtoBD = mock(ProdutoBD.class);
        AdicionarProdutoCarrinhoController adicionarProdutoCarrinhoController = new AdicionarProdutoCarrinhoController(produtoBD);
        when(produtoBD.getProdutoById(1)).thenReturn(new Produto(1, "Produto", 1, 10, 1));
        adicionarProdutoCarrinhoController.setIdProduto(1);
        assertEquals(10,adicionarProdutoCarrinhoController.getStock());
    }


*/
    @Test
    void adicionarAoCarrinho() {
        CarrinhoCompras.limparCarrinho();
        produtoBD = mock(ProdutoBD.class);
        Produto produto=new Produto(1, "Produto", 1, 10, 1);
        when(produtoBD.getProdutoById(1)).thenReturn(produto);
        AdicionarProdutoCarrinhoController adicionarProdutoCarrinhoController = new AdicionarProdutoCarrinhoController(produtoBD);
        adicionarProdutoCarrinhoController.setIdProduto(1);
        adicionarProdutoCarrinhoController.adicionarAoCarrinho(10);
        HashMap<Produto,Integer> expResult=new HashMap<>();
        expResult.put(produto,10);
        assertEquals(expResult,CarrinhoCompras.getCarrinho());
    }


    @Test
    void adicionarAoCarrinhoFail() {
        CarrinhoCompras.limparCarrinho();
        try {
            produtoBD = mock(ProdutoBD.class);
            when(produtoBD.getProdutoById(1)).thenReturn(new Produto(1, "Produto", 1, 10, 1));
            AdicionarProdutoCarrinhoController adicionarProdutoCarrinhoController = new AdicionarProdutoCarrinhoController(produtoBD);
            adicionarProdutoCarrinhoController.setIdProduto(1);
            adicionarProdutoCarrinhoController.adicionarAoCarrinho(20000);
        }catch (InvalidParameterException e){
            assertEquals(e.getMessage(),"Não tem stock suficiente");
        }
    }



}