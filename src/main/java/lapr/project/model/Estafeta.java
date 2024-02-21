package lapr.project.model;

import lapr.project.utils.Constantes;
import java.util.Objects;

/**
 * classe referente ao estafeta
 */
public class Estafeta extends Utilizador{

    private final int numSegSocial;
    private final int farmaciaId;
    private final int estadoEstafetaId;

    /**
     * construtor estafeta
     * @param nome nome do estafeta
     * @param email email do estafeta
     * @param nif nif do estafeta
     * @param telefone telefone do estafeta
     * @param password password do estafeta
     * @param numSegSocial numSegSocial do estafeta
     * @param farmaciaId farmaciaId do estafeta
     * @param estadoEstafetaId estadoEstafetaId do estafeta
     */
    public Estafeta(String nome, String email, int nif, int telefone, String password, int numSegSocial, int farmaciaId, int estadoEstafetaId){
        super(nome, email, nif, telefone, password, Constantes.PAPEL_ESTAFETA);

        this.numSegSocial = numSegSocial;
        this.farmaciaId = farmaciaId;
        this.estadoEstafetaId = estadoEstafetaId;

    }

    /**
     * equals estafeta
     * @param o objeto estafeta comparacao
     * @return boolean equals estafeta
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Estafeta estafeta = (Estafeta) o;
        return numSegSocial == estafeta.numSegSocial && farmaciaId == estafeta.farmaciaId && estadoEstafetaId == estafeta.estadoEstafetaId;
    }

    /**
     * hash code estafeta
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numSegSocial, farmaciaId, estadoEstafetaId);
    }

    /**
     * to string estafeta
     * @return to string
     */
    @Override
    public String toString() {
        return super.toString()+String.format("Numero de SS: %d%nFarmacia id: %d%nEstado do estafeta: %d%n", numSegSocial, farmaciaId, estadoEstafetaId);
    }
}
