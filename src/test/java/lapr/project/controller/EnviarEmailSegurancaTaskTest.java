package lapr.project.controller;

import lapr.project.data.DroneBD;
import lapr.project.data.ScooterBD;
import lapr.project.ui.FileReaderWriter;
import lapr.project.utils.Pair;
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

class EnviarEmailSegurancaTaskTest {
    @Mock
    private DroneBD droneBD;

    @Mock
    private ScooterBD scooterBD;
    @Test
    void sendEmail() {
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
        when(droneBD.isDrone(1)).thenReturn(false);
        when(scooterBD.getScooterByID(1)).thenReturn("1");
        when(scooterBD.getCapacidadeAndParqueByIdVeiculo(1)).thenReturn(new Pair<>(2.0,2));
      assertTrue( EnviarEmailSegurancaTask.sendEmail(name,scooterBD,droneBD));
        File f = new File(PATHC+"\\info.txt");
        if(f.exists())
        f.delete();
    }
    @Test
    void sendEmail1() {
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
        assertTrue( EnviarEmailSegurancaTask.sendEmail(name,scooterBD,droneBD));;
        File f = new File(PATHC+"\\info.txt");
        if(f.exists())
        f.delete();
    }
    @Test
    void sendEmail2() {
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
        when(droneBD.isDrone(1)).thenReturn(false);
        assertFalse( EnviarEmailSegurancaTask.sendEmail(name,scooterBD,droneBD));;
        File f = new File(PATHC+"\\info.txt");
    if(f.exists())
        f.delete();
    }

}