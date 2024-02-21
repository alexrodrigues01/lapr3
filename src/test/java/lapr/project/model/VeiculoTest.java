package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VeiculoTest {

    Veiculo veiculo = new Veiculo( 1,10,10.0,10.0,10.0,10.0,10.0);

    @Test
    void testConstrutor(){
        Veiculo test = new Veiculo(1,7,8);
        assertEquals(Veiculo.class, test.getClass());

        Veiculo test2 = new Veiculo(1,7);
        assertEquals(Veiculo.class, test.getClass());
    }

    @Test
    void setCarga() {
        veiculo.setCarga(3);
        assertEquals(3, veiculo.getCarga());
        assertNotEquals(66, veiculo.getCarga());

        veiculo.setCarga(0);
        assertEquals(0, veiculo.getCarga());
        veiculo.setCarga(100);
        assertEquals(100, veiculo.getCarga());

        IllegalArgumentException exception = new IllegalArgumentException("Carga inválida");
        try{
            veiculo.setCarga(-55);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(), e.getMessage());
        }

        try{
            veiculo.setCarga(108);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(), e.getMessage());
        }

    }

    @Test
    void setCapacidadeBateria() {
        veiculo.setCapacidadeBateria(9);
        assertEquals(9, veiculo.getCapacidadeBateria());
        assertNotEquals(66, veiculo.getCarga());

        veiculo.setCapacidadeBateria(0);
        assertEquals(0, veiculo.getCapacidadeBateria());
        assertNotEquals(66, veiculo.getCapacidadeBateria());

        IllegalArgumentException exception = new IllegalArgumentException("Capacidade da bateria inválida");
        try{
            veiculo.setCapacidadeBateria(-55);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(), e.getMessage());
        }
    }

    @Test
    void setConsumoHoraBateria() {
        veiculo.setConsumoHoraBateria(1);
        assertEquals(1, veiculo.getConsumoHoraBateria());
        assertNotEquals(66, veiculo.getConsumoHoraBateria());

        veiculo.setConsumoHoraBateria(0);
        assertEquals(0, veiculo.getConsumoHoraBateria());
        assertNotEquals(66, veiculo.getConsumoHoraBateria());

        IllegalArgumentException exception = new IllegalArgumentException("Consumo por hora da bateria inválido");
        try{
            veiculo.setConsumoHoraBateria(-55);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(), e.getMessage());
        }
    }

    @Test
    void setTensaoBateria() {
        veiculo.setTensaoBateria(2);
        assertEquals(2, veiculo.getTensaoBateria());
        assertNotEquals(3, veiculo.getTensaoBateria());

        veiculo.setTensaoBateria(0);
        assertEquals(0, veiculo.getTensaoBateria());
        assertNotEquals(3, veiculo.getTensaoBateria());

        IllegalArgumentException exception = new IllegalArgumentException("Tensão da bateria inválida");
        try{
            veiculo.setTensaoBateria(-55);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(), e.getMessage());
        }
    }

    @Test
    void setMassa() {
        veiculo.setMassa(6);
        assertEquals(6, veiculo.getMassa());
        assertNotEquals(9, veiculo.getMassa());

        veiculo.setMassa(0);
        assertEquals(0, veiculo.getMassa());
        assertNotEquals(9, veiculo.getMassa());

        IllegalArgumentException exception = new IllegalArgumentException("Massa do veiculo inválida");
        try{
            veiculo.setMassa(-55);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(), e.getMessage());
        }
    }

    @Test
    void setPotenciaMotor() {
        veiculo.setPotenciaMotor(7);
        assertEquals(7, veiculo.getPotenciaMotor());
        assertNotEquals(9, veiculo.getPotenciaMotor());

        veiculo.setPotenciaMotor(0);
        assertEquals(0, veiculo.getPotenciaMotor());
        assertNotEquals(9, veiculo.getPotenciaMotor());

        IllegalArgumentException exception = new IllegalArgumentException("Potência do motor do veiculo inválida");
        try{
            veiculo.setPotenciaMotor(-55);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(), e.getMessage());
        }
    }

    @Test
    void getId() {
        assertEquals(1, veiculo.getId());
        assertNotEquals(10, veiculo.getId());
    }

    @Test
    void getCarga() {
        assertEquals(10, veiculo.getCarga());
        assertNotEquals(1, veiculo.getCarga());
    }

    @Test
    void getCapacidadeBateria() {
        assertEquals(10, veiculo.getCapacidadeBateria());
        assertNotEquals(1, veiculo.getCapacidadeBateria());
    }

    @Test
    void getConsumoHoraBateria() {
        assertEquals(10, veiculo.getConsumoHoraBateria());
        assertNotEquals(1, veiculo.getConsumoHoraBateria());
    }

    @Test
    void getTensaoBateria() {
        assertEquals(10, veiculo.getTensaoBateria());
        assertNotEquals(1, veiculo.getTensaoBateria());
    }

    @Test
    void getMassa() {
        assertEquals(10, veiculo.getMassa());
        assertNotEquals(1, veiculo.getMassa());
    }

    @Test
    void getPotenciaMotor() {
        assertEquals(10, veiculo.getPotenciaMotor());
        assertNotEquals(1, veiculo.getPotenciaMotor());
    }

    @Test
    void testHashCode() {
        assertEquals(1237286132, veiculo.hashCode());
        assertNotEquals(66, veiculo.hashCode());
    }

    @Test
    void testEquals() {
        assertTrue(veiculo.equals(veiculo));
        assertFalse(veiculo.equals(null));

        Farmacia farmacia = new Farmacia(1,"Teste",123456789,123456789,"teste@email.com","teste2@email.com","1");
        assertFalse(veiculo.equals(farmacia));

        assertFalse(veiculo.equals(new Veiculo(2,10,10,10,10,10,10)));
        assertFalse(veiculo.equals(new Veiculo(1,20,10,10,10,10,10)));
        assertFalse(veiculo.equals(new Veiculo(1,10,20,10,10,10,10)));
        assertFalse(veiculo.equals(new Veiculo(1,10,10,20,10,10,10)));
        assertFalse(veiculo.equals(new Veiculo(1,10,10,10,20,10,10)));
        assertFalse(veiculo.equals(new Veiculo(1,10,10,10,10,20,10)));
        assertFalse(veiculo.equals(new Veiculo(1,10,10,10,10,10,20)));
    }

    @Test
    void testToString() {
        String expResult=String.format("ID: %d%nCarga: %d%nCapacidade da bateria: %f%nConsumo Hora da bateria: %f%nTensao da bateria: %f%nMassa: %f%nPotencia: %f%n", 1,10,10.0,10.0,10.0,10.0,10.0);
        assertEquals(expResult,veiculo.toString());
    }
}