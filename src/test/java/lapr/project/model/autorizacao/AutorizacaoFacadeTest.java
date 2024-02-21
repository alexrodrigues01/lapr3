package lapr.project.model.autorizacao;

import lapr.project.data.UtilizadorBD;
import lapr.project.model.Utilizador;
import lapr.project.model.autorizacao.model.SessaoUtilizador;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AutorizacaoFacadeTest {

    @Mock
    private UtilizadorBD utilizadorBD;

    AutorizacaoFacade instance;

    @Test
    void doLogin() {
        utilizadorBD = mock(UtilizadorBD.class);
        instance=new AutorizacaoFacade(utilizadorBD);
        SessaoUtilizador sessaoUtilizador = new SessaoUtilizador(new Utilizador("Nome","joao@email.com",123456789,123456789,"pessego",1));
        when(utilizadorBD.procuraUtilizador("joao@email.com")).thenReturn(new Utilizador("Nome","joao@email.com",123456789,123456789,"pessego",1));
        assertEquals(sessaoUtilizador,instance.doLogin("joao@email.com","pessego"));
        instance.doLogout();
    }

    @Test
    void doLoginNull() {
        utilizadorBD = mock(UtilizadorBD.class);
        instance=new AutorizacaoFacade(utilizadorBD);
        when(utilizadorBD.procuraUtilizador("joao@email.com")).thenReturn(null);
        assertNull(instance.doLogin("joao@email.com", "pessego"));
    }

    @Test
    void doLogout(){
        utilizadorBD = mock(UtilizadorBD.class);
        instance=new AutorizacaoFacade(utilizadorBD);
        when(utilizadorBD.procuraUtilizador("joao@email.com")).thenReturn(null);
        SessaoUtilizador sessaoUtilizador=instance.doLogin("joao@email.com","pessego");
        instance.doLogout();
        assertNull(sessaoUtilizador);
    }
}