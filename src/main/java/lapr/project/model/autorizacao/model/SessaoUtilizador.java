/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model.autorizacao.model;

import lapr.project.model.Utilizador;
import java.util.Objects;

/**
 * class SessaoUtilizador
 */
public class SessaoUtilizador
{
    private Utilizador utilizador;

    /**
     * construtor sessao utilizador
     * @param utilizador utilizador da sessao
     */
    public SessaoUtilizador(Utilizador utilizador)
    {
        if (utilizador==null)
            throw new IllegalArgumentException("Argumento não pode ser nulo.");
        this.utilizador = utilizador;
    }

    /**
     * logout
     */
    public void doLogout() {
        this.utilizador = null;
    }

    /**
     * has login
     * @return utilizador!=null
     */
    public boolean hasLogin(){
        return utilizador!=null;
    }

    /**
     * getter email
     * @return email
     */
    public String getEmailUtilizador()
    {
        if(hasLogin())
            return this.utilizador.getEmail();
        return null;
    }

    /**
     * equals utilizador
     * @param o objeto utilizador comparacao
     * @return boolean equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessaoUtilizador that = (SessaoUtilizador) o;
        return Objects.equals(utilizador, that.utilizador);
    }

    /**
     * hash code utilizador
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(utilizador);
    }
}
