package lapr.project.model;

import lapr.project.utils.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FaturaTest {

    Fatura fatura;

    @BeforeEach
    void setUp() {
        Date date = new Date();
        double valorProdutos = 12;
        int nif = 123456789;

        fatura = new Fatura(date, valorProdutos, Constantes.TAXA_ENTREGA, nif);
    }

    @Test
    void LinhaFaturaConstrutor() {
        assertEquals(Fatura.class, fatura.getClass());
    }

    @Test
    void setProdutos() {
        Date date = new Date();
        double valorProdutos = 12;
        int nif = 123456789;
        Fatura f = new Fatura(1,date, valorProdutos, Constantes.TAXA_ENTREGA, nif);

        f.setProdutos("Teste", 3.0, 2, 2, 80);
        f.setProdutos("Teste", 3.0, 2, 2, 80);
        f.setProdutos("Teste", 3.0, 2, 2, 80);
        f.setProdutos("Teste", 3.0, 2, 2, 80);

        String expected = "ID: 1\n" +
                "Data: "+new Date()+"\n" +
                "Valor Total: 12.0\n" +
                "Taxa Entrega: 0\n" +
                "NIF: 123456789\n" +
                "Produtos:\n" +
                "-> Nome: Teste, Valor: 3.0, Quantidade: 2, Preço: 2.0, IVA: 80\n" +
                "-> Nome: Teste, Valor: 3.0, Quantidade: 2, Preço: 2.0, IVA: 80\n" +
                "-> Nome: Teste, Valor: 3.0, Quantidade: 2, Preço: 2.0, IVA: 80\n" +
                "-> Nome: Teste, Valor: 3.0, Quantidade: 2, Preço: 2.0, IVA: 80\n\n";


        assertEquals(expected, f.toString());
    }

    @Test
    void testToString() {
        fatura.setProdutos("Teste", 3.0, 2, 2, 80);
        fatura.setProdutos("Teste", 3.0, 2, 2, 80);
        fatura.setProdutos("Teste", 3.0, 2, 2, 80);
        fatura.setProdutos("Teste", 3.0, 2, 2, 80);

        String expected = "ID: 0\n" +
                "Data: "+new Date()+"\n" +
                "Valor Total: 12.0\n" +
                "Taxa Entrega: 0\n" +
                "NIF: 123456789\n" +
                "Produtos:\n" +
                "-> Nome: Teste, Valor: 3.0, Quantidade: 2, Preço: 2.0, IVA: 80\n" +
                "-> Nome: Teste, Valor: 3.0, Quantidade: 2, Preço: 2.0, IVA: 80\n" +
                "-> Nome: Teste, Valor: 3.0, Quantidade: 2, Preço: 2.0, IVA: 80\n" +
                "-> Nome: Teste, Valor: 3.0, Quantidade: 2, Preço: 2.0, IVA: 80\n\n";

        assertEquals(expected, fatura.toString());
    }
}