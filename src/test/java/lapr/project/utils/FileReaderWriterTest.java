package lapr.project.utils;

import lapr.project.data.DroneBD;
import lapr.project.data.ScooterBD;
import lapr.project.ui.FileReaderWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static lapr.project.utils.Constantes.PATHC;
import static lapr.project.utils.Constantes.PATH;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileReaderWriterTest {

    @Mock
    private ScooterBD scooterBD;
    @Mock
    private DroneBD droneBD;

    @Test
    void lockFileReader() {
        scooterBD = mock(ScooterBD.class);
        droneBD=mock(DroneBD.class);
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"lock_2020_12_32_12_34_54.data.flag.txt")) {
            fileWriter.write(1+";"+20+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"lock_2020_12_32_12_34_54.data.txt")) {
            fileWriter.write(1+";"+20+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name="lock_2020_12_32_12_34_54.data.flag.txt";
        when(scooterBD.isScooter(1)).thenReturn(true);
        when(scooterBD.getScooterByID(1)).thenReturn("1");
        when(scooterBD.getCapacidadeAndParqueByIdVeiculo(1)).thenReturn(new Pair<>(2.0,2));
        Pair<String,Pair<String,String>>  expResult=new Pair<>("2020_12_32_12_34_54",new Pair<>("1","email@gmail.com"));
        Pair<String,Pair<String,String>>  result= FileReaderWriter.lockFileReader(name,scooterBD,droneBD);
        assertEquals(expResult,result);
        File f = new File(PATHC+"\\info.txt");
        assertTrue(f.exists());
        f.delete();
    }

    @Test
    void lockFileReader1() {
        scooterBD = mock(ScooterBD.class);
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\ScooterInfo"+"\\"+"lock_2020_12_32_12_34_54.data.flag.txt")) {
            fileWriter.write(1+";"+20+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\ScooterInfo"+"\\"+"lock_2020_12_32_12_34_54.data.txt")) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        String name="lock_2020_12_32_12_34_54.data.flag.txt";
        when(scooterBD.isScooter(1)).thenReturn(true);
        when(scooterBD.getScooterByID(1)).thenReturn("1");
        Pair<String,Pair<String,String>>  result=FileReaderWriter.lockFileReader(name,scooterBD,droneBD);
        assertNull(result);
        File f = new File(PATHC+"\\info.txt");
        if(f.exists())
        f.delete();
    }


    @Test
    void lockFileReader3() {
        droneBD=mock(DroneBD.class);
        scooterBD = mock(ScooterBD.class);
        when(scooterBD.getScooterByID(1)).thenReturn("1");
        Pair<String,Pair<String,String>>  result=FileReaderWriter.lockFileReader(null,scooterBD,droneBD);
        assertNull (result);
    }
    @Test
    void lockFileReader5() {
        scooterBD = mock(ScooterBD.class);
        droneBD=mock(DroneBD.class);
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"lock_2020_12_32_12_34_54.data.flag.txt")) {
            fileWriter.write(1+";"+20+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"lock_2020_12_32_12_34_54.data.txt")) {
            fileWriter.write(1+";"+20+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name="lock_2020_12_32_12_34_54.data.flag.txt";
        when(scooterBD.isScooter(1)).thenReturn(false);
        when(droneBD.isDrone(1)).thenReturn(true);
        when(droneBD.getDroneByID(1)).thenReturn("1");
        when(droneBD.getEmailAdministradorByDrone(1)).thenReturn("email@gmail.com");
        when(scooterBD.getCapacidadeAndParqueByIdVeiculo(1)).thenReturn(new Pair<>(2.0,2));
        Pair<String,Pair<String,String>>  expResult=new Pair<>("2020_12_32_12_34_54",new Pair<>("1","email@gmail.com"));
        Pair<String,Pair<String,String>>  result=FileReaderWriter.lockFileReader(name,scooterBD,droneBD);
        assertEquals(expResult,result);
        File f = new File(PATHC+"\\info.txt");
        assertTrue(f.exists());
        f.delete();
    }
    @Test
    void estimateFileReader() {
        droneBD=mock(DroneBD.class);
        scooterBD = mock(ScooterBD.class);
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"estimate_[datetime].data.flag.txt")) {
            fileWriter.write(1+";"+24+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"estimate_[datetime].data.txt")) {
            fileWriter.write(1+";"+24+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name="estimate_[datetime].data.flag.txt";
        when(scooterBD.isScooter(1)).thenReturn(true);
        when(scooterBD.getScooterByID(1)).thenReturn("1");
        Pair<String,Pair<String,Integer>>  expResult=new Pair<>("email@gmail.com",new Pair<>("1",24));
        Pair<String,Pair<String,Integer>>  result=FileReaderWriter.estimateFileReader(name,scooterBD,droneBD);
        assertEquals(expResult,result);
    }


    @Test
    void estimateFileReader2() {
        droneBD=mock(DroneBD.class);
        scooterBD = mock(ScooterBD.class);
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"estimate_[datetime].data.flag.txt")) {
            fileWriter.write(1+";"+20+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"estimate_[datetime].data.txt")) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        String name="estimate_[datetime].data.flag.txt";
        when(scooterBD.isScooter(1)).thenReturn(true);
        when(scooterBD.getScooterByID(1)).thenReturn("1");
        Pair<String,Pair<String,Integer>>  result=FileReaderWriter.estimateFileReader(name,scooterBD,droneBD);
        assertNull(result);
    }

    @Test
    void estimateFileReader4() {
        droneBD=mock(DroneBD.class);
        scooterBD = mock(ScooterBD.class);
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"estimate_[datetime].data.flag.txt")) {
            fileWriter.write(1+";"+20+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name="estimate_[datetime].data.flag.txt";
        when(scooterBD.isScooter(1)).thenReturn(true);
        when(scooterBD.getScooterByID(1)).thenReturn("1");
        Pair<String,Pair<String,Integer>>  result=FileReaderWriter.estimateFileReader("fail.txt",scooterBD,droneBD);
        assertNull(result);
    }

    @Test
    void estimateFileReader3() {
        droneBD=mock(DroneBD.class);
        scooterBD = mock(ScooterBD.class);
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"estimate_[datetime].data.flag.txt")) {
            fileWriter.write(1+";"+20+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name="estimate_[datetime].data.flag.txt";
        when(scooterBD.isScooter(1)).thenReturn(true);
        when(scooterBD.getScooterByID(1)).thenReturn("1");
        Pair<String,Pair<String,Integer>>  result=FileReaderWriter.estimateFileReader(name,scooterBD,droneBD);
        assertNull(result);
    }

    @Test
    void estimateFileReader5() {
        droneBD=mock(DroneBD.class);
        scooterBD = mock(ScooterBD.class);
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"estimate_[datetime].data.flag.txt")) {
            fileWriter.write(1+";"+24+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"estimate_[datetime].data.txt")) {
            fileWriter.write(1+";"+24+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name="estimate_[datetime].data.flag.txt";
        when(droneBD.isDrone (1)).thenReturn(true);
        when(droneBD.getDroneByID(1)).thenReturn("1");
        when(droneBD.getEmailAdministradorByDrone(1)).thenReturn("email@gmail.com");
        Pair<String,Pair<String,Integer>>  expResult=new Pair<>("email@gmail.com",new Pair<>("1",24));
        Pair<String,Pair<String,Integer>>  result=FileReaderWriter.estimateFileReader(name,scooterBD,droneBD);
        assertEquals(expResult,result);
    }
//    @Test
//    void estimateFileReader1() {
//        scooterBD = mock(ScooterBD.class);
//        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\ScooterInfo"+"\\"+"estimate_[datetime].data.flag.txt")) {
//            fileWriter.write(1+";"+24+";"+"email@gmail.com");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String name="estimate_[datetime].data.flag.txt";
//        when(scooterBD.getScooterByID(1)).thenReturn("1");
//        Pair<String,Pair<String,Integer>>  result=FileReaderWriter.estimateFileReader(name,scooterBD);
//        assertNull(result);
//    }


}