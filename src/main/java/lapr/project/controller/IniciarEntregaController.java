package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.Encomenda;
import lapr.project.model.Entrega;
import lapr.project.model.autorizacao.model.SessaoUtilizador;
import lapr.project.utils.Emails;
import lapr.project.utils.StringConverter;
import java.util.Collections;
import java.util.List;

/**
 * The type Iniciar entrega controller.
 */
public class IniciarEntregaController {
    private EntregaBD entregaBD;
    private EncomendaBD encomendaBD;
    private boolean isDrone;
    private boolean isEstafeta;
    private String email;

    /**
     * Este metodo vai procurar o objeto entrega a partir do email do estafeta
     * para que o estafeta possa verificar e confirmar as informaçoes da mesma.
     *
     * @param estafetaBD       the estafeta bd
     * @param gestorFarmaciaBD the gestor farmacia bd
     * @return lista de entregas
     */
    public List<String> getEntregas(EstafetaBD estafetaBD, GestorFarmaciaBD gestorFarmaciaBD) {
      AplicacaoPOT app=  AplicacaoPOT.getInstance();
      SessaoUtilizador sessao=app.getSessaoAtual();
       email=sessao.getEmailUtilizador();
      // se for um estafeta
      if(estafetaBD.isEstafeta(email)){
          isEstafeta=true;
        List<Entrega> lista=entregaBD.getEntregaByEstafeta(email);
        return StringConverter.convertToStringList(lista);
      }else{
          try {
              // se for um gestor
              gestorFarmaciaBD.validaGestor(email);
          }catch(IllegalArgumentException e){
              isDrone=true;
              List<Entrega> lista=  entregaBD.getEntregasToDoDrone(email);
              return StringConverter.convertToStringList(lista);
          }
      }
        return Collections.emptyList();
    }

    /**
     * Instantiates a new Iniciar entrega controller.
     */
    public IniciarEntregaController() {
        this.entregaBD = new EntregaBD();
        this.encomendaBD=new EncomendaBD();
    }

    /**
     * Sets entrega bd.
     *
     * @param entregaBD the entrega bd
     */
    public void setEntregaBD(EntregaBD entregaBD) {
        this.entregaBD=entregaBD;
    }

    /**
     * Sets encomenda bd.
     *
     * @param encomendaBD the encomenda bd
     */
    public void setEncomendaBD(EncomendaBD encomendaBD) {
        this.encomendaBD = encomendaBD;
    }

    /**
     * Este metodo muda na base de dados o estado da entrega
     * e envia email a todos os clientes em que sua encomenda faz porte desta entrega
     *
     * @param idEntrega the id entrega
     */
    public void iniciarEntrega(int idEntrega) {
        if(isDrone){
           entregaBD.validaIdEntregaToDoDrone(email,idEntrega);
        }

        if(isEstafeta){
            entregaBD.validaIdEntregaEstafeta(email,idEntrega);
        }

        List<Encomenda> listaEncomenda = encomendaBD.getEncomendasByEntregaId(idEntrega);
        if (listaEncomenda == null) {
            throw new NullPointerException("Encomendas inexistentes");
        }
        entregaBD.iniciarEntrega(idEntrega);
        for (Encomenda encomenda : listaEncomenda) {
            Emails.sendEmail("Lapr20", encomenda.getEmailCliente(), "Entrega da encomenda "
                        , "A encomenda com o seguinta peso:" + encomenda.getCarga() + " irá ser enviada");
        }
    }

    /**
     * Sets drone.
     *
     * @param drone the drone
     */
    public void setDrone(boolean drone) {
        isDrone = drone;
    }

    /**
     * Sets estafeta.
     *
     * @param estafeta the estafeta
     */
    public void setEstafeta(boolean estafeta) {
        isEstafeta = estafeta;
    }
}
