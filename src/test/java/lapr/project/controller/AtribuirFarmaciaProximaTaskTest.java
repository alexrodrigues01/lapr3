package lapr.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lapr.project.data.MoradaBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.model.Morada;
import lapr.project.model.Utilizador;
import lapr.project.model.graph.Graph;
import lapr.project.utils.Constantes;
import lapr.project.utils.Pair;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

class AtribuirFarmaciaProximaTaskTest {

    @Mock
    MoradaBD moradaBD = mock(MoradaBD.class);
    @Mock
    UtilizadorBD utilizadorBD = mock(UtilizadorBD.class);

    AtribuirFarmaciaProximaTask task = new AtribuirFarmaciaProximaTask(moradaBD);


    @Test
    void atribuiEncomendaFarmacia() {

        String emailCliente = "cliente@email.com";
        int idFarmacia1 = 1;

        String emailEstafeta = "estafeta@gmail.com";
        String password = "password";
        when(utilizadorBD.procuraUtilizador(emailEstafeta)).thenReturn(new Utilizador("estafeta", emailEstafeta, 123456789, 123456789, password, Constantes.PAPEL_ESTAFETA));

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin(emailEstafeta, password);

        Morada moradaCliente = new Morada("cliente", 10, 10, 10);
        Morada farmarcia1 = new Morada("farmarcia1", 20, 20, 20);
        Morada farmarcia2 = new Morada("farmarcia2", 30, 30, 30);
        Morada farmarcia3 = new Morada("farmarcia3", 40, 40, 40);
        Morada farmarcia4 = new Morada("farmarcia3", 50, 50, 50);
        Morada farmarcia5 = new Morada("farmarcia3", 60, 60, 60);
        Morada farmarcia6 = new Morada("farmarcia3", 70, 70, 70);

        Graph<Morada, Double> grafo = new Graph<>(false);
        grafo.insertEdge(moradaCliente, farmarcia1, 0.0, 1);
        grafo.insertEdge(moradaCliente, farmarcia2, 0.0, 2);
        grafo.insertEdge(farmarcia1, farmarcia3, 0.0, 3);
        grafo.insertEdge(farmarcia3, farmarcia4, 0.0, -2);
        grafo.insertEdge(farmarcia4, farmarcia5, 0.0, 2);
        grafo.insertEdge(farmarcia2, farmarcia6, 0.0, 2);

        app.setMapaTerrestreDistancias(grafo);

        Map<Integer, Morada> moradasFarmacias = new HashMap<>();
        moradasFarmacias.put(1, farmarcia1);
        moradasFarmacias.put(2, farmarcia2);
        moradasFarmacias.put(3, farmarcia3);
        moradasFarmacias.put(4, farmarcia4);
        moradasFarmacias.put(5, farmarcia5);
        moradasFarmacias.put(6, farmarcia6);

        when(moradaBD.getMoradasFarmacia()).thenReturn(moradasFarmacias);
        when(moradaBD.getMoradaUtilizador(emailCliente)).thenReturn(moradaCliente);


        //sucesso
        assertEquals(idFarmacia1, task.atribuiEncomendaFarmacia(emailCliente));


        //sucesso do metodo de ordenacao
        List<Pair<Double, Integer>> farmaciasOrdenadas = new ArrayList<>();
        farmaciasOrdenadas.add(new Pair<>(1.0, 1));
        farmaciasOrdenadas.add(new Pair<>(2.0, 2));
        farmaciasOrdenadas.add(new Pair<>(2.0, 4));
        farmaciasOrdenadas.add(new Pair<>(4.0, 3));
        farmaciasOrdenadas.add(new Pair<>(4.0, 5));
        farmaciasOrdenadas.add(new Pair<>(4.0, 6));

        assertEquals(farmaciasOrdenadas, task.getDistanciaFarmacias());


        // insucesso (não conseguiu obter a morada do cliente)
        when(moradaBD.getMoradaUtilizador(emailCliente)).thenReturn(null);
        try{
            task.atribuiEncomendaFarmacia(emailCliente);
        }catch (Exception e){
            assertEquals("Erro ao ir buscar a morada do cliente", e.getMessage());
        }


        //insucesso (não conseguiu obter as moradas das farmacias)
        when(moradaBD.getMoradasFarmacia()).thenReturn(null);
        try{
            task.atribuiEncomendaFarmacia(emailCliente);
        }catch (Exception e){
            assertEquals("Erro ao ir buscar todas as moradas da farmacia", e.getMessage());
        }
    }
}