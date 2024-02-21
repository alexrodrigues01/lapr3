insert into EstadoVeiculo (id,designacao) values (1,'A carregar');
insert into EstadoVeiculo (id,designacao) values (2,'Disponivel');
insert into EstadoVeiculo (id,designacao) values (3,'Ocupada');

insert into EstadoEntrega (id,designacao) values (1,'Por entregar');
insert into EstadoEntrega (id,designacao) values (2,'A entregar');
insert into EstadoEntrega (id,designacao) values (3,'Entregue');

insert into PapelUtilizador (id,designacao) values (1,'PAPEL_GESTOR_FARMACIA');
insert into PapelUtilizador (id,designacao) values (2,'PAPEL_CLIENTE');
insert into PapelUtilizador (id,designacao) values (3,'PAPEL_ESTAFETA');

insert into TipoEstacionamento(id,designacao) values (1,'Scooter');
insert into TipoEstacionamento(id,designacao) values (2,'Drone');

insert into SuporteCarregamento(id,designacao) values (1,'Sem suporte');
insert into SuporteCarregamento (id,designacao) values (2,'Com suporte');

insert into EstadoEstafeta (id,designacao) values (1,'Disponivel');
insert into EstadoEstafeta (id,designacao) values (2,'Ocupado');

insert into TipoNota(id, designacao) values (1, 'Nota Emissora');
insert into TipoNota(id, designacao) values (2, 'Nota Recetora');



insert into Produto(nome, preco_unitario, iva, massa_unitaria) values ('Brufen', 2.3, 15, 100);
insert into Produto(nome, preco_unitario, iva, massa_unitaria) values ('Acido', 20.3, 15, 100);
insert into Produto(nome, preco_unitario, iva, massa_unitaria) values ('Base', 2.3, 15, 100);
insert into Produto(nome, preco_unitario, iva, massa_unitaria) values ('Vacina', 50.3, 15, 100);
insert into Produto(nome, preco_unitario, iva, massa_unitaria) values ('Aspirina', 50.3, 15, 5000);

select * from morada;
select * from caminho;
select * from entrega;
select * from veiculo;
select * from estafeta;
select * from produto;
select * from caminho;
select * from encomenda;
select * from farmacia;
select * from utilizador;
select * from produto_farmacia;
select * from nota;

delete nota;
delete produto_farmacia;
delete linhafatura;
delete fatura;
delete lugarestacionamento;
delete drone;
delete scooter;
delete caminho;
delete parqueestacionamento;
delete linhaencomendaproduto;
delete encomenda;
delete entrega;
delete veiculo;
delete cliente;
delete estafeta;
delete farmacia;
delete gestorfarmacia;
delete utilizador;
delete morada;


