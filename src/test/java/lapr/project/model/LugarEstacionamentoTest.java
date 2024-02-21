package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LugarEstacionamentoTest {

    LugarEstacionamento le = new LugarEstacionamento(1,false,1,1);
    LugarEstacionamento l = new LugarEstacionamento(1,false,1,1);

    @Test
    void setScooter() {
        le.setScooter(2);
        assertEquals(2,le.getScooterID());
    }

    @Test
    void getId() {
        assertEquals(1,le.getId());
    }

    @Test
    void testEquals() {
        assertEquals(le,l);

        assertNotEquals(le,new LugarEstacionamento(1,true,2,2));

        assertNotEquals(le,"ola");

        assertNotEquals(le,null);

        assertNotEquals(le,new LugarEstacionamento(2,true,2,2));

        assertNotEquals(le,new LugarEstacionamento(2,false,3,25));

        assertNotEquals(le,new LugarEstacionamento(1,false,3,25));

        assertNotEquals(le,new LugarEstacionamento(1,false,1,25));

        LugarEstacionamento lugarEstacionamento = new LugarEstacionamento(false,1,1);;
        assertNotEquals(lugarEstacionamento,le);
    }

    @Test
    void testHashCode() {
        assertEquals(le.hashCode(), Objects.hash(1,false,1,1));
    }


    @Test
    void construtorID(){
        LugarEstacionamento l= new LugarEstacionamento(1);
        assertEquals(LugarEstacionamento.class,l.getClass());
    }
}