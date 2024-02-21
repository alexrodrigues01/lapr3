package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncomendaTest {


    @Test
    void getCarga() {
        Encomenda encomenda=new Encomenda(1,"joao@gmail.com",10,1);
        assertEquals(10,encomenda.getCarga());
    }

    @Test
    void getEmailCliente() {
        Encomenda encomenda=new Encomenda(1,"joao@gmail.com",10,1);
        assertEquals("joao@gmail.com",encomenda.getEmailCliente());

        Encomenda encomenda1 = new Encomenda(1,10,"joao@gmail.com");
        assertEquals("joao@gmail.com",encomenda1.getEmailCliente());
    }

    @Test
    void getId() {
        Encomenda encomenda=new Encomenda(1,"joao@gmail.com",10,1);
        assertEquals(1,encomenda.getId());
    }

    @Test
    void getIdEntrega() {
        Encomenda encomenda=new Encomenda(1,"joao@gmail.com",10,1);
        assertEquals(1,encomenda.getIdEntrega());
    }

    @Test
    void getIdFarmacia() {
        Encomenda encomenda = new Encomenda(1,"joao@gmail.com",10,1,1);
        assertEquals(1, encomenda.getIdFarmacia());
    }

    @Test
    void testEquals1() {
        Encomenda encomenda=new Encomenda(1,"joao@gmail.com",10,1);
        assertEquals(encomenda,encomenda);
    }

    @Test
    void testEquals2() {
        Encomenda encomenda=new Encomenda(1,"joao@gmail.com",10,1);
        assertNotEquals(encomenda, null);
    }

    @Test
    void testEquals3() {
        Encomenda encomenda=new Encomenda(1,"joao@gmail.com",10,1);
        Encomenda encomenda2=new Encomenda(1,"joao@gmail.com",10,1);
        assertEquals(encomenda,encomenda2);
    }

    @Test
    void testEquals4() {
        Encomenda encomenda=new Encomenda(1,"joao@gmail.com",10,1);
        LugarEstacionamento lugarEstacionamento= new LugarEstacionamento(true,1,1);
        assertNotEquals(encomenda, lugarEstacionamento);
    }

    @Test
    void testFail(){
        Encomenda encomenda=new Encomenda(1,"joao@gmail.com",10,1,1);
        assertNotEquals(encomenda,new Encomenda(2,"joao@gmail.com",10,1,1));
        assertNotEquals(encomenda,new Encomenda(1,"joaoaaaa@gmail.com",10,1,1));
        assertNotEquals(encomenda,new Encomenda(1,"joao@gmail.com",11,1,1));
        assertNotEquals(encomenda,new Encomenda(1,"joao@gmail.com",10,2,1));
        assertNotEquals(encomenda,new Encomenda(1,"joao@gmail.com",10,1,2));
    }

    @Test
    void testHashCode() {
        Encomenda encomenda=new Encomenda(1,"joao@gmail.com",10,1);
        assertEquals(2061860699,encomenda.hashCode());
    }

    @Test
    void testConstrutores(){
        Encomenda encomenda=new Encomenda(1,10,"joao@gmail.com",1);
        Encomenda encomenda1=new Encomenda(1,"joao@gmail.com",10,1,1);
        Encomenda encomenda2=new Encomenda(1,10,"joao@gmail.com");
        assertEquals(Encomenda.class,encomenda.getClass());
        assertEquals(Encomenda.class,encomenda1.getClass());
        assertEquals(Encomenda.class,encomenda2.getClass());
    }

    @Test
    void testToString() {
        Encomenda encomenda=new Encomenda(1,"joao@gmail.com",10,1,1);
        String expresult = "ID: 1\n" +
                "emailCliente: 'joao@gmail.com'\n" +
                "Carga: 10.0\n" +
                "idEntrega: 1\n" +
                "idFarmacia: 1\n";
        assertEquals(expresult, encomenda.toString());
    }
}