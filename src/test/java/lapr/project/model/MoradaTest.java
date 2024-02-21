package lapr.project.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MoradaTest {

    @Test
    void testHashCode() {
        Morada morada = new Morada(1,"Rua",55,55,0);
        assertEquals(-1662516921,morada.hashCode());
    }

    @Test
    void testEqualsSuccess() {
        Morada morada = new Morada(1,"Rua",55,55,0);
        assertTrue(morada.equals(morada));
    }

    @Test
    void testEqualsNullFail() {
        Morada morada = new Morada(1,"Rua",55,55,0);
        assertFalse(morada.equals(null));
    }

    @Test
    void testEqualsClassFail() {
        Morada morada = new Morada(1,"Rua",55,55,0);
        Utilizador utilizador = new Utilizador();
        assertFalse(morada.equals(utilizador));
    }

    @Test
    void testEqualsLatitudeFail() {
        Morada morada = new Morada(1,"Rua",55,55,0);
        Morada morada2 = new Morada(1,"Rua",66,55,0);
        assertFalse(morada.equals(morada2));
    }

    @Test
    void testEqualsLongitudeFail() {
        Morada morada = new Morada(1,"Rua",55,55,0);
        Morada morada2 = new Morada(1,"Rua",55,66,0);
        assertFalse(morada.equals(morada2));
    }

    @Test
    void getMorada() {
        Morada morada = new Morada(1,"Rua",55,55,0);
        assertEquals("Rua",morada.getStringMorada());
    }

    @Test
    void distanceFrom() {
        Morada morada = new Morada(1,"Rua",55,55,0);
        Morada morada2 = new Morada(1,"Rua",66,66,0);
        assertEquals(1358837.0107836735,morada.distanceFrom(morada2));
    }


    @Test
    void getId() {
        Morada morada = new Morada(1,"Rua",55,55,0);
        assertEquals(morada.getId(),1);
    }

    @Test
    void getLatitude() {
        Morada morada = new Morada(1,"Rua",55,55,0);
        assertEquals(morada.getLatitude(),55);
    }

    @Test
    void getLongitude() {
        Morada morada = new Morada(1,"Rua",55,55,0);
        assertEquals(morada.getLongitude(),55);
    }

    @Test
    void getAltitude() {
        Morada morada = new Morada(1,"Rua",55,55,0);
        assertEquals(morada.getAltitude(),0);
    }

    @Test
    void getAltitude1() {
        Morada morada = new Morada(1,"Rua",55,55,40);
        assertEquals(morada.getAltitude(),40);
    }

    @Test
    void getInclinacaoRuaRadianos2(){
        Morada morada = new Morada(1,"Rua",55,55,40);
        Morada morada2 = new Morada(1,"Rua",55,55,50);
        assertEquals(morada.getInclinacaoRuaRadianos(morada2),1.5707963267948966);
    }
    @Test
    void getInclinacaoRuaRadianos3(){
        Morada morada = new Morada(1,"Rua",55,55,40);
        Morada morada2 = new Morada(1,"Rua",55,55,40);
        assertEquals(morada.getInclinacaoRuaRadianos(morada2),0);
    }

    @Test
    void testToString() {
        Morada morada = new Morada(1,"Rua",55,55,40);
        assertEquals("Rua", morada.toString());
    }

    @Test
    void distanceFromNoHeight() {
        Morada morada = new Morada(1,"Rua",50,55,40);
        Morada morada2 = new Morada(1,"Rua",55,55,50);

        assertEquals(555974.6332227937, morada.distanceFromNoHeight(morada2));
        assertNotEquals(6, morada.distanceFromNoHeight(morada2));
    }
}