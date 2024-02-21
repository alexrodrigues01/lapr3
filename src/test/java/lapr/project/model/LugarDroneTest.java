package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LugarDroneTest {

    LugarDrone l = new LugarDrone(1,false,1,1);
    LugarDrone ld = new LugarDrone(1,false,1,1);

    @Test
    void getId() {
        assertEquals(1,l.getId());
    }

    @Test
    void getDroneID() {
        assertEquals(1,l.getDroneID());
    }

    @Test
    void testEquals() {
        assertEquals(l,ld);
        assertEquals(l,l);
        assertNotEquals(l,new LugarDrone(1,true,2,2));

        assertNotEquals(l,"ola");

        assertNotEquals(l,null);

        assertNotEquals(l,new LugarDrone(2,true,2,2));

        assertNotEquals(l,new LugarDrone(2,false,3,25));

        assertNotEquals(l,new LugarDrone(1,false,3,25));

        assertNotEquals(l,new LugarDrone(1,false,1,25));

        LugarDrone lugarDrone = new LugarDrone(false,1,1);;
        assertNotEquals(lugarDrone,l);
    }

    @Test
    void testHashCode() {
        assertEquals(l.hashCode(), Objects.hash(1,false,1,1));
    }


    @Test
    void construtorID(){
        LugarDrone l= new LugarDrone(1);
        assertEquals(LugarDrone.class,l.getClass());
    }
}