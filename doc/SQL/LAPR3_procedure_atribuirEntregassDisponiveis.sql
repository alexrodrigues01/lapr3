CREATE OR REPLACE PROCEDURE prcAtribuirEncomendasDisponiveis IS
    idEntregaAux number(10,0);
    emailEstafetaAux varchar2(50);
    nao_ha_entregas exception;
    nao_ha_estafetas exception;
BEGIN
    select id into idEntregaAux from Entrega where idestadoentrega = 3;
    if idEntregaAux is not null then
        select min(emailEstafeta) into emailEstafetaAux from Estafeta where idestadoestafeta = 1;
        if idEntregaAux is not null then
            update Entrega set idEstadoEntrega = 3, emailEstafeta = emailEstafetaAux where id = idEntregaAux;
            update Estafeta set idEstadoestafeta = 2 where emailEstafeta = emailEstafetaAux;
        else
            raise nao_ha_estafetas;
        end if;
    else
        raise nao_ha_entregas;
    end if;   
EXCEPTION
    when nao_ha_entregas then dbms_output.put_line('Nao existem entregas a atribuir');
    when nao_ha_estafetas then dbms_output.put_line('Nao existem estafetas disponiveis');
END;