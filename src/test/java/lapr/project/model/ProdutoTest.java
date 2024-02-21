package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    Produto instance = new Produto(3,"Produto",2.0,2,80);
    Produto paracetamol = new Produto(1,"Paracetamol",6.75,0,35);
    Produto viagra = new Produto(2,"Viagra",19.75,69,180);
    Produto xanax = new Produto(4,"Xanax",5.55,55,155);

    @Test
    void setNomeSucess() {
        instance.setNome("Medicamento");
        String expResult="Medicamento";
        String result=instance.getNome();
        assertEquals(expResult,result);
    }

    @Test
    void setNomeFail(){
        NullPointerException expResult= new NullPointerException("O nome do produto não pode ser null");
        try{
            instance.setNome(null);
        }catch (NullPointerException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setNomeFail2(){
        NullPointerException expResult= new NullPointerException("O nome do produto não pode ser null");
        try{
            instance.setNome("");
        }catch (NullPointerException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setPrecoUnitarioFail(){
        NumberFormatException expResult= new NumberFormatException("Preço tem ser positivo");
        try{
            instance.setPrecoUnitario(-5000000);
        }catch (NumberFormatException e){
            assertEquals(expResult.getMessage(),e.getMessage());
        }
    }

    @Test
    void setPrecoUnitarioSucess(){
        instance.setPrecoUnitario(2);
        double expResult=2;
        double result=instance.getPrecoUnitario();
        assertTrue(expResult==result);
    }

    @Test
    void getId(){
        assertEquals(1,paracetamol.getId());
    }


    @Test
    void getPesoUnitario(){
        assertEquals(180,viagra.getPesoUnitario());
    }

    @Test
    void getIva(){
        assertEquals(2,instance.getIva());
    }


    @Test
    void testeConstrutor(){
        Produto produto= new Produto(1,"pessego",25.3,5,10);
        assertEquals(produto.getNome(),"pessego");
    }

    @Test
    void testeConstrutor2(){
        Produto produto= new Produto(1,"pessego",25.3,5,10);
        assertEquals(produto.getNome(),"pessego");
    }

    @Test
    void testeToString2(){
        Produto produto= new Produto(1,"pessego",25.3,5,10);
        assertEquals("Id=1\nNome:pessego",produto.toString());
    }
}