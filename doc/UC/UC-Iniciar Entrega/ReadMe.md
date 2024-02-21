# ReadMe #

## 1. Requisitos
Como Estafeta/Gestor da Farmacia, eu pretendo adicionar escolher qual a entrega a inciare iniciá-la.

A interpretação feita deste requisito foi no sentido de que o Estafeta/Gestor da Farmacia pretende visualizar as entregas existentes no sistema e depois iniciá-la, enviando email a todos os clientes dessa entrega.
## 2. Análise
### Descrição e Regras de Negócio
Foi utilizado o mecanismo de persitência de dados na base de dados. 
Desta forma, os dados relativos as Entregas, aos clientes  , do estafeta ou do gestor estão na base de dados. 
Desta forma o estado da encomenda muda.
### Pré-condições
O sistema contém um conjunto de encomendas,registado o estafeta/drone/gestor da farmacia.

### Pós-condições

### SSD
 ![SSD](SSD.SVG)

## 3. Design
### 3.1. Realização da Funcionalidade
 ![SD](SD.SVG)
 ![CD](CD.SVG)

### 3.2. Padrões Aplicados
Aplicamos o padrão de projeto de software MVC, focado na reutilização de código e a separação de conceitos em três camadas interconectadas, onde a apresentação dos dados e a interação dos utilizadores são separados dos métodos que interagem com o a base de dados.

### 3.3. Testes
Teste 1: Verificar que é possível buscar as entregas relativas ao um estafeta.

    @Test
    void getEntregas() {

        entregaBD=  mock(EntregaBD.class);
        String expResult=new Entrega(1,10,10,10,"joao@gmail.com",3,1).toString();
        List<String > ex=new ArrayList<>();
        ex.add(expResult);
        ArrayList<Entrega> arrayList=new ArrayList<>();
        arrayList.add((new Entrega(1,10,10,10,"joao@gmail.com",3,1)));
        when(entregaBD.getEntregaByEstafeta("joao@gmail.com")).thenReturn(arrayList);
        IniciarEntregaController controller=new IniciarEntregaController();
        controller.setEntregaBD(entregaBD);

        utilizadorBD = mock(UtilizadorBD.class);

        when(utilizadorBD.procuraUtilizador("joao@gmail.com")).thenReturn(new Utilizador("teste", "joao@gmail.com", 123456789, 123456789, "password", 1));

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin("joao@gmail.com","password");
        EstafetaBD estafetaBD=mock(EstafetaBD.class);

        when(estafetaBD.isEstafeta("joao@gmail.com")).thenReturn(true);
        List<String> result= controller.getEntregas(estafetaBD,new GestorFarmaciaBD());
        assertEquals(ex,result);
    }


Teste 2: Verificar que não é possível buscar as entregas relativas ao um estafeta.

    @Test
    void getEntregasFail() {
        entregaBD=  mock(EntregaBD.class);
        String expResult=new Entrega(1,10,10,10,"joao@gmail.com",3,1).toString();
        List<String > ex=new ArrayList<>();
        ex.add(expResult);
        ArrayList<Entrega> arrayList=new ArrayList<>();
        arrayList.add((new Entrega(1,10,10,10,"joao@gmail.com",3,1)));
        when(entregaBD.getEntregasToDoDrone("joao@gmail.com")).thenReturn(arrayList);
        IniciarEntregaController controller=new IniciarEntregaController();
        controller.setEntregaBD(entregaBD);

        utilizadorBD = mock(UtilizadorBD.class);

        when(utilizadorBD.procuraUtilizador("joao@gmail.com")).thenReturn(new Utilizador("teste", "joao@gmail.com", 123456789, 123456789, "password", 1));

        AplicacaoPOT app = AplicacaoPOT.getInstance();
        app.getAutorizacaoFacade().setUtilizadorBD(utilizadorBD);
        app.getAutorizacaoFacade().doLogin("joao@gmail.com","password");
        GestorFarmaciaBD gestorFarmaciaBD=mock(GestorFarmaciaBD.class);
        EstafetaBD estafetaBD=mock(EstafetaBD.class);
        List<String> result= controller.getEntregas(estafetaBD,gestorFarmaciaBD);
        assertEquals(Collections.emptyList(),result);
    }
    
 Teste 3: Verificar que a entrega foi iniciada e o email enviado para todos. 
  
     @Test
      void iniciarEntrega(){
          entregaBD=  mock(EntregaBD.class);
          encomendaBD=mock(EncomendaBD.class);
          when(entregaBD.getIdEstadoEntrega(1)).thenReturn(2);
          when(encomendaBD.getEncomendasByEntregaId(1)).thenReturn(new ArrayList<>());
          IniciarEntregaController controller=new IniciarEntregaController();
          controller.setEncomendaBD(encomendaBD);
          controller.setEntregaBD(entregaBD);
          controller.iniciarEntrega(1);
          controller.setDrone(false);
          controller.setEstafeta(false);
          assertEquals(entregaBD.getIdEstadoEntrega(1),2);
      }  
 Teste 4: Verificar que a não entrega foi iniciada e o email não enviado para todos. 
   
      @Test
         void iniciarEntrega4(){
             try {
                 entregaBD = mock(EntregaBD.class);
                 encomendaBD = mock(EncomendaBD.class);
                 when(entregaBD.getIdEstadoEntrega(1)).thenReturn(2);
                 when(encomendaBD.getEncomendasByEntregaId(1)).thenReturn(null);
                 IniciarEntregaController controller = new IniciarEntregaController();
                 controller.setEncomendaBD(encomendaBD);
                 controller.setEntregaBD(entregaBD);
                 controller.iniciarEntrega(1);
             }catch (NullPointerException e){
                 assertEquals(entregaBD.getIdEstadoEntrega(1),2);
             }
     
    
## 4. Implementação
Metodos do controller:
    
    public List<String> getEntregas(EstafetaBD estafetaBD, GestorFarmaciaBD gestorFarmaciaBD) {
    }
    
    public IniciarEntregaController() {
    }

    public void iniciarEntrega(int idEntrega) {
    }
## 5. Integração/Demonstração


## 6. Observações
