package lapr.project.controller;

import com.google.zxing.WriterException;
import lapr.project.data.FarmaciaBD;
import lapr.project.data.ScooterBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.model.Farmacia;
import lapr.project.model.Scooter;
import lapr.project.model.Utilizador;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AtualizarScooterControllerTest {

    @Mock
    private ScooterBD scooterBD;

    @Mock
    private FarmaciaBD farmaciaBD;

    @Mock
    private UtilizadorBD utilizadorBD;

    private AtualizarScooterController instance;

    @Test
    void getListaScooter() throws IOException, WriterException {
        scooterBD = mock(ScooterBD.class);
        farmaciaBD= mock(FarmaciaBD.class);
        utilizadorBD= mock(UtilizadorBD.class);
        instance = new AtualizarScooterController(scooterBD,farmaciaBD);

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

        assertEquals(scooters, instance.getListaScooter());

    }

    @Test
    void getScooterByID() throws IOException, WriterException {
        scooterBD = mock(ScooterBD.class);
        instance = new AtualizarScooterController(scooterBD,farmaciaBD);
        Scooter s = new Scooter(1, 20, 20, 1, 300, 20, 10, 100);
        List<String> scooters = new ArrayList<>();
        scooters.add(s.getId() + "");//0
        scooters.add(s.getCarga() + "");//1
        scooters.add(s.getCapacidadeBateria() + "");//2
        scooters.add(s.getEstadoScooterID() + "");
        scooters.add(s.getConsumoHoraBateria() + "");
        scooters.add(s.getTensaoBateria() + "");
        scooters.add(s.getMassa() + "");
        scooters.add(s.getPotenciaMotor() + "");


        when(scooterBD.getDadosScooterbyID(1)).thenReturn(scooters);

        assertEquals(scooters, instance.getScooterByID(1));

    }

    @Test
    void validaDados() {
        scooterBD = mock(ScooterBD.class);
        instance = new AtualizarScooterController(scooterBD,farmaciaBD);
        instance.validaDados("20", "20", "1", "300", "20", "10", "100");
    }

    @Test
    void validaDadosFail() {
        scooterBD = mock(ScooterBD.class);
        IllegalArgumentException expResult = new IllegalArgumentException("Carga inválida");
        instance = new AtualizarScooterController(scooterBD,farmaciaBD);
        try {
            instance.validaDados("-45", "20", "1", "300", "20", "10", "100");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaDadosFail2() {
        scooterBD = mock(ScooterBD.class);
        IllegalArgumentException expResult = new IllegalArgumentException("Carga inválida");
        instance = new AtualizarScooterController(scooterBD,farmaciaBD);
        try {
            instance.validaDados("", "20", "1", "300", "20", "10", "100");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void updateDados() throws IOException, WriterException {
        scooterBD = mock(ScooterBD.class);
        instance = new AtualizarScooterController(scooterBD,farmaciaBD);
        Scooter s = new Scooter(1, 20, 20, 1, 300, 20, 10, 100);
        List<String> scooters = new ArrayList<>();
        scooters.add(s.getId() + "");//0
        scooters.add(s.getCarga() + "");//1
        scooters.add(s.getCapacidadeBateria() + "");//2
        scooters.add(s.getEstadoScooterID() + "");
        scooters.add(s.getConsumoHoraBateria() + "");
        scooters.add(s.getTensaoBateria() + "");
        scooters.add(s.getMassa() + "");
        scooters.add(s.getPotenciaMotor() + "");

        when(scooterBD.getDadosScooterbyID(1)).thenReturn(scooters);
        instance.getScooterByID(1);
        when(scooterBD.updateDados("1", "20", "20.0", "1", "300.0", "20.0", "10.0", "100.0")).thenReturn(true);
        boolean result = instance.updateDados();
        assertTrue(result);

    }

    @Test
    void updateDadosFail() throws IOException, WriterException {
        scooterBD = mock(ScooterBD.class);
        instance = new AtualizarScooterController(scooterBD,farmaciaBD);
        Scooter s = new Scooter(1, 20, 20, 1, 300, 20, 10, 100);
        List<String> scooters = new ArrayList<>();
        scooters.add(s.getId() + "");//0
        scooters.add(s.getCarga() + "");//1
        scooters.add(s.getCapacidadeBateria() + "");//2
        scooters.add(s.getEstadoScooterID() + "");
        scooters.add(s.getConsumoHoraBateria() + "");
        scooters.add(s.getTensaoBateria() + "");
        scooters.add(s.getMassa() + "");
        scooters.add(s.getPotenciaMotor() + "");

        when(scooterBD.getDadosScooterbyID(1)).thenReturn(scooters);
        instance.getScooterByID(1);
        when(scooterBD.updateDados("1", "-20", "20.0", "1", "300.0", "20.0", "10.0", "100.0")).thenReturn(false);
        boolean result = instance.updateDados();
        assertFalse(result);

    }
}