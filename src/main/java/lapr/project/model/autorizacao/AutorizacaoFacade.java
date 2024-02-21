package lapr.project.model.autorizacao;

import lapr.project.data.UtilizadorBD;
import lapr.project.model.Utilizador;
import lapr.project.model.autorizacao.model.*;

/**
 * classe AutorizacaoFacade
 */
public class AutorizacaoFacade
{
    private SessaoUtilizador sessao = null;
    private UtilizadorBD utilizadorBD;

    /**
     * construtor autorizacaofacade vazio
     */
    public AutorizacaoFacade(){
        utilizadorBD= new UtilizadorBD();
    }

    /**
     * construtor autorizacaofacade
     * @param utilizadorBD utilizadorbd
     */
    public AutorizacaoFacade(UtilizadorBD utilizadorBD) {
        this.utilizadorBD = utilizadorBD;
    }

    /**
     * faz login
     * @param email email da sessao utilizador
     * @param pwd password da sessao utilizador
     * @return SessaoUtilizador
     */
    public SessaoUtilizador doLogin(String email, String pwd) {
        Utilizador utlz = utilizadorBD.procuraUtilizador(email);
        if (utlz != null && utlz.hasPassword(pwd)){
            sessao = new SessaoUtilizador(utlz);
            return sessao;
        }
        return null;
    }

    /**
     * getter sessao atual
     * @return sessao
     */
    public SessaoUtilizador getSessaoAtual()
    {
        return sessao;
    }

    /**
     * logout
     */
    public void doLogout(){
        if (sessao != null)
            sessao.doLogout();
        sessao = null;
    }

    /**
     * setter utilizador bd
     * @param utilizadorBD utilizadorBD
     */
    public void setUtilizadorBD(UtilizadorBD utilizadorBD) {
        this.utilizadorBD = utilizadorBD;
    }
}
