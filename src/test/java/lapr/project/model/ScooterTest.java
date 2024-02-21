package lapr.project.model;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import java.io.IOException;
import lapr.project.utils.QRCode;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class ScooterTest {
    Scooter instance= new Scooter(1,20,300.0,1,250.0,50.0,300.0,10.0);

    ScooterTest() throws Exception {
    }

    @Test
    void setCargaFail(){
        IllegalArgumentException expResult=new IllegalArgumentException("Carga inválida");
        try{
            instance.setCarga(-2);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setCargaFail2(){
        IllegalArgumentException expResult=new IllegalArgumentException("Carga inválida");
        try{
            instance.setCarga(200);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setCargaSuccess() {
        instance.setCarga(10);
        int expResult= 10;
        int result=instance.getCarga();
        assertEquals(expResult,result);
    }

    @Test
    void setCargaSuccess2() {
        instance.setCarga(0);
        int expResult= 0;
        int result=instance.getCarga();
        assertEquals(expResult,result);
    }

    @Test
    void setCargaSuccess3() {
        instance.setCarga(100);
        int expResult= 100;
        int result=instance.getCarga();
        assertEquals(expResult,result);
    }

    @Test
    void setCapacidadeFail(){
        IllegalArgumentException expResult=new IllegalArgumentException("Capacidade da bateria inválida");
        try{
            instance.setCapacidadeBateria(-20);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setCapacidadeSuccess2(){
        instance.setCapacidadeBateria(0);
        double expResult=0;
        double result= instance.getCapacidadeBateria();
        assertEquals(expResult,result);
    }


    @Test
    void setCapacidadeSuccess(){
        instance.setCapacidadeBateria(350);
        double expResult=350;
        double result= instance.getCapacidadeBateria();
        assertEquals(expResult,result);
    }


    @Test
    void setConsumoHoraBateriaFail() {
        IllegalArgumentException expResult= new IllegalArgumentException("Consumo por hora da bateria inválido");
        try{
            instance.setConsumoHoraBateria(-45);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setConsumoHoraBateriaSuccess(){
        instance.setConsumoHoraBateria(20);
        double expResult=20;
        double result=instance.getConsumoHoraBateria();
        assertEquals(expResult,result);
    }
    @Test
    void setConsumoHoraBateriaSuccess2(){
        instance.setConsumoHoraBateria(0);
        double expResult=0;
        double result=instance.getConsumoHoraBateria();
        assertEquals(expResult,result);
    }

    @Test
    void setTensaoBateriaFail() {
        IllegalArgumentException expResult= new IllegalArgumentException("Tensão da bateria inválida");
        try{
            instance.setTensaoBateria(-20);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setTensaoBateriaSuccess(){
        instance.setTensaoBateria(40);
        double expResult=40;
        double result=instance.getTensaoBateria();
        assertEquals(expResult,result);

    }

    @Test
    void setTensaoBateriaSuccess2(){
        instance.setTensaoBateria(0);
        double expResult=0;
        double result=instance.getTensaoBateria();
        assertEquals(expResult,result);

    }

    @Test
    void setPesoFail() {
        IllegalArgumentException expResult= new IllegalArgumentException("Massa do veiculo inválida");
        try{
            instance.setMassa(-500);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }
    @Test
    void setPesoSuccess2(){
        instance.setMassa(1);
        double expResult=1;
        double result=instance.getMassa();
        assertEquals(expResult,result);
    }

    @Test
    void setPesoSuccess(){
        instance.setMassa(350);
        double expResult=350;
        double result=instance.getMassa();
        assertEquals(expResult,result);
    }

    @Test
    void setPotenciaMotorFail() {
        IllegalArgumentException expResult= new IllegalArgumentException("Potência do motor do veiculo inválida");
        try{
            instance.setPotenciaMotor(-40);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setPotenciaMotorSuccess(){
        instance.setPotenciaMotor(20);
        double expResult=20;
        double result=instance.getPotenciaMotor();
        assertEquals(expResult,result);
    }

    @Test
    void setPotenciaMotorSuccess2(){
        instance.setPotenciaMotor(0);
        double expResult=0;
        double result=instance.getPotenciaMotor();
        assertEquals(expResult,result);
    }

    @Test
    void getQrCode() throws NotFoundException {
        String result=QRCode.convertToText( instance.getQrCode());
        String expResult="1";
        assertEquals(expResult,result);

    }

    @Test
    void getId() {
        int result=instance.getId();
        int expResult=1;
        assertEquals(expResult,result);
    }

    @Test
    void getEstadoScooterID() {
        int result=instance.getEstadoScooterID();
        int expResult=1;
        assertEquals(expResult,result);
    }

    @Test
    void testEquals() throws IOException, WriterException {
        Scooter scooter= new Scooter(1,20,300,1,250,50,300,10);
        assertTrue(instance.equals(scooter));
    }

    @Test
    void testEquals2() throws IOException, WriterException {
        assertTrue(instance.equals(instance));
    }


    @Test
    void testEqualsFail() throws IOException, WriterException {
        //test scooter diferente
        Scooter scooter= new Scooter(2,20,300,1,250,50,300,10);
        assertFalse(instance.equals(scooter));
        assertFalse(instance.equals(new Scooter(1,20,300,1,250,50,300,20)));
        assertFalse(instance.equals(new Scooter(1,20,300,1,250,50,400,10)));
        assertFalse(instance.equals(new Scooter(1,20,300,1,250,20,300,10)));
        assertFalse(instance.equals(new Scooter(1,20,300,1,150,50,300,10)));
        assertFalse(instance.equals(new Scooter(1,20,300,2,250,50,300,10)));
        assertFalse(instance.equals(new Scooter(1,20,100,1,250,50,300,10)));
        assertFalse(instance.equals(new Scooter(1,10,300,1,250,50,300,10)));
    }

    @Test
    void testEqualsFail2() throws IOException, WriterException {
        //test scooter diferente
        LugarEstacionamento lE = new LugarEstacionamento(true, 1, 2);
        assertFalse(instance.equals(lE));
    }
    @Test
    void testEqualsFail3() throws IOException, WriterException {
        //test scooter diferente
        Scooter scooter= null;
        assertFalse(instance.equals(scooter));
    }

    @Test
    void testConstrutor(){
        Scooter scooter= new Scooter(1,20,30);
        assertEquals(Scooter.class,scooter.getClass());
    }

    @Test
    void testHashCode() {
        assertEquals(120,instance.hashCode());
    }

    @Test
    void testToString() {
        String expResult=String.format("ID: %d%nCarga: %d%nCapacidade da bateria: %f%nConsumo Hora da bateria: %f%nTensao da bateria: %f%nMassa: %f%nPotencia: %f%n", 1,20,300.0,250.0,50.0,300.0,10.0);
        assertEquals(expResult,instance.toString());
    }
}