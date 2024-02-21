DROP TABLE Caminho CASCADE CONSTRAINTS;
DROP TABLE Cliente CASCADE CONSTRAINTS;
DROP TABLE Drone CASCADE CONSTRAINTS;
DROP TABLE Encomenda CASCADE CONSTRAINTS;
DROP TABLE Entrega CASCADE CONSTRAINTS;
DROP TABLE EstadoEntrega CASCADE CONSTRAINTS;
DROP TABLE EstadoEstafeta CASCADE CONSTRAINTS;
DROP TABLE EstadoVeiculo CASCADE CONSTRAINTS;
DROP TABLE Estafeta CASCADE CONSTRAINTS;
DROP TABLE Farmacia CASCADE CONSTRAINTS;
DROP TABLE Fatura CASCADE CONSTRAINTS;
DROP TABLE GestorFarmacia CASCADE CONSTRAINTS;
DROP TABLE LinhaEncomendaProduto CASCADE CONSTRAINTS;
DROP TABLE LinhaFatura CASCADE CONSTRAINTS;
DROP TABLE LugarEstacionamento CASCADE CONSTRAINTS;
DROP TABLE Morada CASCADE CONSTRAINTS;
DROP TABLE Nota CASCADE CONSTRAINTS;
DROP TABLE PapelUtilizador CASCADE CONSTRAINTS;
DROP TABLE ParqueEstacionamento CASCADE CONSTRAINTS;
DROP TABLE Produto CASCADE CONSTRAINTS;
DROP TABLE Produto_Farmacia CASCADE CONSTRAINTS;
DROP TABLE Scooter CASCADE CONSTRAINTS;
DROP TABLE SuporteCarregamento CASCADE CONSTRAINTS;
DROP TABLE TipoEstacionamento CASCADE CONSTRAINTS;
DROP TABLE TipoNota CASCADE CONSTRAINTS;
DROP TABLE Utilizador CASCADE CONSTRAINTS;
DROP TABLE Veiculo CASCADE CONSTRAINTS;




CREATE TABLE Caminho (
  id_Entrega    number(10) CONSTRAINT nnCaminhoIdEntrega NOT NULL,
  ordemEntrega  number(10) CONSTRAINT nnOrdemEntrega NOT NULL,
  id_Morada     number(10) CONSTRAINT nnIdMorada NOT NULL,

  PRIMARY KEY (id_Entrega, ordemEntrega)
);

CREATE TABLE Cliente (
  emailCliente varchar2(50)            CONSTRAINT nnClienteEmailCliente NOT NULL,
  nCartCred    number(16)   	         CONSTRAINT nnClienteNCartCred NOT NULL,
  valCartCred  varchar2(255) 	         CONSTRAINT nnClienteValCartCred NOT NULL,
  cvv          number(3)               CONSTRAINT nnClienteCvv NOT NULL,
  creditos	   number(10)	 DEFAULT 0   CONSTRAINT nnClienteCreditos NOT NULL,
  PRIMARY KEY (emailCliente)
);

CREATE TABLE Encomenda (
  id            number(10),
  carga         number(10,3)    CONSTRAINT nnEncomendaCarga NOT NULL,
  emailCliente  varchar2(50) 	  CONSTRAINT nnEncomendaEmailCliente NOT NULL,
  id_Entrega    number(10),
  id_farmacia   number(10)      CONSTRAINT nnEncomendaIdFarmacia NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE Entrega (
  id              number(10),
  cargaTotal      number(10,3)	CONSTRAINT nnEntregaCargaTotal NOT NULL,
  distanciaTotal  number(10,3)  CONSTRAINT nnEntregaDistanciaTotal NOT NULL,
  consumoEstimado number(19,3)  CONSTRAINT nnEntregaConsumoEstimando NOT NULL,
  emailEstafeta   varchar2(50),
  id_estadoEntrega number(10)   CONSTRAINT nnEntregaIdEstadoEntrega NOT NULL,
  id_veiculo      number(10)    CONSTRAINT nnEntregaIdVeiculo NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE EstadoEntrega (
  id         number(10),
  designacao varchar2(255)  CONSTRAINT nnEstadoEntregaDesignacao NOT NULL
                            CONSTRAINT ukEstadoEntragaDesignacao UNIQUE,

  PRIMARY KEY (id)
);

CREATE TABLE EstadoEstafeta (
  id         number(10),
  designacao varchar2(255) CONSTRAINT nnEstadoEstafetaDesignacao NOT NULL
                           CONSTRAINT ukEstadoEstafetaDesignacao UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE EstadoVeiculo (
  id              number(10),
  designacao      varchar2(255) CONSTRAINT nnEstadoScooterDesignacao NOT NULL
                                CONSTRAINT ukEstadoScooterDesignacao UNIQUE,

  PRIMARY KEY (id)
);

CREATE TABLE Estafeta (
  emailEstafeta    varchar2(50)   CONSTRAINT nnEstafetaEmailEstafeta NOT NULL,
  numSegSocial     varchar2(11)   CONSTRAINT nnEstafetaNumSegSocial NOT NULL
                                  CONSTRAINT ukEstafetaNumSegSocial UNIQUE,
  cargaMaxima      number(10,3)	  CONSTRAINT nnEstafetaCargaMaxima NOT NULL,
  id_farmacia      number(10)     CONSTRAINT nnId_farmacia NOT NULL,
  idEstadoEstafeta number(10)     CONSTRAINT nnIdEstadoEstafeta NOT NULL,

  PRIMARY KEY (emailEstafeta)
);

CREATE TABLE Farmacia (
  id          number(10),
  nome        varchar2(255)     CONSTRAINT nnFarmaciaNome         NOT NULL,
  nif         number(9)         CONSTRAINT nnFarmaciaNif          NOT NULL
                                CONSTRAINT ukFarmaciaNif          UNIQUE
                                CONSTRAINT chFarmaciaNIF          CHECK (REGEXP_LIKE(nif, '[0-9]{9}')),
  telefone    number(9)         CONSTRAINT nnFarmaciaTelefone     NOT NULL
                                CONSTRAINT ckFarmaciaTelefone     CHECK (REGEXP_LIKE(telefone, '([0-9]{3,5})?([0-9]{7,11})')),
  email       varchar2(50)      CONSTRAINT nnFarmaciaEmail        NOT NULL
                                CONSTRAINT ukFarmaciaEmail        UNIQUE
                                CONSTRAINT chFarmaciaEmail        CHECK(REGEXP_LIKE(email,'^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$')),
  emailGestor varchar2(50)      CONSTRAINT nnFarmaciaEmailGestor  NOT NULL
                                CONSTRAINT chFarmaciaEmailGestor  CHECK(REGEXP_LIKE(emailGestor,'^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$')),
  id_Morada    number(10)       CONSTRAINT nnFarmaciaMoradaId     NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE Fatura (
  id            number(10),
  data          date            CONSTRAINT nnFaturaData         NOT NULL,
  valorProdutos number(10,2),
  taxaEntrega   number(10,2)    CONSTRAINT nnFaturaTaxaEntrega  NOT NULL,
  desconto      number(10)      CONSTRAINT nnFaturaDesconto     NOT NULL
                                CONSTRAINT chFaturaDesconto     CHECK(desconto>=0),
  nif           number(9)       CONSTRAINT nnFaturaNif          NOT NULL
                                CONSTRAINT chFaturaNIF          CHECK (REGEXP_LIKE(nif, '[0-9]{9}')),
  id_farmacia   number(10)      CONSTRAINT nnFaturaFarmaciaID   NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE GestorFarmacia (
  emailGestor varchar2(50),

  PRIMARY KEY (emailGestor)
);

CREATE TABLE LinhaEncomendaProduto (
  id_encomenda number(10)        CONSTRAINT nnLinhaEncomendaProdutoEncomendaid NOT NULL,
  id_produto   number(10)        CONSTRAINT nnLinhaEncomendaProdutoProdutoId NOT NULL,
  quantidade   number(10)        CONSTRAINT nnLinhaEncomendaProdutoQuantidade NOT NULL,

  PRIMARY KEY (id_encomenda, id_produto)
);

CREATE TABLE LinhaFatura (
  id_fatura              number(10)       CONSTRAINT nnLinhaFaturaIdFatura NOT NULL,
  linha                  number(10)       CONSTRAINT nnLinhaFaturaLinha NOT NULL,
  valor                  number(10,2)     CONSTRAINT nnLinhaFaturaValor NOT NULL,
  quantidade             number(10)       CONSTRAINT nnLinhaFaturaQuantidade NOT NULL,
  preco_unitario         number(10,2)     CONSTRAINT nnLinhaFaturaPrecoUnitario NOT NULL,
  iva            	       number(10)	      CONSTRAINT nnLinhaFaturaIva NOT NULL,
  nomeProduto		         varchar2(255)	  CONSTRAINT nnLinhaFaturaNomeProduto NOT NULL,
  id_encomenda		       number(10)       CONSTRAINT nnLinhaFaturaEncomenda_ProdutoEncomendaid NOT NULL,
  id_produto   		       number(10)       CONSTRAINT nnLinhaFaturaEncomend_PRodutoProdutoid NOT NULL,

  PRIMARY KEY (id_fatura, linha)
);

CREATE TABLE LugarEstacionamento (
  id                        number(10),
  id_suporteCarregamento    number(10)    CONSTRAINT nnLugarEstacionamentoIdSuporteCarregamento NOT NULL,
  id_veiculo                number(10),
  id_parqueEstacionamento   number(10)    CONSTRAINT nnLugarEstacionamentoIdParqueEstacionamento NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE Morada (
  id        number(10)          GENERATED AS IDENTITY,
  morada    varchar2(255)       CONSTRAINT nnMoradaMorada NOT NULL
                                CONSTRAINT ukMoradaMorada UNIQUE,
  latitude  number(18,15)         CONSTRAINT nnMoradaLatitude NOT NULL,
  longitude number(18,15)         CONSTRAINT nnMoradaLongitude NOT NULL,
  altitude  number(10,3)        CONSTRAINT nnMoradaAltitude NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE PapelUtilizador (
  id         number(10) ,
  designacao varchar2(255)  CONSTRAINT nnPapelUtilizadorDesignacao NOT NULL
                            CONSTRAINT ukPapelUtilizadorDesignacao UNIQUE,

  PRIMARY KEY (id)
);

CREATE TABLE ParqueEstacionamento(
  id                    number(10),
  capacidadeEnergia     number(10,3)  CONSTRAINT nnParqueEstacionamentoCapacidadeEnergia NOT NULL,
  id_farmacia           number(10)    CONSTRAINT nnParqueEstacionamentoIdFarmacia NOT NULL,
  id_tipoEstacionamento number(10)    CONSTRAINT nnParqueEstacionamentoIdTipoEstacionamento NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE Produto (
  id             number(10)         GENERATED AS IDENTITY,
  nome           varchar2(255)      CONSTRAINT nnProdutoNome NOT NULL
                                    CONSTRAINT ukProdutoNome UNIQUE,
  preco_unitario number(10,2)       CONSTRAINT nnProdutoPrecoUnitario NOT NULL,
  iva            number(10)	        CONSTRAINT nnProdutoIva NOT NULL,
  massa_unitaria number(10)	        CONSTRAINT nnProdutoMassaUnitaria NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE Produto_Farmacia(
  id_produto  number(10) CONSTRAINT nnProdutoFarmaciaIdProduto NOT NULL,
  id_farmacia number(10) CONSTRAINT nnProdutoFarmaciaIdFarmacia NOT NULL,
  stock       number(10) CONSTRAINT nnProdutoFarmaciaStock NOT NULL,

  PRIMARY KEY (id_produto, id_farmacia)
);

CREATE TABLE Veiculo (
  id                    number(10),
  carga                 number(3)           CONSTRAINT nnScooterCarga NOT NULL,
  capacidadeBateria     number(19, 5)       CONSTRAINT nnScooterCapacidadeBateria NOT NULL,
  consumoHoraBateria    number(10,3)        CONSTRAINT nnScooterConsumoHoraBateria NOT NULL,
  tensaoBateria         number(10,3)        CONSTRAINT nnScooterTensaoBateria NOT NULL,
  massa                 number(10,3)        CONSTRAINT nnScooterMassa NOT NULL,
  potenciaMotor		      number(10,3)	      CONSTRAINT nnScooterPotenciaMotor NOT NULL,
  id_estadoVeiculo      number(10)          CONSTRAINT nnScooterIdEstadoVeiculo NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE Utilizador (
  email     varchar2(50)                CONSTRAINT nnUtilizadorEmail NOT NULL
                                        CONSTRAINT chUtilizadorEmail                CHECK(REGEXP_LIKE(email,'^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$')),
  password  varchar2(32)                CONSTRAINT nnUtilizadorPassword NOT NULL,
  nome      varchar2(255)               CONSTRAINT nnUtilizadorNome NOT NULL,
  nif       number(9)                   CONSTRAINT nnUtilizadorNif NOT NULL
                                        CONSTRAINT chUtilizadorNIF                  CHECK (REGEXP_LIKE(nif, '[0-9]{9}'))
                                        CONSTRAINT ukUtilizadorNIF UNIQUE,
  telefone  number(9)                   CONSTRAINT nnUtilizadorTelefone NOT NULL
                                        CONSTRAINT ckClienteTelefone                CHECK (REGEXP_LIKE(telefone, '([0-9]{3,5})?([0-9]{7,11})')),
  id_morada number(10)                  CONSTRAINT nnUtilizadorIdMorada NOT NULL,
  id_papel  number(10)                  CONSTRAINT nnUtilizadorIDPapel NOT NULL,

  PRIMARY KEY (email)
);

CREATE TABLE TipoEstacionamento (
  id         number(10),
  designacao varchar2(255)    CONSTRAINT nnTipoEstacionamentoDesignacao NOT NULL
                              CONSTRAINT ukTipoEstacionamentoDesignacao UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE Scooter (
  id     number(10)                     CONSTRAINT nnScooterID NOT NULL,
  qrCode varchar2(255)                  CONSTRAINT nnScooterQrCode NOT NULL
                                        CONSTRAINT ukScooterQrCode UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE Drone (
  id          number(10)    CONSTRAINT nnDroneId NOT NULL,
  velocidade  number(10,3)  CONSTRAINT nnDroneVelocidade NOT NULL,
  avionics    number(10,3)  CONSTRAINT nnDroneAvionics NOT NULL,
  drag        number(10,3)  CONSTRAINT nnDroneDrag NOT NULL,
  rotors      number(10,3)  CONSTRAINT nnDroneRotors NOT NULL,
  cargaMaxima number(10,3)  CONSTRAINT nnDroneCargaMaxima NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE Nota (
  id                  number(10),
  quantidade          number(10)        CONSTRAINT nnNotaQuantidade NOT NULL,
  id_farmaciaEmissora number(10)        CONSTRAINT nnNotaIdFarmaciaEmissora NOT NULL,
  id_farmaciaRecetora number(10)        CONSTRAINT nnNotaIdFarmaciaRecetora NOT NULL,
  id_produto          number(10)        CONSTRAINT nnNotaIdProduto NOT NULL,
  id_tipoNota         number(10)        CONSTRAINT nnNotaIdTipoNota NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE TipoNota (
  id         number(10),
  designacao varchar2(255)    CONSTRAINT nnTipoNotaDesignacao NOT NULL
                              CONSTRAINT ukTipoNotaDesignacao UNIQUE,

  PRIMARY KEY (id)
);

CREATE TABLE SuporteCarregamento (
  id         number(10),
  designacao varchar2(255)    CONSTRAINT nnSuporteCarregamentoDesignacao NOT NULL
                              CONSTRAINT ukSuporteCarregamentoDesignacao UNIQUE,

  PRIMARY KEY (id)
);

ALTER TABLE Utilizador ADD CONSTRAINT FKUtilizador15966 FOREIGN KEY (id_morada) REFERENCES Morada (id);
ALTER TABLE LugarEstacionamento ADD CONSTRAINT FKLugarEstac757385 FOREIGN KEY (id_suporteCarregamento) REFERENCES SuporteCarregamento (id);
ALTER TABLE LinhaFatura ADD CONSTRAINT FKLinhaFatur704327 FOREIGN KEY (id_fatura) REFERENCES Fatura (id);
ALTER TABLE Estafeta ADD CONSTRAINT FKEstafeta651774 FOREIGN KEY (emailEstafeta) REFERENCES Utilizador (email);
ALTER TABLE Encomenda ADD CONSTRAINT FKEncomenda560905 FOREIGN KEY (emailCliente) REFERENCES Cliente (emailCliente);
ALTER TABLE Cliente ADD CONSTRAINT FKCliente498238 FOREIGN KEY (emailCliente) REFERENCES Utilizador (email);
ALTER TABLE GestorFarmacia ADD CONSTRAINT FKGestorFarm800630 FOREIGN KEY (emailGestor) REFERENCES Utilizador (email);
ALTER TABLE LinhaEncomendaProduto ADD CONSTRAINT FKLinhaEncom760665 FOREIGN KEY (id_encomenda) REFERENCES Encomenda (id);
ALTER TABLE LinhaEncomendaProduto ADD CONSTRAINT FKLinhaEncom520593 FOREIGN KEY (id_produto) REFERENCES Produto (id);
ALTER TABLE LinhaFatura ADD CONSTRAINT FKLinhaFatur660899 FOREIGN KEY (id_encomenda, id_produto) REFERENCES LinhaEncomendaProduto (id_encomenda, id_produto);
ALTER TABLE Estafeta ADD CONSTRAINT FKEstafeta549278 FOREIGN KEY (id_farmacia) REFERENCES Farmacia (id);
ALTER TABLE Farmacia ADD CONSTRAINT FKFarmacia416881 FOREIGN KEY (emailGestor) REFERENCES GestorFarmacia (emailGestor);
ALTER TABLE Farmacia ADD CONSTRAINT FKFarmacia658207 FOREIGN KEY (id_morada) REFERENCES Morada (id);
ALTER TABLE Veiculo ADD CONSTRAINT FKVeiculo651903 FOREIGN KEY (id_estadoVeiculo) REFERENCES EstadoVeiculo (id);
ALTER TABLE Entrega ADD CONSTRAINT FKEntrega607145 FOREIGN KEY (id_estadoEntrega) REFERENCES EstadoEntrega (id);
ALTER TABLE Estafeta ADD CONSTRAINT FKEstafeta951637 FOREIGN KEY (idEstadoEstafeta) REFERENCES EstadoEstafeta (id);
ALTER TABLE Fatura ADD CONSTRAINT FKFatura376421 FOREIGN KEY (id_farmacia) REFERENCES Farmacia (id);
ALTER TABLE Utilizador ADD CONSTRAINT FKUtilizador373384 FOREIGN KEY (id_papel) REFERENCES PapelUtilizador (id);
ALTER TABLE LugarEstacionamento ADD CONSTRAINT FKLugarEstac93199 FOREIGN KEY (id_veiculo) REFERENCES Veiculo (id);
ALTER TABLE Entrega ADD CONSTRAINT FKEntrega260328 FOREIGN KEY (emailEstafeta) REFERENCES Estafeta (emailEstafeta);
ALTER TABLE Encomenda ADD CONSTRAINT FKEncomenda19900 FOREIGN KEY (id_Entrega) REFERENCES Entrega (id);
ALTER TABLE Caminho ADD CONSTRAINT FKCaminho9555 FOREIGN KEY (id_entrega) REFERENCES Entrega (id);
ALTER TABLE Caminho ADD CONSTRAINT FKCaminho215945 FOREIGN KEY (id_Morada) REFERENCES Morada (id);
ALTER TABLE Entrega ADD CONSTRAINT FKEntrega751831 FOREIGN KEY (id_veiculo) REFERENCES Veiculo (id);
ALTER TABLE Drone ADD CONSTRAINT FKDrone798966 FOREIGN KEY (id) REFERENCES Veiculo (id);
ALTER TABLE Scooter ADD CONSTRAINT FKScooter104907 FOREIGN KEY (id) REFERENCES Veiculo (id);
ALTER TABLE Nota ADD CONSTRAINT FKNota15844 FOREIGN KEY (id_farmaciaEmissora) REFERENCES Farmacia (id);
ALTER TABLE Nota ADD CONSTRAINT FKNota975349 FOREIGN KEY (id_farmaciaRecetora) REFERENCES Farmacia (id);
ALTER TABLE Nota ADD CONSTRAINT FKNota500925 FOREIGN KEY (id_produto) REFERENCES Produto (id);
ALTER TABLE Nota ADD CONSTRAINT FKNota79076 FOREIGN KEY (id_tipoNota) REFERENCES TipoNota (id);
ALTER TABLE Produto_Farmacia ADD CONSTRAINT FKProduto_Fa949954 FOREIGN KEY (id_produto) REFERENCES Produto (id);
ALTER TABLE Produto_Farmacia ADD CONSTRAINT FKProduto_Fa834571 FOREIGN KEY (id_farmacia) REFERENCES Farmacia (id);
ALTER TABLE Encomenda ADD CONSTRAINT FKEncomenda349663 FOREIGN KEY (id_farmacia) REFERENCES Farmacia (id);
ALTER TABLE ParqueEstacionamento ADD CONSTRAINT FKParqueEsta468623 FOREIGN KEY (id_farmacia) REFERENCES Farmacia (id);
ALTER TABLE ParqueEstacionamento ADD CONSTRAINT FKParqueEsta200621 FOREIGN KEY (id_tipoEstacionamento) REFERENCES TipoEstacionamento (id);
ALTER TABLE LugarEstacionamento ADD CONSTRAINT FKLugarEstac990560 FOREIGN KEY (id_parqueEstacionamento) REFERENCES ParqueEstacionamento (id);
