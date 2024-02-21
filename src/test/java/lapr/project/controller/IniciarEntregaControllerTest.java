package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.Encomenda;
import lapr.project.model.Entrega;
import lapr.project.model.Utilizador;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IniciarEntregaControllerTest {
    @Mock
    private EntregaBD entregaBD;
    @Mock
    private EncomendaBD encomendaBD;
    @Mock
    private UtilizadorBD utilizadorBD;

    @Test
    void getEntregas() {

        entregaBD=  mock(EntregaBD.class);
        String expResult=new Entrega(1,10,10,10,"joao@gmail.com",3,1).toString();
        List<String > ex=new ArrayList<>();
        ex.add(expResult);
        ArrayList<Entrega> arrayList=new ArrayList<>();
        arrayList.add((new Entrega(1,10,10,10,"joao@gmail.com",3,1)));
        when(entregaBD.getEntregaByEstafeta("joao@gmail.com")).thenReturn(arrayList);
        IniciarEntregaController controller=new IniciarEntregaController();
        controller.setEntregaBD(entregaBD);

        utilizadorBD = mock(UtilizadorBD.class);

        when(utilizadorBD.procuraUtilizador("joao@gmail.com")).thenReturn(new Utilizador("teste", "joao@gmail.com", 123456789, 123456789, "password", 1));

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin("joao@gmail.com","password");
        EstafetaBD estafetaBD=mock(EstafetaBD.class);

        when(estafetaBD.isEstafeta("joao@gmail.com")).thenReturn(true);
        List<String> result= controller.getEntregas(estafetaBD,new GestorFarmaciaBD());
        assertEquals(ex,result);
    }
    @Test
    void getEntregas1() {

        entregaBD=  mock(EntregaBD.class);
        String expResult=new Entrega(1,10,10,10,"joao@gmail.com",3,1).toString();
        List<String > ex=new ArrayList<>();
        ex.add(expResult);
        ArrayList<Entrega> arrayList=new ArrayList<>();
        arrayList.add((new Entrega(1,10,10,10,"joao@gmail.com",3,1)));
        when(entregaBD.getEntregasToDoDrone("joao@gmail.com")).thenReturn(arrayList);
        IniciarEntregaController controller=new IniciarEntregaController();
        controller.setEntregaBD(entregaBD);

        utilizadorBD = mock(UtilizadorBD.class);

        when(utilizadorBD.procuraUtilizador("joao@gmail.com")).thenReturn(new Utilizador("teste", "joao@gmail.com", 123456789, 123456789, "password", 1));

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin("joao@gmail.com","password");
        GestorFarmaciaBD gestorFarmaciaBD=mock(GestorFarmaciaBD.class);
        EstafetaBD estafetaBD=mock(EstafetaBD.class);
        when(gestorFarmaciaBD.validaGestor("joao@gmail.com")).thenThrow( new IllegalArgumentException("Já existe um utilizador com este email"));
        List<String> result= controller.getEntregas(estafetaBD,gestorFarmaciaBD);
        assertEquals(ex,result);
    }

    @Test
    void getEntregasFail() {

        entregaBD=  mock(EntregaBD.class);
        String expResult=new Entrega(1,10,10,10,"joao@gmail.com",3,1).toString();
        List<String > ex=new ArrayList<>();
        ex.add(expResult);
        ArrayList<Entrega> arrayList=new ArrayList<>();
        arrayList.add((new Entrega(1,10,10,10,"joao@gmail.com",3,1)));
        when(entregaBD.getEntregasToDoDrone("joao@gmail.com")).thenReturn(arrayList);
        IniciarEntregaController controller=new IniciarEntregaController();
        controller.setEntregaBD(entregaBD);

        utilizadorBD = mock(UtilizadorBD.class);

        when(utilizadorBD.procuraUtilizador("joao@gmail.com")).thenReturn(new Utilizador("teste", "joao@gmail.com", 123456789, 123456789, "password", 1));

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin("joao@gmail.com","password");
        GestorFarmaciaBD gestorFarmaciaBD=mock(GestorFarmaciaBD.class);
        EstafetaBD estafetaBD=mock(EstafetaBD.class);
        List<String> result= controller.getEntregas(estafetaBD,gestorFarmaciaBD);
        assertEquals(Collections.emptyList(),result);
    }

    @Test
    void iniciarEntrega(){
        entregaBD=  mock(EntregaBD.class);
        encomendaBD=mock(EncomendaBD.class);
        when(entregaBD.getIdEstadoEntrega(1)).thenReturn(2);
        when(encomendaBD.getEncomendasByEntregaId(1)).thenReturn(new ArrayList<>());
        IniciarEntregaController controller=new IniciarEntregaController();
        controller.setEncomendaBD(encomendaBD);
        controller.setEntregaBD(entregaBD);
        controller.iniciarEntrega(1);
        controller.setDrone(false);
        controller.setEstafeta(false);
        assertEquals(entregaBD.getIdEstadoEntrega(1),2);
    }


    @Test
    void iniciarEntrega2(){
            entregaBD = mock(EntregaBD.class);
            encomendaBD = mock(EncomendaBD.class);
            when(entregaBD.getIdEstadoEntrega(1)).thenReturn(2);
            ArrayList<Encomenda> encomendas = new ArrayList<>();
            encomendas.add(new Encomenda(1, "joao@gmail.com", 10, 1));
            when(encomendaBD.getEncomendasByEntregaId(1)).thenReturn(encomendas);
            IniciarEntregaController controller = new IniciarEntregaController();
            controller.setEncomendaBD(encomendaBD);
            controller.setEntregaBD(entregaBD);
            controller.setEstafeta(true);
            controller.iniciarEntrega(1);
            assertEquals(entregaBD.getIdEstadoEntrega(1),2);
    }
    @Test
    void iniciarEntrega5(){
        entregaBD = mock(EntregaBD.class);
        encomendaBD = mock(EncomendaBD.class);
        when(entregaBD.getIdEstadoEntrega(1)).thenReturn(2);
        ArrayList<Encomenda> encomendas = new ArrayList<>();
        encomendas.add(new Encomenda(1, "joao@gmail.com", 10, 1));
        when(encomendaBD.getEncomendasByEntregaId(1)).thenReturn(encomendas);
        IniciarEntregaController controller = new IniciarEntregaController();
        controller.setDrone(true);
        controller.setEncomendaBD(encomendaBD);
        controller.setEntregaBD(entregaBD);
        controller.iniciarEntrega(1);
        assertEquals(entregaBD.getIdEstadoEntrega(1),2);
    }

    @Test
    void iniciarEntrega3(){
        try {
            entregaBD = mock(EntregaBD.class);
            encomendaBD = mock(EncomendaBD.class);
            when(entregaBD.getIdEstadoEntrega(1)).thenReturn(2);
            when(encomendaBD.getEncomendasByEntregaId(1)).thenReturn(null);
            IniciarEntregaController controller = new IniciarEntregaController();
            controller.setEncomendaBD(encomendaBD);
            controller.setEntregaBD(entregaBD);
            controller.iniciarEntrega(1);
        }catch (NullPointerException e){
            assertEquals(e.getMessage(),"Encomendas inexistentes");
        }
    }
    @Test
    void iniciarEntrega4(){
        try {
            entregaBD = mock(EntregaBD.class);
            encomendaBD = mock(EncomendaBD.class);
            when(entregaBD.getIdEstadoEntrega(1)).thenReturn(2);
            when(encomendaBD.getEncomendasByEntregaId(1)).thenReturn(null);
            IniciarEntregaController controller = new IniciarEntregaController();
            controller.setEncomendaBD(encomendaBD);
            controller.setEntregaBD(entregaBD);
            controller.iniciarEntrega(1);
        }catch (NullPointerException e){
            assertEquals(entregaBD.getIdEstadoEntrega(1),2);
        }
    }

}