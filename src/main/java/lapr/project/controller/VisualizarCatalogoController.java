package lapr.project.controller;

import java.util.ArrayList;
import java.util.List;
import lapr.project.data.ProdutoBD;
import lapr.project.model.Produto;
import lapr.project.utils.StringConverter;

/**
 * The type Visualizar catalogo controller.
 */
public class VisualizarCatalogoController {
    /**
     * The Lista produtos.
     */
    List<Produto> listaProdutos;
    private ProdutoBD produtoBD;

    /**
     * Instantiates a new Visualizar catalogo controller.
     */
    public VisualizarCatalogoController() {
        listaProdutos=new ArrayList<>();
        produtoBD=new ProdutoBD();
    }

    /**
     * Este metodo retorna uma lista com todos os produtos.
     *
     * @return the list
     */
    public List<String> getTodosProdutos(){
        listaProdutos= produtoBD.getTodosProdutos();
        return StringConverter.convertToStringList(listaProdutos);
    }

    /**
     * Sets produto bd.
     *
     * @param produtoBD the produto bd
     */
    public void setProdutoBD(ProdutoBD produtoBD) {
        this.produtoBD = produtoBD;
    }

}
