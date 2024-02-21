package lapr.project.controller;

import lapr.project.data.FarmaciaBD;
import lapr.project.data.ProdutoBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.model.Farmacia;
import lapr.project.model.LugarEstacionamento;
import lapr.project.model.Produto;
import lapr.project.model.Utilizador;
import lapr.project.utils.StringConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdicionarStockProdutoControllerTest {

    @Mock
    private FarmaciaBD farmaciaBD;

    @Mock
    private ProdutoBD produtoBD;

    @Mock
    private UtilizadorBD utilizadorBD;

    private AdicionarStockProdutoController instance;

    @Test
    void getIdFarmacia() throws InvalidNameException {
        farmaciaBD = mock(FarmaciaBD.class);
        produtoBD = mock(ProdutoBD.class);
        utilizadorBD = mock(UtilizadorBD.class);

        instance= new AdicionarStockProdutoController(farmaciaBD,produtoBD);
        String email = "teste@email.com";
        LugarEstacionamento lE = new LugarEstacionamento(true, 1, 2);

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));
        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1, "farm", 111111111, 919191919, "teste@email.com", email, "1", lE));
        when(produtoBD.validarProdutoIdByIdFarm(1,1)).thenReturn(true);

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(email,"password");

        int result = instance.getIdFarmacia();
        assertEquals(1,result);
    }

    @Test
    void getProdutosByFarmacia() {
        farmaciaBD = mock(FarmaciaBD.class);
        produtoBD = mock(ProdutoBD.class);
        utilizadorBD = mock(UtilizadorBD.class);

        instance= new AdicionarStockProdutoController(farmaciaBD,produtoBD);
        String email = "teste@email.com";
        LugarEstacionamento lE = new LugarEstacionamento(true, 1, 2);

        Produto paracetamol = new Produto(1,"Paracetamol",6.75,6,35);
        Produto viagra = new Produto(2,"Viagra",19.75,66,180);
        Produto produto = new Produto(3,"Produto",2.0,6,80);

        List<Produto> lista = new ArrayList<>();
        lista.add(paracetamol);
        lista.add(viagra);
        lista.add(produto);

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));
        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1, "farm", 111111111, 919191919, "teste@email.com", email, "1", lE));
        when(produtoBD.getProdutoByFarmacia(1)).thenReturn(lista);

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(email,"password");

        List<String> expResult = StringConverter.convertToStringList(lista);
        List<String> result = instance.getProdutosByFarmacia();

        assertEquals(expResult,result);

    }

    @Test
    void validaProdutoSuccess() throws Exception {
        farmaciaBD = mock(FarmaciaBD.class);
        produtoBD = mock(ProdutoBD.class);
        utilizadorBD = mock(UtilizadorBD.class);

        instance= new AdicionarStockProdutoController(farmaciaBD,produtoBD);
        String email = "teste@email.com";
        LugarEstacionamento lE = new LugarEstacionamento(true, 1, 2);
        Produto paracetamol = new Produto(1,"Paracetamol",6.75,6,35);

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));
        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1, "farm", 111111111, 919191919, "teste@email.com", email, "1", lE));
        when(produtoBD.validarProdutoIdByIdFarm(1,1)).thenReturn(true);

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(email,"password");

        boolean result = instance.validaProduto(1);
        assertTrue(result);
    }

    @Test
    void validaProdutoFail() throws Exception {
        farmaciaBD = mock(FarmaciaBD.class);
        produtoBD = mock(ProdutoBD.class);
        utilizadorBD = mock(UtilizadorBD.class);

        instance= new AdicionarStockProdutoController(farmaciaBD,produtoBD);
        String email = "teste@email.com";
        LugarEstacionamento lE = new LugarEstacionamento(true, 1, 2);
        Produto paracetamol = new Produto(1,"Paracetamol",6.75,0,35);

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));
        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1, "farm", 111111111, 919191919, "teste@email.com", email, "1", lE));
        when(produtoBD.validarProdutoIdByIdFarm(1,1)).thenReturn(false);

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(email,"password");

        boolean result = instance.validaProduto(1);
        assertFalse(result);
    }

    @Test
    void alteraStockProdutoSuccess() {
        farmaciaBD = mock(FarmaciaBD.class);
        produtoBD = mock(ProdutoBD.class);

        instance= new AdicionarStockProdutoController(farmaciaBD,produtoBD);
        utilizadorBD = mock(UtilizadorBD.class);

        String email = "teste@email.com";
        LugarEstacionamento lE = new LugarEstacionamento(true, 1, 2);
        Produto paracetamol = new Produto(1,"Paracetamol",6.75,0,35);

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));
        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1, "farm", 111111111, 919191919, "teste@email.com", email, "1", lE));
        when(produtoBD.setStock(1,1,2)).thenReturn(true);

        boolean result = instance.alteraStockProduto(1,2);
        assertTrue(result);
    }

    @Test
    void alteraStockProdutoFail() {
        farmaciaBD = mock(FarmaciaBD.class);
        produtoBD = mock(ProdutoBD.class);

        instance= new AdicionarStockProdutoController(farmaciaBD,produtoBD);
        utilizadorBD = mock(UtilizadorBD.class);

        String email = "teste@email.com";
        LugarEstacionamento lE = new LugarEstacionamento(true, 1, 2);
        Produto paracetamol = new Produto(1,"Paracetamol",6.75,0,35);

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));
        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1, "farm", 111111111, 919191919, "teste@email.com", email, "1", lE));
        when(produtoBD.setStock(1,1,2)).thenReturn(false);

        boolean result = instance.alteraStockProduto(1,2);
        assertFalse(result);
    }
}