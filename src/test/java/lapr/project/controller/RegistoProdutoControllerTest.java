package lapr.project.controller;

import lapr.project.data.FarmaciaBD;
import lapr.project.data.ProdutoBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.model.Farmacia;
import lapr.project.model.LugarEstacionamento;
import lapr.project.model.Utilizador;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegistoProdutoControllerTest {

    @Mock
    private ProdutoBD produtoBD;

    private RegistoProdutoController instance;

    @Test
    void novoProdutoSuccess() {
        produtoBD = mock(ProdutoBD.class);
        instance= new RegistoProdutoController(produtoBD);
        when(produtoBD.validaProduto("viagra")).thenReturn(true);
        instance.novoProduto("viagra",19.75,55,6);
    }

    @Test
    void novoProdutoFail() {
        produtoBD = mock(ProdutoBD.class);
        instance= new RegistoProdutoController(produtoBD);
        IllegalArgumentException exception = new IllegalArgumentException("Produto já existe na base de dados.");
        try{
            when(produtoBD.validaProduto("batata")).thenReturn(false);
            instance.novoProduto("batata",19.75,55,6);
        }catch (IllegalArgumentException e){
            assertEquals(exception.getMessage(),e.getMessage());
        }
    }

    @Test
    void novoProdutoFail2() {
        produtoBD = mock(ProdutoBD.class);
        instance= new RegistoProdutoController(produtoBD);
        IllegalArgumentException exception = new IllegalArgumentException("O preço não pode ser menor ou igual a 0.");
        try{
            instance.novoProduto("viagra",-1,55,6);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(),e.getMessage());
        }
    }

    @Test
    void registarProdutoSuccess() throws Exception {
        produtoBD = mock(ProdutoBD.class);
        instance= new RegistoProdutoController(produtoBD);
        when(produtoBD.registaProduto("viagra",19.75,180,6)).thenReturn(true);

        boolean result = instance.registarProduto("viagra",19.75,180,6);
        assertTrue(result);
    }

    @Test
    void registarProdutoFail() throws Exception {
        produtoBD = mock(ProdutoBD.class);
        instance= new RegistoProdutoController(produtoBD);
        when(produtoBD.registaProduto("viagra",19.75,180,6)).thenReturn(false);

        boolean result = instance.registarProduto("viagra",19.75,180,6);
        assertFalse(result);
    }
}
