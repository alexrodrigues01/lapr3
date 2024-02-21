package lapr.project.model;

import lapr.project.utils.Constantes;
import java.util.Objects;

/**
 * classe referente ao cliente
 */
public class Cliente extends Utilizador {
    /**
     * nr cartao de credito
     */
    private final String nCartCred;
    /**
     * data validade do cartao de credito
     */
    private final String valCartCred;
    /**
     * cvv
     */
    private final int cvv;

    /**
     * construtor cliente
     * @param nome nome do cliente
     * @param email email do cliente
     * @param nif nif do cliente
     * @param telefone telefone do cliente
     * @param password password do cliente
     * @param nCartCred nCartCred do cliente
     * @param valCartCred valCartCred do cliente
     * @param cvv cvv do cliente
     */
    public Cliente(String nome, String email, int nif, int telefone, String password, String nCartCred, String valCartCred, int cvv) {

        super(nome, email, nif, telefone, password, Constantes.PAPEL_CLIENTE);

        this.nCartCred = nCartCred;
        this.valCartCred = valCartCred;
        this.cvv = cvv;
    }

    /**
     * equals
     * @param o objeto cliente a comparar
     * @return boolean comparação
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return nCartCred.equals(cliente.nCartCred) && cvv == cliente.cvv && Objects.equals(valCartCred, cliente.valCartCred);
    }

    /**
     * hash code
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nCartCred, valCartCred, cvv);
    }

    /**
     * to string do cliente
     * @return to string do cliente
     */
    @Override
    public String toString() {
        return super.toString()+
                "nCartCred: " + nCartCred +
                "\nvalCartCred: " + valCartCred +
                "\ncvv: " + cvv +"\n";
    }
}
