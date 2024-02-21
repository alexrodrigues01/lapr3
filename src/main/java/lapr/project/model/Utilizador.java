/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;
import java.util.Objects;

/**
 * classe utilizador
 */
public class Utilizador
{
    private String nome;
    private String email;
    private int nif;
    private int telefone;
    private String palavraChave; // Não deveria guardar a password em "plain text"
    private int papelId;

    /**
     * construtor utilizador
     * @param nome nome do utilizador
     * @param email email do utilizador
     * @param nif nif do utilizador
     * @param telefone telefone do utilizador
     * @param password password do utilizador
     * @param papelId papelId do utilizador
     */
    public Utilizador(String nome, String email, int nif, int telefone, String password, int papelId) {

        this.email = email;
        this.palavraChave = password;
        this.nome = nome;
        this.nif = nif;
        this.telefone = telefone;
        this.papelId = papelId;
    }

    /**
     * construtor utilizador vazio
     */
    public Utilizador(){ }

    /**
     * getter email
     * @return email
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * has id
     * @param strId id utilizador
     * @return equals
     */
    public boolean hasId(String strId)
    {
        return this.email.equals(strId);
    }

    /**
     * has password
     * @param strPwd password utilizador
     * @return equals string
     */
    public boolean hasPassword(String strPwd)
    {
        return this.palavraChave.equals(strPwd);
    }

    /**
     * equals utilizador
     * @param o objeto utilizador comparacao
     * @return equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador that = (Utilizador) o;
        return nif == that.nif && telefone == that.telefone && papelId == that.papelId && Objects.equals(nome, that.nome) && Objects.equals(email, that.email) && Objects.equals(palavraChave, that.palavraChave);
    }

    /**
     * hash code utilizador
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome, email, nif, telefone, palavraChave, papelId);
    }

    /**
     * to string utilizador
     * @return to string
     */
    @Override
    public String toString()
    {
        return String.format("Nome: %s%nEmail: %s%nNIF: %d%nTelefone: %d%nPassword: %s%n", nome, email, nif, telefone, palavraChave);
    }
}
