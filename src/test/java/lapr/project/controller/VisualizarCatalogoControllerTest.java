package lapr.project.controller;

import lapr.project.data.FarmaciaBD;
import lapr.project.data.ProdutoBD;
import lapr.project.data.ScooterBD;
import lapr.project.model.Farmacia;
import lapr.project.model.Produto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VisualizarCatalogoControllerTest {
    @Mock
    private ProdutoBD produtoBD;
    @Mock
    private FarmaciaBD farmaciaBD;
//    @Test
//    void getFarmacias() {
//        VisualizarCatalogoController visualizarCatalogoController=new VisualizarCatalogoController();
//        farmaciaBD = mock(FarmaciaBD.class);
//
//        ArrayList<Farmacia> lista=new ArrayList<>();
//        Farmacia farmacia1=new Farmacia("farmacia1",2,2,"farmacia1@gmail.com");
//        Farmacia farmacia2=new Farmacia("farmacia2",2,2,"farmacia2@gmail.com");
//        lista.add(farmacia1);
//        lista.add(farmacia2);
//        when(farmaciaBD.getFarmacias()).thenReturn(lista);
//        visualizarCatalogoController.setFarmaciaBD(farmaciaBD);
//        ArrayList<String> listaString=new ArrayList<>();
//        listaString.add(farmacia1.toString());
//        listaString.add(farmacia2.toString());
//        assertEquals(listaString,visualizarCatalogoController.getFarmacias());
//    }

    @Test
    void getTodosProdutos() {
        VisualizarCatalogoController visualizarCatalogoController=new VisualizarCatalogoController();
        produtoBD = mock(ProdutoBD.class);

        ArrayList<Produto> lista=new ArrayList<>();
        Produto produto1=new Produto(1,"nome",1,1,1);
        Produto produto2=new Produto(2,"nome",12,13,2);
        lista.add(produto1);
        lista.add(produto2);
        when(produtoBD.getTodosProdutos()).thenReturn(lista);
        visualizarCatalogoController.setProdutoBD(produtoBD);
        ArrayList<String> listaString=new ArrayList<>();
        listaString.add(produto1.toString());
        listaString.add(produto2.toString());
        assertEquals(listaString,visualizarCatalogoController.getTodosProdutos());
    }

//    @Test
//    void getProdutosByFarmacia() {
//        VisualizarCatalogoController visualizarCatalogoController=new VisualizarCatalogoController();
//        produtoBD = mock(ProdutoBD.class);
//        ArrayList<Produto> lista=new ArrayList<>();
//        Produto produto1=new Produto(1,"nome",1,1,1);
//        Produto produto2=new Produto(2,"nome",12,13,2);
//        lista.add(produto1);
//        lista.add(produto2);
//        when(produtoBD.getProdutoByFarmacia(1)).thenReturn(lista);
//        visualizarCatalogoController.setProdutoBD(produtoBD);
//        ArrayList<String> listaString=new ArrayList<>();
//        listaString.add(produto1.toString());
//        listaString.add(produto2.toString());
//        assertEquals(listaString,visualizarCatalogoController.getProdutosByFarmacia(1));
//    }

}