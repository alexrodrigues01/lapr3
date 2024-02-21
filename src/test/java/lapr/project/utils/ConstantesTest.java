package lapr.project.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ConstantesTest {

    @Test
    void constantesTest(){
        assertEquals(0,Constantes.PAPEL_ADMIN);
        assertEquals(1,Constantes.PAPEL_GESTOR_FARMACIA);
        assertEquals(2,Constantes.PAPEL_CLIENTE);
        assertEquals(3,Constantes.PAPEL_ESTAFETA);
        assertEquals(30,Constantes.VELOCIDADE_SCOOTER);
        assertEquals(0,Constantes.TAXA_ENTREGA);
        assertEquals("g20.lapr3.2020.2021.testes@gmail.com",Constantes.EMAIL);
        assertEquals(10,Constantes.PERCENTAGEM_CREDITOS);

        assertEquals("src/main/resources/application.properties",Constantes.PARAMS_FICHEIRO);
        assertEquals("database.url",Constantes.PARAMS_BASEDADOS_URL);
        assertEquals("database.username",Constantes.PARAMS_BASEDADOS_USERNAME);
        assertEquals("database.password", Constantes.PARAMS_BASEDADOS_PASS);
        assertEquals("src/main/resources/moradas.txt",Constantes.FICHEIRO_MORADA_NOME);
        assertEquals("src/main/resources/ligacoes_terrestre.txt",Constantes.FICHEIRO_LIGACOES_TERRESTRE_NOME);
        assertEquals("src/main/resources/ligacoes_aereo.txt",Constantes.FICHEIRO_LIGACOES_AEREO_NOME);
        assertEquals("src/main/resources/ventos.txt",Constantes.FICHEIRO_VENTO);
    }
    
    @Test
    void test(){
        Constantes.setVentoxDrone(1);
        assertEquals(1, Constantes.ventoxDrone);
    }

    @Test
    void test1(){
        Constantes.setVentoyDrone(1);
        assertEquals(1, Constantes.ventoyDrone);
    }

    @Test
    void test2(){
        Constantes.setVentoxScooter(1);
        assertEquals(1, Constantes.ventoxScooter);
    }

    @Test
    void test3(){
        Constantes.setVentoyScooter(1);
        assertEquals(1, Constantes.ventoyScooter);
    }

}