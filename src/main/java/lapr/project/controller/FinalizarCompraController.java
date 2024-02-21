package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.CarrinhoCompras;
import lapr.project.utils.Constantes;
import lapr.project.model.Fatura;
import lapr.project.model.Produto;
import lapr.project.model.autorizacao.model.SessaoUtilizador;
import lapr.project.utils.Emails;
import lapr.project.utils.ValidarParametros;
import java.util.Map;

public class FinalizarCompraController {

    private final ClienteBD clienteBD;
    private final ProdutoBD produtoBD;
    private final EncomendaBD encomendaBD;
    private final FaturaBD faturaBD;
    private final FarmaciaBD farmaciaBD;
    private final MoradaBD moradaBD;
    private final NotaBD notaBD;

    private String emailCliente;

    /**
     * Construtor da classe FinalizarCompraController
     * @param clienteBD clienteBD
     * @param produtoBD produtoBD
     * @param encomendaBD encomendaBD
     * @param faturaBD faturaBD
     * @param farmaciaBD farmaciaBD
     * @param moradaBD moradaBD
     * @param notaBD notaBD
     */
    public FinalizarCompraController(ClienteBD clienteBD, ProdutoBD produtoBD, EncomendaBD encomendaBD, FaturaBD faturaBD, FarmaciaBD farmaciaBD, MoradaBD moradaBD, NotaBD notaBD) {
        this.clienteBD = clienteBD;
        this.produtoBD = produtoBD;
        this.encomendaBD = encomendaBD;
        this.faturaBD = faturaBD;
        this.farmaciaBD = farmaciaBD;
        this.moradaBD = moradaBD;
        this.notaBD = notaBD;
    }

    /**
     * metodo que vai buscar o numero de creditos que o cliente tem acumulados
     * @return return no numero de creditos que o cliente tem
     */
    public int getCreditos() {
        AplicacaoPOT app = AplicacaoPOT.getInstance();
        SessaoUtilizador sessao = app.getSessaoAtual();
        emailCliente = sessao.getEmailUtilizador();
        return clienteBD.getCreditos(emailCliente);
    }

    /**
     * forma de verificacao que e o proprio cliente a fazer a compra
     * compara o cvv que introduziu com o cvv do cartao de credito do cliente
     * @param cvv cvv que o cliente introduziu
     * @return true se o CVV esta correto, false quando esta incorreto
     */
    public boolean validaCVV(int cvv) {

        return clienteBD.verificaCVV(emailCliente, cvv);
    }

    /**
     *
     * @param numeroCreditosDescontados numero de créditos que o cliente quis descontar.
     * @param nif nif que a fatura vai estar associada
     * @return return true caso enviou email, return false caso não enviou email
     */
    public boolean finalizarCompra(int numeroCreditosDescontados, int nif) {

        ValidarParametros.validaParametroNIF(nif);

        AtribuirFarmaciaProximaTask atribuirFarmaciaProximaTask = new AtribuirFarmaciaProximaTask(moradaBD);
        int farmaciaID = atribuirFarmaciaProximaTask.atribuiEncomendaFarmacia(emailCliente);

        Map<Produto, Integer> carrinho = CarrinhoCompras.getCarrinho();

        if (!validaStock(carrinho, farmaciaID))
            throw new IllegalArgumentException("ERRO - nao ha nenhuma farmacia que possa satisfazer o pedido");

        int carga = calcularPesoCarrinho(carrinho);

        int encomendaID = encomendaBD.novaEncomenda(farmaciaID, carga, carrinho, emailCliente);

        double precoTotal = CarrinhoCompras.getPrecoTotal();

        if (!clienteBD.adicionaCreditos(emailCliente, calcularCreditos(precoTotal)))
            throw new IllegalArgumentException("ERRO - novos creditos nao adicionados");

        if (numeroCreditosDescontados != 0 && !clienteBD.descontaCreditos(emailCliente, numeroCreditosDescontados))
            throw new IllegalArgumentException("ERRO - creditos de desconto nao descontados da conta do cliente");

        CarrinhoCompras.limparCarrinho();

        int faturaID = faturaBD.novaFatura(encomendaID, numeroCreditosDescontados, Constantes.TAXA_ENTREGA, nif, farmaciaID);

        Fatura fatura = faturaBD.getFatura(faturaID);

        String nomeFarmacia = farmaciaBD.getNomeFarmacia(farmaciaID);

        return Emails.sendEmail(nomeFarmacia, emailCliente, "Fatura", fatura.toString());

    }

    /**
     * verifica para cada produto se existe stock suficiente. Caso nao haja, entao vai pedir a outras farmacias
     * @param carrinho
     * @return true caso consiga satisfazer a encomenda, false caso contrario
     */
    private boolean validaStock(Map<Produto, Integer> carrinho, int farmaciaID) {

        for (Map.Entry<Produto, Integer> entry : carrinho.entrySet()) {

            int produtoID = entry.getKey().getId();
            int quantidadeNecessariaPedir = entry.getValue() - produtoBD.getStock(produtoID, farmaciaID);

            if (quantidadeNecessariaPedir > 0) {
                PedirProdutoTask pedirProdutoTask = new PedirProdutoTask(moradaBD, produtoBD, notaBD);

                if (!pedirProdutoTask.pedirProduto(farmaciaID, produtoID, quantidadeNecessariaPedir))
                    return false;
            }
        }
        return true;
    }

    /**
     * calcula o peso total (carga) do carrinho
     * @param carrinho carrinho de compras que contem todos os produtos que o cliente deseja comprar
     * @return peso (em gramas) total do carrinho
     */
    private int calcularPesoCarrinho(Map<Produto, Integer> carrinho) {
        int pesoTotal = 0;
        for (Map.Entry<Produto, Integer> entry : carrinho.entrySet()) {
            pesoTotal += entry.getKey().getPesoUnitario() * entry.getValue();
        }
        return pesoTotal;
    }

    /**
     * calcula o numero de creditos que o cliente vai ganhar com esta encomenda (a cada 10 euros, anha 1 credito)
     * @param precoTotal preco total dos produtos
     * @return numero de creditos
     */
    private int calcularCreditos(double precoTotal) {
        return (int) ((precoTotal * Constantes.PERCENTAGEM_CREDITOS) / 100);
    }
}
