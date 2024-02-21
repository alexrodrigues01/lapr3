package lapr.project.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import lapr.project.data.BaseDados;
import lapr.project.data.MoradaBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.model.Morada;
import lapr.project.model.Utilizador;
import lapr.project.model.graph.Graph;
import lapr.project.utils.Constantes;
import lapr.project.utils.Pair;
import lapr.project.utils.PreencherGrafo;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AplicacaoPOTTest {

    @Mock
    private UtilizadorBD utilizadorBD;

    @Mock
    private MoradaBD moradaBD;

    @Test
    void doLogin() throws Exception {
        utilizadorBD = mock(UtilizadorBD.class);
        when(utilizadorBD.procuraUtilizador("pessego")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));
        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste", "teste@email.com", 123456789, 123456789, "password", 1));
        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        assertFalse(app.doLogin("pessego", "ola"));
        assertTrue(app.doLogin("teste@email.com", "password"));
        assertEquals("teste@email.com",app.getSessaoAtual().getEmailUtilizador());
        app.doLogout();
        assertNull(app.getSessaoAtual());
        assertTrue(app.doLogin("teste@email.com", "password"));
        assertEquals("teste@email.com",app.getSessaoAtual().getEmailUtilizador());
        app.doLogout();
        assertNull(app.getSessaoAtual());
    }

    @Test
    void setBaseDados() {
        AplicacaoPOT.getInstance();
        assertTrue("jdbc:oracle:thin:@vsrvbd1.dei.isep.ipp.pt:1521/pdborcl".equalsIgnoreCase(BaseDados.getJdbcUrl()) && "LAPR3_G20".equalsIgnoreCase(BaseDados.getUsername()) && "malibu".equalsIgnoreCase(BaseDados.getPassword()));
    }

    /**
     * Test of kebabs method.
     */
    @Test
    public void testKebabsx50() {
        for (int i = 0; i < 50; i++) {
            System.out.println("Kebabs");
        }
    }

    /**
     * Test of setVento method, of class AplicacaoPOT.
     */
    @Test
    public void testSetVento() throws FileNotFoundException {
        System.out.println("setVento");
        AplicacaoPOT instance = AplicacaoPOT.getInstance();
        instance.setVento();
        double ventoxS = Constantes.ventoxScooter;
        double ventoyS = Constantes.ventoyScooter;
        double ventoxD = Constantes.ventoxDrone;
        double ventoyD = Constantes.ventoyDrone;
        PreencherGrafo.getVento(Constantes.FICHEIRO_VENTO);
        assertEquals(ventoxD, Constantes.ventoxDrone);
        assertEquals(ventoyD, Constantes.ventoyDrone);
        assertEquals(ventoxS, Constantes.ventoxScooter);
        assertEquals(ventoyS, Constantes.ventoyScooter);
    }

    /**
     * Test of setGrafos method, of class AplicacaoPOT.
     */
    @Test
    public void testSetGrafos() {
        System.out.println("setGrafos");
        AplicacaoPOT app = AplicacaoPOT.getInstance();
        moradaBD = mock(MoradaBD.class);
        when(moradaBD.addMoradas(new ArrayList<>(Arrays.asList(new Morada("Rua1,BuenosAires", -34.6131500, -58.3772300, 43.513), new Morada("Rua2,Lapaz", -16.5000000, -68.1500000, 456.567), new Morada("Rua3,Brasilia", -15.7797200, -47.9297200, 1320.349), new Morada("Rua4,Santiago", -33.4569400, -70.6482700, 27.895))))).thenReturn(true);
        app.setGrafos(moradaBD,Constantes.FICHEIRO_MORADA_NOME_TESTE,Constantes.FICHEIRO_LIGACOES_TERRESTRE_NOME_TESTE,Constantes.FICHEIRO_LIGACOES_AEREO_NOME_TESTE);
        Graph<Morada, Double> mapaTerrestreEstimativas = app.getMapaTerrestreEstimativas();
        Graph<Morada, Double> mapaTerrestreDistancias = app.getMapaTerrestreDistancias();
        Graph<Morada, Double> mapaAereoEstimativas = app.getMapaAereoEstimativas();
        Graph<Morada, Double> mapaAereoDistancias = app.getMapaAereoDistancias();
        Graph<Morada, Double> mte = PreencherGrafo.lerMoradas(Constantes.FICHEIRO_MORADA_NOME_TESTE);
        Graph<Morada, Double> mtd = PreencherGrafo.lerMoradas(Constantes.FICHEIRO_MORADA_NOME_TESTE);
        Graph<Morada, Double> mae = PreencherGrafo.lerMoradas(Constantes.FICHEIRO_MORADA_NOME_TESTE);
        Graph<Morada, Double> mad = PreencherGrafo.lerMoradas(Constantes.FICHEIRO_MORADA_NOME_TESTE);

        Pair<Graph<Morada, Double>, Graph<Morada, Double>> pmt = PreencherGrafo.lerLigacoes(Constantes.FICHEIRO_LIGACOES_TERRESTRE_NOME_TESTE, mte, mtd, false);
        Pair<Graph<Morada, Double>, Graph<Morada, Double>> pma = PreencherGrafo.lerLigacoes(Constantes.FICHEIRO_LIGACOES_AEREO_NOME_TESTE, mae, mad, true);

        System.out.println(mapaTerrestreDistancias);
        System.out.println("--------------");
        System.out.println(pmt.getValue());
        assertEquals(mapaTerrestreDistancias, pmt.getValue());
        assertEquals(mapaTerrestreEstimativas, pmt.getKey());
        assertEquals(mapaAereoEstimativas, pma.getKey());
        assertEquals(mapaAereoDistancias, pma.getValue());

    }

}
