package lapr.project.controller;

import lapr.project.data.DroneBD;
import lapr.project.data.ScooterBD;
import lapr.project.utils.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EnviarEmailEstimativaTaskTest {
    @Mock
    private ScooterBD scooterBD;
    @Mock
    private DroneBD droneBD;

    @Test
    void sendEmail() {
        scooterBD = mock(ScooterBD.class);
        droneBD=mock(DroneBD.class);
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
        when(scooterBD.getScooterByID(1)).thenReturn("1");
        when(droneBD.getDroneByID(1)).thenReturn("1");
        when(scooterBD.isScooter(1)).thenReturn(true);
        when(droneBD.isDrone(1)).thenReturn(false);

        Pair<String, Pair<String,Integer>>  expResult=new Pair<>("email@gmail.com",new Pair<>("1",24));
        assertTrue(EnviarEmailEstimativaTask.sendEmail(name,scooterBD,droneBD));
    }

    @Test
    void sendEmail1() {
        scooterBD = mock(ScooterBD.class);
        droneBD=mock(DroneBD.class);
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
        when(scooterBD.getScooterByID(1)).thenReturn("1");
        when(scooterBD.isScooter(1)).thenReturn(false);
        when(droneBD.isDrone(1)).thenReturn(true);
        when(droneBD.getDroneByID(1)).thenReturn("1");
        when(droneBD.getEmailAdministradorByDrone(1)).thenReturn("email@gmail.com");
        assertTrue(EnviarEmailEstimativaTask.sendEmail(name,scooterBD,droneBD));
    }

    @Test
    void sendEmail2() {
        scooterBD = mock(ScooterBD.class);
        droneBD=mock(DroneBD.class);
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
        assertFalse(EnviarEmailEstimativaTask.sendEmail(name,scooterBD,droneBD));
    }
    @Test
    void sendEmail3() {
        scooterBD = mock(ScooterBD.class);
        droneBD=mock(DroneBD.class);
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"estimate_[datetime].data.flag.txt")) {
            fileWriter.write(1+";"+24+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\VeiculoInfo"+"\\"+"estimate_[datetime].data.txt")) {
            fileWriter.write(1+";"+";"+"email@gmail.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
        when(scooterBD.isScooter(1)).thenReturn(true);
        String name="estimate_[datetime].data.flag.txt";
        assertFalse(EnviarEmailEstimativaTask.sendEmail(name,scooterBD,droneBD));
    }
    @Test
    void sendEmail4() {
        scooterBD = mock(ScooterBD.class);
        droneBD=mock(DroneBD.class);
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
        when(scooterBD.isScooter(1)).thenReturn(true);
        String name="estimate_[datetime].data.flag.txt";
        assertFalse(EnviarEmailEstimativaTask.sendEmail(name,scooterBD,droneBD));
    }

}