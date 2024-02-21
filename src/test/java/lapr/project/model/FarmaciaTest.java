package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FarmaciaTest {

    @Test
    void getId() {
        Farmacia farmacia = new Farmacia(1,"Teste",123456789,123456789,"teste@email.com","teste2@email.com","2");
        assertEquals(1,farmacia.getId());
    }

    @Test
    void getLugarEstacionamento() {
        LugarEstacionamento lugar = new LugarEstacionamento(true,1,1);
        Farmacia farmacia = new Farmacia(1,"Teste",123456789,123456789,"teste@email.com","teste2@email.com","2",lugar);
        assertEquals(lugar,farmacia.getLugarEstacionamento());
    }


    @Test
    void testToString() {
        Farmacia farmacia = new Farmacia(1,"Teste",123456789,123456789,"teste@email.com","teste2@email.com","2");
        String expResult=String.format("ID: %d%nNome: %s%nNIF: %d%nTelefone: %d%nEmail: %s%nEmailGestor: %s%nMorada ID: %s%n",1,"Teste",123456789,123456789,"teste@email.com","teste2@email.com","2");

        assertEquals(expResult,farmacia.toString());
    }

    @Test
    void testConstrutor(){
        Farmacia farmacia= new Farmacia("Pessego",123456789,123456789,"teste@email.com");
        assertEquals(Farmacia.class,farmacia.getClass());
    }

    @Test
    void getMoradaId() {
        Farmacia farmacia = new Farmacia(1,"Teste",123456789,123456789,"teste@email.com","teste2@email.com","2");
        assertEquals("2",farmacia.getMoradaId());
    }

    @Test
    void getLugarDrone() {
        LugarDrone lugar = new LugarDrone(true,1,1);
        Farmacia farmacia = new Farmacia(1,"Teste",123456789,123456789,"teste@email.com","teste2@email.com","2",lugar);
        assertEquals(lugar,farmacia.getLugarDrone());
    }
}