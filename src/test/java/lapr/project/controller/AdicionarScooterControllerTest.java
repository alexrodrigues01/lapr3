package lapr.project.controller;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import lapr.project.data.FarmaciaBD;
import lapr.project.data.ScooterBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.model.Farmacia;
import lapr.project.model.LugarEstacionamento;
import lapr.project.model.Scooter;
import lapr.project.model.Utilizador;

import lapr.project.utils.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


import org.mockito.Mock;


import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AdicionarScooterControllerTest {

    @Mock
    private ScooterBD scooterBD;

    @Mock
    private FarmaciaBD farmaciaBD;

    @Mock
    private UtilizadorBD utilizadorBD;

    private AdicionarScooterController instance;
    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;

    @Test
    void novaScooter() throws Exception {
        scooterBD = mock(ScooterBD.class);
        farmaciaBD = mock(FarmaciaBD.class);
        utilizadorBD = mock(UtilizadorBD.class);

        instance = new AdicionarScooterController(scooterBD, farmaciaBD);

        Scooter s = new Scooter(2, 20, 20, 1, 300, 20, 10, 100);
        LugarEstacionamento lE = new LugarEstacionamento(true, 1, 2);
        String email = "teste@email.com";

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));
        when(scooterBD.lastID()).thenReturn(1);
        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1, "farm", 111111111, 919191919, "teste@email.com", email, "1", lE));
        when(farmaciaBD.getLugarEstacionamentobyFarm(1)).thenReturn(new LugarEstacionamento(true, 1, 1));

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(email,"password");




        Pair<Scooter, LugarEstacionamento> expResult = new Pair<Scooter, LugarEstacionamento>(s, lE);
        Pair<Scooter, LugarEstacionamento> result = instance.novaScooter(20, 20, 1, 300, 20, 10, 100);
        assertEquals(expResult, result);


    }


    @Test
    void testUpdate() throws SQLException, IOException, WriterException, NotFoundException {
        scooterBD = mock(ScooterBD.class);
        farmaciaBD = mock(FarmaciaBD.class);
        instance = new AdicionarScooterController(scooterBD, farmaciaBD);
        AplicacaoPOT.getInstance();
        LugarEstacionamento lugarEstacionamento = new LugarEstacionamento(1, true, 1, 1);
        Scooter scooter = new Scooter(1, 20, 300, 1, 250, 50, 300, 10);
        when(scooterBD.updateScooter(scooter)).thenReturn(true);
        when(farmaciaBD.updateLugar(lugarEstacionamento)).thenReturn(true);
        boolean result = instance.update(lugarEstacionamento, scooter);
        assertTrue(result);
    }

    @Test
    void testUpdate2() throws SQLException, IOException, WriterException, NotFoundException {
        scooterBD = mock(ScooterBD.class);
        farmaciaBD = mock(FarmaciaBD.class);
        instance = new AdicionarScooterController(scooterBD, farmaciaBD);
        AplicacaoPOT.getInstance();
        LugarEstacionamento lugarEstacionamento = new LugarEstacionamento(1, true, 1, 1);
        Scooter scooter = new Scooter(1, 20, 300, 1, 250, 50, 300, 10);
        when(scooterBD.updateScooter(scooter)).thenReturn(true);
        when(farmaciaBD.updateLugar(lugarEstacionamento)).thenReturn(false);
        boolean result = instance.update(lugarEstacionamento, scooter);
        assertFalse(result);
    }

    @Test
    void testUpdate3() throws SQLException, IOException, WriterException, NotFoundException {
        scooterBD = mock(ScooterBD.class);
        farmaciaBD = mock(FarmaciaBD.class);
        instance = new AdicionarScooterController(scooterBD, farmaciaBD);
        AplicacaoPOT.getInstance();
        LugarEstacionamento lugarEstacionamento = new LugarEstacionamento(1, true, 1, 1);
        Scooter scooter = new Scooter(1, 20, 300, 1, 250, 50, 300, 10);
        when(scooterBD.updateScooter(scooter)).thenReturn(false);
        when(farmaciaBD.updateLugar(lugarEstacionamento)).thenReturn(true);
        boolean result = instance.update(lugarEstacionamento, scooter);
        assertFalse(result);
    }

    @Test
    void testUpdate4() throws SQLException, IOException, WriterException, NotFoundException {
        scooterBD = mock(ScooterBD.class);
        farmaciaBD = mock(FarmaciaBD.class);
        instance = new AdicionarScooterController(scooterBD, farmaciaBD);
        AplicacaoPOT.getInstance();
        LugarEstacionamento lugarEstacionamento = new LugarEstacionamento(1, true, 1, 1);
        Scooter scooter = new Scooter(1, 20, 300, 1, 250, 50, 300, 10);
        when(scooterBD.updateScooter(scooter)).thenReturn(false);
        when(farmaciaBD.updateLugar(lugarEstacionamento)).thenReturn(false);
        boolean result = instance.update(lugarEstacionamento, scooter);
        assertFalse(result);
    }



}