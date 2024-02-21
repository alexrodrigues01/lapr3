package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.model.graph.Graph;
import lapr.project.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FinalizarCompraControllerTest {


    @Mock
    private ClienteBD clienteBD = mock(ClienteBD.class);;
    @Mock
    private ProdutoBD produtoBD = mock(ProdutoBD.class);
    @Mock
    private EncomendaBD encomendaBD = mock(EncomendaBD.class);
    @Mock
    private FaturaBD faturaBD = mock(FaturaBD.class);
    @Mock
    private FarmaciaBD farmaciaBD = mock(FarmaciaBD.class);
    @Mock
    private UtilizadorBD utilizadorBD = mock(UtilizadorBD.class);
    @Mock
    private MoradaBD moradaBD = mock(MoradaBD.class);
    @Mock
    private NotaBD notaBD = mock(NotaBD.class);

    private AplicacaoPOT app = AplicacaoPOT.getInstance();

    private FinalizarCompraController controller = new FinalizarCompraController(clienteBD, produtoBD, encomendaBD, faturaBD, farmaciaBD, moradaBD, notaBD);

    private String emailCliente = Constantes.EMAIL;
    private String password = "password";

    private int nif = 123456789;
    private int encomendaID = 1;
    private int farmaciaID = 1;
    private int faturaID = 1;
    private int carga = 3200;
    private int numeroCreditos = 10;
    private int idProduto1 = 1;
    private int idProduto2 = 2;
    private int idProduto3 = 3;
    private int idProduto4 = 4;
    private int quantidadeProduto = 4;
    private int farmaciaRecetoraID = 1;
    private int farmaciaEmissoraID = 2;

    private final Morada moradaCliente = new Morada("cliente", 10, 10, 10);
    private Map<Integer, Morada> moradasFarmacias = new HashMap<>();

    @BeforeEach
    void setUp() {

        //simulacao do log-in
        when(utilizadorBD.procuraUtilizador(emailCliente)).thenReturn(new Utilizador("teste", emailCliente, 123456789, 123456789, password, Constantes.PAPEL_CLIENTE));
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(emailCliente, password);

        //necessário para o funcionamento da UC - ATRIBUIR FARMACIA PROXIMA
        Morada moradaCliente = new Morada("cliente", 10, 10, 10);
        Morada farmarcia1 = new Morada("farmarcia1", 20, 20, 20);
        Morada farmarcia2 = new Morada("farmarcia2", 30, 30, 30);

        moradasFarmacias.put(1, farmarcia1);
        moradasFarmacias.put(2, farmarcia2);

        //GRAFO

        moradasFarmacias.put(farmaciaRecetoraID, farmarcia1);
        moradasFarmacias.put(farmaciaEmissoraID, farmarcia2);

        Graph<Morada, Double> graph = new Graph<>(false);
        graph.insertEdge(farmarcia1, farmarcia2, 0.04, 1);
        graph.insertEdge(moradaCliente, farmarcia1, 0.04, 1);

        app.setMapaTerrestreDistancias(graph);

        //CARRINHO
        Produto produto1 = new Produto(idProduto1, "teste1", 2.5, 5, 200);
        Produto produto2 = new Produto(idProduto2, "teste2", 2.5, 5, 200);
        Produto produto3 = new Produto(idProduto3, "teste3", 2.5, 5, 200);
        Produto produto4 = new Produto(idProduto4, "teste4", 2.5, 5, 200);

        CarrinhoCompras.limparCarrinho();
        CarrinhoCompras.adicionarProduto(produto1, quantidadeProduto);
        CarrinhoCompras.adicionarProduto(produto2, quantidadeProduto);
        CarrinhoCompras.adicionarProduto(produto3, quantidadeProduto);
        CarrinhoCompras.adicionarProduto(produto4, quantidadeProduto);

        // necessário para que o email do cliente fique guardado no controller
        when(clienteBD.getCreditos(emailCliente)).thenReturn(0);
        controller.getCreditos();
    }

    @Test
    void getCreditos() {
        int numeroCreditos = 5;
        when(clienteBD.getCreditos(emailCliente)).thenReturn(numeroCreditos);
        assertEquals(numeroCreditos, controller.getCreditos());
    }

    @Test
    public void validaCVVTest() {


        when(clienteBD.verificaCVV(emailCliente, 123)).thenReturn(true);
        assertTrue(controller.validaCVV(123));

        when(clienteBD.verificaCVV(emailCliente, 123)).thenReturn(false);
        assertFalse(controller.validaCVV(123));
    }

    @Test
    public void finalizarCompraTestSucesso1() {

        //UC - ATRIBUIR FARMACIA PROXIMA
        when(moradaBD.getMoradasFarmacia()).thenReturn(moradasFarmacias);
        when(moradaBD.getMoradaUtilizador(emailCliente)).thenReturn(moradaCliente);

        // sucesso
        when(produtoBD.getStock(idProduto1, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto2, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto3, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto4, farmaciaID)).thenReturn(quantidadeProduto);
        when(encomendaBD.novaEncomenda(farmaciaID, carga, CarrinhoCompras.getCarrinho(), emailCliente)).thenReturn(encomendaID);
        when(clienteBD.adicionaCreditos(emailCliente, 4)).thenReturn(true);
        when(clienteBD.descontaCreditos(emailCliente, numeroCreditos)).thenReturn(true);
        when(faturaBD.novaFatura(encomendaID, numeroCreditos, Constantes.TAXA_ENTREGA, nif, farmaciaID)).thenReturn(faturaID);
        when(faturaBD.getFatura(faturaID)).thenReturn(new Fatura(new Date(), 45, Constantes.TAXA_ENTREGA, nif));
        when(farmaciaBD.getNomeFarmacia(farmaciaID)).thenReturn("Teste");

        assertTrue(controller.finalizarCompra(numeroCreditos, nif));
        assertEquals(0, CarrinhoCompras.getCarrinho().size());
    }

    @Test
    public void finalizarCompraTestSucesso2() {

        //necessario para que a UC - pedir produto consiga produzir o output correto
        int quantidadeNecessaria = 1;

        String nomeProduto = "produto";

        Produto produto = new Produto(1, nomeProduto, 2.30, 5, 100);

        //UC - ATRIBUIR FARMACIA PROXIMA
        when(moradaBD.getMoradasFarmacia()).thenReturn(moradasFarmacias);
        when(moradaBD.getMoradaUtilizador(emailCliente)).thenReturn(moradaCliente);

        //UC - PEDIR PRODUTO
        when(produtoBD.getProdutoById(idProduto1)).thenReturn(produto);
        when(produtoBD.verificaStock(farmaciaEmissoraID, idProduto1, quantidadeNecessaria)).thenReturn(true);
        when(produtoBD.getIDProduto(nomeProduto, farmaciaEmissoraID)).thenReturn(2);
        when(notaBD.gerarNotas(farmaciaRecetoraID, farmaciaEmissoraID, idProduto1, quantidadeNecessaria)).thenReturn(1);


        // sucesso
        when(produtoBD.getStock(idProduto1, farmaciaID)).thenReturn(quantidadeProduto-1);
        when(produtoBD.getStock(idProduto2, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto3, farmaciaID)).thenReturn(quantidadeProduto+1);
        when(produtoBD.getStock(idProduto4, farmaciaID)).thenReturn(quantidadeProduto);
        when(encomendaBD.novaEncomenda(farmaciaID, carga, CarrinhoCompras.getCarrinho(), emailCliente)).thenReturn(encomendaID);
        when(clienteBD.adicionaCreditos(emailCliente, 4)).thenReturn(true);
        when(faturaBD.novaFatura(encomendaID, 0, Constantes.TAXA_ENTREGA, nif, farmaciaID)).thenReturn(faturaID);
        when(faturaBD.getFatura(faturaID)).thenReturn(new Fatura(new Date(), 45, Constantes.TAXA_ENTREGA, nif));
        when(farmaciaBD.getNomeFarmacia(farmaciaID)).thenReturn("Teste");

        assertTrue(controller.finalizarCompra(0, nif));
    }

    @Test
    public void finalizarCompraTestInsucesso1() {

        int quantidadeNecessaria = 1;

        int farmaciaEmissoraID = 2;
        String nomeProduto = "produto";

        Produto produto = new Produto(1, nomeProduto, 2.30, 5, 100);

        //UC - ATRIBUIR FARMACIA PROXIMA
        when(moradaBD.getMoradasFarmacia()).thenReturn(moradasFarmacias);
        when(moradaBD.getMoradaUtilizador(emailCliente)).thenReturn(moradaCliente);

        //UC - PEDIR PRODUTO
        when(produtoBD.getProdutoById(idProduto1)).thenReturn(produto);
        when(produtoBD.verificaStock(farmaciaEmissoraID, idProduto1, quantidadeNecessaria)).thenReturn(false);

        //insucesso (nenhuma farmacia tem a quantidade nessessaria para satisfazer aquela encomenda)
        when(produtoBD.getStock(idProduto1, farmaciaID)).thenReturn(quantidadeProduto-quantidadeNecessaria);

        try {
            controller.finalizarCompra(numeroCreditos, nif);
        } catch (Exception e) {
            assertEquals("ERRO - nao ha nenhuma farmacia que possa satisfazer o pedido", e.getMessage());
        }
    }

    @Test
    public void finalizarCompraTesteInsucesso2(){


        // insucesso (nif inválido)

        try {
            controller.finalizarCompra(numeroCreditos, 1);
        } catch (Exception e) {
            assertEquals("NIF inválido", e.getMessage());
        }
    }

    @Test
    public void finalizarCompraTestInsucesso3() {

        //UC - ATRIBUIR FARMACIA PROXIMA
        when(moradaBD.getMoradasFarmacia()).thenReturn(moradasFarmacias);
        when(moradaBD.getMoradaUtilizador(emailCliente)).thenReturn(moradaCliente);

        when(produtoBD.getStock(idProduto1, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto2, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto3, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto4, farmaciaID)).thenReturn(quantidadeProduto);
        when(encomendaBD.novaEncomenda(farmaciaID, carga, CarrinhoCompras.getCarrinho(), emailCliente)).thenThrow(new IllegalArgumentException("ERRO - encomenda não criada na base de dados"));

        try {
            controller.finalizarCompra(numeroCreditos, nif);
        } catch (Exception e) {
            assertEquals("ERRO - encomenda não criada na base de dados", e.getMessage());
        }
    }



    @Test
    public void finalizarCompraTestInsucesso5() {

        //UC - ATRIBUIR FARMACIA PROXIMA
        when(moradaBD.getMoradasFarmacia()).thenReturn(moradasFarmacias);
        when(moradaBD.getMoradaUtilizador(emailCliente)).thenReturn(moradaCliente);

        when(produtoBD.getStock(idProduto1, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto2, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto3, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto4, farmaciaID)).thenReturn(quantidadeProduto);
        when(encomendaBD.novaEncomenda(farmaciaID, carga, CarrinhoCompras.getCarrinho(), emailCliente)).thenReturn(encomendaID);
        when(clienteBD.adicionaCreditos(emailCliente, 4)).thenReturn(false);

        try {
            controller.finalizarCompra(numeroCreditos, nif);
        } catch (Exception e) {
            assertEquals("ERRO - novos creditos nao adicionados", e.getMessage());
        }
    }

    @Test
    public void finalizarCompraTestInsucesso6() {

        //UC - ATRIBUIR FARMACIA PROXIMA
        when(moradaBD.getMoradasFarmacia()).thenReturn(moradasFarmacias);
        when(moradaBD.getMoradaUtilizador(emailCliente)).thenReturn(moradaCliente);

        when(produtoBD.getStock(idProduto1, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto2, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto3, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto4, farmaciaID)).thenReturn(quantidadeProduto);
        when(encomendaBD.novaEncomenda(farmaciaID, carga, CarrinhoCompras.getCarrinho(), emailCliente)).thenReturn(encomendaID);
        when(clienteBD.adicionaCreditos(emailCliente, 4)).thenReturn(true);
        when(clienteBD.descontaCreditos(emailCliente, numeroCreditos)).thenReturn(false);

        try {
            controller.finalizarCompra(numeroCreditos, nif);
        } catch (Exception e) {
            assertEquals("ERRO - creditos de desconto nao descontados da conta do cliente", e.getMessage());
        }
    }

    @Test
    public void finalizarCompraTestInsucesso7() {

        //UC - ATRIBUIR FARMACIA PROXIMA
        when(moradaBD.getMoradasFarmacia()).thenReturn(moradasFarmacias);
        when(moradaBD.getMoradaUtilizador(emailCliente)).thenReturn(moradaCliente);

        when(produtoBD.getStock(idProduto1, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto2, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto3, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto4, farmaciaID)).thenReturn(quantidadeProduto);
        when(encomendaBD.novaEncomenda(farmaciaID, carga, CarrinhoCompras.getCarrinho(), emailCliente)).thenReturn(encomendaID);
        when(clienteBD.adicionaCreditos(emailCliente, 4)).thenReturn(true);
        when(clienteBD.descontaCreditos(emailCliente, numeroCreditos)).thenReturn(true);
        when(faturaBD.novaFatura(encomendaID, numeroCreditos, Constantes.TAXA_ENTREGA, nif, farmaciaID)).thenThrow(new IllegalArgumentException("ERRO - fatura nao criada"));

        try {
            controller.finalizarCompra(numeroCreditos, nif);
        } catch (Exception e) {
            assertEquals("ERRO - fatura nao criada", e.getMessage());
        }
    }

    @Test
    public void finalizarCompraTestInsucesso8() {

        //UC - ATRIBUIR FARMACIA PROXIMA
        when(moradaBD.getMoradasFarmacia()).thenReturn(moradasFarmacias);
        when(moradaBD.getMoradaUtilizador(emailCliente)).thenReturn(moradaCliente);

        when(produtoBD.getStock(idProduto1, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto2, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto3, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto4, farmaciaID)).thenReturn(quantidadeProduto);
        when(encomendaBD.novaEncomenda(farmaciaID, carga, CarrinhoCompras.getCarrinho(), emailCliente)).thenReturn(encomendaID);
        when(clienteBD.adicionaCreditos(emailCliente, 4)).thenReturn(true);
        when(clienteBD.descontaCreditos(emailCliente, numeroCreditos)).thenReturn(true);
        when(faturaBD.novaFatura(encomendaID, numeroCreditos, Constantes.TAXA_ENTREGA, nif, farmaciaID)).thenReturn(faturaID);
        when(faturaBD.getFatura(faturaID)).thenThrow(new IllegalArgumentException("ERRO - falha na obtencao da fatura da base de dados"));

        try {
            controller.finalizarCompra(numeroCreditos, nif);
        } catch (Exception e) {
            assertEquals("ERRO - falha na obtencao da fatura da base de dados", e.getMessage());
        }
    }

    @Test
    public void finalizarCompraTestInsucesso9() {

        //UC - ATRIBUIR FARMACIA PROXIMA
        when(moradaBD.getMoradasFarmacia()).thenReturn(moradasFarmacias);
        when(moradaBD.getMoradaUtilizador(emailCliente)).thenReturn(moradaCliente);

        when(produtoBD.getStock(idProduto1, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto2, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto3, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto4, farmaciaID)).thenReturn(quantidadeProduto);
        when(encomendaBD.novaEncomenda(farmaciaID, carga, CarrinhoCompras.getCarrinho(), emailCliente)).thenReturn(encomendaID);
        when(clienteBD.adicionaCreditos(emailCliente, 4)).thenReturn(true);
        when(clienteBD.descontaCreditos(emailCliente, numeroCreditos)).thenReturn(true);
        when(faturaBD.novaFatura(encomendaID, numeroCreditos, Constantes.TAXA_ENTREGA, nif, farmaciaID)).thenReturn(faturaID);
        when(faturaBD.getFatura(faturaID)).thenReturn(new Fatura(new Date(), 45, Constantes.TAXA_ENTREGA, nif));
        when(farmaciaBD.getNomeFarmacia(farmaciaID)).thenThrow(new IllegalArgumentException("ERRO - falha na obtencao do nome da farmacia"));

        try {
            controller.finalizarCompra(numeroCreditos, nif);
        } catch (Exception e) {
            assertEquals("ERRO - falha na obtencao do nome da farmacia", e.getMessage());
        }
    }

    @Test
    public void finalizarCompraTesteInsucesso10(){

        //UC - ATRIBUIR FARMACIA PROXIMA
        when(moradaBD.getMoradasFarmacia()).thenReturn(moradasFarmacias);
        when(moradaBD.getMoradaUtilizador(null)).thenReturn(moradaCliente);

        when(utilizadorBD.procuraUtilizador(null)).thenReturn(new Utilizador("teste", null, 123456789, 123456789, password, Constantes.PAPEL_CLIENTE));

        //simulacao do log-in
        app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(null, password);

        // necessário para que o email do cliente fique guardado no controller
        when(clienteBD.getCreditos(null)).thenReturn(0);
        controller.getCreditos();


        // insucesso (não enviou email)
        when(produtoBD.getStock(idProduto1, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto2, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto3, farmaciaID)).thenReturn(quantidadeProduto);
        when(produtoBD.getStock(idProduto4, farmaciaID)).thenReturn(quantidadeProduto);
        when(encomendaBD.novaEncomenda(farmaciaID, carga, CarrinhoCompras.getCarrinho(), null)).thenReturn(encomendaID);
        when(clienteBD.adicionaCreditos(null, 4)).thenReturn(true);
        when(clienteBD.descontaCreditos(null, numeroCreditos)).thenReturn(true);
        when(faturaBD.novaFatura(encomendaID, numeroCreditos, Constantes.TAXA_ENTREGA, nif, farmaciaID)).thenReturn(faturaID);
        when(faturaBD.getFatura(faturaID)).thenReturn(new Fatura(new Date(), 45, Constantes.TAXA_ENTREGA, nif));
        when(farmaciaBD.getNomeFarmacia(farmaciaID)).thenReturn("Teste");

        assertFalse(controller.finalizarCompra(numeroCreditos, nif));
    }
}