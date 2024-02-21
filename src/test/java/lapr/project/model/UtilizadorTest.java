package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilizadorTest {

    Utilizador instance = new Utilizador("Nome","teste@email.com",123456789,123456789,"password",1);

    @Test
    void utilizadorTest() {
        Utilizador utilizador = new Utilizador();
        assertEquals(Utilizador.class, utilizador.getClass());
    }

    @Test
    void getEmail() {
        Utilizador utilizador = new Utilizador("Nome","teste@email.com",123456789,123456789,"password",1);
        assertEquals("teste@email.com",utilizador.getEmail());
    }

    @Test
    void hasIdSuccess() {
        Utilizador utilizador = new Utilizador("Nome","teste@email.com",123456789,123456789,"password",1);
        assertTrue(utilizador.hasId("teste@email.com"));
    }

    @Test
    void hasIdFail() {
        Utilizador utilizador = new Utilizador("Nome","teste@email.com",123456789,123456789,"password",1);
        assertFalse(utilizador.hasId("fail"));
    }

    @Test
    void hasPasswordSuccess() {
        Utilizador utilizador = new Utilizador("Nome","teste@email.com",123456789,123456789,"password",1);
        assertTrue(utilizador.hasPassword("password"));
    }

    @Test
    void hasPasswordFail() {
        Utilizador utilizador = new Utilizador("Nome","teste@email.com",123456789,123456789,"password",1);
        assertFalse(utilizador.hasPassword("fail"));
    }

    @Test
    void testHashCode() {
        assertEquals(-467847088,instance.hashCode());
    }

    @Test
    void testEqualsSuccess() {
        Utilizador utilizador = new Utilizador("Nome","teste@email.com",123456789,123456789,"password",1);
        assertTrue(utilizador.equals(utilizador));
    }

    @Test
    void testEqualsNullFail() {
        Utilizador utilizador = new Utilizador("Nome","teste@email.com",123456789,123456789,"password",1);
        assertFalse(utilizador.equals(null));
    }

    @Test
    void testEqualsClassFail() {
        Utilizador utilizador = new Utilizador("Nome","teste@email.com",123456789,123456789,"password",1);
        Entrega instance= new Entrega(1,40,100,20,"email@email.com",1,1);
        assertFalse(utilizador.equals(instance));
    }

    @Test
    void testeEqualsFail(){
        assertFalse(instance.equals(new Utilizador("Nome","teste@email.com",123456789,123456789,"password",2)));
        assertFalse(instance.equals(new Utilizador("Nome","teste@email.com",123456789,123456789,"pard",1)));
        assertFalse(instance.equals(new Utilizador("Nome","teste@email.com",123456789,123546789,"password",1)));
        assertFalse(instance.equals(new Utilizador("Nome","teste@email.com",123450789,123456789,"password",1)));
        assertFalse(instance.equals(new Utilizador("Nome","teste2@email.com",123456789,123456789,"password",1)));
        assertFalse(instance.equals(new Utilizador("Nommmmme","teste@email.com",123456789,123456789,"password",1)));
    }

    @Test
    void testToString() {
        Utilizador utilizador = new Utilizador("Nome","teste@email.com",123456789,123456789,"password",1);
        String result = String.format("Nome: %s%nEmail: %s%nNIF: %d%nTelefone: %d%nPassword: %s%n", "Nome", "teste@email.com", 123456789, 123456789, "password");
        assertEquals(result,utilizador.toString());
    }
}