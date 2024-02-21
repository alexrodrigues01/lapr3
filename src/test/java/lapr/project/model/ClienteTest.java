package lapr.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    Cliente instance = new Cliente("Teste","teste@email.com",123456789,123456789,"password","1234567891234567","02/21",555);

    @Test
    void clienteTest(){
        Cliente cliente = new Cliente("Teste","teste@email.com",123456789,123456789,"password","1234567891234567","02/21",555);
        assertEquals(Cliente.class,cliente.getClass());
    }

    @Test
    void testEquals() {
        Cliente cliente = new Cliente("Teste","teste@email.com",123456789,123456789,"password","1234567891234567","02/21",555);
        assertTrue(cliente.equals(cliente));
        assertTrue(cliente.equals(instance));
    }

    @Test
    void testEquals2(){
        assertFalse(instance.equals(new Cliente("Teste","teste@email.com",123456789,123456789,"password","1234567891234567","02/21",123)));
        assertFalse(instance.equals(new Cliente("Teste","teste@email.com",123456789,123456789,"password","1234567891234567","01/21",555)));
        assertFalse(instance.equals(new Cliente("Teste","teste@email.com",123456789,123456789,"password","1234567891234560","02/21",555)));
}

    @Test
    void testEqualsClassFail() {
        Cliente cliente = new Cliente("Pessego","teste@email.com",123456789,123456789,"password","1234567891234567","02/21",555);
        Entrega entrega= new Entrega(1,40,100,20,"email@email.com",1,1);
        assertFalse(cliente.equals(entrega));
        assertFalse(cliente.equals(null));
        assertFalse(cliente.equals(instance));
    }

    @Test
    void testHashCode() {
        assertEquals(20532520,instance.hashCode());
    }

    @Test
    void testToString() {
        Cliente cliente = new Cliente("Pessego","teste@email.com",123456789,123456789,"password","1234567891234567","02/21",555);
        String result = String.format("Nome: %s%nEmail: %s%nNIF: %d%nTelefone: %d%nPassword: %s%n", "Pessego", "teste@email.com", 123456789, 123456789, "password")+
                "nCartCred: " + "1234567891234567" +
                "\nvalCartCred: " + "02/21" +
                "\ncvv: " + 555 +"\n";
        assertEquals(result,cliente.toString());
    }
}