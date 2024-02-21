package lapr.project.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidarParametrosTest {

    @Test
    void validarParametrosUtilizadorEmailFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Endereço de email inválido.");
        try {
            ValidarParametros.validarParametrosUtilizador("788", "password", "Nome", 123456789, 912345678);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosUtilizadorPasswordFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Password inválida.");
        try {
            ValidarParametros.validarParametrosUtilizador("teste@email.com", "", "Nome", 123456789, 912345678);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosUtilizadorNomeFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Nome inválido.");
        try {
            ValidarParametros.validarParametrosUtilizador("teste@email.com", "password", "55", 123456789, 912345678);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosUtilizadorNomeFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("Nome inválido.");
        try {
            ValidarParametros.validarParametrosUtilizador("teste@email.com", "password", "", 123456789, 912345678);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosUtilizadorNifFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("NIF inválido");
        try {
            ValidarParametros.validarParametrosUtilizador("teste@email.com", "password", "Nome", 55, 912345678);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosUtilizadorTelefoneFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Número de telefone inválido");
        try {
            ValidarParametros.validarParametrosUtilizador("teste@email.com", "password", "Nome", 123456789, 55);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosUtilizadorSuccess() {
        ValidarParametros.validarParametrosUtilizador("teste@email.com", "password", "Nome", 123456789, 912345678);
    }

    @Test
    void validarParametrosEstafetaFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Número de Segurança Social inválido");
        try {
            ValidarParametros.validarParametrosEstafeta("55",10);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }

        expResult = new IllegalArgumentException("Carga Máxima não pode ser igual ou inferior a 0");
        try {
            ValidarParametros.validarParametrosEstafeta("12345678987",0);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }

        try {
            ValidarParametros.validarParametrosEstafeta("12345678987",-45);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosEstafetaSuccess() {
        ValidarParametros.validarParametrosEstafeta("12345678987",50);
    }

    @Test
    void validaParametrosMoradaLongitudeFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Longitude inválida");
        try {
            ValidarParametros.validaParametrosMorada("Rua teste", 1000, 55);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosMoradaLatitudeFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Latitude inválida");
        try {
            ValidarParametros.validaParametrosMorada("Rua teste", 55, 1000);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosMoradaMoradaFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Morada inválida");
        try {
            ValidarParametros.validaParametrosMorada("", 55, 55);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosMoradaSuccess() {
        ValidarParametros.validaParametrosMorada("Rua teste", 55, 55);
    }

    @Test
    void validaParametrosFarmaciaEmailFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Endereço de email inválido.");
        try {
            ValidarParametros.validaParametrosFarmacia("Nome", 123456789, 123456789, "teste");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosFarmaciaNomeFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Nome inválido.");
        try {
            ValidarParametros.validaParametrosFarmacia("55", 123456789, 123456789, "teste@email.com");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosFarmaciaNomeFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("Nome inválido.");
        try {
            ValidarParametros.validaParametrosFarmacia("", 123456789, 123456789, "teste@email.com");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosFarmaciaNifFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("NIF inválido");
        try {
            ValidarParametros.validaParametrosFarmacia("Nome", 55, 123456789, "teste@email.com");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosFarmaciaTelefoneFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Número de telefone inválido");
        try {
            ValidarParametros.validaParametrosFarmacia("Nome", 123456789, 55, "teste@email.com");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosFarmaciaSuccess() {
        ValidarParametros.validaParametrosFarmacia("Nome", 123456789, 123456789, "teste@email.com");
    }

    @Test
    void validaParametrosScooterCargaFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Carga inválida");
        try {
            ValidarParametros.validaParametrosScooter("", "300", "1", "20", "40", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterCargaFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("Carga inválida");
        try {
            ValidarParametros.validaParametrosScooter("-1", "300", "1", "20", "40", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterCargaFail3() {
        IllegalArgumentException expResult = new IllegalArgumentException("Carga inválida");
        try {
            ValidarParametros.validaParametrosScooter("101", "300", "1", "20", "40", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterCapacidadeFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Capacidade da bateria inválida");
        try {
            ValidarParametros.validaParametrosScooter("40", "-1", "1", "20", "40", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterCapacidadeFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("Capacidade da bateria inválida");
        try {
            ValidarParametros.validaParametrosScooter("40", "0", "1", "20", "40", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterCapacidadeFail3() {
        IllegalArgumentException expResult = new IllegalArgumentException("Capacidade da bateria inválida");
        try {
            ValidarParametros.validaParametrosScooter("40", "", "1", "20", "40", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterEstadoScooterIdFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Id do estado da scooter inválido");
        try {
            ValidarParametros.validaParametrosScooter("40", "300", "", "20", "40", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterEstadoScooterIdFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("Id do estado da scooter inválido");
        try {
            ValidarParametros.validaParametrosScooter("40", "300", "-1", "20", "40", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterEstadoScooterIdFail3() {
        IllegalArgumentException expResult = new IllegalArgumentException("Id do estado da scooter inválido");
        try {
            ValidarParametros.validaParametrosScooter("40", "300", "0", "20", "40", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterConsumoHoraBateriaFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Consumo da bateria inválido");
        try {
            ValidarParametros.validaParametrosScooter("40", "300", "1", "", "40", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterConsumoHoraBateriaFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("Consumo da bateria inválido");
        try {
            ValidarParametros.validaParametrosScooter("40", "300", "1", "-20", "40", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterTensaoBateriaFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Tensão da bateria inválida");
        try {
            ValidarParametros.validaParametrosScooter("40", "300", "1", "20", "", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterTensaoBateriaFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("Tensão da bateria inválida");
        try {
            ValidarParametros.validaParametrosScooter("40", "300", "1", "20", "-40", "100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterPesoFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Peso da scooter inválido");
        try {
            ValidarParametros.validaParametrosScooter("40", "300", "1", "20", "40", "", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterPesoFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("Peso da scooter inválido");
        try {
            ValidarParametros.validaParametrosScooter("40", "300", "1", "20", "40", "-100", "30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterPotenciaMotorFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Potencia do motor da scooter inválida");
        try {
            ValidarParametros.validaParametrosScooter("40", "300", "1", "20", "40", "100", "");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterPotenciaMotorFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("Potencia do motor da scooter inválida");
        try {
            ValidarParametros.validaParametrosScooter("40", "300", "1", "20", "40", "100", "-30");
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validaParametrosScooterSuccess() {
        ValidarParametros.validaParametrosScooter("40", "300", "1", "20", "40", "100", "30");
    }

    @Test
    void validaParametrosScooterSuccess2() {
        ValidarParametros.validaParametrosScooter("0", "0.1", "1", "0", "0", "0", "0");
    }

    @Test
    void validaParametrosScooterSuccess3() {
        ValidarParametros.validaParametrosScooter("100", "1", "1", "0", "0", "0", "0");
    }

    @Test
    void validarParametrosProdutoNomeFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Nome não pode ser vazio.");
        try {
            ValidarParametros.validarParametrosProduto("", 55, 55, 6);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosProdutoPrecoUnitarioFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("O preço não pode ser menor ou igual a 0.");
        try {
            ValidarParametros.validarParametrosProduto("Nome", -55, 55, 6);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosProdutoPrecoUnitarioFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("O preço não pode ser menor ou igual a 0.");
        try {
            ValidarParametros.validarParametrosProduto("Nome", 0, 55, 6);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosProdutoPesoUnitarioFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("O peso não pode ser menor ou igual a 0.");
        try {
            ValidarParametros.validarParametrosProduto("Nome", 55, -2,6);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosProdutoIvaFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Iva inválido.");
        try {
            ValidarParametros.validarParametrosProduto("Nome", 55, 138,-1);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosProdutoIvaFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("Iva inválido.");
        try {
            ValidarParametros.validarParametrosProduto("Nome", 55, 138, 101);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosProdutoSuccess() {
        ValidarParametros.validarParametrosProduto("Nome", 1,1,0);
        ValidarParametros.validarParametrosProduto("Nome", 1,1,100);
    }

    @Test
    void validarParametrosClienteNrCartCredFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Número de Cartão Crédito inválido");
        try {
            ValidarParametros.validarParametrosCliente("0222222222222222", "02/21", 555);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosClienteValCartCredFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("Validade do Cartão Crédito inválido.");
        try {
            ValidarParametros.validarParametrosCliente("1222222222222222", "teste", 555);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosClienteValCartCredFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("Validade do Cartão Crédito inválido.");
        try {
            ValidarParametros.validarParametrosCliente("1222222222222222", "", 555);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosClienteCVVFail() {
        IllegalArgumentException expResult = new IllegalArgumentException("CVV inválido.");
        try {
            ValidarParametros.validarParametrosCliente("1222222222222222", "02/21", 5);
        } catch (IllegalArgumentException e) {
            assertEquals(expResult.getMessage(), e.getMessage());
        }
    }

    @Test
    void validarParametrosClienteSuccess() {
        ValidarParametros.validarParametrosCliente("1222222222222222", "02/21", 555);
    }

    @Test
    void validaParametrosLugarDroneSuccess1() {
        ValidarParametros.validaParametrosLugarEstacionamento(10, 5,200);
    }

    @Test
    void validaParametrosLugarDroneSuccess2() {
        ValidarParametros.validaParametrosLugarEstacionamento(0, 0,0);
    }

    @Test
    void validaParametrosLugarDroneFail1() {
        IllegalArgumentException expResult = new IllegalArgumentException("Número de lugares de estacionamento sem carregamento inválido");
        try {
            ValidarParametros.validaParametrosLugarEstacionamento(-10, 5,200);
        } catch (IllegalArgumentException result) {
            assertEquals(expResult.getMessage(), result.getMessage());
        }
    }

    @Test
    void validaParametrosLugarDroneFail2() {
        IllegalArgumentException expResult = new IllegalArgumentException("Número de lugares de estacionamento com carregamento inválido");
        try {
            ValidarParametros.validaParametrosLugarEstacionamento(10, -5,200);
        } catch (IllegalArgumentException result) {
            assertEquals(expResult.getMessage(), result.getMessage());
        }
    }

    @Test
    void validaParametrosLugarDroneFail3() {
        IllegalArgumentException expResult = new IllegalArgumentException("Capacidade de energia do parque inválida");
        try {
            ValidarParametros.validaParametrosLugarEstacionamento(10, 5,-200);
        } catch (IllegalArgumentException result) {
            assertEquals(expResult.getMessage(), result.getMessage());
        }
    }

    @Test
    void validaParametroNif(){
        //sucesso
        try{
            ValidarParametros.validaParametroNIF(123456789);
        }catch (Exception e){
            assertTrue(false);
        }

        try{
            ValidarParametros.validaParametroNIF(1);
        }catch (Exception e){
            assertEquals("NIF inválido", e.getMessage());
        }
    }
}