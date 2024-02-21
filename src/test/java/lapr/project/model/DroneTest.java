package lapr.project.model;

import com.google.zxing.WriterException;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DroneTest {

    Drone instance= new Drone(1,20,300.0,1,250.0,50.0,300.0,10.0,8.0,1.5,4.0,0.2,3.0);

    @Test
    void getId() {
        int result=instance.getId();
        int expResult=1;
        assertEquals(expResult,result);
    }

    @Test
    void getEstadoDroneID() {
        int result=instance.getEstadoDroneID();
        int expResult=1;
        assertEquals(expResult,result);
    }

    @Test
    void testEquals() {
        Drone instance= new Drone(1,20,300,1,250,50,300,10,8,1.5,4,0.2,3);
        assertTrue(instance.equals(instance));
    }

    @Test
    void testEquals2() {
        assertTrue(instance.equals(instance));
    }


    @Test
    void testEqualsFail() throws IOException, WriterException {
        //test drone diferente
        assertFalse(instance.equals(new Drone(1,20,300,1,250,50,300,10,8,1.5,4,0.2,5)));
        assertFalse(instance.equals(new Drone(1,20,300,1,250,50,300,10,8,1.5,4,0.1,3)));
        assertFalse(instance.equals(new Drone(1,20,300,1,250,50,300,10,8,1.5,3,0.2,3)));
        assertFalse(instance.equals(new Drone(1,20,300,1,250,50,300,10,8,1.2,4,0.2,3)));
        assertFalse(instance.equals(new Drone(1,20,300,1,250,50,300,10,6,1.5,4,0.2,3)));
        assertFalse(instance.equals(new Drone(1,20,300,1,250,50,300,20,8,1.5,4,0.2,3)));
        assertFalse(instance.equals(new Drone(1,20,300,1,250,50,400,10,8,1.5,4,0.2,3)));
        assertFalse(instance.equals(new Drone(1,20,300,1,250,20,300,10,8,1.5,4,0.2,3)));
        assertFalse(instance.equals(new Drone(1,20,300,1,150,50,300,10,8,1.5,4,0.2,3)));
        assertFalse(instance.equals(new Drone(1,20,300,2,250,50,300,10,8,1.5,4,0.2,3)));
        assertFalse(instance.equals(new Drone(1,20,100,1,250,50,300,10,8,1.5,4,0.2,3)));
        assertFalse(instance.equals(new Drone(1,10,300,1,250,50,300,10,8,1.5,4,0.2,3)));
        assertFalse(instance.equals(new Drone(2,10,300,1,250,50,300,10,8,1.5,4,0.2,3)));
    }

    @Test
    void testEqualsFail2() throws IOException, WriterException {
        //test drone diferente
        LugarEstacionamento lE = new LugarEstacionamento(true, 1, 2);
        assertFalse(instance.equals(lE));
    }
    @Test
    void testEqualsFail3() throws IOException, WriterException {
        //test drone diferente
        Drone drone= null;
        assertFalse(instance.equals(drone));
    }

    @Test
    void testConstrutor(){
        Drone drone= new Drone(2,20,300,1,250,50,300,10, 8,1.5,4,0.2,3);
        assertEquals(Drone.class,drone.getClass());
    }

    @Test
    void getCarga() {
        assertEquals(20,instance.getCarga());
    }

    @Test
    void getCapacidadeBateria() {
        assertEquals(300,instance.getCapacidadeBateria());
    }

    @Test
    void getConsumoHoraBateria() {
        assertEquals(250,instance.getConsumoHoraBateria());
    }

    @Test
    void getTensaoBateria() {
        assertEquals(50,instance.getTensaoBateria());
    }

    @Test
    void getMassa() {
        assertEquals(300,instance.getMassa());
    }

    @Test
    void getPotencia() {
        assertEquals(10,instance.getPotenciaMotor());
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
        try{
            instance.setCapacidadeBateria(0);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }


    @Test
    void setEstadoSuccess(){
        instance.setEstadoDroneID(3);
        double expResult=3;
        double result= instance.getEstadoDroneID();
        assertEquals(expResult,result);

        instance.setEstadoDroneID(1);
        expResult=1;
        result= instance.getEstadoDroneID();
        assertEquals(expResult,result);

        instance.setEstadoDroneID(2);
        expResult=2;
        result= instance.getEstadoDroneID();
        assertEquals(expResult,result);
    }

    @Test
    void setEstadoFail(){
        IllegalArgumentException expResult=new IllegalArgumentException("Id do estado do drone inválido");
        try{
            instance.setEstadoDroneID(-20);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
        try{
            instance.setEstadoDroneID(0);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
        try{
            instance.setEstadoDroneID(4);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
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
    void setMassaFail() {
        IllegalArgumentException expResult= new IllegalArgumentException("Massa do veiculo inválida");
        try{
            instance.setMassa(-500);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }
    @Test
    void setMassaSuccess2(){
        instance.setMassa(0);
        double expResult=0;
        double result=instance.getMassa();
        assertEquals(expResult,result);
    }

    @Test
    void setMassaSuccess(){
        instance.setMassa(350);
        double expResult=350;
        double result=instance.getMassa();
        assertEquals(expResult,result);
    }

    @Test
    void setPotenciaFail() {
        IllegalArgumentException expResult= new IllegalArgumentException("Potência do motor do veiculo inválida");
        try{
            instance.setPotenciaMotor(-40);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setPotenciaSuccess(){
        instance.setPotenciaMotor(20);
        double expResult=20;
        double result=instance.getPotenciaMotor();
        assertEquals(expResult,result);
    }

    @Test
    void setPotenciaSuccess2(){
        instance.setPotenciaMotor(0);
        double expResult=0;
        double result=instance.getPotenciaMotor();
        assertEquals(expResult,result);
    }

    @Test
    void setVelocidadeFail() {
        IllegalArgumentException expResult= new IllegalArgumentException("Velocidade inválida");
        try{
            instance.setVelocidade(-40);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
        try{
            instance.setVelocidade(0);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setVelocidadeSuccess(){
        instance.setVelocidade(20);
        double expResult=20;
        double result=instance.getVelocidade();
        assertEquals(expResult,result);
    }

    @Test
    void setAvionicsFail() {
        IllegalArgumentException expResult= new IllegalArgumentException("Valor de avionics inválido");
        try{
            instance.setAvionics(-40);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
        try{
            instance.setAvionics(0);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setAvionicsSuccess(){
        instance.setAvionics(20);
        double expResult=20;
        double result=instance.getAvionics();
        assertEquals(expResult,result);
    }

    @Test
    void setDragFail() {
        IllegalArgumentException expResult= new IllegalArgumentException("Valor de drag inválido");
        try{
            instance.setDrag(-40);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
        try{
            instance.setDrag(0);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setDragSuccess(){
        instance.setDrag(4);
        double expResult=4;
        double result=instance.getDrag();
        assertEquals(expResult,result);
    }

    @Test
    void setRotorsSuccess(){
        instance.setRotors(0.5);
        double expResult=0.5;
        double result= instance.getRotors();
        assertEquals(expResult,result);
    }

    @Test
    void setRotorsFail(){
        IllegalArgumentException expResult=new IllegalArgumentException("Valor dos rotors inválido");
        try{
            instance.setRotors(-20);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
        try{
            instance.setRotors(0);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
        try{
            instance.setRotors(1);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
        try{
            instance.setRotors(20);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setCargaMaxFail() {
        IllegalArgumentException expResult= new IllegalArgumentException("Carga maxima inválida");
        try{
            instance.setCargaMaxima(-40);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
        try{
            instance.setCargaMaxima(0);
        }catch (IllegalArgumentException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setCargaMaxSuccess(){
        instance.setCargaMaxima(4);
        double expResult=4;
        double result=instance.getDrag();
        assertEquals(expResult,result);
    }



    @Test
    void testHashCode() {
        assertEquals(-20706121,instance.hashCode());
    }

    @Test
    void testToString() {
        String expResult=String.format("ID: %d%nCarga: %d%nCapacidade da bateria: %f%nConsumo Hora da bateria: %f%nTensao da bateria: %f%nMassa: %f%nPotencia: %f%n", 1,20,300.0,250.0,50.0,300.0,10.0) + "estadoDroneID: " + 1 +
                "\nvelocidade: " + 8.0 +
                "\navionics: " + 1.5 +
                "\ndrag: " + 4.0 +
                "\nrotors: " + 0.2 +
                "\ncargaMaxima: " + 3.0 + "\n";
        System.out.println(instance.toString());
        assertEquals(expResult,instance.toString());
    }
}