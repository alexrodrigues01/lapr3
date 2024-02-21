package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestorFarmaciaTest {
    @Test
    void gestorFarmaciaTest(){
        GestorFarmacia gestor = new GestorFarmacia("Teste","teste@email.com",123456789,123456789,"password");
        assertEquals(GestorFarmacia.class,gestor.getClass());
    }

}