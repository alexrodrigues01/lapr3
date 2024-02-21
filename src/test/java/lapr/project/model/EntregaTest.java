package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntregaTest {
    Entrega instance= new Entrega(1,40.0,100.0,20.0,"email@email.com",1,1);

    @Test
    void getId() {
        int expResult=1;
        int result=instance.getId();
        assertEquals(expResult,result);
    }

    @Test
    void getCargaTotal() {
        double expResult=40;
        double result=instance.getCargaTotal();
        assertEquals(expResult,result);
    }

    @Test
    void getDistanciaTotal() {
        double expResult=100;
        double result=instance.getDistanciaTotal();
        assertEquals(expResult,result);
    }

    @Test
    void getConsumoEstimando() {
        double expResult=20;
        double result=instance.getConsumoEstimando();
        assertEquals(expResult,result);
    }

    @Test
    void getEmailEstafeta() {
        String expResult="email@email.com";
        String result=instance.getEmailEstafeta();
        assertEquals(expResult,result);

    }

    @Test
    void getId_EstadoEntrega() {
        int expResult=1;
        int result=instance.getIdEstadoEntrega();
        assertEquals(expResult,result);
    }

    @Test
    void testeConstrutor(){
        assertEquals(instance.getClass(),Entrega.class);
    }

    @Test
    void testeConsTrutor(){
        Entrega entrega1= new Entrega(1,40,100,20,1,1);
        assertEquals(entrega1.getClass(),Entrega.class);
    }


    @Test
    void testToString() {
        String expResult=String.format("ID: %d%nCarga Total: %f%nDistancia Total: %f%nConsumo Estimando: %f%nEmail estafeta: %s%nId estado da entrega: %d%nID do veiculo: %d%n", 1,40.0,100.0,20.0,"email@email.com",1,1);
        assertEquals(expResult,instance.toString());
    }
}