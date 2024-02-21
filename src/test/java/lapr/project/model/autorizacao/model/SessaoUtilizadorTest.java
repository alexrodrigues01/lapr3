package lapr.project.model.autorizacao.model;

import lapr.project.model.Utilizador;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SessaoUtilizadorTest {

    SessaoUtilizador instance = new SessaoUtilizador(new Utilizador("user","email@email.pt",123456789,912912912,"password",1));

    @Test
    void getEmailUtilizador() {
        Utilizador utilizador= new Utilizador("user","email@email.pt",123456789,912912912,"password",1);
        SessaoUtilizador sessaoUtilizador= new SessaoUtilizador(utilizador);
        String s= "email@email.pt";
        assertEquals(s,sessaoUtilizador.getEmailUtilizador());
    }

    @Test
    void getEmailUtilizadorFail() {
        try{
            Utilizador utilizador= new Utilizador("user","joao@email.pt",123456789,912912912,"password",1);
            SessaoUtilizador sessaoUtilizador= new SessaoUtilizador(utilizador);
            String s= "email@email.pt";
            assertNotEquals(s,sessaoUtilizador.getEmailUtilizador());
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    @Test
    void getEmailUtilizadorFail2() {
        try{
            Utilizador utilizador= new Utilizador("user","joao@email.pt",123456789,912912912,"password",1);
            SessaoUtilizador sessaoUtilizador= new SessaoUtilizador(utilizador);
            sessaoUtilizador.doLogout();
            assertNull(sessaoUtilizador.getEmailUtilizador());
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    @Test
    void createSessaoUtilizador(){
        IllegalArgumentException expResult= new IllegalArgumentException("Argumento não pode ser nulo.");
        try {
            new SessaoUtilizador(null);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }

    }

    @Test
    void hasLogin() {
        try{
            Utilizador utilizador= new Utilizador("user","email@email.pt",123456789,912912912,"password",1);
            SessaoUtilizador sessaoUtilizador= new SessaoUtilizador(utilizador);
            sessaoUtilizador.doLogout();
            assertFalse(sessaoUtilizador.hasLogin());
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    @Test
    void testEqualsSuccess() {
        SessaoUtilizador sessaoUtilizador = new SessaoUtilizador(new Utilizador("user","email@email.pt",123456789,912912912,"password",1));
        assertTrue(sessaoUtilizador.equals(instance));
        assertTrue(sessaoUtilizador.equals(sessaoUtilizador));
    }

    @Test
    void testEqualsNullFail() {
        SessaoUtilizador sessaoUtilizador = new SessaoUtilizador(new Utilizador("user","email@email.pt",123456789,912912912,"password",1));
        assertFalse(sessaoUtilizador.equals(null));
    }

    @Test
    void testEqualsClassFail() {
        SessaoUtilizador sessaoUtilizador = new SessaoUtilizador(new Utilizador("user","email@email.pt",123456789,912912912,"password",1));
        Utilizador utilizador = new Utilizador("Nome","teste@email.com",123456789,123456789,"password",1);
        assertFalse(sessaoUtilizador.equals(utilizador));
    }

    @Test
    void testEqualsFail() {
        assertFalse(instance.equals(new SessaoUtilizador(new Utilizador("Nome","teste@email.com",123456789,123456789,"password",2))));
        assertFalse(instance.equals(new SessaoUtilizador(new Utilizador("Nome","teste@email.com",123456789,123456789,"prd",1))));
        assertFalse(instance.equals(new SessaoUtilizador(new Utilizador("Nome","teste@email.com",123456789,123456789,"password",1))));
        assertFalse(instance.equals(new SessaoUtilizador(new Utilizador("Nome","teste@email.com",123406789,912912912,"password",1))));
        assertFalse(instance.equals(new SessaoUtilizador(new Utilizador("Nome","teste@email.com",123456789,912912912,"password",1))));
        assertFalse(instance.equals(new SessaoUtilizador(new Utilizador("Nome","email@email.com",123456789,912912912,"password",1))));
    }

    @Test
    void testHashCode() {
        assertEquals(-1345934128,instance.hashCode());
    }
}