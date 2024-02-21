package lapr.project.model;

import lapr.project.utils.Constantes;

/**
 * classe gestor farmacia
 */
public class GestorFarmacia extends Utilizador {
    /**
     * construtor gestor farmacia
     * @param nome nome gestor farmacia
     * @param email email gestor farmacia
     * @param nif nif gestor farmacia
     * @param telefone telefone gestor farmacia
     * @param password password gestor farmacia
     */
    public GestorFarmacia(String nome, String email, int nif, int telefone, String password){

        super(nome, email, nif, telefone, password, Constantes.PAPEL_GESTOR_FARMACIA);

    }
}
