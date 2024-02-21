package lapr.project.model;

/**
 * classe Produto
 */
public class Produto {
    private final int id;
    private String nome;
    private double precoUnitario;
    private final int pesoUnitario;
    private final int iva;

    /**
     * construtor produto
     * @param id id do produto
     * @param nome nome do produto
     * @param precoUnitario precoUnitario do produto
     * @param iva iva do produto
     * @param pesoUnitario pesoUnitario do produto
     */
    public Produto(int id, String nome, double precoUnitario, int iva, int pesoUnitario) {
        this.id = id;
        setNome(nome);
        setPrecoUnitario(precoUnitario);
        this.iva = iva;
        this.pesoUnitario = pesoUnitario;
    }

    /**
     * setter for nome
     * @return nome
     */
    public void setNome(String nome) {
        if(nome==null || nome.isEmpty()){
            throw new NullPointerException("O nome do produto não pode ser null");
        }
        this.nome = nome;
    }

    /**
     * setter for precoUnitario
     * @return precoUnitario
     */
    public void setPrecoUnitario(double precoUnitario) {
        if(Double.compare(precoUnitario,0)<1 ){
            throw new NumberFormatException("Preço tem ser positivo");
        }
        this.precoUnitario = precoUnitario;
    }

    /**
     * getter for id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * getter for nome
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * getter for precoUnitario
     * @return precoUnitario
     */
    public double getPrecoUnitario() {
        return precoUnitario;
    }

    /**
     * getter peso unitario
     * @return pesoUnitario
     */
    public int getPesoUnitario() {
        return pesoUnitario;
    }

    /**
     * getter iva
     * @return iva
     */
    public int getIva() {
        return iva;
    }

    /**
     * to string produto
     * @return to string
     */
    @Override
    public String toString() {
        return "Id="+id+"\nNome:"+nome;
    }
}
