package lapr.project.controller;

import lapr.project.data.DroneBD;
import lapr.project.data.DroneBD;
import lapr.project.data.FarmaciaBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.model.Drone;
import lapr.project.model.Farmacia;
import lapr.project.model.Utilizador;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RemoverDroneControllerTest {

    @Mock
    private DroneBD droneBD;

    @Mock
    private FarmaciaBD farmaciaBD;

    @Mock
    private UtilizadorBD utilizadorBD;

    private RemoverDroneController instance;

    @Test
    void getListaDrone() {
        droneBD = mock(DroneBD.class);
        farmaciaBD= mock(FarmaciaBD.class);
        utilizadorBD= mock(UtilizadorBD.class);
        instance= new RemoverDroneController(droneBD,farmaciaBD);

        Drone drone = new Drone(1,20,300,1,250,50,300,10,8,1.5,4,0.2,3);
        List<Drone> drones = new ArrayList<>();
        drones.add(drone);
        String email = "teste@email.com";

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));
        when(droneBD.getDronesByFarmID(1)).thenReturn(drones);
        when(farmaciaBD.getFarmaciabyGestor(email)).thenReturn(new Farmacia(1,"Teste",123456789,123456789,"teste2@email.com","teste@email.com","2") );
        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(email,"password");

        assertEquals(drones,instance.getListaDrone());
    }

    @Test
    void getDroneByID() {
        droneBD=mock(DroneBD.class);
        instance= new RemoverDroneController(droneBD,farmaciaBD);
        when(droneBD.getDroneByID(1)).thenReturn("Drone: 1");

        String result= instance.getDroneByID(1);

        assertTrue(result.equalsIgnoreCase("Drone: 1"));
    }

    @Test
    void removerDrone() {
        droneBD=mock(DroneBD.class);
        instance= new RemoverDroneController(droneBD,farmaciaBD);
        when(droneBD.removerDrone(1)).thenReturn(true);

        boolean result=instance.removerDrone(1);
        assertTrue(result);
    }

    @Test
    void removerDroneFail() {
        droneBD=mock(DroneBD.class);
        instance= new RemoverDroneController(droneBD,farmaciaBD);
        when(droneBD.removerDrone(1)).thenReturn(false);

        boolean result=instance.removerDrone(1);
        assertFalse(result);
    }
}