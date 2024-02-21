package lapr.project.controller;


import lapr.project.data.ClienteBD;
import lapr.project.data.MoradaBD;
import lapr.project.data.UtilizadorBD;
import lapr.project.utils.Constantes;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegistoClienteControllerTest {

    @Mock private ClienteBD clienteBD=mock(ClienteBD.class);
    @Mock private UtilizadorBD utilizadorBD=mock(UtilizadorBD.class);
    @Mock private MoradaBD moradaBD=mock(MoradaBD.class);

    RegistoClienteController instance = new RegistoClienteController(clienteBD,utilizadorBD,moradaBD);


    @Test
    void novoClienteSuccess() {
        when(moradaBD.validaMorada("Rua aaasd", 25.5,90.5,15)).thenReturn(true);
        when(clienteBD.validaCliente("joao@email.com")).thenReturn(true);
        instance.novoCliente("joao@email.com","asddd","Joao",123456789,911234567,"Rua aaasd", 25.5,90.5,15,"1234567891234569","05/25",123);
    }

    @Test
    void novoClienteFailure() {
        String expResult = "Cliente inválido";
        try{
            when(moradaBD.validaMorada("Rua aaasd", 25.5,90.5,15)).thenReturn(true);
            when(clienteBD.validaCliente("joao")).thenReturn(false);
            instance.novoCliente("joao@email.com","asddd","Joao",123456789,911234567,"Rua aaasd", 25.5,90.5,15,"1234567891234569","05/25",123);
        }catch (IllegalArgumentException e){
            assertEquals(expResult,e.getMessage());
        }
    }

    @Test
    void novoClienteFailure2() {
        String expResult = "Morada inválida";
        try {
            when(moradaBD.validaMorada("Rua aaasd", 25.5, 90.5, 15)).thenReturn(false);
            when(clienteBD.validaCliente("joao")).thenReturn(true);
            instance.novoCliente("joao@email.com", "asddd", "Joao", 123456789, 911234567, "Rua aaasd", 25.5, 90.5, 15, "1234567891234569", "05/25", 123);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult, e.getMessage());
        }
    }

    @Test
    void novoClienteFail() {
        IllegalArgumentException exception = new IllegalArgumentException("Endereço de email inválido.");
        try{
            instance.novoCliente("com","asddd","Joao",123456789,911234567,"Rua aaasd", 25.5,90.5,15,"1234567891234569","05/25",123);
        }catch (IllegalArgumentException e){
            assertEquals(exception.getMessage(),e.getMessage());
        }
    }

    @Test
    void novoClienteFail2() {
        IllegalArgumentException exception = new IllegalArgumentException("Latitude inválida");
        try{
            instance.novoCliente("joao@email.com","asddd","Joao",123456789,911234567,"Rua aaasd", 92,90.5,15,"1234567891234569","05/25",123);
        }catch (IllegalArgumentException e){
            assertEquals(exception.getMessage(),e.getMessage());
        }
    }

    @Test
    void novoClienteFail3() {
        IllegalArgumentException exception = new IllegalArgumentException("Número de Cartão Crédito inválido");
        try{
            instance.novoCliente("joao@email.com","asddd","Joao",123456789,911234567,"Rua aaasd", 25.5,90.5,15,"123","05/25",123);
        }catch (IllegalArgumentException e){
            assertEquals(exception.getMessage(),e.getMessage());
        }
    }

    @Test
    void registarClienteSuccess() {
        when(utilizadorBD.novoUtilizador("joao@email.com","asddd","Joao",123456789,911234567, Constantes.PAPEL_CLIENTE,"Rua aaasd", 25.5,90.5,115.0)).thenReturn(true);
        when(clienteBD.registaCliente("joao@email.com","1234567891234569","05/25",123)).thenReturn(true);
        boolean result = instance.registarCliente("joao@email.com","asddd","Joao",123456789,911234567,"Rua aaasd", 25.5,90.5,115.0,"1234567891234569","05/25",123);
        assertTrue(result);
    }

    @Test
    void registarClienteFail() {
        when(utilizadorBD.novoUtilizador("joao@email.com","asddd","Joao",123456789,911234567, Constantes.PAPEL_CLIENTE,"Rua aaasd", 25.5,90.5,115.0)).thenReturn(false);
        when(clienteBD.registaCliente("joao@email.com","1234567891234569","05/25",123)).thenReturn(false);
        boolean result = instance.registarCliente("joao@email.com","asddd","Joao",123456789,911234567,"Rua aaasd", 25.5,90.5,115.0,"1234567891234569","05/25",123);
        assertFalse(result);
    }

    @Test
    void registarClienteFail2() {
        when(utilizadorBD.novoUtilizador("joao@email.com","asddd","Joao",123456789,911234567, Constantes.PAPEL_CLIENTE,"Rua aaasd", 25.5,90.5,115.0)).thenReturn(true);
        when(clienteBD.registaCliente("joao@email.com","1234567891234569","05/25",123)).thenReturn(false);
        boolean result = instance.registarCliente("joao@email.com","asddd","Joao",123456789,911234567,"Rua aaasd", 25.5,90.5,115.0,"1234567891234569","05/25",123);
        assertFalse(result);
    }
}