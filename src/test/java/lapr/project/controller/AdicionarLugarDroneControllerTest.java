package lapr.project.controller;

import lapr.project.data.FarmaciaBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.model.Farmacia;
import lapr.project.model.LugarEstacionamento;
import lapr.project.model.Utilizador;
import lapr.project.utils.ValidarParametros;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AdicionarLugarDroneControllerTest {

    private AdicionarLugarDroneController instance;

    @Mock
    private FarmaciaBD farmaciaBD;

    @Mock
    private UtilizadorBD utilizadorBD;

    @Test
    void validaParametros() {
        farmaciaBD= mock(FarmaciaBD.class);
        instance= new AdicionarLugarDroneController(farmaciaBD);
        assertTrue(instance.validaParametros(10,5,200));
        assertEquals(instance.getLugaresSemCarregamento(),10);
        assertEquals(instance.getLugaresComCarregamento(),5);
        assertEquals(instance.getCapacidadeEnergia(),200);
    }

    @Test
    void validaParametrosFail1(){
        farmaciaBD= mock(FarmaciaBD.class);
        instance= new AdicionarLugarDroneController(farmaciaBD);
        assertFalse(instance.validaParametros(-4, 5,200));
    }

    @Test
    void validaParametrosFail2(){
        farmaciaBD= mock(FarmaciaBD.class);
        instance= new AdicionarLugarDroneController(farmaciaBD);
        assertFalse(instance.validaParametros(10, -5,200));

    }

    @Test
    void validaParametrosFail3(){
        farmaciaBD= mock(FarmaciaBD.class);
        instance= new AdicionarLugarDroneController(farmaciaBD);
        assertFalse(instance.validaParametros(10, 5,-200));

    }

    @Test
    void validaParametrosSuccess2(){
        farmaciaBD= mock(FarmaciaBD.class);
        instance= new AdicionarLugarDroneController(farmaciaBD);
        instance.validaParametros(0,0,0);
        assertEquals(instance.getLugaresSemCarregamento(),0);
        assertEquals(instance.getLugaresComCarregamento(),0);
        assertEquals(instance.getCapacidadeEnergia(),0);
    }
    @Test
    void validaParametrosSuccess3(){
        farmaciaBD= mock(FarmaciaBD.class);
        instance= new AdicionarLugarDroneController(farmaciaBD);
        instance.validaParametros(10,8,200);
        ValidarParametros.validaParametrosLugarEstacionamento(10,8,200);

    }


    @Test
    void getFarmaciaByGestor() {
        utilizadorBD = mock(UtilizadorBD.class);
        farmaciaBD= mock(FarmaciaBD.class);
        instance= new AdicionarLugarDroneController(farmaciaBD);
        String email = "teste@email.com";

        LugarEstacionamento lE = new LugarEstacionamento(true, 1, 2);

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));

        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1, "farm", 111111111, 919191919, "teste@email.com", email, "1", lE));

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(email,"password");

        assertEquals(instance.getFarmaciaByGestor(),1);
        assertEquals(instance.getFarmaciaID(),1);

    }

    @Test
    void setLugaresDrone() {
        utilizadorBD = mock(UtilizadorBD.class);
        farmaciaBD= mock(FarmaciaBD.class);
        instance= new AdicionarLugarDroneController(farmaciaBD);

        instance.validaParametros(10,5,200);

        String email = "teste@email.com";

        LugarEstacionamento lE = new LugarEstacionamento(true, 1, 2);

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));

        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1, "farm", 111111111, 919191919, "teste@email.com", email, "1", lE));

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(email,"password");


        instance.getFarmaciaByGestor();


        when(farmaciaBD.setLugaresDrone(1,10,5,200)).thenReturn(true);

        assertTrue(instance.setLugaresDrone());
    }

    @Test
    void setLugaresDroneFail() {
        utilizadorBD = mock(UtilizadorBD.class);
        farmaciaBD= mock(FarmaciaBD.class);
        instance= new AdicionarLugarDroneController(farmaciaBD);

        instance.validaParametros(10,5,200);

        String email = "teste@email.com";

        LugarEstacionamento lE = new LugarEstacionamento(true, 1, 2);

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));

        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1, "farm", 111111111, 919191919, "teste@email.com", email, "1", lE));

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(email,"password");


        instance.getFarmaciaByGestor();


        when(farmaciaBD.setLugaresDrone(1,10,5,200)).thenReturn(false);

        assertFalse(instance.setLugaresDrone());
    }


}