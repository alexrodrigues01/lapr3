package lapr.project.controller;

import lapr.project.data.EstafetaBD;
import lapr.project.data.FarmaciaBD;
import lapr.project.data.MoradaBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.model.Farmacia;
import lapr.project.model.Utilizador;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RegistarEstafetaControllerTest {

    private RegistarEstafetaController instance;
    private FarmaciaBD farmaciaBD;
    private UtilizadorBD utilizadorBD;
    private EstafetaBD estafetaBD;
    private MoradaBD moradaBD;

    @Test
    void validarEstafetaSuccess() {
        farmaciaBD = mock(FarmaciaBD.class);
        utilizadorBD = mock(UtilizadorBD.class);
        estafetaBD = mock(EstafetaBD.class);
        moradaBD = mock(MoradaBD.class);
        instance = new RegistarEstafetaController(farmaciaBD, utilizadorBD, estafetaBD, moradaBD);
        when(estafetaBD.validaEstafeta("teste@email.com",123456789,"12345678987")).thenReturn(true);
        when(moradaBD.validaMorada("rua",55,55,55)).thenReturn(true);
        instance.validarEstafeta("teste@email.com","password","Nome",123456789,123456789,"12345678987",10,"rua",55,55,55);
    }

    @Test
    void validarEstafetaValidaFail() {
        farmaciaBD = mock(FarmaciaBD.class);
        utilizadorBD = mock(UtilizadorBD.class);
        estafetaBD = mock(EstafetaBD.class);
        moradaBD = mock(MoradaBD.class);
        instance = new RegistarEstafetaController(farmaciaBD, utilizadorBD, estafetaBD, moradaBD);
        IllegalArgumentException exception = new IllegalArgumentException("Estafeta inválido");
        try{
            when(moradaBD.validaMorada("rua",55,55,55)).thenReturn(true);
            when(estafetaBD.validaEstafeta("teste@email.com",2,"12345678987")).thenReturn(false);
            instance.validarEstafeta("teste@email.com","password","Nome",123456789,123456789,"12345678987",10,"rua",55,55,55);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(),e.getMessage());
        }
    }

    @Test
    void validarEstafetaValidaFail2() {
        farmaciaBD = mock(FarmaciaBD.class);
        utilizadorBD = mock(UtilizadorBD.class);
        estafetaBD = mock(EstafetaBD.class);
        moradaBD = mock(MoradaBD.class);
        instance = new RegistarEstafetaController(farmaciaBD, utilizadorBD, estafetaBD, moradaBD);
        IllegalArgumentException exception = new IllegalArgumentException("Morada inválida");
        try{
            when(moradaBD.validaMorada("rua",55,55,55)).thenReturn(false);
            when(estafetaBD.validaEstafeta("teste@email.com",2,"12345678987")).thenReturn(false);
            instance.validarEstafeta("teste@email.com","password","Nome",123456789,123456789,"12345678987",10,"rua",55,55,55);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(),e.getMessage());
        }
    }

    @Test
    void validarEstafetaParametrosEstafetaFail() {
        IllegalArgumentException exception = new IllegalArgumentException("Número de Segurança Social inválido");
        try{
            instance = new RegistarEstafetaController(farmaciaBD, utilizadorBD, estafetaBD, moradaBD);
            instance.validarEstafeta("teste@email.com","password","Nome",123456789,123456789,"2",10,"Rua teste",55,55,55);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(),e.getMessage());
        }

        exception = new IllegalArgumentException("Carga Máxima não pode ser igual ou inferior a 0");
        try{
            instance = new RegistarEstafetaController(farmaciaBD, utilizadorBD, estafetaBD, moradaBD);
            instance.validarEstafeta("teste@email.com","password","Nome",123456789,123456789,"12345678987",0,"Rua teste",55,55,55);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(),e.getMessage());
        }
    }

    @Test
    void validarEstafetaParametrosUtilizadorFail() {
        IllegalArgumentException exception = new IllegalArgumentException("Endereço de email inválido.");
        try{
            instance = new RegistarEstafetaController(farmaciaBD, utilizadorBD, estafetaBD, moradaBD);
            instance.validarEstafeta("fail","password","Nome",123456789,123456789,"12345678987",10,"Rua teste",55,55,55);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(),e.getMessage());
        }
    }

    @Test
    void validarEstafetaParametrosMoradaFail() {
        IllegalArgumentException exception = new IllegalArgumentException("Morada inválida");
        try{
            instance = new RegistarEstafetaController(farmaciaBD, utilizadorBD, estafetaBD, moradaBD);
            instance.validarEstafeta("teste@email.com","password","Nome",123456789,123456789,"12345678987",10,"",55,55,55);
        }catch(IllegalArgumentException e){
            assertEquals(exception.getMessage(),e.getMessage());
        }
    }

    @Test
    void registarEstafeta() throws Exception {
        farmaciaBD = mock(FarmaciaBD.class);
        utilizadorBD = mock(UtilizadorBD.class);
        estafetaBD = mock(EstafetaBD.class);
        moradaBD = mock(MoradaBD.class);
        instance = new RegistarEstafetaController(farmaciaBD, utilizadorBD, estafetaBD, moradaBD);

        when(utilizadorBD.procuraUtilizador("teste@email.com")).thenReturn(new Utilizador("teste","teste@email.com",123456789,123456789,"password",1));
        when(farmaciaBD.getFarmaciabyGestor("teste@email.com")).thenReturn(new Farmacia(1,"teste",123456789,123456789,"teste@email.com","teste2@email.com","1"));
        when(estafetaBD.registaEstafeta("teste@email.com",1,"12345678987",10)).thenReturn(true);

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.doLogin("teste@email.com","password");

        boolean result = instance.registarEstafeta("teste@email.com","password","Nome",123456789,123456789,"12345678987",10,"Rua teste",55,55,115.0);
        assertTrue(result);
    }


}