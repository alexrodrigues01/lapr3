package lapr.project.controller;

import com.google.zxing.WriterException;
import lapr.project.data.FarmaciaBD;
import lapr.project.data.ScooterBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.model.Farmacia;
import lapr.project.model.Scooter;
import lapr.project.model.Utilizador;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;

import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RemoverScooterControllerTest {

    @Mock
    private ScooterBD scooterBD;

    @Mock
    private FarmaciaBD farmaciaBD;

    @Mock
    private UtilizadorBD utilizadorBD;

    private RemoverScooterController instance;

    @Test
    void getListaScooter() throws IOException, WriterException {
        scooterBD=mock(ScooterBD.class);
        farmaciaBD= mock(FarmaciaBD.class);
        utilizadorBD= mock(UtilizadorBD.class);
        instance= new RemoverScooterController(scooterBD,farmaciaBD);

        Scooter s = new Scooter(1, 20, 20, 1, 300, 20, 10, 100);
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(s);
        String email = "teste@email.com";

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));
        when(scooterBD.getScootersByFarmID(1)).thenReturn(scooters);
        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1,"Teste",123456789,123456789,"teste2@email.com","teste@email.com","2") );
        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(email,"password");

        assertEquals(scooters,instance.getListaScooter());

    }

    @Test
    void getScooterByID() {
        scooterBD=mock(ScooterBD.class);
        instance= new RemoverScooterController(scooterBD,farmaciaBD);
        when(scooterBD.getScooterByID(1)).thenReturn("Scooter: 1");

        String result= instance.getScooterByID(1);

        assertTrue(result.equalsIgnoreCase("Scooter: 1"));
    }

    @Test
    void removerScooter() {
        scooterBD=mock(ScooterBD.class);
        instance= new RemoverScooterController(scooterBD,farmaciaBD);
        when(scooterBD.removerScooter(1)).thenReturn(true);

        boolean result=instance.removerScooter(1);
        assertTrue(result);
    }

    @Test
    void removerScooterFail() {
        scooterBD=mock(ScooterBD.class);
        instance= new RemoverScooterController(scooterBD,farmaciaBD);
        when(scooterBD.removerScooter(1)).thenReturn(false);

        boolean result=instance.removerScooter(1);
        assertFalse(result);
    }

}