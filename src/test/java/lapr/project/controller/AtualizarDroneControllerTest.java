package lapr.project.controller;

import lapr.project.data.DroneBD;
import lapr.project.data.FarmaciaBD;
import lapr.project.data.ScooterBD;
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

class AtualizarDroneControllerTest {

    @Mock
    private DroneBD droneBD;

    @Mock
    private FarmaciaBD farmaciaBD;

    @Mock
    private UtilizadorBD utilizadorBD;


    private AtualizarDroneController instance;

    @Test
    void getListaDrone() {
        droneBD = mock(DroneBD.class);
        farmaciaBD= mock(FarmaciaBD.class);
        utilizadorBD= mock(UtilizadorBD.class);
        instance = new AtualizarDroneController(droneBD,farmaciaBD);

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

        assertEquals(drones, instance.getListaDrone());

    }

    @Test
    void getDroneByID() {
        droneBD = mock(DroneBD.class);
        instance = new AtualizarDroneController(droneBD,farmaciaBD);
        Drone drone = new Drone(1,20,300,1,250,50,300,10,8,1.5,4,0.2,3);
        List<String> drones = new ArrayList<>();
        drones.add(drone.getId() + "");//0
        drones.add(drone.getCarga() + "");//1
        drones.add(drone.getCapacidadeBateria() + "");//2
        drones.add(drone.getEstadoDroneID() + "");
        drones.add(drone.getConsumoHoraBateria() + "");
        drones.add(drone.getTensaoBateria() + "");
        drones.add(drone.getMassa() + "");
        drones.add(drone.getPotenciaMotor() + "");
        drones.add(drone.getVelocidade() + "");
        drones.add(drone.getAvionics() + "");
        drones.add(drone.getDrag() + "");
        drones.add(drone.getRotors() + "");
        drones.add(drone.getCargaMaxima() + "");

        when(droneBD.getDadosDronebyID(1)).thenReturn(drones);

        assertEquals(drones, instance.getDroneByID(1));

    }

    @Test
    void validaDados() {
        droneBD = mock(DroneBD.class);
        instance = new AtualizarDroneController(droneBD,farmaciaBD);
        instance.validaDados(20,300,1,250,50,300,10,8,1.5,4,0.2,3);
    }

    @Test
    void validaDadosFail() {
        droneBD = mock(DroneBD.class);
        IllegalArgumentException expResult = new IllegalArgumentException("Carga inválida");
        instance = new AtualizarDroneController(droneBD,farmaciaBD);
        try {
            instance.validaDados(-20,300,1,250,50,300,10,8,1.5,4,0.2,3);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }

    }

    @Test
    void updateDados() {
        droneBD = mock(DroneBD.class);
        instance = new AtualizarDroneController(droneBD,farmaciaBD);
        Drone drone = new Drone(1,20,300,1,250,50,300,10,8,1.5,4,0.2,3);
        List<String> drones = new ArrayList<>();
        drones.add(drone.getId() + "");//0
        drones.add(drone.getCarga() + "");//1
        drones.add(drone.getCapacidadeBateria() + "");//2
        drones.add(drone.getEstadoDroneID() + "");
        drones.add(drone.getConsumoHoraBateria() + "");
        drones.add(drone.getTensaoBateria() + "");
        drones.add(drone.getMassa() + "");
        drones.add(drone.getPotenciaMotor() + "");
        drones.add(drone.getVelocidade() + "");
        drones.add(drone.getAvionics() + "");
        drones.add(drone.getDrag() + "");
        drones.add(drone.getRotors() + "");
        drones.add(drone.getCargaMaxima() + "");

        when(droneBD.getDadosDronebyID(1)).thenReturn(drones);
        instance.getDroneByID(1);
        when(droneBD.updateDadosDrone(1, 20,300,1,250,50,300,10,8,1.5,4,0.2,3)).thenReturn(true);
        boolean result = instance.updateDados();
        assertTrue(result);

    }

    @Test
    void updateDadosFail() {
        droneBD = mock(DroneBD.class);
        instance = new AtualizarDroneController(droneBD,farmaciaBD);
        Drone drone = new Drone(1, 20,300,1,250,50,300,10,8,1.5,4,0.2,3);
        List<String> drones = new ArrayList<>();
        drones.add(drone.getId() + "");//0
        drones.add(drone.getCarga() + "");//1
        drones.add(drone.getCapacidadeBateria() + "");//2
        drones.add(drone.getEstadoDroneID() + "");
        drones.add(drone.getConsumoHoraBateria() + "");
        drones.add(drone.getTensaoBateria() + "");
        drones.add(drone.getMassa() + "");
        drones.add(drone.getPotenciaMotor() + "");
        drones.add(drone.getVelocidade() + "");
        drones.add(drone.getAvionics() + "");
        drones.add(drone.getDrag() + "");
        drones.add(drone.getRotors() + "");
        drones.add(drone.getCargaMaxima() + "");

        when(droneBD.getDadosDronebyID(1)).thenReturn(drones);
        instance.getDroneByID(1);
        when(droneBD.updateDadosDrone(1, -20,300,1,250,50,300,10,8,1.5,4,0.2,3)).thenReturn(false);
        boolean result = instance.updateDados();
        assertFalse(result);

    }
}