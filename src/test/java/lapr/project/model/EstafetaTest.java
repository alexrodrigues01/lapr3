package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstafetaTest {

    Estafeta instance = new Estafeta("Teste","teste@email.com",123456789,123456789,"password",123456789,1,1);

    @Test
    void estafetaTest(){
        Estafeta estafeta = new Estafeta("Teste","teste@email.com",123456789,123456789,"password",123456789,1,1);
        assertEquals(Estafeta.class,estafeta.getClass());
    }

    @Test
    void testEquals() {
        Estafeta estafeta = new Estafeta("Teste","teste@email.com",123456789,123456789,"password",123456789,1,1);
        assertTrue(estafeta.equals(estafeta));
        assertTrue(estafeta.equals(instance));
    }

    @Test
    void testEquals2(){
        assertFalse(instance.equals(new Estafeta("Teste","teste@email.com",123456789,123456789,"password",123456789,1,2)));
        assertFalse(instance.equals(new Estafeta("Teste","teste@email.com",123456789,123456789,"password",123456789,2,1)));
        assertFalse(instance.equals(new Estafeta("Teste","teste@email.com",123456789,123456789,"password",123450789,1,1)));
    }

    @Test
    void testEqualsClassFail() {
        Estafeta estafeta = new Estafeta("Joao","teste@email.com",123456789,123456789,"password",123456789,1,1);
        Entrega entrega= new Entrega(1,40,100,20,"email@email.com",1,1);
        assertFalse(estafeta.equals(entrega));
        assertFalse(estafeta.equals(null));
        assertFalse(estafeta.equals(instance));
    }

    @Test
    void testHashCode() {
        assertEquals(2039538334,instance.hashCode());
    }

    @Test
    void testToString() {
        String expResult= String.format("Nome: Teste%nEmail: teste@email.com%nNIF: 123456789%nTelefone: 123456789%nPassword: password%nNumero de SS: 123456789%nFarmacia id: 1%nEstado do estafeta: 1%n");

        String result=instance.toString();
        System.out.println(result);
        assertEquals(expResult,result);
    }
}