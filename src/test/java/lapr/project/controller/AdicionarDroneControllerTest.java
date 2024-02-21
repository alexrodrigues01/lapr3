package lapr.project.controller;

import lapr.project.data.DroneBD;
import lapr.project.data.FarmaciaBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.model.*;
import lapr.project.utils.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdicionarDroneControllerTest {

    @Mock
    private DroneBD droneBD;

    @Mock
    private FarmaciaBD farmaciaBD;

    @Mock
    private UtilizadorBD utilizadorBD;

    private AdicionarDroneController instance;

    @Test
    void novoDrone() {
        droneBD = mock(DroneBD.class);
        farmaciaBD = mock(FarmaciaBD.class);
        utilizadorBD = mock(UtilizadorBD.class);

        instance = new AdicionarDroneController(droneBD, farmaciaBD);

        Drone drone = new Drone(2, 20, 20, 1, 300, 20, 10, 100,8,1.5,4,0.2,3);
        LugarDrone lugarDrone = new LugarDrone(true, 1, 2);
        String email = "teste@email.com";

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));
        when(droneBD.lastIDdrone()).thenReturn(1);
        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1, "farm", 111111111, 919191919, "teste@email.com", email, "1", lugarDrone));
        when(farmaciaBD.getLugarDronebyFarm(1)).thenReturn(new LugarDrone(true, 1, 1));

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(email,"password");

        Pair<Drone, LugarDrone> expResult = new Pair<Drone, LugarDrone>(drone, lugarDrone);
        Pair<Drone, LugarDrone> result = instance.novoDrone(20, 20, 1, 300, 20, 10, 100,8,1.5,4,0.2,3);
        assertEquals(expResult, result);
    }


    @Test
    void testUpdate() {
        droneBD = mock(DroneBD.class);
        farmaciaBD = mock(FarmaciaBD.class);
        instance = new AdicionarDroneController(droneBD, farmaciaBD);
        AplicacaoPOT.getInstance();
        LugarDrone lugarDrone = new LugarDrone(1, true, 1, 1);
        Drone drone = new Drone(1, 20, 300, 1, 250, 50, 300, 10,8,1.5,4,0.2,3);
        when(droneBD.updateDrone(drone)).thenReturn(true);
        when(farmaciaBD.updateLugarDrone(lugarDrone)).thenReturn(true);
        boolean result = instance.updateDrone(lugarDrone, drone);
        assertTrue(result);
    }

    @Test
    void testUpdate2() {
        droneBD = mock(DroneBD.class);
        farmaciaBD = mock(FarmaciaBD.class);
        instance = new AdicionarDroneController(droneBD, farmaciaBD);
        AplicacaoPOT.getInstance();
        LugarDrone lugarDrone = new LugarDrone(1, true, 1, 1);
        Drone drone = new Drone(1, 20, 300, 1, 250, 50, 300, 10,8,1.5,4,0.2,3);
        when(droneBD.updateDrone(drone)).thenReturn(true);
        when(farmaciaBD.updateLugarDrone(lugarDrone)).thenReturn(false);
        boolean result = instance.updateDrone(lugarDrone, drone);
        assertFalse(result);
    }

    @Test
    void testUpdate3() {
        droneBD = mock(DroneBD.class);
        farmaciaBD = mock(FarmaciaBD.class);
        instance = new AdicionarDroneController(droneBD, farmaciaBD);
        AplicacaoPOT.getInstance();
        LugarDrone lugarDrone = new LugarDrone(1, true, 1, 1);
        Drone drone = new Drone(1, 20, 300, 1, 250, 50, 300, 10,8,1.5,4,0.2,3);
        when(droneBD.updateDrone(drone)).thenReturn(false);
        when(farmaciaBD.updateLugarDrone(lugarDrone)).thenReturn(true);
        boolean result = instance.updateDrone(lugarDrone, drone);
        assertFalse(result);
    }

    @Test
    void testUpdate4() {
        droneBD = mock(DroneBD.class);
        farmaciaBD = mock(FarmaciaBD.class);
        instance = new AdicionarDroneController(droneBD, farmaciaBD);
        AplicacaoPOT.getInstance();
        LugarDrone lugarDrone = new LugarDrone(1, true, 1, 1);
        Drone drone = new Drone(1, 20, 300, 1, 250, 50, 300, 10,8,1.5,4,0.2,3);
        when(droneBD.updateDrone(drone)).thenReturn(false);
        when(farmaciaBD.updateLugarDrone(lugarDrone)).thenReturn(false);
        boolean result = instance.updateDrone(lugarDrone, drone);
        assertFalse(result);
    }

}