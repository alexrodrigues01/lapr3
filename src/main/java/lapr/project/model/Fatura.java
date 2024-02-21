package lapr.project.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * classe refente à fatura
 */
public class Fatura {


    /**
     * classe privada referente à LinhaFatura
     */
    private class LinhaFatura{
        private final String nome;
        private final double valor;
        private final int quantidade;
        private final double precoUnitario;
        private final int iva;

        /**
         * constreutor linha fatura
         * @param nome nome linha fatura
         * @param valor valor linha fatura
         * @param quantidade quantidade linha fatura
         * @param precoUnitario precoUnitario linha fatura
         * @param iva iva linha fatura
         */
        public LinhaFatura(String nome, double valor, int quantidade, double precoUnitario, int iva){
            this.nome = nome;
            this.valor = valor;
            this.quantidade = quantidade;
            this.precoUnitario = precoUnitario;
            this.iva = iva;
        }

        /**
         * to string linha fatura
         * @return to string
         */
        @Override
        public String toString() {
            return "-> Nome: " + nome +
                    ", Valor: " + valor +
                    ", Quantidade: " + quantidade +
                    ", Preço: " + precoUnitario +
                    ", IVA: " + iva ;
        }
    }

    private final Date data;
    private final double valorProdutos;
    private final int taxaEntrega;
    private final int nif;
    private int id;

    Set<LinhaFatura> produtos;

    /**
     * construtor fatura
     * @param data data fatura
     * @param valorProdutos valor produtos fatura
     * @param taxaEntrega taxa entrega fatura
     * @param nif nif fatura
     */
    public Fatura(Date data, double valorProdutos, int taxaEntrega, int nif){
        this.data = (Date) data.clone();
        this.valorProdutos = valorProdutos;
        this.taxaEntrega = taxaEntrega;
        this.nif = nif;

        produtos = new HashSet<>();
    }

    /**
     * construtor fatura
     * @param id id da fatura
     * @param data data fatura
     * @param valorProdutos valor produtos fatura
     * @param taxaEntrega taxa entrega fatura
     * @param nif nif fatura
     */
    public Fatura(int id, Date data, double valorProdutos, int taxaEntrega, int nif){
        this.id = id;
        this.data = (Date) data.clone();
        this.valorProdutos = valorProdutos;
        this.taxaEntrega = taxaEntrega;
        this.nif = nif;

        produtos = new HashSet<>();
    }

    /**
     * setter produtos
     * @param nome nome linha fatura
     * @param valor valor linha fatura
     * @param quantidade quantidade linha fatura
     * @param precoUnitario precoUnitario linha fatura
     * @param iva iva linha fatura
     */
    public void setProdutos(String nome, double valor, int quantidade, double precoUnitario, int iva) {
        produtos.add(new LinhaFatura(nome, valor, quantidade, precoUnitario, iva));
    }

    /**
     * to string fatura
     * @return to string
     */
    @Override
    public String toString() {
        return "ID: "+ id +
                "\nData: " + data +
                "\nValor Total: " + valorProdutos +
                "\nTaxa Entrega: " + taxaEntrega +
                "\nNIF: " + nif +
                "\nProdutos:\n" + produtosToString() +
                "\n";
    }

    /**
     * produtos to string
     * @return to string produtos
     */
    private String produtosToString(){
        StringBuilder out = new StringBuilder();

        for (LinhaFatura linha : produtos){
            out.append(linha.toString() + "\n");
        }
        return out.toString();
    }


}
