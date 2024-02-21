package lapr.project.controller;

import lapr.project.data.ProdutoBD;
import lapr.project.utils.ValidarParametros;

import java.sql.SQLException;

public class RegistoProdutoController {

    private final ProdutoBD produtoBD;

    /**
     * construtor registo produto controller
     * @param produtoBD produtoBD
     */
    public RegistoProdutoController(ProdutoBD produtoBD) {
        this.produtoBD = produtoBD;
    }

    /**
     * valida produto
     * @param nome
     * @param precoUnitario
     */
    public void novoProduto(String nome, double precoUnitario, int pesoUnitario,int iva){
        ValidarParametros.validarParametrosProduto(nome, precoUnitario, pesoUnitario,iva);
        if(!produtoBD.validaProduto(nome))
            throw new IllegalArgumentException("Produto já existe na base de dados.");
    }


    /**
     * regista produto na base de dados
     * @param nome
     * @param precoUnitario
     * @throws SQLException
     */
    public boolean registarProduto(String nome, double precoUnitario, int pesoUnitario,int iva) throws SQLException {
        return produtoBD.registaProduto(nome,precoUnitario,pesoUnitario,iva);
    }
}
